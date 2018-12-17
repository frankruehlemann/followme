package control;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import model.Matrix;
import view.TrackSysGUI;

public class TrackSysGUIControl {

	private TrackSysGUI tsgui;
	
	public TrackSysGUIControl(TrackSysGUI tsgui) {
		this.tsgui=tsgui;
		
		this.tsgui.getBtConnect().setOnAction(this::handle);
		this.tsgui.getBtQuit().setOnAction(this::handle);
		this.tsgui.getBtGetNext().setOnAction(this::handle);
	
		this.tsgui.getMarkers().setOnAction(this::comboHandle);
		
	}
	
	private void comboHandle(Event e) {
		if(e.getSource().equals(this.tsgui.getMarkers())) {
			try {
				this.tsgui.getTrackSys().chooseMarker(this.tsgui.getMarkers().getSelectionModel().getSelectedItem().toString());
				this.tsgui.getTrackSys().setMatrixMode();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void handle(ActionEvent event) {
		if(event.getSource().equals((this.tsgui.getBtConnect()))) {
			
			String ipAddress = this.tsgui.getTfIpAddress().getText();
			int port = Integer.parseInt(this.tsgui.getTfPort().getText());
			try {
				this.tsgui.getTrackSys().connect(ipAddress, port);
				this.tsgui.getTrackSys().getState();
				String[] markers = this.tsgui.getTrackSys().getMarkers();
				this.tsgui.getMarkers().setItems(FXCollections.observableArrayList(markers));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(event.getSource().equals(this.tsgui.getBtQuit())) {
			try {
				this.tsgui.getTrackSys().quit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(event.getSource().equals(this.tsgui.getBtGetNext())) {
			try {
				double[] values = this.tsgui.getTrackSys().getNextValue();
				this.tsgui.getMatrixView().setMatrix(new Matrix(values,4));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
