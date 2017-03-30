package uk.ac.livjm.cms;

import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

public class Client implements Runnable {
	private SSLSocket socket;
	private String host = "localhost";
	private int port = 8080;
	private Connection connection;
	private Chat frame;
	
	public Client (Chat frame, String host, int port) {
		this.frame = frame;
		this.host = host;
		this.port = port;
	}

	public Connection getConnection () {
		return connection;
	}

	@Override
	public void run() {
		try {
			// Try to connect to the server
			//socket = new Socket(host, port);
			SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
			socket = (SSLSocket)sslSocketFactory.createSocket(host, port);

			// We've successfully created a connection, so create a new thread to communicate across it
			connection = new Connection (frame, socket.getInputStream(), socket.getOutputStream());
			connection.run();

		} catch (Exception e) {
			// There was a problem connecting
			JOptionPane.showMessageDialog(frame, e.getMessage());
			connection = null;
		}
	}
}
