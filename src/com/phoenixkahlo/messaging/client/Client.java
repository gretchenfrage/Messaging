package com.phoenixkahlo.messaging.client;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.client.commands.ClientCommandExecuter;
import com.phoenixkahlo.messaging.messagetypes.ActivateConnectionCommand;
import com.phoenixkahlo.messaging.messagetypes.DeactivateConnectionCommand;
import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.NicknameChange;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.utils.FileUtils;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents a complete, runnable messaging client
 * Version 0007
 */
public class Client {
	
	public static final String DEFAULT_IP = "71.87.82.153";
	public static final int DEFAULT_PORT = 39422;
		
	private SendableCoder coder;
	private Socket socket;
	private ClientListener listener;
	private PropertiesRepository properties;
	private ClientCommandExecuter commandExecuter;
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
		coder = new SendableCoder();
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			relaunch();
		}
		listener = new ClientListener(this, socket, coder);
		try {
			properties = new PropertiesRepository();
		} catch (IOException e) {
			System.err.println("Failed to create repository");
			e.printStackTrace();
			System.exit(1);
		}
		commandExecuter = new ClientCommandExecuter(this);
		frame = new ClientFrame(this, properties, commandExecuter);
	}
	
	public void start() {
		if (properties.get("nickname") != null)
			send(new NicknameChange(properties.get("nickname")));
		send(new ActivateConnectionCommand());
		listener.start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				send(new DeactivateConnectionCommand());
			}
		}, "Shutdown hook thread"));
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
			frame.scrollToBottom();
			System.out.println("SENDABLE SENT: " + sendable);
		} catch (IOException e) {
			relaunch();
		}
	}
	
	public static void relaunch() {
		File launcher = new File(FileUtils.getAppDirPath(Protocol.APP_DIR_NAME + File.separator + "LAUNCHER.jar"));
		FileUtils.launchJar(launcher);
		System.exit(0);
	}

	public PropertiesRepository getProperties() {
		return properties;
	}
	
	public ClientFrame getFrame() {
		return frame;
	}
	
}
