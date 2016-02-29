package com.phoenixkahlo.messaging.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Static class for all the binary transmission protocols local to this messaging application
 */
public class MessagingProtocol {

	private MessagingProtocol() {}

	public static void writeMessage(OutputStream out, String message) throws IOException {
		byte[] body = BinUtils.stringToBytes(message);
		byte[] header = BinUtils.intToBytes(body.length);
		out.write(header);
		out.write(body);
	}
	
	public static String readMessage(InputStream in) throws IOException {
		byte[] header = new byte[4];
		in.read(header);
		byte[] body = new byte[BinUtils.bytesToInt(header)];
		in.read(body);
		return BinUtils.bytesToString(body);
	}
	
	/*
	 * Writes the array to the stream, prefixed with lenght
	 */
	public static void writeByteArray(byte[] array, OutputStream out) throws IOException {
		out.write(BinUtils.intToBytes(array.length));
		out.write(array);
	}
	
	public static byte[] readByteArray(InputStream in) throws IOException {
		byte[] head = new byte[4];
		in.read(head);
		byte[] body = new byte[BinUtils.bytesToInt(head)];
		in.read(body);
		return body;
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
			
			byte[] head = BinUtils.intToBytes(body.length);
			
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
