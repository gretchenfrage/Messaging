package com.phoenixkahlo.messaging.updateserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.BinOps;
import com.phoenixkahlo.messaging.utils.MessagingProtocol;

public class UpdatingConnection {
	
	public static final int CURRENT_VERSION_NUMBER_REQUEST = 0;
	public static final int CURRENT_VERSION_FILE_REQUEST = 1;
	public static final int LAUNCHER_FILE_REQUEST = 2;
	
	private static final byte[] CURRENT_VERSION_NUMBER_DATA = BinOps.intToBytes(UpdateServer.CURRENT_VERSION_NUMBER);
	private static final byte[] CURRENT_VERSION_FILE_DATA;
	static {
		//TODO: fix in-jar location problems
		String path = Integer.toString(UpdateServer.CURRENT_VERSION_NUMBER);
		while (path.length() < 4) path = "0" + path;
		path = "V" + path + ".jar";
		try {
			CURRENT_VERSION_FILE_DATA = MessagingProtocol.pathToData(path);
		} catch (IOException e) {
			System.err.println("IOException within static initializer for CURRENT_VERSION_FILE_DATA");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException(); // Present only to satisfy "may not have been initialized" compile error
		}
	}
	private static final byte[] LAUNCHER_FILE_DATA;
	static {
		try {
			LAUNCHER_FILE_DATA = MessagingProtocol.pathToData("LAUNCH.jar");
		} catch (IOException e) {
			System.err.println("IOException within static initializer for LAUNCHER_FILE_DATA");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();
		}
	}
	
	private Socket socket;
	
	public UpdatingConnection(Socket socket) {
		this.socket = socket;
	}
	
	public void start() {
		//TODO: should avoid waiting forever
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			while (true) {
				int request = in.read();
				if (request == CURRENT_VERSION_NUMBER_REQUEST)
					out.write(CURRENT_VERSION_NUMBER_DATA);
				else if (request == CURRENT_VERSION_FILE_REQUEST)
					out.write(CURRENT_VERSION_FILE_DATA);
				else if (request == LAUNCHER_FILE_REQUEST)
					out.write(LAUNCHER_FILE_DATA);
			}
		} catch (IOException e) {
			System.out.println(socket.getInetAddress() + " abruptly disconnected from update server");
		}
	}
	
}
