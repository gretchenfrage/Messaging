package com.phoenixkahlo.messaging.client;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents a complete, runnable messaging client
 * Version 0002
 */
public class Client {
	
	public static final String DEFAULT_IP = "localhost";//"71.87.82.153";
	public static final int DEFAULT_PORT = 39424;
	
	public static final boolean PRINT_DEBUG = false;
	
	private SendableCoder coder;
	private Socket socket;
	private ClientListener listener;
	private ClientFrame frame;
	private PropertiesRepository properties;
	
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
		coder = new SendableCoder();
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			relaunch("Failed to connect to server", e);
		}
		listener = new ClientListener(this, socket, coder);
		frame = new ClientFrame(this);
		properties = new PropertiesRepository();
	}
	
	public void start() {
		listener.start();
		frame.start();
		System.out.println("~~~ MESSAGING CLIENT STARTED ~~~");
	}
	
	public void recieveMessage(Message message) {
		System.out.println(message);
		frame.addComponent(message.toComponent());
	}
	
	public void send(Sendable sendable) {
		try {
			OutputStream out = socket.getOutputStream();
			coder.write(sendable, out);
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
