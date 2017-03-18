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
	private JButton connectButton;
	private JTextArea historyText;
	private JTextArea messageText;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton){
			//terminate
			System.exit(0);
		}
		

	}

	public static void main(String[] args) {
		JFrame frame = new Chat();
		frame.setVisible(true);
	}

	public Chat() {

		// add history text
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;

		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(new JLabel("History"), constraints);

		setTitle("Chat Window");
		setSize(1280, 720);
		setLocation(60, 40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add send button
		constraints = new GridBagConstraints();
		sendButton = new JButton("Send");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(sendButton, constraints);

		// add close button
		constraints = new GridBagConstraints();
		closeButton = new JButton("Close");
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(closeButton, constraints);

		// add message field
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

		// add history
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

		// Add the send message text label
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(new JLabel("Message to send"), constraints);

		// Add the empty space text label
		constraints = new GridBagConstraints();
		connectButton = new JButton("Connect");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(connectButton, constraints);

		closeButton.addActionListener(this);
		connectButton.addActionListener(this);
		sendButton.addActionListener(this);

	}

}
