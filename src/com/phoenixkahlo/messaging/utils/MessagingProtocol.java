package com.phoenixkahlo.messaging.utils;
import java.io.File;
import java.io.FileInputStream;
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
	
	/*
	 * Specify a file path, it returns it's contents as
	 * [int contents length, contents]
	 * Assumes file is under Integer.MAX_VALUE bytes (2.15 gigabytes) in size
	 * Although any file of that size really shouldn't be entirely loaded into the memory at once
	 */
	public static byte[] pathToData(String path) throws IOException {
		InputStream in = null;
		try {
			File file = new File(path);
			
			byte[] body = new byte[(int) file.length()];
			in = new FileInputStream(file);
			in.read(body);
			
			byte[] head = BinOps.intToBytes(body.length);
			
			byte[] out = new byte[body.length + 4];
			for (int i = 0; i < 4; i++) {
				out[i] = head[i];
			}
			for (int i = 0; i < body.length; i++) {
				out[i + 4] = body[i];
			}
			return out;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}
	
}
