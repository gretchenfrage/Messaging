import java.util.List;
import java.util.ArrayList;

/*
 * Represents a complete, runnable messaging server
 */
public class Server {

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private MessageRepository repository;
	private Waiter waiter;
	private HeartBeat heartBeat;

	public Server() {
		repository = new MessageRepository();
		waiter = new Waiter(new MessagingConnectionFactory(this), 36987);
		heartBeat = new HeartBeat(this);
	}

	public void start() {
		for (String s : repository.getAllMessages()) {
			System.out.println(s);
		}
		waiter.start();
		heartBeat.start();
	}

	private List<MessagingConnection> connections = new ArrayList<MessagingConnection>();

	public void addConnection(MessagingConnection connection) {
		System.out.println("SYSTEM: Connection added: " + connection);
		connections.add(connection);
		for (String s : repository.getAllMessages()) {
			connection.sendMessage(s);
		}
		connection.start();
	}
	
	public void removeConnection(MessagingConnection connection) {
		System.out.println("SYSTEM: Connection removed: " + connection);
		synchronized (connection) {
			connections.remove(connection);
			connection.terminate();
		}
	}

	/*
	 * Sends message to all connected clients
	 */
	public void sendMessage(String message) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			connections.get(i).sendMessage(message);
		}
	}

	/*
	 * Called upon by MessagingConnection threads
	 */
	public void recieveMessage(String message) {
		System.out.println("RECIEVED MESSAGE: " + message);
		/*
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			System.out.print(element + " ");
		}
		*/
		repository.addMessage(message);
		sendMessage(message);
	}

}
