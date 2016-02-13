import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
 * Listens to a server for incoming messages
 */
public class ClientListener extends Thread {

	private Client client;
	private Socket socket;
	
	public ClientListener(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("SYSTEM: Launching ClientListener thread");
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			System.err.println("Failed to create InputStream from socket");
			e.printStackTrace();
		}
		try {
			while (true) {
				String message = MessagingProtocol.readMessage(in);
				if (message.length() > 0) client.recieveMessage(message);
			}
		} catch (IOException e) {
			System.out.println("SYSTEM: Server disconnected");
		}
	}
	
}
