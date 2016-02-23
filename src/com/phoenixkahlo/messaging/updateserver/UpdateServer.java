package com.phoenixkahlo.messaging.updateserver;

import java.util.ArrayList;
import java.util.List;

import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable update server which connects to clients and fulfills data requests
 */
public class UpdateServer {

	/*
	 * Disconnects inactive clients
	 */
	private class ConnectionsCleaner extends Thread {
		
		@Override
		public void run() {
			for (int i = connections.size() - 1; i >= 0; i--) {
				if (connections.get(i).getLastInteractionTime() < System.currentTimeMillis() - 60_000)
					connections.remove(i);
			}
			try {
				Thread.sleep(30_000);
			} catch (InterruptedException e) {
				System.err.println("ConnectionsCleaner thread interuppted");
				e.printStackTrace();
			}
		}
		
	}
	
	public static final int DEFAULT_PORT = 39423;
	public static final int CURRENT_VERSION_NUMBER = 1;
	
	private Waiter waiter;
	private List<UpdatingConnection> connections = new ArrayList<UpdatingConnection>();
	private ConnectionsCleaner cleaner = new ConnectionsCleaner();
	
	/*
	 * First argument is port, second argument is version number
	 * Both arguments are optional
	 */
	public static void main(String[] args) {
		UpdateServer server = null;
		if (args.length == 1)
			server = new UpdateServer(Integer.parseInt(args[0]));
		else
			server = new UpdateServer(DEFAULT_PORT);
		server.start();
	}
	
	public UpdateServer(int port) {
		waiter = new Waiter(new UpdatingConnectionFactory(this), port);
	}
	
	public void start() {
		waiter.start();
		cleaner.start();
	}
	
	/*
	 * To be called by UpdatingConnectionFactory
	 */
	public void addConnection(UpdatingConnection connection) {
		connections.add(connection);
	}
	
}
