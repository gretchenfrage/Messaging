package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;

import com.phoenixkahlo.messaging.utils.FileUtils;

public class ImageResource extends Resource {
	
	public ImageResource(String path) throws IOException {
		super(FileUtils.toBytes(path));
	}
	
	public ImageResource(InputStream in) throws IOException {
		super(in);
	}

	@Override
	public String getExtension() {
		return "png";
	}
	
}
