package com.phoenixkahlo.messaging.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Static class for reading and writing messages in binary
 */
public class MessagingProtocol {

	private MessagingProtocol() {}

	public static void writeMessage(OutputStream out, String message) throws IOException {
		byte[] body = BinOps.stringToBytes(message);
		byte[] header = BinOps.intToBytes(body.length);
		out.write(header);
		out.write(body);
	}
	
	public static String readMessage(InputStream in) throws IOException {
		byte[] header = new byte[4];
		in.read(header);
		byte[] body = new byte[BinOps.bytesToInt(header)];
		in.read(body);
		return BinOps.bytesToString(body);
	}
	
}
