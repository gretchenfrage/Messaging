package com.phoenixkahlo.messaging.server;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerFrame extends JFrame {

	private static final long serialVersionUID = 3083411003324008560L;
	
	private JTextArea displayArea;
	
	public ServerFrame() {
		// Create frame
		super("Messaging Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Create display area
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);
		
		// Wrap display area in scroll pane
		JScrollPane displayScrollPane = new JScrollPane(displayArea);
		displayScrollPane.setPreferredSize(new Dimension(500, 500));
		
		// Add display area scroll pane
		add(displayScrollPane);
		
		// Pack and center
		pack();
		setLocationRelativeTo(null);
	}
	
	public void start() {
		setVisible(true);
	}
	
	public void println(String text) {
		displayArea.append(text + '\n');
	}
	
}