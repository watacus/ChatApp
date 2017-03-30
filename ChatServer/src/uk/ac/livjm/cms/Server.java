package uk.ac.livjm.cms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;



public class Server implements Runnable {
	private int port = 7047;
	private boolean running = false;
	private Connection conn;
	private Chat frame;

	public Server (Chat frame, int port) {
		this.frame = frame;
		this.port = port;
	}

	public void stop () {
		running = false;
	}

	public Connection getConnection () {
		return conn;
	}

	@Override
	public void run() {
		try {
			ServerSocket listener = new ServerSocket(port);
			Socket server;
			
			running = true;
			while (running) {
					// Listen for incoming connections
					server = listener.accept();
					
					// We've received a new connection, so create a new thread to communicate across it
					conn = new Connection (frame, server.getInputStream(), server.getOutputStream());
					Thread thread =  new Thread (conn);
					thread.start();
					}	
		}catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

