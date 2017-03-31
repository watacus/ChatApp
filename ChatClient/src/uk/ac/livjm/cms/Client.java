package uk.ac.livjm.cms;

import java.net.Socket;
import javax.swing.JOptionPane;

public class Client implements Runnable {
	private Socket socket;
	private String host = "localhost";
	private int port = 7047;
	private Connection conn;
	private Chat frame;

	public Client(Chat frame, String host, int port) {
		this.frame = frame;
		this.host = host;
		this.port = port;
	}

	public Connection getConnection() {
		return conn;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(host, port);

			conn = new Connection(frame, socket.getInputStream(), socket.getOutputStream());
			Thread thread = new Thread(conn);
			thread.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
			conn = null;
		}
	}
}
