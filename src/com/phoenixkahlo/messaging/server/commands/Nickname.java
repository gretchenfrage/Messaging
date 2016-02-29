package com.phoenixkahlo.messaging.server.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.phoenixkahlo.messaging.server.Server;
import com.phoenixkahlo.messaging.utils.FileUtils;

/*
 * Called when users execute the /nickname command
 * Maps IP addresses to their nicknames to be used in printing, displaying, and sending
 */
public class Nickname implements Command {

	// Maps IPs to Nicknames
	private Map<String, String> nicknames = new HashMap<String, String>();
	private File repository;
	private Server server;
	
	public Nickname(Server server) {
		this.server = server;
		repository = new File(FileUtils.getParallelPath("Nicknames.txt"));
		Scanner scanner = null;
		try {
			repository.createNewFile();
			scanner = new Scanner(repository);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				nicknames.put(line.substring(0, line.indexOf('=')), line.substring(line.indexOf('=') + 1));
			}
		} catch (IOException e) {
			System.err.println("Failed to read from Nicknames.txt");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
	
	@Override
	public void execute(String sender, String[] arguments) {
		nicknames.put(sender, arguments[0]);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(repository, true));
			writer.write(sender + '=' + arguments[0]);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Failed to write to Nicknames.txt");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		server.sendTo(sender, "set nickname to " + arguments[0]);
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
