package com.phoenixkahlo.messaging.client.commands;

import java.io.IOException;

import com.phoenixkahlo.messaging.client.Client;

public class NicknameCommand implements ClientCommand {

	@Override
	public void effect(Client client, String[] args) throws BadCommandException {
		try {
			if (args.length == 0)
				client.getProperties().remove("nickname");
			else
				client.getProperties().set("nickname", args[0]);
		} catch (IOException e) {
			System.err.println("PropertiesRepository failure");
			e.printStackTrace();
			System.exit(1);
		}
	}

}
