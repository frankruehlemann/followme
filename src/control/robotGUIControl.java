package control;

import javafx.event.ActionEvent;
import view.robotGUI;

public class robotGUIControl {
	
	private robotGUI robgui;
	
	public robotGUIControl(robotGUI robgui) {
		this.robgui=robgui;
		
		this.robgui.getRobConnect().setOnAction(this::handle);
		this.robgui.getRobQuit().setOnAction(this::handle);
		this.robgui.getRobSendMatrix().setOnAction(this::handle);
		this.robgui.getRobCalibrate().setOnAction(this::handle);
		
	}
	
	public void handle(ActionEvent event) {
		if(event.getSource().equals(this.robgui.getRobConnect())) {
			if(!this.robgui.getIpAddress().getText().isEmpty() && !this.robgui.getPort().getText().isEmpty()) {
				if(this.robgui.getRobot().connect(this.robgui.getRobIpadress().getText(), this.robgui.getPort().getText())) {
					this.robgui.gatherInformation();
				}
				
			}
		}
		
		if(event.getSource().equals(this.robgui.getRobCalibrate())) {
			this.robgui.getCalib().calibrate();
		}
		
		if(event.getSource().equals(this.robgui.getRobSendMatrix())) {
			this.robgui.getMatrixView().updateMatrix();
			this.robgui.getRobot().setAdeptSpeed(Double.parseDouble(this.robgui.getSpeedField().getText()));
			this.robgui.getRobot().moveHomRowWise(this.robgui.getMatrixView().getMatrix());
		}
		
		if(event.getSource().equals(this.robgui.getRobQuit())) {
			this.robgui.clearInfo();
			this.robgui.getRobot().quit();
		}
		
	}
}
