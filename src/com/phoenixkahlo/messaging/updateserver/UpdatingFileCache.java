package com.phoenixkahlo.messaging.updateserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.utils.BinUtils;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Caches the files the client might request and is responsible for writing them to streams
 * Writes fully formatted, no need for padding or anything
 */
public class UpdatingFileCache {

	private byte[] clientBytes;
	private byte[] launcherBytes;
	private int currentVersionNumber;
	
	public UpdatingFileCache(int currentVersionNumber) throws IOException {
		this.currentVersionNumber = currentVersionNumber;
		
		// Create the client bytes
		String clientPath = Integer.toString(currentVersionNumber);
		while (clientPath.length() < 4) clientPath = "0" + clientPath;
		clientPath = FileUtils.getParallelPath("V" + clientPath + ".jar");
		
		File clientFile = new File(clientPath);
		clientBytes = new byte[(int) clientFile.length()];
		InputStream clientIn = new FileInputStream(clientFile);
		clientIn.read(clientBytes);
		clientIn.close();
		
		// Create the launcher bytes
		File launcherFile = new File(FileUtils.getParallelPath("LAUNCHER.jar"));
		launcherBytes = new byte[(int) launcherFile.length()];
		InputStream launcherIn = new FileInputStream(launcherFile);
		launcherIn.read(launcherBytes);
		launcherIn.close();
	}
	
	public void writeClient(OutputStream out) throws IOException {
		Protocol.writeByteArray(clientBytes, out);
	}
	
	public void writeLauncher(OutputStream out) throws IOException {
		Protocol.writeByteArray(launcherBytes, out);
	}
	
	public void writeCurrentVersionNumber(OutputStream out) throws IOException {
		out.write(BinUtils.intToBytes(currentVersionNumber));
	}
	
}
