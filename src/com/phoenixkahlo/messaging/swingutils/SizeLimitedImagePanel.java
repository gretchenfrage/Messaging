package com.phoenixkahlo.messaging.swingutils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.phoenixkahlo.messaging.utils.IntCallback;

public class SizeLimitedImagePanel extends ImagePanel {

	private static final long serialVersionUID = -6872488998230466834L;
	
	private IntCallback widthLimiter;
	private IntCallback heightLimiter;
	
	public SizeLimitedImagePanel(File image, IntCallback widthLimiter, IntCallback heightLimiter) throws IOException {
		super(image);
		this.widthLimiter = widthLimiter;
		this.heightLimiter = heightLimiter;
	}
	
	public SizeLimitedImagePanel(BufferedImage image, IntCallback widthLimiter, IntCallback heightLimiter) throws IOException {
		super(image);
		this.widthLimiter = widthLimiter;
		this.heightLimiter = heightLimiter;
	}
	
	@Override
	public Dimension getPreferredSize() {
		int widthLimit = widthLimiter.invoke();
		int heightLimit = heightLimiter.invoke();
		if (widthLimit < heightToWidth(heightLimit)) { // If the width limiter might be limiting the draw size
			return new Dimension(widthLimit, widthToHeight(widthLimit));
		} else { // If the height limiter might be limiting the draw size
			return new Dimension(heightToWidth(heightLimit), heightLimit);
		}
	}

}
