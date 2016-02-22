package com.phoenixkahlo.messaging.updateserver;

import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable update server which connects to clients and fulfills data requests
 */
public class UpdateServer {

	public static final int DEFAULT_PORT = 39423;
	public static final int CURRENT_VERSION_NUMBER = 1;
	
	private Waiter waiter;
	
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
		waiter = new Waiter(new UpdatingConnectionFactory(), port);
	}
	
	public void start() {
		waiter.start();
	}
	
}
