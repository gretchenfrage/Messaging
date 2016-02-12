import java.net.Socket;

/*
 * A ConnectionFactory that produces MessagingConnections to represent client connections
 */
public class MessagingConnectionFactory implements ConnectionFactory {

	private Server server;

	public MessagingConnectionFactory(Server server) {
		this.server = server;
	}

	@Override
	public void createConnection(Socket socket) {
		server.addConnection(new MessagingConnection(server, socket));
	}

}