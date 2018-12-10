package view;
import java.io.PrintStream;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.CustomOutputStream;
import model.Robot;

public class robotGUI extends GridPane{
	
	private Text cptRobName = new Text();
	private Text cptRobType = new Text();
	private Text cptRobServerVersion = new Text();
	
	private Text robIpadress = new Text();
	private Text RobPort = new Text();
		
	
	private Button robConnect = new Button("Connect");
	private Button robQuit = new Button("Quit");
	
	private TextField ipAddress= new TextField();
	private TextField port= new TextField();
	
	private TextField RobotName = new TextField();
	private TextField RobotType= new TextField();
	private TextField ServerVersion= new TextField();
	
	private Robot robot;
	
	private TextArea log = new TextArea();
	
	public robotGUI(Robot robot) {
		
		this.robot = robot;
		
		this.RobotName.setEditable(false);
		this.RobotType.setEditable(false);
		this.ServerVersion.setEditable(false);
		
		this.setPadding(new Insets(15));
		
		this.add(new Label("Robot Name"), 0, 0);
		this.add(this.RobotName, 1, 0);
		this.add(new Label("Robot Type"), 0, 1);
		this.add(this.RobotType, 1, 1);
		this.add(new Label("Server Version"), 0, 2);
		this.add(this.ServerVersion, 1, 2);
		
		this.add(new Label("IP-Address"), 4, 0);
		this.add(this.ipAddress, 5, 0);
		this.add(new Label("Port"), 4, 1);
		this.add(this.port, 5, 1);
		
		this.add(this.robConnect, 4, 2);
		this.add(this.robQuit, 5, 2);
		
		log.setEditable(false);
		this.add(this.log, 6, 0,1,7);
		
		PrintStream ps = new PrintStream(new CustomOutputStream(this.log));
		System.setOut(ps);
		System.setErr(ps);
	}
	
	public void gatherInformation() {
		this.RobotName.setText(this.robot.getRobot());
		this.RobotType.setText(this.robot.getRobType());
		this.ServerVersion.setText(this.robot.getVersion());
		
	}
	
	public Robot getRobot() {
		return this.robot;
	}
	
	public Text getCptRobName() {
		return cptRobName;
	}

	public Text getCptRobType() {
		return cptRobType;
	}

	public Text getCptRobServerVersion() {
		return cptRobServerVersion;
	}

	public Button getRobQuit() {
		return this.robQuit;
	}

	public Button getRobConnect() {
		return this.robConnect;
	}

	public Text getRobIpadress() {
		return robIpadress;
	}

	public Text getRobPort() {
		return RobPort;
	}

	public TextField getIpAddress() {
		return ipAddress;
	}

	public TextField getPort() {
		return port;
	}
	
	
	
}
