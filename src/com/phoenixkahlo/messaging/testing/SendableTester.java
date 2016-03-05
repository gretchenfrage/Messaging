package com.phoenixkahlo.messaging.testing;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.phoenixkahlo.messaging.messagetypes.Pulse;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

public class SendableTester {

	public static void main(String[] args) throws IOException {
		SendableCoder coder = new SendableCoder();
		Sendable pulse = Pulse.INSTANCE;
		Sendable text = new com.phoenixkahlo.messaging.messagetypes.TextMessage("phoenix", "i am god");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		coder.write(text, out);
		coder.write(pulse, out);
		byte[] bytes = out.toByteArray();
		for (byte b : bytes) {
			System.out.print(b + ":");
		}
		System.out.println();
		InputStream in = new ByteArrayInputStream(bytes);
		List<Sendable> sendables = new ArrayList<Sendable>();
		while (in.available() > 0) {
			sendables.add(coder.read(in));
		}
		System.out.println(sendables);
	}

}
