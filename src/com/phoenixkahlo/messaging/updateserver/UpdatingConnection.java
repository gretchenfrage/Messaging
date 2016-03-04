package com.phoenixkahlo.messaging.updateserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.utils.Protocol;

public class UpdatingConnection extends Thread {
	
	private Socket socket;
	private UpdatingFileCache fileCache;
	
	public UpdatingConnection(Socket socket, UpdatingFileCache fileCache) {
		this.socket = socket;
		this.fileCache = fileCache;
	}
	
	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			while (true) {
				int request = in.read();
				if (request == Protocol.CURRENT_VERSION_NUMBER_REQUEST) {
					fileCache.writeCurrentVersionNumber(out);
				} else if (request == Protocol.CURRENT_VERSION_FILE_REQUEST) {
					fileCache.writeClient(out);
				} else if (request == Protocol.LAUNCHER_FILE_REQUEST) {
					fileCache.writeLauncher(out);
				}
			}
		} catch (IOException e) {
			System.out.println(socket.getInetAddress() + " disconnected");
		}
	}
	
}
