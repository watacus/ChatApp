package uk.ac.livjm.cms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Chat extends JFrame implements ActionListener {

	private JButton sendButton;
	private JButton closeButton;
	private JTextArea historyText;
	private JTextArea messageText;

	private Server server;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			// terminate
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
		JFrame frame = new Chat();
		frame.setVisible(true);
	}

	public Chat() {

		setTitle("Chat Window - Server");
		setSize(1280, 720);
		setLocation(60, 40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;

		// Add history text
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(new JLabel("History"), constraints);

		// Add send button
		constraints = new GridBagConstraints();
		sendButton = new JButton("Send");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(sendButton, constraints);

		// Add close button
		constraints = new GridBagConstraints();
		closeButton = new JButton("Close");
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(closeButton, constraints);

		// Add message field
		constraints = new GridBagConstraints();
		messageText = new JTextArea("");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.ipady = 100;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		add(messageText, constraints);

		// Add history
		constraints = new GridBagConstraints();
		historyText = new JTextArea("");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.ipady = 400;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		add(historyText, constraints);

		// Send message text
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(new JLabel("Message to send"), constraints);

		closeButton.addActionListener(this);
		sendButton.addActionListener(this);

		server = new Server(this, 7047);
		Thread thread = new Thread(server);
		thread.start();

	}

	void setReceivedText(String text) {
		historyText.append("You:\t" + text + "\n");
	}
}
