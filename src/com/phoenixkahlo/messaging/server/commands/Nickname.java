package com.phoenixkahlo.messaging.server.commands;

import java.util.HashMap;
import java.util.Map;

/*
 * Called when users execute the /nickname command
 * Maps IP addresses to their nicknames to be used in printing, displaying, and sending
 */
public class Nickname implements Command {

	// Maps IPs to Nicknames
	private Map<String, String> nicknames = new HashMap<String, String>();
	
	@Override
	public void execute(String sender, String[] arguments) {
		nicknames.put(sender, arguments[0]);
	}
	
	/*
	 * Returns the nickname of the IP if has nickname, else returns the IP
	 */
	public String nicknameOf(String IP) {
		String nickname = nicknames.get(IP);
		return nickname != null ? nickname : IP;
	}
	
	public boolean hasNickname(String IP) {
		return nicknames.containsKey(IP);
	}
	
}
