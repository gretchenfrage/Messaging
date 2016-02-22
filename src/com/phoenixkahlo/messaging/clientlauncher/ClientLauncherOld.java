package com.phoenixkahlo.messaging.clientlauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.ApplicationDirectoryGetter;
import com.phoenixkahlo.messaging.utils.BinOps;

public class ClientLauncherOld {
	
	public static final String DEFAULT_IP = "71.87.82.153";
	public static final int DEFAULT_PORT = 39423;
	
	public static void main(String[] args) {
		if (args.length == 1) {
			ClientLauncherOld launcher = new ClientLauncherOld();
			launcher.start(args[0].split(":")[0], Integer.parseInt(args[0].split(":")[1]));
		} else {
			ClientLauncherOld launcher = new ClientLauncherOld();
			launcher.start(DEFAULT_IP, DEFAULT_PORT);
		}
	}
	
	public void start(String ip, int port) {
		Socket socket = null;
		OutputStream fileOut = null;
		try {
			// Open the connection, which will prompt the update server to send the data
			// Data is [current version number, jar length, jar]
			System.out.println("Attempting to connect to " + ip + ":" + port);
			socket = new Socket(ip, port);
			InputStream socketIn = socket.getInputStream();
			// Get the current version number from the server
			byte[] currentVersionNumberBytes = new byte[4];
			socketIn.read(currentVersionNumberBytes);
			int currentVersionNumber = BinOps.bytesToInt(currentVersionNumberBytes);
			// Create the file representing the current version
			File currentVersion = new File(getPathOfVersionNumber(currentVersionNumber));
			// If the current version is not installed, install it
			if (!currentVersion.exists()) {
				// Get the data from the socket into a byte array
				byte[] jarLengthBytes = new byte[4];
				socketIn.read(jarLengthBytes);
				int jarLength = BinOps.bytesToInt(jarLengthBytes);
				byte[] jarBytes = new byte[jarLength];
				socketIn.read(jarBytes);
				// Create the file and save it to it
				currentVersion.createNewFile();
				fileOut = new FileOutputStream(currentVersion);
				fileOut.write(jarBytes);
			}
			// Launch it
			ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", currentVersion.getAbsolutePath());
			processBuilder.start();
			System.out.println("Launch process completed");
		} catch (IOException e) {
			System.err.println("IOException in launching client");
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {}
			}
		}
	}
	
	/*
	 * Returns the path of the jar file for the given version number of the client, regardless of if the file exists
	 * Version 307 would be stored as V0307.jar
	 */
	private static String getPathOfVersionNumber(int version) {
		String out = Integer.toString(version);
		while (out.length() < 4) out = '0' + out;
		return ApplicationDirectoryGetter.getPath("phoenix messaging") + File.separatorChar + "V" + out + ".jar";
	}
	
}
