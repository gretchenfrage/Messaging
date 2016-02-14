package com.phoenixkahlo.messaging.server;

import java.util.HashMap;
import java.util.Map;

import com.phoenixkahlo.messaging.server.commands.Command;
import com.phoenixkahlo.messaging.server.commands.Nickname;

/*
 * Called upon to execute commands when recieved messages beginning with a slash
 */
public class CommandExecuter {

	private Map<String, Command> commands = new HashMap<String, Command>();
	
	public CommandExecuter(Server server) {
		commands.put("nickname", new Nickname());
	}
	
	/*
	 * Returns the Command object mapped to the command String, which may be null
	 */
	public Command getCommand(String command) {
		return commands.get(command);
	}
	
	/*
	 * Command is raw, in "/[command] [args]" form
	 */
	public void execute(String sender, String rawCommand) {
		// Each space-seperated section after the slash
		String[] parts = rawCommand.substring(1).split(" ");
		// All the parts except the first
		String[] args = new String[parts.length - 1];
		for (int i = 0; i < args.length; i++) args[i] = parts[i + 1];
		// The Command to execute
		Command command = commands.get(parts[0]);
		// Execute it
		if (command != null) command.execute(sender, args);
	}
	
}
