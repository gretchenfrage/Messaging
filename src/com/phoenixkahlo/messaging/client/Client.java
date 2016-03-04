package com.phoenixkahlo.messaging.client;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.MessageOld;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents a complete, runnable messaging client
 * Version 0002
 */
public class Client {
	
	public static final String DEFAULT_IP = "71.87.82.153";
	public static final int DEFAULT_PORT = 39424;
	
	public static final boolean PRINT_DEBUG = false;
	
	private Socket socket;
	private ClientListener listener;
	private ClientFrame frame;
	
	public static void main(String[] args) {
		if (args.length == 1) {
			Client client = new Client(args[0].split(":")[0], Integer.parseInt(args[0].split(":")[1]));
			client.start();
		} else {
			Client client = new Client(DEFAULT_IP, DEFAULT_PORT);
			client.start();
		}
	}
	
	public Client(String ip, int port) {
		System.out.println("Constructing client");
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			relaunch("Failed to connect to server", e);
		}
		listener = new ClientListener(this, socket);
		frame = new ClientFrame(this);
	}
	
	public void start() {
		listener.start();
		frame.start();
		System.out.println("~~~ MESSAGING CLIENT STARTED ~~~");
	}
	
	public void recieveMessage(Message message) {
		System.out.println(message);
		frame.addMessage(message.toComponent());
	}
	
	public void sendMessage(MessageOld message) {
		try {
			OutputStream out = socket.getOutputStream();
			message.write(out);
		} catch (IOException e) {
			relaunch("Server disconnected", e);
		}
	}
	
	public static void relaunch(String reason, Exception exception) {
		System.out.println(reason);
		exception.printStackTrace();
		File launcher = new File(FileUtils.getAppDirPath(Protocol.APP_DIR_NAME + File.separator + "LAUNCHER.jar"));
		FileUtils.launchJar(launcher);
		System.exit(0);
	}

}
