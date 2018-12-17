package view;

import java.io.PrintStream;

import control.CalibrationViewControl;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import model.CustomOutputStream;
import model.Kalibrierung;

public class MainGUI extends GridPane{
	
	private TrackSysGUI tsgui;
	private robotGUI robgui;
	private CalibrationView calgui;
	
	private TextArea log = new TextArea();
	
	public MainGUI(TrackSysGUI tsgui,robotGUI robgui) {
		this.robgui=robgui;
		this.tsgui=tsgui;
		
		Kalibrierung cal = new Kalibrierung(tsgui,robgui);
		
		this.calgui = new CalibrationView(cal);
		CalibrationViewControl calibControl = new CalibrationViewControl(calgui);
		
		this.add(this.robgui, 0, 0);
		
		this.add(this.calgui,1,0);
		
		this.add(this.tsgui, 2, 0);
		
		this.log.setEditable(false);
		this.log.setMinHeight(400);
		this.log.setMaxHeight(400);
		this.add(this.log, 0, 1,3,1);
		
		PrintStream ps = new PrintStream(new CustomOutputStream(this.log));
		System.setOut(ps);
		System.setErr(ps);
		
	}
}
