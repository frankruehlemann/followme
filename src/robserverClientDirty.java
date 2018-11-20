import java.io.*;
import java.net.*;

public class robserverClientDirty {

	public static void main(String[] args) throws IOException {
		// Variables
		String serverAddress = "";
		String serverPort = "";
		int menuChosen = 0;
		Boolean isConnected = false;
		Socket clientSocket = null;
		DataOutputStream toServer = null;
		BufferedReader fromServer = null;

		// Start
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		// Menu
		while (true) {
			System.out.println();
			System.out.println("Choose your command:");
			System.out.println("1) Connect to robot");
			System.out.println("2) Get state (requires connection to robot)");
			System.out.println("3) Enter command");
			System.out.println("4) Quit");
			System.out.print("> ");

			menuChosen = Integer.parseInt(inputReader.readLine());

			switch (menuChosen) {
				case 1:
					// Read data
					System.out.println("Server address: [127.0.0.1]");
					serverAddress = inputReader.readLine();
					serverAddress = serverAddress.trim().length() != 0 ? serverAddress : "127.0.0.1";
					System.out.println("Server port: [5005]");
					serverPort = inputReader.readLine();
					serverPort = serverPort.trim().length() != 0 ? serverPort : "5005";
					// Connection starts
					System.out.println("Connecting to " + serverAddress + ':' + serverPort);
					clientSocket = new Socket(serverAddress, Integer.parseInt(serverPort));
					toServer = new DataOutputStream(clientSocket.getOutputStream());
					fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					System.out.println(fromServer.readLine());
					// Hello Robot
					printSendServerln(toServer, fromServer, "Hello Robot");
					isConnected = true;

					break;
				case 2:
					if (!isConnected) {
						System.out.println("Not connected.");
						break;
					}
					printSendServerln(toServer, fromServer, "GetStatus");
					break;
				case 3:
					System.out.println("Enter command:");
					printSendServerln(toServer, fromServer, inputReader.readLine());
					break;
				case 4:
					clientSocket.close();
					System.out.println("Connecting closed.");
					System.exit(0);
			}
		}
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
