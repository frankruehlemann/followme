import java.io.*;
import java.net.*;

public class trackserverClientDirty {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Variables
		String serverAddress;
		String serverPort;
		String[] markers;
		String chosenMarker;

		// Start
		System.out.println("Demonstrations starts.");
		// Read data
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server address: [127.0.0.1]");
		System.out.print("> ");
		serverAddress = inputReader.readLine();
		serverAddress = serverAddress.trim().length() != 0 ? serverAddress : "127.0.0.1";
		System.out.println("Server port: [5000]");
		System.out.print("> ");
		serverPort = inputReader.readLine();
		serverPort = serverPort.trim().length() != 0 ? serverPort : "5000";

		// Connection starts
		System.out.println("Connecting to " + serverAddress + ':' + serverPort);
		Socket clientSocket = new Socket(serverAddress, Integer.parseInt(serverPort));
		DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		String answer = sendServerln(toServer, fromServer, "CM_GETSYSTEM");

		// Get state
		System.out.print("State: ");
		System.out.println(answer == "ANS FALSE" ? "Unready." : "Ready.");

		// Get tracking system
		System.out.print("Tracking system: ");
		System.out.println(answer.split(" ")[4].split("=")[1]);

		// Get Markers
		System.out.println("Markers:");
		markers = sendServerln(toServer, fromServer, "CM_GETTRACKERS").split(";");
		for(int i=0; i< markers.length; i++) {
			System.out.println(i +") " + markers[i]);
		}

		// Choose marker
		System.out.println("Choose your marker:");
		System.out.print("> ");
		chosenMarker = markers[Integer.parseInt(inputReader.readLine()) -1];
		sendServerln(toServer, fromServer, chosenMarker);

		// Get values in matrix format
		sendServerln(toServer, fromServer, "FORMAT_MATRIXROWWISE");
		sendServerln(toServer, fromServer, "CM_SETPUSHVALUES ON");
		System.out.println("Values in matrix format (10 values)");
		for(int i=0; i<10; i++) {
			System.out.println(fromServer.readLine());
		}

		// Connection ends
		System.out.println("Closing connection:");
		sendServerln(toServer, fromServer, "CM_QUITCONNECTION");
		clientSocket.close();
		System.out.println("Connecting closed.");
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
