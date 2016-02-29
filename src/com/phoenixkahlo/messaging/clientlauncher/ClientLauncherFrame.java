package com.phoenixkahlo.messaging.clientlauncher;

import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * A small frame to indicate that the client is indeed launching
 */
public class ClientLauncherFrame extends JFrame {
	
	private static final long serialVersionUID = -6426354040256089196L;
	
	private Thread textChangingThread;
	private Thread delayedStartThread;
	
	public ClientLauncherFrame() {
		super("Launcher");
		JLabel label = new JLabel("Attempting to Update and Launch");
		label.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(label);
		setSize(300, 100);
		setLocationRelativeTo(null);
		
		textChangingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int n = 0;
					while (true) {
						String text = "Attempting to Update and Launch";
						for (int i = 0; i < n % 4; i++) text += '.';
						label.setText(text);
						Thread.sleep(600);
						n++;
					}
				} catch (InterruptedException e) {}
			}
		});
	}
	
	/*
	 * Makes the frame visible
	 */
	public void start() {
		setVisible(true);
		textChangingThread.start();
	}
	
	public void startIn(long milliseconds) {
		delayedStartThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(milliseconds);
					start();
				} catch (InterruptedException e) {}
			}
		});
		delayedStartThread.start();
	}
	
	/*
	 * Destroyes the frame and all internal threads
	 */
	@Override
	public void dispose() {
		textChangingThread.interrupt();
		if (delayedStartThread != null)
			delayedStartThread.interrupt();
		super.dispose();
	}
	
}