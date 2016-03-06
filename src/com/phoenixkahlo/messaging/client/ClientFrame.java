package com.phoenixkahlo.messaging.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.phoenixkahlo.messaging.client.commands.BadCommandException;
import com.phoenixkahlo.messaging.client.commands.ClientCommandExecuter;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.TextMessage;
import com.phoenixkahlo.messaging.utils.ScrollablePanel;
import com.phoenixkahlo.messaging.utils.VerticalLayout;

public class ClientFrame extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = -4932951363118395533L;

	private Client client;
	private PropertiesRepository properties;
	private ClientCommandExecuter commandExecuter;
	
	private ScrollablePanel displayArea;
	private JTextArea enterArea;
	private JScrollBar scrollBar;
	
	public ClientFrame(Client client, PropertiesRepository properties, ClientCommandExecuter commandExecuter) {
		// Create frame
		super("Messaging Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Memorize args
		this.client = client;
		this.properties = properties;
		this.commandExecuter = commandExecuter;
		
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
		scrollToBottom();
	}
	
	public void addComponent(Component component) {
		boolean isGluedToBottom = scrollBar.getValue() + scrollBar.getModel().getExtent() == scrollBar.getMaximum();
		
		displayArea.add(component);
		revalidate();
		displayArea.repaint();
		
		if (isGluedToBottom) scrollToBottom();
	}

	public void scrollToBottom() {
		scrollBar.setValue(scrollBar.getMaximum());
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			event.consume();
			String text = enterArea.getText();
			enterArea.setText("");
			if (text.toCharArray()[0] == '/') {
				try {
					commandExecuter.parse(text);
				} catch (BadCommandException e) {
					//TODO: print bad command raw message
					System.out.println("BadCommandException at " + text);
				}
			} else {
				try {
					Sendable message = new TextMessage(properties.get("nickname", InetAddress.getLocalHost().toString()), text);
					client.send(message);
				} catch (UnknownHostException e) {
					System.err.println("Couldn't find local address");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
	
}