package model;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TrackingSystem {

	private String host;
	private int port;
	private String[] markers;
	private String chosenMarker;
	private Socket clientSocket = null;
	private DataOutputStream toServer = null;
	private BufferedReader fromServer = null;
	private BufferedReader inputReader = null;
	
	private boolean connected = false;
	
	
	public boolean connect(
			String host,
			int port) throws IOException{
		host = host.trim().length() != 0 ? host : "127.0.0.1";
		this.host = host;
		System.out.println("Host: "+host);
		port = port != 0 ? port : 5000;
		this.port = port;
		
		System.out.println("Server port: "+port);
		Socket clientSocket = new Socket(host, port);
		
		this.clientSocket = clientSocket;
		DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
		this.toServer = toServer;
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.fromServer = fromServer;
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		this.inputReader = inputReader;
		
		this.connected=true;
		return true;
	}
	
	public boolean chooseOutputFormat(String format)throws IOException {
		System.out.println("Choosing "+format);
		if("ANS_TRUE" == sendServerln(toServer, fromServer, format)) {
			 return true;
		 }else {
			 return false;
		 }	
	}
	
	public String[] getMarkers()throws IOException {
		System.out.println("Markers:");		
		markers = sendServerln(toServer, fromServer, "CM_GETTRACKERS").split(";");
		for(int i=0; i< markers.length; i++) {
			System.out.println(i+1 +") " + markers[i]);
		}
		return markers;
	}
	
	public boolean chooseMarker(String marker) throws IOException {
		
		
		if("ANS_TRUE".equals(sendServerln(toServer, fromServer, marker))) {
			System.out.println(marker);
			return true;
		}else {
			return false;
		}		
	}
	
	public double[] getNextValue() throws IOException {
		//String answer2 = sendServerln(toServer, fromServer, "CM_SETPUSHVALUES OFF");
		String answer = sendServerln(toServer, fromServer, "CM_NEXTVALUE");
		//System.out.println(answer);
		
		
		answer = answer.trim();
		
		String[] parts = answer.split(" ");
		//System.out.println(parts.length);
		//Return null if not visible	
		if(parts[1].equals("n")) {
			return null;
		}
		//Filling Matrix
		double [] matrix = new double[parts.length-3];
		for(int i=2;i<parts.length-1;i++) {	
			matrix[i-2] = Double.parseDouble(parts[i]);
			//System.out.println("R"+(i-2)+" : "+matrix[i-2]);
		}
		
		return matrix;
	}
	
	public boolean setMatrixMode() {
		try {
			if("ANS_TRUE".equals( sendServerln(toServer, fromServer, "FORMAT_MATRIXROWWISE"))) {
				 return true;
			 }else {
				 return false;
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setLogLevel(int level)throws IOException {
		String command;
		switch(level) {
			case 1: command = "LOGLEVEL_QUIET";
					break;
			case 2: command = "LOGLEVEL_ERROR";
					break;
			case 3: command = "LOGLEVEL_WARN";
					break;
			case 4: command = "LOGLEVEL_INFO";
					break;
			case 5: command = "LOGLEVEL_DEBUG";
					break;
			default : 	System.out.println("Ungueltige Eingabe");
						return false;
		}
		if("ANS_TRUE" == sendServerln(toServer, fromServer, command)) {
			 return true;
		 }else {
			 return false;
		 }		
	}
	
	public boolean quit() throws IOException{
		System.out.println("Closing connection:");
		if("ANS_TRUE".equals(sendServerln(toServer, fromServer, "CM_QUITCONNECTION"))) {
			clientSocket.close();			
			this.connected=false;
			System.out.println("Connecting closed.");
			 return true;
		 }else {
			 return false;
		 }		
	}
	public void getState() throws IOException{
		String answer = sendServerln(toServer, fromServer, "CM_GETSYSTEM");
		System.out.print("State: ");
		System.out.println(answer == "ANS FALSE" ? "Unready." : "Ready.");
		System.out.print("Tracking system: ");
		System.out.println(answer.split(" ")[4].split("=")[1]);
		
	}
	
	public boolean isConnected() {
		return this.connected;
	}
	
	private static String sendServer(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		streamToServer.write(command.getBytes("US-ASCII"));
		return streamFromServer.readLine();
	}
	
	private static String sendServerln(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		return sendServer(streamToServer, streamFromServer, command + "\n");
	}
	
	private static void printSendServerln(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		System.out.println(sendServerln(streamToServer, streamFromServer, command));
	}
}
