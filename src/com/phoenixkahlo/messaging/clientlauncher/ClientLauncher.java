package com.phoenixkahlo.messaging.clientlauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.BinUtils;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.MessagingProtocol;
import com.phoenixkahlo.messaging.utils.UpdatingConstants;

/*
 * An object that can be used to attempt to launch the client
 * And a static method to use it to launch the client
 */
public class ClientLauncher {
	
	public static final String DEFAULT_IP = "71.87.82.153";
	public static final int DEFAULT_PORT = 39423;
	
	public static void main(String[] args) {
		if (args.length == 2)
			launch(args[0], Integer.parseInt(args[1]));
		else
			launch(DEFAULT_IP, DEFAULT_PORT);
	}
	
	/*
	 * Attempts to launch client until successfull
	 * Displays frame if taking too long
	 */
	public static void launch(String ip, int port) {
		ClientLauncherFrame frame = new ClientLauncherFrame();
		frame.startIn(3_000);
		
		boolean launched = false;
		while (!launched) {
			try {
				ClientLauncher launcher = new ClientLauncher(ip, port);
				launcher.start();
				launched = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		frame.dispose();
	}
	
	private Socket socket;
	
	/*
	 * Attempts to construct and connect to server
	 */
	public ClientLauncher(String ip, int port) throws IOException {
		socket = new Socket(ip, port);
	}
	
	/*
	 * Attempts to update and launch client, throws IOException if fails
	 */
	public void start() throws IOException {
		// Ensure app directory exists
		File directory = new File(FileUtils.getAppDirPath("Phoenix Messaging"));
		directory.mkdirs();
		// Ensure launcher is installed
		File launcher = new File(FileUtils.getAppDirPath("Phoenix Messaging" + File.separator + "LAUNCHER.jar"));
		if (!launcher.exists())
			writeTo(downloadLauncherFileData(), launcher);
		// Ensure client is installed
		File client = getVersionFile(downloadCurrentVersionNumber());
		if (!client.exists())
			writeTo(downloadCurrentVersionFileData(), client);
		// Launch client
		FileUtils.launchJar(client);
	}
	
	/*
	 * Downloads the current version number from the server
	 */
	private int downloadCurrentVersionNumber() throws IOException {
		// Request
		OutputStream out = socket.getOutputStream();
		out.write(UpdatingConstants.CURRENT_VERSION_NUMBER_REQUEST);
		// Download
		InputStream in = socket.getInputStream();
		byte[] bytes = new byte[4];
		in.read(bytes);
		// Return
		return BinUtils.bytesToInt(bytes);
	}
	
	/*
	 * Downloads the bytes of the current version jar from the server
	 */
	private byte[] downloadCurrentVersionFileData() throws IOException {
		// Request
		OutputStream out = socket.getOutputStream();
		out.write(UpdatingConstants.CURRENT_VERSION_FILE_REQUEST);
		//Download
		InputStream in = socket.getInputStream();
		byte[] data = MessagingProtocol.readByteArray(in);
		// Return
		return data;
	}
	
	/*
	 * Downloads the bytes of the launcher jar from the server
	 */
	private byte[] downloadLauncherFileData() throws IOException {
		// Request
		OutputStream out = socket.getOutputStream();
		out.write(UpdatingConstants.LAUNCHER_FILE_REQUEST);
		// Download
		InputStream in = socket.getInputStream();
		byte[] data = MessagingProtocol.readByteArray(in);
		// Return
		return data;
	}

	/*
	 * Gets the file associated with the given version of the client
	 */
	private File getVersionFile(int version) {
		String fileID = Integer.toString(version);
		while (fileID.length() < 4) fileID = '0' + fileID;
		return new File(FileUtils.getAppDirPath("Phoenix Messaging" + File.separator + 'V' + fileID + ".jar"));
	}
	
	/*
	 * Ensures the file exists, and writes to it
	 * Does not ensure that the directories leading up to the file exist
	 */
	private void writeTo(byte[] data, File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();
		} catch (IOException e) {
			System.err.println("Unexpected IOException in file writing");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*
	 * Launches the given jar file
	 */
	
	
}