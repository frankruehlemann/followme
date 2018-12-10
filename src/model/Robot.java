package model;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

public class Robot implements IrobserverClient{
	
	private String serverAddress = "127.0.0.1";
	private String serverPort = "5005";
	private Boolean isConnected = false;
	private Boolean isReady = false;
	
	private Socket clientSocket = null;
	private DataOutputStream toServer = null;
	private BufferedReader fromServer = null;
	
	private String robName ="";
	private String robType = "";
	private String robServerVersion ="";
	
	private LinkedList<String> readQueue = new LinkedList<String>();
	
	
	//************************************************************************************
	/**
	 * 
	 */
	public Robot() {
		
		
	}

	@Override
	//************************************************************************************
	/**
	 * 
	 */
	public boolean connect(String host,String port) {
		
		if(this.isConnected) {
			System.err.println("Already connected!");
			return true;
		}
		
		this.serverAddress = host;
		this.serverPort = port;
		try {
			this.clientSocket = new Socket(this.serverAddress, Integer.parseInt(this.serverPort));
			this.toServer = new DataOutputStream(clientSocket.getOutputStream());
			this.fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("-->>"+this.read());
		}
		catch(IOException e){
			System.err.println(e.getStackTrace());
			return false;
		}
		
		// Hello Robot
		isConnected = true;
		this.readWrite("Hello Robot");
		//System.out.println(this.read());
		return true;
	}
	//************************************************************************************
	/**
	 * 
	 * @param command
	 * @return
	 */
	private boolean send(String command) {
		
		if(this.isConnected) {
			try {
				System.out.println("<<--Send: "+command);
				this.toServer.write((command+'\n').getBytes("US-ASCII"));
			}
			catch( IOException e) {
				System.err.println(e.getStackTrace());
				return false;
			}
		}
		else {
			System.err.println("Not connected!");
			return false;
		}
		
		return true;
	}
	//************************************************************************************
	/**
	 * 
	 * @return
	 */
	private String read() {
		String answer="";
		try {
			answer=this.fromServer.readLine();
			this.readQueue.add(answer);
		}
		catch(IOException e) {
			System.err.println(e.getStackTrace());
			answer="0xee";
		}
		return answer;
	}
	//************************************************************************************
	/**
	 * 
	 * @param command
	 * @param displayOnCon
	 * @return
	 */
	private String readWrite(String command,boolean displayOnCon) {
		String answer=null;
		if(this.isConnected) {
			if(this.send(command)) {
				
				answer = this.read();
				if(displayOnCon) {
					System.out.println("-->>"+answer);
				}
				return answer;
			}
		}
		else {
			System.out.println("-->>Not Connected!!!");
			return "Not Connected!!!";
		}
		return "0xee";
	}
	//************************************************************************************
	/**
	 * 
	 * @param command
	 * @return
	 */
	private String readWrite(String command) {
		return this.readWrite(command,true);
	}
	
	//************************************************************************************
	
	@Override
	/**
	 *
	 * @return
	 */
	public String getVersion() {
		
		this.robServerVersion=this.readWrite("GetVersion",true);
		return this.robServerVersion;
	}
	
	@Override
	/**
	 * 
	 */
	public boolean quit() {
		if(this.isConnected) {
			try {
				this.readWrite("Quit");
				this.clientSocket.close();
				this.isConnected=false;
				this.isReady=false;
			}
			catch(IOException e) {
				System.out.println(e.getStackTrace());
			}
		}
		return false;
	}

	@Override
	/**
	 * 
	 */
	public String getTimestamp() {
		
		return this.readWrite("GetTimestamp");
	}

	@Override
	public double[] pingRobot(int num, int wait) {
		double[] values = null;
		String answer;
		if(this.isConnected) {
			answer=this.readWrite("PingRobot "+Integer.toString(num)+ " "+Integer.toString(wait));
			
			String[] parts = answer.split(" ");
			values =new double[parts.length];
			
			for(int i =0;i<parts.length;i++) {
				values[i]=Double.parseDouble(parts[i]);
			}
			return values;
		}
		
		return null;
	}
	
