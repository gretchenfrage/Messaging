package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Sent by the client to the server to update the server's cache of the client's nickname
 */
public class NicknameChange implements Sendable {

	private String nickname;
	
	public NicknameChange(String newNickname) {
		this.nickname = newNickname;
	}
	
	public NicknameChange(InputStream in) throws IOException {
		nickname = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		Protocol.writeString(nickname, out);
	}

	@Override
	public void effectClient(Client client) {
		System.err.println("NicknameChange recieved by client");
	}

	@Override
	public void effectServer(MessagingConnection connection) {
		String oldName = connection.getNickname();
		String newName;
		if (nickname.isEmpty()) {
			newName = connection.getIP();
		} else {
			newName = nickname;
		}
		connection.getServer().recieveMessage(new RawTextMessage(oldName + " changed name to " + newName));
		connection.setNickname(newName);
	}

}
