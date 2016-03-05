package com.phoenixkahlo.messaging.client.commands;

import java.util.HashMap;
import java.util.Map;

import com.phoenixkahlo.messaging.client.Client;

public class ClientCommandExecuter {

	private Map<String, ClientCommand> map = new HashMap<String, ClientCommand>();
	private Client client;
	
	public ClientCommandExecuter(Client client) {
		this.client = client;
		
		map.put("nickname", new NicknameCommand());
	}
	
	public void parse(String raw) throws BadCommandException {
		String[] parts = raw.substring(1).split(" ");

		String commandName = parts[0];

		String[] args = new String[parts.length - 1];
		for (int i = 0; i < args.length; i++) args[i] = parts[i + 1];

		ClientCommand command = map.get(commandName);
		if (command == null) throw new BadCommandException();
		command.effect(client, args);
	}
	
}
