package model;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TrackingSystem {

	String host;
	int port;
	String[] markers;
	String chosenMarker;
	Socket clientSocket = null;
	DataOutputStream toServer = null;
	BufferedReader fromServer = null;
	BufferedReader inputReader = null;
	
	
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
	public boolean chooseMarker(String[] markers) throws IOException {
		System.out.println("Enter Number next to desired Marker");
		chosenMarker = inputReader.readLine();
		switch(chosenMarker) {
			case "1": chosenMarker = markers[0];
					break;
			case "2": chosenMarker = markers[1];
					break;
			case "3": chosenMarker = markers[2];
					break;
			case "4": chosenMarker = markers[3];
					break;
		}
		
		 if("ANS_TRUE" == sendServerln(toServer, fromServer, chosenMarker)) {
			 return true;
		 }else {
			 return false;
		 }		
	}
	public double[] getNextValue() throws IOException {
		//String answer2 = sendServerln(toServer, fromServer, "CM_SETPUSHVALUES OFF");
		String answer = sendServerln(toServer, fromServer, "CM_NEXTVALUE");
		System.out.println(answer);
		
		double [] matrix = new double[14];
		answer = answer.trim();
		String[] parts = answer.split(" ");
		System.out.println(parts.length);
		//Return null if not visible	
		if(parts[0] == "n") {
			return null;
		}
		//Filling Matrix
		for(int i=2;i<parts.length;i++) {	
			matrix[i-2] = Double.parseDouble(parts[i]);
			System.out.println("R"+(i-2)+" : "+matrix[i-2]);
		}
		
		return matrix;
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
		if("ANS_TRUE" == sendServerln(toServer, fromServer, "CM_QUITCONNECTION")) {
			clientSocket.close();			
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
	public static String sendServer(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		streamToServer.write(command.getBytes("US-ASCII"));
		return streamFromServer.readLine();
	}
	
	public static String sendServerln(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		return sendServer(streamToServer, streamFromServer, command + "\n");
	}
	
	public static void printSendServerln(
			DataOutputStream streamToServer,
			BufferedReader streamFromServer,
			String command
		) throws IOException {
		System.out.println(sendServerln(streamToServer, streamFromServer, command));
	}
}
