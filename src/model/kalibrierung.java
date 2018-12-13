package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import view.robotGUI;



public class Kalibrierung {
	private int n=5;
	
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
		
		for(int i = 0; i<this.n; i++) {
			Rob_matrices.add(new Matrix(randMatrix()));
			
			this.robot.moveHomRowWise(this.Rob_matrices.get(i));
			
			
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.gui.getMatrixView().setMatrix(this.robot.getRobotPosition());
			
			try {
				TS_matrices.set(i, new Matrix(track.getNextValue(),4));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		//TODO:solve
	}
	
	public void setMeasureCount(int c) {
		this.n=Math.abs(c);
	}	
	
	public double[][] randMatrix() {
		double[][] m = new double[3][4];
		for(int i=0; i<3;i++) {
			for(int j=0;j<4; j++) {
				//Translationsteil
				if(j==3) {
					if(i==0) {
						m[i][j] = ThreadLocalRandom.current().nextInt(-500, 500 + 1);
						System.out.println("m "+i+" "+j+": "+m[i][j]);
					}
					if(i==1) {
						m[i][j] = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
						System.out.println("m "+i+" "+j+": "+m[i][j]);
					}
					if(i==2) {
						m[i][j] = ThreadLocalRandom.current().nextInt(-500, 500 + 1);
						System.out.println("m "+i+" "+j+": "+m[i][j]);
					}
				}else {
					m[i][j] = ThreadLocalRandom.current().nextInt(1, 300 + 1);
					System.out.println("m "+i+" "+j+": "+m[i][j]);
				}
			}
		}
		return m;
	}
	
}
