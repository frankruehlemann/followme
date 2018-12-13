package model;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;



public class Kalibrierung {
	int n;
	ArrayList <Matrix> Rob_matrices = new ArrayList<Matrix>();
	ArrayList <Matrix> TS_matrices = new ArrayList<Matrix>();
	public Kalibrierung(TrackingSystem track, Robot rob) {
		for(int i = 0; i<6; i++) {
			Rob_matrices.set(i, new Matrix(randMatrix());
			//TODO: Matrix Befehl an Roboter geben
			//TODO: wait
			TS_matrices.set(i, new Matrix(track.getNextValue()));
		}
		//TODO:solve
		
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
