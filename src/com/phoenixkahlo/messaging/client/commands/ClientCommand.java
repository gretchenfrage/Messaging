package com.phoenixkahlo.messaging.client.commands;

import com.phoenixkahlo.messaging.client.Client;

/*
 * Each instance of ClientCommand represents a command and is registered with the ClientCommandParser
 */
public interface ClientCommand {

	void effect(Client client, String[] args) throws BadCommandException;
	
}
