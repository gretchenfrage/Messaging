package com.phoenixkahlo.messaging;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 * Static class for reading and writing messages in binary
 */
public class MessagingProtocol {

	private MessagingProtocol() {}
	
	private static byte[] intToBytes(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	private static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	private static byte[] stringToBytes(String string) {
    	return string.getBytes(StandardCharsets.UTF_8);
    }
    
	private static String bytesToString(byte[] bytes) {
    	return new String(bytes, StandardCharsets.UTF_8);
    }
	
	public static void writeMessage(OutputStream out, String message) throws IOException {
		byte[] body = stringToBytes(message);
		byte[] header = intToBytes(body.length);
		out.write(header);
		out.write(body);
	}
	
	public static String readMessage(InputStream in) throws IOException {
		byte[] header = new byte[4];
		in.read(header);
		byte[] body = new byte[bytesToInt(header)];
		in.read(body);
		return bytesToString(body);
	}
	
}
