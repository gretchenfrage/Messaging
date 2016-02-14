package com.phoenixkahlo.messaging.utils;
import java.net.ServerSocket;

import com.phoenixkahlo.messaging.server.Server;

import java.io.IOException;

/*
 * Can be run to wait on a ServerSocket to accept clients and produce them through a ConnectionFactory
 */
public class Waiter extends Thread {

	private ConnectionFactory connectionFactory;
	private ServerSocket serverSocket;

	public Waiter(ConnectionFactory connectionFactory, int port) {
		super("Waiter thread");
		this.connectionFactory = connectionFactory;
		try {
			serverSocket = new ServerSocket(port);
			if (Server.PRINT_DEBUG)
				System.out.println("ServerSocket successfully bound to " + port);
		} catch (IOException e) {
			System.err.println("Failed to bind ServerSocket");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		if (Server.PRINT_DEBUG)
			System.out.println("Waiter waiting");
		while (true) {
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
