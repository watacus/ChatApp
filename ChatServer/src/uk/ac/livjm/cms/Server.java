package uk.ac.livjm.cms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Server implements Runnable {
	private int port = 8080;
	private boolean running = false;
	private Connection connection;
	private Chat frame;

	public Server (Chat frame, int port) {
		this.frame = frame;
		this.port = port;
	}

	public void stop () {
		running = false;
	}
	
	public Connection getConnection () {
		return connection;
	}

	@Override
	public void run() {
		try {
			//ServerSocket listener = new ServerSocket(port);
			//Socket server;
			SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			SSLServerSocket listener = (SSLServerSocket)sslServerSocketFactory.createServerSocket(port);
			SSLSocket server;
			listener.setSoTimeout(10000);
			
			running = true;
			
			while (running) {
				try {
					// Listen for incoming connections
					server = (SSLSocket) listener.accept();
					
					// We've received a new connection, so create a new thread to communicate across it
					connection = new Connection (frame, server.getInputStream(), server.getOutputStream());
					Thread thread = new Thread (connection);
					thread.start();

				} catch (SocketTimeoutException e) {
					// Do nothing and carry on
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
