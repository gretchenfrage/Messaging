package com.phoenixkahlo.messaging.server;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.phoenixkahlo.messaging.utils.ScrollablePanel;
import com.phoenixkahlo.messaging.utils.VerticalLayout;

public class ServerFrame extends JFrame {

	private static final long serialVersionUID = -3811769629075136691L;

	private ScrollablePanel displayArea;
	private JScrollBar scrollBar;
	
	public ServerFrame() {
		// Create frame
		super("Messaging Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Create display area
		displayArea = new ScrollablePanel(new VerticalLayout());
		displayArea.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
		displayArea.setBackground(Color.WHITE);
		
		// Wrap display area in scroll pane
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollBar = scrollPane.getVerticalScrollBar();
		
		// Add scroll pane
		add(scrollPane);
		
		// Pack and center
		pack();
		setLocationRelativeTo(null);
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
	
	public void start() {
		setVisible(true);
	}
	
}