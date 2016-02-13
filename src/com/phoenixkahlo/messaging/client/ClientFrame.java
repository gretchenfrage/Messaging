package com.phoenixkahlo.messaging.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener {
	
	private Client client;
	
	private JTextArea textArea;
	private JTextField textField;
	private JScrollBar scrollBar;
	
	public ClientFrame(Client client) {
		// Create frame
		super("Messaging Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.client = client;
		
		// Create main panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// Create text area
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		// Wrap text area in scroll pane
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollBar = scrollPane.getVerticalScrollBar();
		scrollPane.setPreferredSize(new Dimension(500, 500));
		
		// Add scroll pane
		panel.add(scrollPane);
		
		// Create text field
		textField = new JTextField();
		textField.addActionListener(this);
		textField.setPreferredSize(new Dimension(500, 80));
		textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		// Add text field
		panel.add(textField);
		
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
		textArea.append(text + '\n');
		scrollBar.setValue(scrollBar.getMaximum());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String message = textField.getText();
		textField.setText("");
		client.sendMessage(message);
	}
	
}
