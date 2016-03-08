package com.phoenixkahlo.messaging.swingutils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	private static final long serialVersionUID = -7641525774844957585L;
	
	private BufferedImage image;

    public ImagePanel(File file) throws IOException {
         image = ImageIO.read(file);
    }
    
    
    
    @Override
	public Dimension getPreferredSize() {
    	//return new Dimension(image.getWidth(), image.getHeight());
    	return super.getPreferredSize();
    	
	}



	@Override
	public Dimension getMaximumSize() {
		// TODO Auto-generated method stub
		return super.getMaximumSize();
	}



	@Override
	public Dimension getMinimumSize() {
		// TODO Auto-generated method stub
		return super.getMinimumSize();
	}



	@Override
	public int getWidth() {
		//return super.getWidth();
		return image.getWidth();
	}



	@Override
	public int getHeight() {
		//return super.getHeight();
		return image.getHeight();
	}



	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, null);   
        g.drawImage(image, 0, 0, 100, 100, Color.BLUE, null);
    }
	
}