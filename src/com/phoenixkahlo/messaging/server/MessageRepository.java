package com.phoenixkahlo.messaging.server;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Stores messages and saves them in a file system
 */
public class MessageRepository {

	private List<String> messages = new ArrayList<String>();
	private File file;
	
	public MessageRepository() {
		file = new File("MessageRepository.txt");
		Scanner scanner = null;
		try {
			file.createNewFile();
			scanner = new Scanner(file);
			while (scanner.hasNextLine())
				messages.add(scanner.nextLine());
		} catch (IOException e) {
			System.err.println("Failed to read from MessageRepository.txt");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
	
	public void addMessage(String message) {
		messages.add(message);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(message);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Failed to write to MessageRepository.txt");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		
	}
	
	public String[] getAllMessages() {
		synchronized (messages) {
			String[] out = new String[messages.size()];
			for (int i = 0; i < out.length; i++) {
				out[i] = messages.get(i);
			}
			return out;
		}
	}

}
