package uk.ac.livjm.cms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;


public class Connection implements Runnable {
	private InputStream in;
	private OutputStream out;
	private boolean running = false;
	private Chat frame;

	public Connection (Chat frame, InputStream in, OutputStream out) {
		this.frame = frame;
		this.in = in;
		this.out = out;
	}

	void stop () {
		this.running = false;
	}
	
	@Override
	public void run() {
		running = true;
		BufferedReader inRead = new BufferedReader (new InputStreamReader (in));
		while (running) {
			try {
				String line = inRead.readLine();
				try {
					frame.setRecievedText(line);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
			}
		}
	}

	public void sendLine (String text) {
		PrintWriter outRead = new PrintWriter (out,true);
		outRead.println(text);
		}
	}
