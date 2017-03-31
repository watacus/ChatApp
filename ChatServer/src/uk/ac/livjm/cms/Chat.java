package uk.ac.livjm.cms;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Chat extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton sendButton;
	private JButton closeButton;
	private JTextArea messageText;
	private JTextArea historyText;
	private Server server;
	private JCheckBox urlCheckBox;
	private JCheckBox saveCheckBox;

	private FileHandler logHandler;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			// Close the program
			System.exit(0);
		}
		if (e.getSource() == sendButton) {
			String text = messageText.getText();
			server.getConnection().sendLine(text);
			historyText.append("Me:\t" + text + "\n");
			messageText.setText("");

			if (saveCheckBox.isSelected()) {
				try {
					LogRecord record = new LogRecord(Level.INFO, "Me:\t" + text);
					logHandler.publish(record);
				} catch (Exception f) {
				
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {

		JFrame frame = new Chat();
		frame.setVisible(true);
	}

	public Chat() throws IOException {
		setTitle("Chatter v1.4 - Server");
		setSize(600, 800);
		setLocation(60, 40);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;

		// Add history text
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 3;
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
		historyText = new JTextArea(
				"Welcome to Chatter version 1.4!\n Please use the close button to close the chat application.\n");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.ipady = 400;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		historyText.setEditable(false);
		add(historyText, constraints);

		// Adds checkbox for automatically opening URLs
		constraints = new GridBagConstraints();
		urlCheckBox = new JCheckBox("Auto open URLs sent in chat");
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(urlCheckBox, constraints);

		// Adds checkbox for text saving checkbox
		constraints = new GridBagConstraints();
		saveCheckBox = new JCheckBox("Save chat history to .txt file");
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(saveCheckBox, constraints);

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
		historyText.setLineWrap(true);
		messageText.setLineWrap(true);
		historyText.setWrapStyleWord(true);
		messageText.setWrapStyleWord(true);
		urlCheckBox.setSelected(false);
		saveCheckBox.setSelected(false);
		sendButton.setMnemonic(KeyEvent.VK_ENTER);

		// Initialise the file handler.
		logHandler = new FileHandler("log", 4096, 5);
		logHandler.setLevel(Level.INFO);
		
		server = new Server(this, 7047);
		Thread thread = new Thread(server);
		thread.start();
	}

	void setRecievedText(String text) throws IOException, URISyntaxException {

		if (saveCheckBox.isSelected()) {
			LogRecord record = new LogRecord(Level.INFO, "User:\t" + text);
			logHandler.publish(record);
		}
		historyText.append("User:\t" + text + "\n");

		if (urlCheckBox.isSelected()) { //Checks state of urlCheckBox, runs if box is checked 
			String checking = text;
			String checked = checking.replaceAll("http://.+?(com|net|org)/{0,1}", "<a href=\"$0\">$0</a>");
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(checked));
			}
		}
	}
}
