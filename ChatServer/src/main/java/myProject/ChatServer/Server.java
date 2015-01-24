package myProject.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server implements Runnable{
	private int port;
	private List<Client> clients = new LinkedList();
	private boolean running = true;

	private class Client extends Thread {
		Socket client;
		PrintWriter out;
		BufferedReader in;
		boolean running = true;
		
		public Client(Socket client) throws IOException {
			this.client = client;
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			broadcast("new Client joined the session");
		}
		
		public void sendMessage(String message) {
			out.println(message);
		}
		
		@Override
		public void run() {
			super.run();
			running = true;

//			System.out.println("Client running now");
			while(running) {
				try {
					String message = in.readLine();
//					System.out.println("message recieved: " + message);
					if(message == null) {
						clients.remove(this);
						running = false;
						break;
					}
//					System.out.println("Message for broadcast: " + message);
					broadcast(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public Server(int port) {
		this.port = port;
	}

	private void broadcast(String message) {
//		System.out.println("Broadcasting: " + message);
//		for(Client c : clients) {
//			c.sendMessage(message);
//		}
		clients.forEach( (c) -> c.sendMessage(message) );
	}

	@Override
	public void run() {
		running = true;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			
			while(running) {
					Socket clientSocket = serverSocket.accept();
//					System.out.println("new Client");
					Client client = new Client(clientSocket); 
					clients.add(client);
					client.start();
//					System.out.println("client started");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(6667);
		Thread serverThread = new Thread(server);
		serverThread.start();
		System.out.println("server started");
	}
	
}
