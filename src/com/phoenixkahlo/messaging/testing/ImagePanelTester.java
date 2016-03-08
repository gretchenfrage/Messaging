package com.phoenixkahlo.messaging.testing;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.phoenixkahlo.messaging.server.ServerFrame;
import com.phoenixkahlo.messaging.swingutils.ImagePanel;

public class ImagePanelTester {

	public static void main(String[] args) throws IOException {
		ServerFrame frame = new ServerFrame();
		//JFrame frame = new JFrame();
		File image = new File("freeman.jpeg");
		ImagePanel panel = new ImagePanel(image);
		ImageIcon icon = new ImageIcon("freeman.jpeg");
		JLabel label = new JLabel(icon);
		//frame.addComponent(label);
		frame.addComponent(panel);
		frame.start();
		/*
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		*/
	}
	
}
