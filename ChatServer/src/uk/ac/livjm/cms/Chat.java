package uk.ac.livjm.cms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;

public class Chat extends JFrame implements ActionListener {
	private JButton sendButton;
	private JButton closeButton;
	private JTextArea messageText;
	private JTextArea historyText;
	private Server server;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			System.exit(0);
		}
		if (e.getSource() == sendButton) {
			String text = messageText.getText();
			server.getConnection().sendLine(text);
			historyText.append("Me:\t" + text + "\n");
			messageText.setText("");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new Chat ();
		frame.setVisible(true);
	}
	public Chat() {
		setTitle("Chat Window");
		setSize(400, 600);
		setLocation(60, 40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;

		// Add the history text label
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add (new JLabel("History"), constraints);
		
		// Add the send button
		constraints = new GridBagConstraints();
		sendButton = new JButton ("Send");
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add (sendButton, constraints);
		
		// Add the close button
		constraints = new GridBagConstraints();
		closeButton = new JButton ("Close");
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add (closeButton, constraints);
	
		// Add the send text input box
		constraints = new GridBagConstraints();
		messageText = new JTextArea ("");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.ipady = 100;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		add (messageText, constraints);
		
		// Add the history text box
		constraints = new GridBagConstraints();
		historyText = new JTextArea ("");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.ipady = 400;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		add (historyText, constraints);
		
		// Add the send message text label
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add (new JLabel("Message to send"), constraints);
	
		// Add the empty space text label
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add (new JLabel(""), constraints);
		
		closeButton.addActionListener(this);
		sendButton.addActionListener(this);
		
		server = new Server(this, 7047);
		Thread thread = new Thread (server);
		thread.start();
	}
	void setRecievedText (String text) {
		historyText.append("You:\t" + text + "\n");
	}
}
