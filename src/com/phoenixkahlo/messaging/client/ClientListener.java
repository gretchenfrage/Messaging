package com.phoenixkahlo.messaging.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.MessagingProtocol;

/*
 * Listens to a server for incoming messages
 */
public class ClientListener extends Thread {

	private Client client;
	private Socket socket;
	
	public ClientListener(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		if (Client.PRINT_DEBUG)
			System.out.println("Launching ClientListener thread");
		try {
			InputStream in = socket.getInputStream();
			while (true) {
				String message = MessagingProtocol.readMessage(in);
				if (message.length() > 0) client.recieveMessage(message);
			}
		} catch (IOException e) {
			System.err.println("Server disconnected");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
