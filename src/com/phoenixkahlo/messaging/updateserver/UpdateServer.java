package com.phoenixkahlo.messaging.updateserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.BinOps;
import com.phoenixkahlo.messaging.utils.ConnectionFactory;
import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable update server
 */
public class UpdateServer {

	public final int PORT = 39423;
	public final int CURRENT_VERSION_NUMBER = 2;
	private byte[] dataToSend;
	
	public static void main(String[] args) {
		UpdateServer server = new UpdateServer();
		server.start();
	}
	
	public UpdateServer() {
		InputStream fileInputStream = null;
		try {
			// Data is [current version number, jar length, jar]
			// Setup the version number bytes
			byte[] versionNumberBytes = BinOps.intToBytes(CURRENT_VERSION_NUMBER);
			// Get the current version File
			String currentVersionPath = Integer.toString(CURRENT_VERSION_NUMBER);
			while (currentVersionPath.length() < 4) currentVersionPath = "0" + currentVersionPath;
			currentVersionPath = "V" + currentVersionPath + ".jar";
			File currentVersion = new File(currentVersionPath);
			// Setup the jar length bytes
			byte[] jarLengthBytes = BinOps.intToBytes((int) currentVersion.length());
			// Setup the jar bytes
			byte[] jarBytes = new byte[(int) currentVersion.length()];
			fileInputStream = new FileInputStream(currentVersion);
			fileInputStream.read(jarBytes);
			// Concatenate the 3 parts together into the dataToSendVariable
			dataToSend = new byte[jarBytes.length + 8];
			for (int i = 0; i < 4; i++) {
				dataToSend[i] = versionNumberBytes[i];
			}
			for (int i = 0; i < 4; i++) {
				dataToSend[i + 4] = jarLengthBytes[i];
			}
			for (int i = 0; i < jarBytes.length; i++) {
				dataToSend[i + 8] = jarBytes[i];
			}
		} catch (IOException e) {
			System.err.println("Failed to construct dataToSend");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {}
			}
		}
	}
	
	public void start() {
		ConnectionFactory factory = new ConnectionFactory() {
			@Override
			public void createConnection(Socket socket) {
				try {
					OutputStream out = socket.getOutputStream();
					out.write(dataToSend);
				} catch (IOException e) {
					System.out.println("IOException in sending data to " + socket.getInetAddress());
					e.printStackTrace();
				}
			}
		};
		Waiter waiter = new Waiter(factory, PORT);
		waiter.start();
		System.out.println("Update server started");
	}
	
}
