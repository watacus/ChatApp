package uk.ac.livjm.cms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

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
				// Try to read a line
				String line = inRead.readLine();
				if (line != null) {
					// We received some text, so send it to the window
					frame.setRecievedText(line);
				}
				else {
					// We received nothing because the connection was closed
					JOptionPane.showMessageDialog(frame, "Connection closed");
					running = false;
				}
			} catch (IOException e) {
				// Some kind of error occurred
				JOptionPane.showMessageDialog(frame, e.getMessage());
				running = false;
			}
		}
	}

	public void sendLine (String text) {
		PrintWriter outRead = new PrintWriter (out, true);
		if (running) {
			// Send a line of text across the network
			outRead.println(text);
		}
	}

}
