package model;

import com.mathworks.engine.MatlabEngine;

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
	
	public Matrix(){
		this(new double[]{0,0,0,0,
				0,0,0,0,
				0,0,0,0},4);
	}
	
	/**
	 * constructor for 1d arrays
	 * @param values
	 * @param width
	 */
	public Matrix(double[] values,int width) throws IllegalArgumentException{
		
		int rows = values.length/width;
		int cols = width;
		
		if(rows==3 && cols==4) {
			this.values = new double[rows+1][cols];
		}
		else {
			this.values = new double[rows][cols];
		}
		
		
		
		int row =0;
		for(int i=0;i<values.length;i++) {
			
			int col = i%cols;
			
			if(i%(cols)==0 && i!=0) {
				row++;
			}
			
			this.values[row][col]=values[i];
		}
		
		if(rows==3 && cols==4) {
			this.values[3]=new double[] {0,0,0,1};
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
		if (values.length > 4) {
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
	
	public void zeros() {
		for(int i =0;i<this.getRowCount();i++) {
			for(int j =0;j<this.getColCount();j++) {
				this.values[i][j]=0;
			}
		}
	}
	public Matrix add(Matrix m) {
		
		if(this.getRowCount()!=m.getRowCount() && this.getColCount()!=m.getColCount()) {
			System.out.println("Matrix size not equal!!!");
			return null;
		}
		
		Matrix res= new Matrix(new double[this.getRowCount()][this.getColCount()]);
		
		for(int row=0;row<this.getRowCount();row++) {
			for(int col=0;col<m.getColCount();col++) {
				double val = this.getValueAt(row, col)+m.getValueAt(row, col);
				res.setValueAt(val, row, col);
			}
		}
		return res;
	}
	public Matrix diff(Matrix m) {
		
		if(this.getRowCount()!=m.getRowCount() && this.getColCount()!=m.getColCount()) {
			System.out.println("Matrix size not equal!!!");
			return null;
		}
		
		Matrix res= new Matrix(new double[this.getRowCount()][this.getColCount()]);
		
		for(int row=0;row<this.getRowCount();row++) {
			for(int col=0;col<m.getColCount();col++) {
				double val = this.getValueAt(row, col)-m.getValueAt(row, col);
				res.setValueAt(val, row, col);
			}
		}
		return res;
	}
	public Matrix multiply(Matrix m) {
		
		Matrix res=null;
		
		if(this.getColCount() != m.getRowCount()) {
			System.out.println("rowcount != colcount");
		}
		
		else {
			
			double[][] val = new double[this.getRowCount()][m.getColCount()]; 
			res=new Matrix(val);
			res.zeros();
			
			for(int row=0;row<this.getRowCount();row++) {
				for(int col=0;col<m.getColCount();col++) {
					
					for(int i=0;i<this.getColCount();i++) {
						double temp = res.getValueAt(row, col)+this.getValueAt(row, i)*m.getValueAt(i, col);
						res.setValueAt(temp, row, col);
					}
				}
			}
		}		
		return res;
	}
	
	public String toMatlabMatrix(){
		
		String temp="[";
		
		for(int i=0;i<this.getRowCount();i++){
			for(int j=0;j<this.getColCount();j++){
				if(j!=this.getColCount()-1){
					temp=temp+this.getValueAt(i, j)+",";
				}
				else{
					temp=temp+this.getValueAt(i, j);
				}
			}
			if(i!=this.getRowCount()-1){
				temp=temp+";";
			}
		}
		temp=temp+"]";
		
		return temp;
	}
	
	public Matrix fromRealWorldPos(String values){
		
		String[] vals = values.split(";");
		
		double xangle=Double.parseDouble(vals[0])*Math.PI/180.0;
		double yangle=Double.parseDouble(vals[1])*Math.PI/180.0;
		double zangle=Double.parseDouble(vals[2])*Math.PI/180.0;
		
		double posx =Double.parseDouble(vals[3]);
		double posy =Double.parseDouble(vals[4]);
		double posz =Double.parseDouble(vals[5]);
		
		return this.fromRealWorldPos(posx, posy, posz, xangle, yangle, zangle);
		
	}
		
	public Matrix fromRealWorldPos(double posx,double posy,double posz,double xangle,double yangle,double zangle){	
		
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
	
	public Matrix invert() throws Exception{
		MatlabEngine eng = MatlabEngine.startMatlab();
		
		eng.eval("x = "+this.toMatlabMatrix());
		eng.eval("x = inv(x);");	
		 
		System.out.println("Matrix invert x: ");
		System.out.println(eng.getVariable("x").toString());
		return new Matrix(eng.getVariable("x"));
	}
	
	
}
