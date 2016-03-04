package com.phoenixkahlo.messaging.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.ui.tabbedui.VerticalLayout;

import com.phoenixkahlo.messaging.messagetypes.ChatMessageOld;
import com.phoenixkahlo.messaging.messagetypes.MessageOld;
import com.phoenixkahlo.messaging.utils.ScrollablePanel;

public class ClientFrame extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = -4932951363118395533L;

	private Client client;
	
	private ScrollablePanel displayArea;
	private JTextArea enterArea;
	@SuppressWarnings("unused") // For later implementation of autoscrolling
	private JScrollBar scrollBar;
	
	public ClientFrame(Client client) {
		// Create frame
		super("Messaging Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Memorize client
		this.client = client;
		
		// Create main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		// Create display area
		displayArea = new ScrollablePanel(new VerticalLayout());
		displayArea.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
		displayArea.setBackground(Color.WHITE);
		
		// Wrap display area in scroll pane
		JScrollPane displayScrollPane = new JScrollPane(displayArea);
		scrollBar = displayScrollPane.getVerticalScrollBar();
		displayScrollPane.setPreferredSize(new Dimension(500, 500));
		displayScrollPane.getViewport().setBackground(Color.WHITE);
		
		// Add display area scroll pane to main panel
		mainPanel.add(displayScrollPane);
		
		// Create entering area
		enterArea = new JTextArea();
		enterArea.addKeyListener(this);
		enterArea.setLineWrap(true);
		enterArea.setWrapStyleWord(true);
		
		// Wrap enter area in scroll pane
		JScrollPane enterScrollPane = new JScrollPane(enterArea);
		enterScrollPane.setPreferredSize(new Dimension(500, 80));
		enterScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		// Add enter area scroll pane to main panel
		mainPanel.add(enterScrollPane);
		
		// Add main panel
		add(mainPanel);
		
		// Pack and center
		pack();
		setLocationRelativeTo(null);
	}
	
	public void start() {
		setVisible(true);
	}
	
	public void addComponent(Component component) {
		displayArea.add(component);
		displayArea.revalidate();
		displayArea.repaint();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			event.consume();
			MessageOld message = new ChatMessageOld("", enterArea.getText());
			enterArea.setText("");
			client.send(message);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
	
}