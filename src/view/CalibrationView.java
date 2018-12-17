package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Kalibrierung;

public class CalibrationView extends GridPane{
	
	private Kalibrierung calib;
	
	private Button btCalibrate = new Button("Calibrate");
	private Button btSetCnt = new Button("Set Count");
	
	private TextField tfMeasureCnt = new TextField("10");
	
	
	public CalibrationView(Kalibrierung calib) {
		this.calib=calib;
		
		this.setStyle("-fx-background-color: #9f9f9f;");
		
		this.add(this.btCalibrate, 0, 0);
		this.add(this.tfMeasureCnt, 0, 1);
		this.add(this.btSetCnt, 0, 2);
		
	}

	public Kalibrierung getCalib() {
		return calib;
	}

	public Button getBtCalibrate() {
		return btCalibrate;
	}
	
	public Button getBtSetCnt() {
		return this.btSetCnt;
	}
	
	public int getMeasureCnt() {
		
		int cnt = Integer.parseInt(this.tfMeasureCnt.getText());
		
		return cnt;
	}
}
