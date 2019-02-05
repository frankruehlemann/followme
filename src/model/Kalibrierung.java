package model;

import com.mathworks.engine.*;

import main.CONSTANTS;

import java.io.StringWriter;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import view.TrackSysGUI;
import view.robotGUI;

public class Kalibrierung {
	private int cnt=1;
	
	private Robot robot;
	private TrackingSystem track;
	private robotGUI robgui;
	private TrackSysGUI tsgui;
	private Matrix xMatrix;
	private Matrix yMatrix;
	
	ArrayList <Matrix> Rob_matrices = new ArrayList<Matrix>();
	ArrayList <Matrix> TS_matrices = new ArrayList<Matrix>();
	
	public Kalibrierung(TrackSysGUI tsgui, robotGUI robgui) {
	
		this.robgui=robgui;
		this.tsgui=tsgui;
		this.robot = robgui.getRobot();
		this.track = tsgui.getTrackSys();

	}
	
	public void calibrate()throws Exception {		
		this.Rob_matrices.clear();
		this.TS_matrices.clear();
		
		//TODO verify if its possible to calibrate
		
		System.out.println("Calibration started...");
		this.robot.setAdeptSpeed(25);
		this.robgui.getMatrixView().setMatrix(randMatrix());
		this.robot.moveHomRowWise(CONSTANTS.initPos);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i<this.cnt; i++) {
			
			boolean success=false;
			Matrix randMat = randMatrix();
			while(!success) {
				randMat = randMatrix();
				this.robgui.getMatrixView().setMatrix(randMat);
				success=this.robot.moveHomRowWise(randMat);
			}	

			
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			Matrix currentPos = this.robot.getRobotPosition();
			
			this.robgui.getMatrixView().setMatrix(currentPos);
			
			Rob_matrices.add(currentPos);
			System.out.println("added...");
			
			try {
				Matrix matrix = new Matrix(track.getNextValue(),4);
				this.tsgui.getMatrixView().setMatrix(matrix);
				
				TS_matrices.add(i, matrix);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
		
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		this.robot.moveHomRowWise(CONSTANTS.initPos);	
		this.solve();		
		Follow follow = new Follow(this.xMatrix,this.yMatrix,this.track,this.robot);

		
	}
	
	public void setMeasureCount(int c) {
		this.cnt=Math.abs(c);
	}	
	
	public void solve()throws Exception{
		        MatlabEngine eng = MatlabEngine.startMatlab();
		        
		        eng.eval("C = zeros(12*"+Integer.toString(this.Rob_matrices.size())+",24);");
		        eng.eval("b = zeros(12*"+Integer.toString(this.Rob_matrices.size())+",1);");
		        
		        for(int k=0;k<this.Rob_matrices.size();k++){
		        
			        eng.eval("M = "+this.Rob_matrices.get(k).toMatlabMatrix());
			        eng.eval("invM = inv(M)");
			        eng.eval("rotM = invM(1:3,1:3)");
			        eng.eval("transM=invM(1:3,4);");
			        
			        eng.eval("N = "+this.TS_matrices.get(k).toMatlabMatrix());
			        eng.eval("Ident = -eye(12);");
			        
			        
			        for(int i=0;i<3;i++){
			        	for(int j=0;j<3;j++){
			        		eng.eval("temp=rotM*N("+Integer.toString(j+1)+","+Integer.toString(i+1)+");");
			        		eng.eval("C("+Integer.toString(k*12+i*3+1)+":"+Integer.toString(k*12+i*3+3)
			        		+","+Integer.toString(j*3+1)+":"+Integer.toString(j*3+3)+")=temp;");
			        	}
			        }
			        
			        
			        eng.eval("C("+Integer.toString(12*(k+1)-2)+":"+Integer.toString(12*(k+1))+",10:12)=rotM;");
			        
					eng.eval("C("+Integer.toString(12*k+1)+":"+Integer.toString(12*(k+1))+",13:24)=Ident;");
					
					//eng.eval("b(10*"+Integer.toString(k+1)+":10*"+Integer.toString(k+1)+"+2)=transM;");
					eng.eval("b("+Integer.toString(12*(k+1)-2)+":"+Integer.toString(12*(k+1))+")=transM;");
		        }
				
				eng.eval("disp(b)");
				eng.eval("disp(C);");
				eng.eval("w = C\\b");
				//X-Matrix
				double[] x = new double[12];
				eng.eval("x = w(1:12);");
				//Y- Matrix
				double[] y = new double[12];
				eng.eval("y = w(13:end);");
				x = eng.getVariable("x");  
				y = eng.getVariable("y");
				xMatrix = new Matrix(x,4);
				yMatrix = new Matrix(y,4);

	}

	public Matrix randMatrix() {
		
		double alpha = ThreadLocalRandom.current().nextInt(-40, -10)*Math.PI/180.0;
		double beta = -ThreadLocalRandom.current().nextInt(80, 100)*Math.PI/180.0;
		double gamma = ThreadLocalRandom.current().nextInt(-30, -10)*Math.PI/180.0;
		
		double xpos = ThreadLocalRandom.current().nextInt(300, 700 + 1);
		double ypos = ThreadLocalRandom.current().nextInt(-300, 300);
		double zpos = ThreadLocalRandom.current().nextInt(300, 600 + 1);
		
		return this.createHomMatrix(xpos, ypos, zpos, alpha, beta, gamma);
	}
	
	public Matrix createHomMatrix(double posx,double posy,double posz,double xangle,double yangle,double zangle) {
		
		Matrix rx = new Matrix(new double[] {1,0,0,0,
											0,Math.cos(xangle),-Math.sin(xangle),0,
											0,Math.sin(xangle),Math.cos(xangle),0},4);
		
		Matrix rz = new Matrix(new double[] {Math.cos(zangle),-Math.sin(zangle),0,0,
											Math.sin(zangle),Math.cos(zangle),0,0,
											0,0,1,0},4);
		
		Matrix ry = new Matrix(new double[] {Math.cos(yangle),0,Math.sin(yangle),0,
											0,-1,0,0,
											-Math.sin(yangle),0,Math.cos(yangle),0},4);
		
		
		
		Matrix rot = rz.multiply(ry).multiply(rx);
		
		Matrix translation = new Matrix(new double[]{1,0,0,posx,
													0,1,0,posy,
													0,0,1,posz,
													0,0,0,1},4);
		
		Matrix res = translation.multiply(rot);
		
		return res;
	}
	
	public robotGUI getRobgui() {
		return robgui;
	}

	public TrackSysGUI getTsgui() {
		return tsgui;
	}

	public Matrix getxMatrix() {
		return xMatrix;
	}

	public Matrix getyMatrix() {
		return yMatrix;
	}
	
}
