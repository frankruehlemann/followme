package view;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Matrix;

public class MatrixView extends GridPane {
	
	
	private TextField[][] fields;
	private Matrix matrix;
	
	public MatrixView(Matrix matrix) {
		
		this.matrix=matrix;
		this.fields=new TextField[matrix.getRowCount()][matrix.getColCount()];
		
		for(int row=0;row<fields.length;row++) {
			for(int col=0;col<fields[1].length;col++) {
				
				this.fields[row][col]=new TextField();
				this.fields[row][col].setText(Double.toString(matrix.getValueAt(row, col)));
				this.add(this.fields[row][col], col, row);
			}
		}
		
	}
	
	public void setMatrix(Matrix m) {
		this.matrix=m;
	}
	
	public void updateView() {
		for(int row=0;row<fields.length;row++) {
			for(int col=0;col<fields[1].length;col++) {
				this.fields[row][col].setText(Double.toString(this.matrix.getValueAt(row, col)));
			}
		}
	}
	
	public void updateMatrix() {
		for(int row=0;row<fields.length;row++) {
			for(int col=0;col<fields[1].length;col++) {
				
				this.matrix.setValueAt(Float.parseFloat(fields[row][col].getText()), row, col);
			}
		}
	}
	
	public Matrix getMatrix() {
		return this.matrix;
	}
	
}
