package uk.ac.livjm.cms;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	private JCheckBox urlButton;

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
			
			try {
				BufferedWriter logWriter = new BufferedWriter (new FileWriter("log.txt", true));
				logWriter.newLine();
				logWriter.write("Me:\t" + text + "\n");
				logWriter.close();
			} catch (Exception f) {
				
			}
		}
}	

	public static void main(String[] args) {

		JFrame frame = new Chat();
		frame.setVisible(true);
	}

	public Chat() {
		setTitle("Chatter v1.2 - Server");
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
		historyText = new JTextArea("Welcome to Chatter version 1.2!\n Please use the close button to close the chat application.\n");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.ipady = 400;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.BOTH;
		historyText.setEditable(false);
		add(historyText, constraints);
		
		//Adds checkbox for automatically opening URLs
		constraints = new GridBagConstraints();
		urlButton = new JCheckBox("Auto open URLs sent in chat");
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.CENTER;
		add(urlButton, constraints);

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
		urlButton.setSelected(true);

		server = new Server(this, 7047);
		Thread thread = new Thread(server);
		thread.start();}
	

	void setRecievedText(String text) throws IOException, URISyntaxException {

		try{
			BufferedWriter logWriter = new BufferedWriter (new FileWriter("log.txt",true)); //Writes recieved text to .txt file
			logWriter.newLine();
			logWriter.write("User:\t" + text + "\n");
			logWriter.close();
		} catch (IOException e){
			
		}
		historyText.append("User:\t" + text + "\n");
		
		if(urlButton.isSelected()) {
			String checking = text;
			String checked = checking.replaceAll("http://.+?(com|net|org)/{0,1}", "<a href=\"$0\">$0</a>");
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(checked));
			}
		}
		}
}
