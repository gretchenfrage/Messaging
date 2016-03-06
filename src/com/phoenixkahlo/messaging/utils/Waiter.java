package com.phoenixkahlo.messaging.utils;
import java.io.IOException;
import java.net.ServerSocket;

/*
 * Can be run to wait on a ServerSocket to accept clients and produce them through a ConnectionFactory
 */
public class Waiter extends Thread {

	private ConnectionFactory connectionFactory;
	private ServerSocket serverSocket;
	private volatile boolean shouldContinueRunning = true;

	public Waiter(ConnectionFactory connectionFactory, int port) {
		super("Waiter thread");
		this.connectionFactory = connectionFactory;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("ServerSocket successfully bound to " + port);
		} catch (IOException e) {
			System.err.println("Failed to bind ServerSocket");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void terminate() {
		shouldContinueRunning = false;
		interrupt();
	}
	
	@Override
	public void run() {
		System.out.println("Waiter waiting");
		while (shouldContinueRunning) {
			try {
				connectionFactory.createConnection(serverSocket.accept());
			} catch (IOException e) {
				System.err.println("Failed to accept socket");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
