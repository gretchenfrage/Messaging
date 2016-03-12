package com.phoenixkahlo.messaging.swingutils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePanel extends ScrollablePanel {

	private static final long serialVersionUID = -7641525774844957585L;

	private BufferedImage image;

	public ImagePanel(File image) throws IOException {
		this.image = ImageIO.read(image);
	}
	
	public ImagePanel(BufferedImage image) throws IOException {
		this.image = image;
	}
	
	public int widthToHeight(int width) {
		return (int) ((double) width / image.getWidth() * image.getHeight());
	}
	
	public int heightToWidth(int height) {
		return (int) ((double) height / image.getHeight() * image.getWidth());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}
	
	@Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (getWidth() < heightToWidth(getHeight())) { // If the panel width might be limiting the draw size
        	graphics.drawImage(image, 0, 0, getWidth(), widthToHeight(getWidth()), Color.WHITE, null);
        } else { // If the panel height might be limiting the draw size
        	graphics.drawImage(image, 0, 0, heightToWidth(getHeight()), getHeight(), Color.WHITE, null);
        }
    }
	
}