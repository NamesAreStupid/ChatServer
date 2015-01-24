package myProject.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket server;
	PrintWriter out;
	BufferedReader in;
	MessageListener ml;
	String userName;
	boolean running;
	
	public Client(String address, int port) throws IOException {
		server = new Socket(address, port);	
		out = new PrintWriter(server.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		ml = new MessageListener();
		ml.start();
		System.out.println("Enter user name:");
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		userName = scanner.nextLine();
		processUserInput();
	}
	
	private void processUserInput() {
		running = true;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter message:");
		while(running) {
			String message = in.nextLine();
			sendMessage(message);
			
		}
	}
	
	public void sendMessage(String message) {
//		System.out.println("Sending message: " + message);
		message = userName + ": " + message;
		out.println(message);
	}
	
	private void displayMessage(String message) {
		System.out.println(message);
	}
	
	class MessageListener extends Thread {
		boolean running = true;
		@Override
		public void run() {
			super.run();
			running = true;
			
			while(running) {
				try {
					String message = in.readLine();
					if(message == null) {
						System.out.println("Stopping client");
						Client.this.running = false;
						this.running = false;
						break;
					}
//					System.out.println("Message received: " + message);
					displayMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("starting Client");
		Client client = new Client("localhost", 6667);
//		Thread.sleep(100);
//		while(true) {
//			client.sendMessage("This is my testmessage. It may not be much but its totally mine. Im super serious!");
//		}
	}

}
