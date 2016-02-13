import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * Represents a complete, runnable messaging client
 */
public class Client {
	
	private Socket socket;
	private ClientListener listener;
	private ClientPrompter prompter;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
	public Client() {
		System.out.println("SYSTEM: Constructing client");
		try {
			socket = new Socket("localhost", 36987);
		} catch (IOException e) {
			System.err.println("Failed to connect to server");
			e.printStackTrace();
		}
		listener = new ClientListener(this, socket);
		prompter = new ClientPrompter(this);
	}
	
	public void start() {
		listener.start();
		prompter.start();
	}
	
	public void recieveMessage(String message) {
		System.out.println(message);
	}
	
	public void sendMessage(String message) {
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Failed to create OutputStream");
			e.printStackTrace();
		}
		try {
			MessagingProtocol.writeMessage(out, message);
		} catch (IOException e) {
			System.err.println("Server disconnected");
			e.printStackTrace();
		}
	}

}
