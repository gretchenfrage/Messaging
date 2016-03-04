package com.phoenixkahlo.messaging.server;

import com.phoenixkahlo.messaging.messagetypes.HeartBeat;

/*
 * Continuously makes sure all the clients are still connected by sending them empty messages
 * These messages will be rejected on the client end for being empty
 */
public class HeartBeatSender extends Thread {
	
	private Server server;
	
	public HeartBeatSender(Server server) {
		this.server = server;
		setPriority(MAX_PRIORITY);
	}
	
	@Override
	public void run() {
		while (true) {
			server.send(HeartBeat.INSTANCE);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
