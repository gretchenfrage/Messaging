package com.phoenixkahlo.messaging.testing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.messagetypes.TextMessage;

public class SendableTester {

	public static void main(String[] args) throws IOException {
		SendableCoder coder = new SendableCoder();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		coder.write(new TextMessage("phoenix", "i am god"), out);
		//coder.write(new TextMessage("", ""), out);
		//coder.write(Pulse.INSTANCE, out);
		
		byte[] data = out.toByteArray();
		
		InputStream in = new ByteArrayInputStream(data);
		List<Sendable> sendables = new ArrayList<Sendable>();
		while (in.available() > 0) {
			sendables.add(coder.read(in));
		}
		System.out.println(sendables);
	}

}
