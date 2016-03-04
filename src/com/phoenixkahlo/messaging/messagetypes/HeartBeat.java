package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

public class HeartBeat implements Sendable {

	public static final HeartBeat INSTANCE = new HeartBeat();
	
	private HeartBeat() {}
	
	@Override
	public void write(OutputStream out) throws IOException {
		out.write(Protocol.HEARTBEAT_HEADER);
	}

	@Override
	public void effectClient(Client client) {}

	@Override
	public void effectServer(MessagingConnection connection) {}

}
