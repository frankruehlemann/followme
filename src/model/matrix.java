package model;
import java.io.IOException;

public class Matrix{
	private double[][] values;
	/**
	 * 
	 * @param values
	 * @throws IllegalArgumentException
	 */
	public Matrix(double[][] values) throws IllegalArgumentException{
		if (!checkArray(values)) {
			throw new IllegalArgumentException("No correct matrix provided");
		}
		this.values = values;
	}
	
	/**
	 * constructor for 1d arrays
	 * @param values
	 * @param width
	 */
	public Matrix(double[] values,int width) throws IllegalArgumentException{
		
		int rows = values.length/width;
		int cols = width;

		this.values = new double[rows][cols];
		
		int row =0;
		for(int i=0;i<values.length;i++) {
			
			int col = i%cols;
			
			if(i%(cols)==0 && i!=0) {
				row++;
			}
			
			this.values[row][col]=values[i];
		}
		
		if(!this.checkArray(this.values)) {
			throw new IllegalArgumentException("Nix is");
		}
	}
	
	/**
	 * checks array if valid
	 * @param values
	 * @return
	 */
	public boolean checkArray(double[][] values) {
		if (values.length > 3) {
			return false;
		}
		for (int i=0; i<values.length; i++) {
			if (values[i].length > 4) {
				return false;
			}
		}
		return true;
	}
	
	public int getRowCount() {
		return this.values.length;
	}
	
	public int getColCount() {
		return this.values[0].length;
	}
	
	/**
	 * returns value at specific position
	 * @param row the desired row
	 * @param col the desired col
	 * @return the value at the given position
	 */
	public double getValueAt(int row,int col) {
		
		return this.values[row][col];
	}
	
	/**
	 * sets value at specific position
	 * @param value the desired value
	 * @param row the desired row
	 * @param col the desired column
	 */
	public void setValueAt(double value, int row,int col) {
		this.values[row][col]=value;
	}
	
	public String getMatrixRowWise() {
		
		String res="";
		
		for(int i=0;i<this.values.length;i++) {
			for(int j=0;j<this.values[i].length;j++) {
				res+=" "+Double.toString(this.values[i][j]);
			}
			res+='\n';
		}
		
		return res;
		
	}
	
	
}