	public String getRobType() {
		if(this.readWrite("IsKuka")=="true") {
			this.robType = "Kuka";
			return "Kuka";
		}
		if(this.readWrite("IsAdept")=="true") {
			this.robType = "Adept";
			return "Adept";
		}
		if(this.readWrite("IsKawa")=="true") {
			this.robType = "Kawa";
			return "Kawa";
		}
		if(this.readWrite("IsKR3")=="true") {
			this.robType = "KR3";
			return "KR3";
		}
		if(this.readWrite("IsKR16")=="true") {
			this.robType = "KR16";
			return "KR16";
		}
		return "Unknown Type";
	}
	
	public String getRobot() {
		this.robName=this.readWrite("GetRobot");
		return this.robName;
	}
	
	@Override
	public boolean ping() {
		String answer =this.readWrite("CM_PING");
		if(answer=="PONG") {
			return true;
		}
		return false;
	}

	@Override
	public boolean setVerbosity(int verbosity) {
		String answer="";
		if(this.isConnected) {
			answer=this.readWrite("SetVerbosity "+Integer.toString(verbosity));
		}
		if(answer=="true") {
			return true;
		}
		return false;
	}

	@Override
	public boolean setAdeptSpeed(int speed) {
		if((speed>=0) && (speed <=120)) {
			if(this.readWrite("SetAdeptSpeed "+Integer.toString(speed))=="true") {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setAdeptAccel(int accelerate, int brake) {
		if((accelerate>=0) && (accelerate <=120) && (brake>=0) && (brake <=120)) {
			if(this.readWrite("SetAdeptAccel "+Integer.toString(accelerate)+" "+Integer.toString(brake))=="true") {
				return true;
			}
		}
		return false;
	}

	@Override
	public double[] getJointsMaxChange() {
		if(!this.isConnected) {
			return null;
		}
		
		String[] parts =this.readWrite("GetJointsMaxChange").split(" ");
		double[] values =new double[parts.length];
		
		for(int i =0;i<parts.length;i++) {
			values[i]=Double.parseDouble(parts[i]);
		}
		return values;
	}

	@Override
	public boolean setJointsMaxChange(double alpha1, double alpha2, double alpha3, double alpha4, double alpha5, double alpha6) {
		
		String command ="SetJointsMaxChange "+
						Double.toString(alpha1)+" "+
						Double.toString(alpha2)+" "+
						Double.toString(alpha3)+" "+
						Double.toString(alpha4)+" "+
						Double.toString(alpha5)+" "+
						Double.toString(alpha6);
		
		if(this.readWrite(command)=="true") {
			return true;
		}
		return false;
	}
	
	public boolean moveHomRowWise(int m11,int m12,int m13,int m14,
								int m21,int m22,int m23,int m24,
								int m31,int m32,int m33,int m34) {
		
		
		String msg = "MoveMinChangeRowWiseStatus "+
				Integer.toString(m11)+" "+Integer.toString(m12)+" "+Integer.toString(m13)+" "+Integer.toString(m14)+" "+
				Integer.toString(m21)+" "+Integer.toString(m22)+" "+Integer.toString(m23)+" "+Integer.toString(m24)+" "+
				Integer.toString(m31)+" "+Integer.toString(m32)+" "+Integer.toString(m33)+" "+Integer.toString(m34)+" "+
				"flip toggleElbow toggleArm";
				
		
		if(this.readWrite(msg)=="true") {
			return true;
		}
		
		return false;
	}
	
	public boolean moveRTHomRowWise(int m11,int m12,int m13,int m14,
									int m21,int m22,int m23,int m24,
									int m31,int m32,int m33,int m34) {
		String msg = "MoveRTHomRowWise "+
				Integer.toString(m11)+" "+Integer.toString(m12)+" "+Integer.toString(m13)+" "+Integer.toString(m14)+" "+
				Integer.toString(m21)+" "+Integer.toString(m22)+" "+Integer.toString(m23)+" "+Integer.toString(m24)+" "+
				Integer.toString(m31)+" "+Integer.toString(m32)+" "+Integer.toString(m33)+" "+Integer.toString(m34);
		
		
		if(this.readWrite(msg)=="true") {
			return true;
		}
		
		return false;
	}
	
	public double[] getRobotPosition() {
		if(!this.isConnected) {
			return null;
		}
		
		String answer=this.readWrite("GetPositionHomRowWise");
		
		String[] parts = answer.split(" ");
		double[] values =new double[parts.length];
		
		for(int i =0;i<parts.length;i++) {
			values[i]=Double.parseDouble(parts[i]);
		}
		return values;
	}
}
