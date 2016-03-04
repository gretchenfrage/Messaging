package com.phoenixkahlo.messaging.server;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.MessageOld;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.utils.FileUtils;

/*
 * Stores messages and saves them in a file system
 * It is trusted that null will never be passed to any methods
 */
public class MessageRepository {

	private List<Message> messages = new ArrayList<Message>();
	private File file;
	
	public MessageRepository() {
		file = new File(FileUtils.getParallelPath("MessageRepository.dat"));
		try {
			file.createNewFile();
			InputStream in = new FileInputStream(file);
			while (in.available() > 0) {
				messages.add(SendableCoder.readMessage(in));
			}
		} catch (IOException e) {
			System.err.println("Failed to read from MessageRepository.dat");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addMessage(Message message) {
		messages.add(message);
		try {
			OutputStream out = new FileOutputStream(file, true);
			message.write(out);
		} catch (IOException e) {
			System.err.println("Failed to write to MessageRepository.dat");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public MessageOld[] getAllMessages() {
		synchronized (messages) {
			MessageOld[] out = new MessageOld[messages.size()];
			for (int i = 0; i < out.length; i++) {
				out[i] = messages.get(i);
			}
			return out;
		}
	}
	
}
