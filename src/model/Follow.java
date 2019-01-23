package model;

import java.io.IOException;

public class Follow {
	private Matrix xMatrix;
	private Matrix yMatrix;
	private TrackingSystem ts; 
	private Robot robot;
	
	private Matrix initPos= new Matrix(new double[]{-0.033671, 0.039825, -0.998639, 192.858921,
            -0.262101, -0.964585, -0.029630, 5.694377, 
            -0.964453, 0.260747, 0.042917, 547.190503},4);
	
	public Follow(Matrix xMatrix, Matrix yMatrix, TrackingSystem ts,Robot robot ){
		this.xMatrix = xMatrix;
		this.yMatrix = yMatrix;
		this.ts=ts;
		this.robot=robot;
		
		
	}
	
	public void test() throws IllegalArgumentException, IOException{
		Matrix m = new Matrix(new double[] {
				0.233002, 0.235814, -0.943452, 428.000687,
				-0.522380, -0.787953, -0.325959, -49.001030,
				-0.820262, 0.568789, -0.060410, 417.000252},4);
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
		
		try {
			res1=n.multiply(this.xMatrix.invert());
			System.out.println("res1:\n"+res1.toMatlabMatrix());
			res2=n.multiply(this.yMatrix.invert());
			System.out.println("res2:\n"+res2.toMatlabMatrix());
			res3=this.xMatrix.multiply(n);
			System.out.println("res3:\n"+res3.toMatlabMatrix());
			res4=this.yMatrix.multiply(n);
			System.out.println("res4:\n"+res4.toMatlabMatrix());
			System.out.println();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		try {
			n=yMatrix.multiply(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("YN:");
		System.out.println(n.toMatlabMatrix());
		
		try {
			n=m.multiply(xMatrix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("MX:");
		System.out.println(n.toMatlabMatrix());
		
		robot.moveHomRowWise(this.initPos);
	}
	
	
}
