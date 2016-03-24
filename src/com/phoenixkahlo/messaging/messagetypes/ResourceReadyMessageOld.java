package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.IntCallback;
import com.phoenixkahlo.messaging.utils.ResourceRepositoryOld;

/*
 * Provides access to the ResourceRepository and frame width-getter callback, whether on Client or Server
 */
public abstract class ResourceReadyMessageOld extends SourcedMessage {

	private ResourceRepositoryOld repository;
	private IntCallback frameWidthGetter;
	
	public ResourceReadyMessageOld(String name) {
		super(name);
	}

	public ResourceReadyMessageOld(InputStream in) throws IOException {
		super(in);
	}

	public ResourceRepositoryOld getResourceRepository() {
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
