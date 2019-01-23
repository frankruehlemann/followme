package view;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Matrix;
import model.Robot;

/**
 * 
 * @author OpfaH
 *
 */
public class robotGUI extends GridPane{
	
	private TextField cptRobName = new TextField();
	private TextField cptRobType = new TextField();
	private TextField cptRobServerVersion = new TextField();
	
	private TextField robIpadress = new TextField();
	private TextField RobPort = new TextField();
		
	
	private Button robConnect = new Button("Connect");
	private Button robQuit = new Button("Quit");
	private Button robSendMatrix = new Button("Send Matrix");
	private Button robGetPos = new Button("Get Matrix");
	
	private TextField speed=new TextField("5");
	
	private TextField ipAddress= new TextField("127.0.0.1");
	private TextField port= new TextField("5005");
	
	private TextField RobotName = new TextField();
	private TextField RobotType= new TextField();
	private TextField ServerVersion= new TextField();
	
	private Robot robot;
	
	private MatrixView matrixView;
	
	private TextArea log = new TextArea();
	
	/**
	 * 
	 * @param robot
	 */
	public robotGUI(Robot robot) {
		
		this.robot = robot;
		
		this.RobotName.setEditable(false);
		this.RobotType.setEditable(false);
		this.ServerVersion.setEditable(false);
		
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(15));
		
		this.setStyle("-fx-background-color: #aaaaaa;");
		
		//*************************************************************************************************
		
		this.add(new Label("Robot Name"), 0, 0);
		this.add(this.RobotName, 1, 0);
		this.add(new Label("Robot Type"), 0, 1);
		this.add(this.RobotType, 1, 1);
		this.add(new Label("Server Version"), 0, 2);
		this.add(this.ServerVersion, 1, 2);
		
		//*************************************************************************************************
		
		this.add(new Label("IP-Address"), 4, 0);
		this.add(this.ipAddress, 5, 0);
		this.add(new Label("Port"), 4, 1);
		this.add(this.port, 5, 1);
		
		this.add(this.robConnect, 4, 2);
		this.add(this.robQuit, 5, 2);
		
		//*************************************************************************************************
		
		this.add(this.robSendMatrix, 0, 5);
		this.add(this.robGetPos, 1, 5);
		this.add(new Label("Speed"), 2, 5);
		this.add(this.speed, 4, 5);
		Matrix matrix = new Matrix(new double[] {1,0,0,200,0,1,0,300,0,0,1,500},4);
		this.matrixView = new MatrixView(matrix);
		this.add(this.matrixView, 0, 6,10,1);
		
		//*************************************************************************************************
		
	}
	/**
	 * 
	 */
	public void gatherInformation() {
		this.RobotName.setText(this.robot.getRobot());
		this.RobotType.setText(this.robot.getRobType());
		this.ServerVersion.setText(this.robot.getVersion());
		
	}
	/**
	 * 
	 */
	public void clearInfo() {
		this.RobotName.clear();
		this.RobotType.clear();
		this.ServerVersion.clear();
	}
	/**
	 * 
	 * @return
	 */
	public Robot getRobot() {
		return this.robot;
	}
	/**
	 * 
	 * @return
	 */
	public TextField getCptRobName() {
		return cptRobName;
	}
	/**
	 * 
	 * @return
	 */
	public TextField getCptRobType() {
		return cptRobType;
	}

	public TextField getCptRobServerVersion() {
		return cptRobServerVersion;
	}

	public Button getRobQuit() {
		return this.robQuit;
	}

	public Button getRobConnect() {
		return this.robConnect;
	}

	public TextField getRobIpadress() {
		return robIpadress;
	}

	public TextField getRobPort() {
		return RobPort;
	}

	public TextField getIpAddress() {
		return ipAddress;
	}

	public TextField getPort() {
		return port;
	}

	public Button getRobSendMatrix() {
		return robSendMatrix;
	}

	public MatrixView getMatrixView() {
		return this.matrixView;
	}
	public TextField getSpeedField() {
		return this.speed;
	}
	
	public Button getRobGetPos() {
		return robGetPos;
	}
	
}
