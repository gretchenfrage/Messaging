package com.phoenixkahlo.messaging.testing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.phoenixkahlo.messaging.messagetypes.RawTextMessage;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

public class MessagingRepositoryTxtToDat {

	public static void main(String[] args) throws Exception {
		File old = new File("C:\\Users\\Phoenix\\Desktop\\Messaging Server\\MessageRepository.txt");
		File neu = new File("C:\\Users\\Phoenix\\Desktop\\Messaging Server\\MessageRepository.dat");
		if (neu.exists())
			throw new RuntimeException("DAT FILE ALREADY EXISTS");
		else
			neu.createNewFile();
		
		
		Scanner scanner = new Scanner(old);
		List<RawTextMessage> raw = new ArrayList<RawTextMessage>();
		while (scanner.hasNextLine())
			raw.add(new RawTextMessage(scanner.nextLine()));
		scanner.close();
		
		SendableCoder coder = new SendableCoder();
		OutputStream out = new FileOutputStream(neu);
		for (RawTextMessage m : raw) {
			coder.write(m, out);
		}
		
	}
	
}
