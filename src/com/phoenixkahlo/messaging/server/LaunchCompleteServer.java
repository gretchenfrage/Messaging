package com.phoenixkahlo.messaging.server;

import com.phoenixkahlo.messaging.updateserver.UpdatingServer;

/*
 * Main class for launching both a chat server and an updating server
 */
public class LaunchCompleteServer {

	private LaunchCompleteServer() {}
	
	/*
	 * Either no args or chat port then updating port
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			Server.main(new String[] {args[0]});
			UpdatingServer.main(new String[] {args[1]});
		} else {
			Server.main(new String[0]);
			UpdatingServer.main(new String[0]);
		}
	}
	
}
