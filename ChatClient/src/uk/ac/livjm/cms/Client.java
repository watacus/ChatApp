package uk.ac.livjm.cms;

import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

public class Client implements Runnable {
	private SSLSocket socket;
	private String host = "localhost";
	private int port = 7047;
	private Connection connection;
	private Chat frame;

	public Client(Chat frame, String host, int port) {
		this.frame = frame;
		this.host = host;
		this.port = port;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void run() {
		try {
			// Try connection
			SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = (SSLSocket) sslSocketFactory.createSocket(host, port);

			// New connection, thread creation
			connection = new Connection(frame, socket.getInputStream(), socket.getOutputStream());
			connection.run();

		} catch (Exception e) {
			// Connection problem
			JOptionPane.showMessageDialog(frame, e.getMessage());
			connection = null;
		}
	}
}
