package control;

import javafx.event.ActionEvent;
import view.CalibrationView;

public class CalibrationViewControl {
	
	private CalibrationView calibgui;
	
	public CalibrationViewControl(CalibrationView calibgui) {
		this.calibgui=calibgui;
		
		this.calibgui.getBtCalibrate().setOnAction(this::handle);
		this.calibgui.getBtSetCnt().setOnAction(this::handle);
		
	}
	
	public void handle(ActionEvent event) {
		if(event.getSource().equals(this.calibgui.getBtCalibrate())) {
			this.calibgui.getCalib().calibrate();
		}
		if(event.getSource().equals(this.calibgui.getBtSetCnt())) {
			this.calibgui.getCalib().setMeasureCount(this.calibgui.getMeasureCnt());
		}
	}
}
