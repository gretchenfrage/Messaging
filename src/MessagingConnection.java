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
	
	public MessagingConnection(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("SYSTEM: MessagingConnection thread started: " + this);
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (IOException e1) {
			System.err.println("Failed to create InputStream from socket: " + this);
			e1.printStackTrace();
		}
		try {
			while (true) {
				server.recieveMessage(MessagingProtocol.readMessage(in));
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
