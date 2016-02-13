package com.phoenixkahlo.messaging.client;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientFrame extends JFrame implements KeyListener {
	
	private Client client;
	
	private JTextArea displayArea;
	private JTextArea enterArea;
	private JScrollBar scrollBar;
	
	public ClientFrame(Client client) {
		// Create frame
		super("Messaging Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.client = client;
		
		// Create main panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// Create display area
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);
		
		// Wrap display area in scroll pane
		JScrollPane displayScrollPane = new JScrollPane(displayArea);
		scrollBar = displayScrollPane.getVerticalScrollBar();
		displayScrollPane.setPreferredSize(new Dimension(500, 500));
		
		// Add display area scroll pane
		panel.add(displayScrollPane);
		
		// Create entering area
		enterArea = new JTextArea();
		enterArea.addKeyListener(this);
		enterArea.setLineWrap(true);
		enterArea.setWrapStyleWord(true);
		
		// Wrap enter area in scroll pane
		JScrollPane enterScrollPane = new JScrollPane(enterArea);
		enterScrollPane.setPreferredSize(new Dimension(500, 80));
		enterScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		// Add enter area scroll pane
		panel.add(enterScrollPane);
		
		// Add panel
		add(panel);
		
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

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			event.consume();
			String message = enterArea.getText();
			enterArea.setText("");
			client.sendMessage(message);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
	
}
