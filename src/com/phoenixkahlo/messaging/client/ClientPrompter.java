package com.phoenixkahlo.messaging.client;
import java.util.Scanner;

/*
 * Prompts client for input messages
 */
public class ClientPrompter extends Thread {

	private Client client;
	
	public ClientPrompter(Client client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		if (Client.PRINT_DEBUG)
			System.out.println("Launching ClientPrompter thread");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			client.sendMessage(input);
		}
	}
	
}
