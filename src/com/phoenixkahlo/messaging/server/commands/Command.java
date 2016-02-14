package com.phoenixkahlo.messaging.server.commands;

public interface Command {

	void execute(String sender, String[] arguments);
	
}
