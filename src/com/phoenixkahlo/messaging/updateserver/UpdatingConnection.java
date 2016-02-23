package com.phoenixkahlo.messaging.updateserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.BinOps;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.MessagingProtocol;

public class UpdatingConnection extends Thread {
	
	public static final int CURRENT_VERSION_NUMBER_REQUEST = 0;
	public static final int CURRENT_VERSION_FILE_REQUEST = 1;
	public static final int LAUNCHER_FILE_REQUEST = 2;
	
	private static final byte[] CURRENT_VERSION_NUMBER_DATA = BinOps.intToBytes(UpdateServer.CURRENT_VERSION_NUMBER);
	private static final byte[] CURRENT_VERSION_FILE_DATA;
	static {
		String path = Integer.toString(UpdateServer.CURRENT_VERSION_NUMBER);
		while (path.length() < 4) path = "0" + path;
		path = "V" + path + ".jar";
		path = FileUtils.getParallelPath(path);
		try {
			CURRENT_VERSION_FILE_DATA = MessagingProtocol.pathToData(path);
		} catch (IOException e) {
			System.err.println("IOException within static initializer for CURRENT_VERSION_FILE_DATA");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();
		}
	}
	private static final byte[] LAUNCHER_FILE_DATA;
	static {
		try {
			LAUNCHER_FILE_DATA = MessagingProtocol.pathToData(FileUtils.getParallelPath("LAUNCH.jar"));
		} catch (IOException e) {
			System.err.println("IOException within static initializer for LAUNCHER_FILE_DATA");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();
		}
	}
	
	private Socket socket;
	// The timestamp of the last interaction with the client
	// If exceeds 60 seconds, the client will be disconnected
	private long lastInteractionTime = System.currentTimeMillis();
	
	public UpdatingConnection(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
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
				lastInteractionTime = System.currentTimeMillis();
			}
		} catch (IOException e) {
			System.out.println(socket.getInetAddress() + " disconnected");
		}
	}
	
	public long getLastInteractionTime() {
		return lastInteractionTime;
	}
	
}
