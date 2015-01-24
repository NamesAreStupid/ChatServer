package myProject.ChatServer.prototyping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket server = new Socket("localhost", 6667);
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		String message;
		while((message = in.readLine()) != null) {
			System.out.println(message);
		}
	}
}
