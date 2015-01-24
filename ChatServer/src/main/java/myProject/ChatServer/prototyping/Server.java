package myProject.ChatServer.prototyping;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(6667);
		System.out.println("Server started");
		Socket client = socket.accept();
		System.out.println("we got a new client");
		PrintWriter pw = new PrintWriter(client.getOutputStream());
		while(true) {
			pw.println("test");
		}
	}
}
