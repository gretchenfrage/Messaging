package com.phoenixkahlo.messaging.updateserver;

import java.io.IOException;

import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable update server which connects to clients and fulfills data requests
 */
public class UpdatingServer {
	
	public static final int DEFAULT_PORT = 39423;
	public static final int CURRENT_VERSION_NUMBER = 3;
	
	private Waiter waiter;
	private UpdatingFileCache fileCache;
	
	/*
	 * First argument is port, second argument is version number
	 * Both arguments are optional
	 */
	public static void main(String[] args) {
		UpdatingServer server = null;
		if (args.length == 1)
			server = new UpdatingServer(Integer.parseInt(args[0]));
		else
			server = new UpdatingServer(DEFAULT_PORT);
		server.start();
	}
	
	public UpdatingServer(int port) {
		try {
			fileCache = new UpdatingFileCache(CURRENT_VERSION_NUMBER);
		} catch (IOException e) {
			System.err.println("IOException in constructing UpdatingFileCache");
			e.printStackTrace();
			System.exit(1);
		}
		waiter = new Waiter(new UpdatingConnectionFactory(fileCache), port);
	}
	
	public void start() {
		waiter.start();
	}
	
}
