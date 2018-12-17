package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Matrix;
import model.TrackingSystem;

public class TrackSysGUI extends GridPane{
	private String ipAddress ="127.0.0.1";
	private String port = "5000";
	
	private TrackingSystem trackSys;
	
	private TextField TfIpAddress = new TextField(ipAddress);
	private TextField TfPort = new TextField(port);
	
	private Button BtConnect = new Button("Connect");
	private Button BtQuit = new Button("Quit");
	private Button BtGetNext = new Button("Get Next");
	
	private ComboBox markers = new ComboBox();
	
	private MatrixView matrixView;
	
	public TrackSysGUI(TrackingSystem trackSys) {
		
		this.trackSys=trackSys;
		
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(15));
		
		this.setStyle("-fx-background-color: #888888;");
		
		this.add(new Label("IP-Address:"), 0, 0);
		this.add(new Label("Port:"), 0, 1);
		
		this.add(this.TfIpAddress, 1, 0);
		this.add(this.TfPort, 1, 1);
		
		this.add(this.BtConnect, 2, 0);
		this.add(this.BtQuit, 2, 1);
		this.add(this.BtGetNext, 3, 1);
		
		
		this.add(this.markers, 3, 0);
		
		
		Matrix matrix = new Matrix(new double[] {0,0,0,0,0,0,0,0,0,0,0,0},4);
		this.matrixView = new MatrixView(matrix);
		this.add(this.matrixView, 0, 4,4,4);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getPort() {
		return port;
	}

	public TrackingSystem getTrackSys() {
		return trackSys;
	}

	public TextField getTfIpAddress() {
		return TfIpAddress;
	}

	public TextField getTfPort() {
		return TfPort;
	}

	public Button getBtConnect() {
		return BtConnect;
	}

	public Button getBtQuit() {
		return BtQuit;
	}
	
	public Button getBtGetNext() {
		return this.BtGetNext;
	}

	public ComboBox getMarkers() {
		return markers;
	}

	public MatrixView getMatrixView() {
		return matrixView;
	}
	
	
}
