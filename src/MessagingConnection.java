import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * Represents a connection to a client. Waits on messages in its own thread, sends messages on Server thread
 */
public class MessagingConnection extends Thread {

	private Server server;
	private Socket socket;
	private volatile boolean shouldContinueRunning = true;
	
	public MessagingConnection(Server server, Socket socket) {
		super("Recieving thread for " + socket.getInetAddress());
		this.server = server;
		this.socket = socket;
	}
	
	public void terminate() {
		shouldContinueRunning = false;
	}

	@Override
	public void run() {
		System.out.println("SYSTEM: MessagingConnection thread started: " + this);
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			System.err.println("Failed to create InputStream from socket: " + this);
			e.printStackTrace();
		}
		try {
			while (shouldContinueRunning) {
				String message = MessagingProtocol.readMessage(in);
				if (message.length() > 0) server.recieveMessage(message);
			}
		} catch (IOException e) {
			System.out.println("SYSTEM: Disconnected socket on connection: " + this);
			server.removeConnection(this);
		}
	}
	
	public void sendMessage(String message) {
		try {
			OutputStream out = socket.getOutputStream();
			MessagingProtocol.writeMessage(out, message);
		} catch (IOException e) {
			System.out.println("SYSTEM: Disconnected socket on connection: " + this);
			server.removeConnection(this);
		}
	}
	
	@Override
	public String toString() {
		return "connection to " + socket.getInetAddress();
	}

}
