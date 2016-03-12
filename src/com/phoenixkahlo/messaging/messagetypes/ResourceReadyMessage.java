package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.IntCallback;
import com.phoenixkahlo.messaging.utils.ResourceRepository;

/*
 * Provides access to the ResourceRepository and frame width-getter callback, whether on Client or Server
 */
public abstract class ResourceReadyMessage extends SourcedMessage {

	private ResourceRepository repository;
	private IntCallback frameWidthGetter;
	
	public ResourceReadyMessage(String name) {
		super(name);
	}

	public ResourceReadyMessage(InputStream in) throws IOException {
		super(in);
	}

	public ResourceRepository getResourceRepository() {
		return repository;
	}

	public IntCallback getFrameWidthGetter() {
		return frameWidthGetter;
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		repository = connection.getServer().getResourceRepository();
		frameWidthGetter = () -> connection.getServer().getFrame().getWidth();
		super.effectServer(connection);
	}

	@Override
	public void effectClient(Client client) {
		repository = client.getResourceRepository();
		frameWidthGetter = () -> client.getFrame().getWidth();
		super.effectClient(client);
	}
	
}
