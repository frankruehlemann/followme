package model;

import java.io.IOException;

import main.CONSTANTS;

public class Follow {
	private Matrix xMatrix;
	private Matrix yMatrix;
	private TrackingSystem ts; 
	private Robot robot;
	
	public Follow(Matrix xMatrix, Matrix yMatrix, TrackingSystem ts,Robot robot ){
		this.xMatrix = xMatrix;
		this.yMatrix = yMatrix;
		this.ts=ts;
		this.robot=robot;		
	}
	
	public void test() throws IllegalArgumentException, Exception{
		Matrix m = new Matrix(new double[] {
				0.233002, 0.235814, -0.943452, 500.000687,
				-0.522380, -0.787953, -0.325959, 500.001030,
				-0.820262, 0.568789, -0.060410, 500.000252},4);
		robot.moveHomRowWise(m);		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}		
		Matrix n = new Matrix(ts.getNextValue(),4);
		
		Matrix res1;
		Matrix res2;
		Matrix res3;
		Matrix res4;
		
		res1 = yMatrix.multiply(n);
		System.out.println(n.toMatlabMatrix());
		robot.moveHomRowWise(CONSTANTS.initPos);
	}	
}
