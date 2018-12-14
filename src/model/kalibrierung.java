package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import view.robotGUI;



public class Kalibrierung {
	private int cnt=2;
	
	private Robot robot;
	private TrackingSystem track;
	private robotGUI gui;
	
	ArrayList <Matrix> Rob_matrices = new ArrayList<Matrix>();
	ArrayList <Matrix> TS_matrices = new ArrayList<Matrix>();
	
	public Kalibrierung(TrackingSystem track, Robot rob,robotGUI gui) {
		
		this.gui=gui;
		this.robot = rob;
		this.track = track;

	}
	
	public void calibrate() {
		
		System.out.println("Calibration started...");
		this.robot.setAdeptSpeed(5);
		
		for(int i = 0; i<this.cnt; i++) {
			
			boolean success=false;
			while(!success) {
				Matrix randMat = randMatrix();
				success=this.robot.moveHomRowWise(randMat);
			}	
			
			
			Rob_matrices.add(randMatrix());
			System.out.println("added...");
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			*/
			this.gui.getMatrixView().setMatrix(this.robot.getRobotPosition());
			this.gui.getMatrixView().updateView();
			/*
			try {
				TS_matrices.set(i, new Matrix(track.getNextValue(),4));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			*/
			
		}
		//TODO:solve
	}
	
	public void setMeasureCount(int c) {
		this.cnt=Math.abs(c);
	}	
	
	public Matrix randMatrix() {
		
		double alpha = ThreadLocalRandom.current().nextInt(30, 180 + 1);
		double beta = ThreadLocalRandom.current().nextInt(30, 180 + 1);
		double gamma = ThreadLocalRandom.current().nextInt(30, 180 + 1);
		
		double xpos = ThreadLocalRandom.current().nextInt(-500, 500 + 1);
		double ypos = ThreadLocalRandom.current().nextInt(200, 500 + 1);
		double zpos = ThreadLocalRandom.current().nextInt(100, 500 + 1);
		
		return this.createHomMatrix(xpos, ypos, zpos, alpha, beta, gamma);
	}
	
	public Matrix createHomMatrix(double posx,double posy,double posz,double xangle,double yangle,double zangle) {
		
		Matrix rx = new Matrix(new double[] {1,0,0,0,
											0,Math.cos(xangle),-Math.sin(xangle),0,
											0,Math.sin(xangle),Math.cos(xangle),0},4);
		
		Matrix ry = new Matrix(new double[] {Math.cos(yangle),0,Math.sin(yangle),0,
											0,1,0,0,
											-Math.sin(yangle),0,Math.cos(yangle),0},4);
		
		Matrix rz = new Matrix(new double[] {Math.cos(zangle),-Math.sin(zangle),0,0,
											Math.sin(zangle),Math.cos(zangle),0,0,
											0,0,1,0},4);
		
		Matrix rot = rz.multiply(ry).multiply(rx);
		
		Matrix translation = new Matrix(new double[]{0,0,0,posx,
													0,0,0,posy,
													0,0,0,posz,
													0,0,0,1},4);
		
		return translation.add(rot);
	}
}
