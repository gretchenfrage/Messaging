package com.phoenixkahlo.messaging.messagetypes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.phoenixkahlo.messaging.utils.FileUtils;

public class ImageResource extends ResourceOld {
	
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
	
	public BufferedImage getImage() throws IOException {
		return ImageIO.read(new ByteArrayInputStream(getData()));
	}
	
}
