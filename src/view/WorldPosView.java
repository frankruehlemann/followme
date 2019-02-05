package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class WorldPosView extends GridPane{
	
	private TextField Xangle; 
	private TextField Yangle; 
	private TextField Zangle;
	
	private TextField Xpos;
	private TextField Ypos;
	private TextField Zpos;
	
	private Button calcMatrix;
	
	public WorldPosView(){
		this.init();
	}
	
	private void init(){
		
		this.Xangle=new TextField();
		this.add(this.Xangle, 0, 0);
		
		this.Yangle=new TextField();
		this.add(this.Yangle, 1,0);
		
		this.Zangle=new TextField();
		this.add(this.Zangle, 2, 0);
		
		//******
		
		this.Xpos = new TextField();
		this.add(this.Xpos, 0, 1);
		
		this.Ypos = new TextField();
		this.add(this.Ypos, 1, 1);
		
		this.Zpos = new TextField();
		this.add(this.Zpos, 2, 1);
		
		//******
		
		this.calcMatrix = new Button("Calc Matrix");		
		this.add(this.calcMatrix, 3, 3);
	}
	
	public String getValues(){
		
		String msg = this.Xpos.getText()+";";
		msg+=this.Ypos.getText()+";";
		msg+=this.Zpos.getText()+";";
		msg+=this.Xangle.getText()+";";
		msg+=this.Yangle.getText()+";";
		msg+=this.Zangle.getText();
		
		return msg;
	}
	public Button getCalcMatrix(){
		return this.calcMatrix;
	}
}
