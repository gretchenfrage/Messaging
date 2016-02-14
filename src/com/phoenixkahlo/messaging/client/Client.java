package com.phoenixkahlo.messaging.client;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.MessagingProtocol;

/*
 * Represents a complete, runnable messaging client
 */
public class Client {
	
	public static final String IP = "localhost";//"71.87.82.153";
	public static final int PORT = 36987;
	
	public static final boolean PRINT_DEBUG = false;
	
	private Socket socket;
	private ClientListener listener;
	private ClientPrompter prompter;
	private ClientFrame frame;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
	public Client() {
		if (PRINT_DEBUG)
			System.out.println("Constructing client");
		try {
			socket = new Socket(IP, PORT);
		} catch (IOException e) {
			System.err.println("Failed to connect to server");
			e.printStackTrace();
			System.exit(1);
		}
		listener = new ClientListener(this, socket);
		prompter = new ClientPrompter(this);
		frame = new ClientFrame(this);
	}
	
	public void start() {
		listener.start();
		prompter.start();
		frame.start();
		System.out.println("~~~ MESSAGING CLIENT STARTED ~~~");
	}
	
	public void recieveMessage(String message) {
		System.out.println(message);
		frame.println(message);
		Toolkit.getDefaultToolkit().beep();
	}
	
	public void sendMessage(String message) {
		try {
			OutputStream out = socket.getOutputStream();
			MessagingProtocol.writeMessage(out, message);
		} catch (IOException e) {
			System.err.println("Server disconnected");
			e.printStackTrace();
			System.exit(1);
		}
	}

}
