package com.phoenixkahlo.messaging.testing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.phoenixkahlo.messaging.swingutils.VerticalLayout;

@SuppressWarnings("serial")
public class MinimumVerifiableExample extends JFrame {

	private JPanel textPanel;
	
	public MinimumVerifiableExample() {
		// Create display area
		textPanel = new JPanel();
		textPanel.setLayout(new VerticalLayout());
		// Wrap text panel in scroll pane
		JScrollPane scrollPane = new JScrollPane(textPanel);
		// Add scroll pane
		add(scrollPane);
		// Size center and display
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void addMessage(String message) {
		JTextArea textArea = new JTextArea(message);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textPanel.add(textArea);
		textPanel.updateUI();
	}
	
	public static void main(String[] args) {
		MinimumVerifiableExample frame = new MinimumVerifiableExample();
		frame.addMessage("According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don�t care what humans think is impossible.");
		frame.addMessage("We begin on Christmas Eve with me, Mark, and my roommate, Roger. We live in an industrial loft on the corner of 11th street and Avenue B, the top floor of what was once a music publishing factory. Old rock 'n' roll posters hang on the walls. They have Roger's picture advertising gigs at CBGB's and the Pyramid Club. We have an illegal wood burning stove; its exhaust pipe crawls up to a skylight. All of our electrical appliances are plugged into one thick extension cord which snakes its way out a window. Outside, a small tent city has sprung up in the lot next to our building. Inside, we are freezing because we have no heat.");
	}
	
}
