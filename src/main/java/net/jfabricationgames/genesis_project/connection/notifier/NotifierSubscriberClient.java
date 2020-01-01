package net.jfabricationgames.genesis_project.connection.notifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A simple notifier subscriber client.<br>
 * Source: https://github.com/tfassbender/notifier/blob/master/src/main/java/net/jfabricationgames/notifier/subscriber/SubscriberClient.java
 */
public class NotifierSubscriberClient {
	
	private static final Logger LOGGER = LogManager.getLogger(NotifierSubscriberClient.class);
	
	private static final String CONFIG_RESOURCE_FILE = "config/notifier_service.config";
	
	/**
	 * The string that the service will send to request a name from the user.
	 */
	private static final String USERNAME_REQUEST = "<<send_username>>";
	/**
	 * A string that indicates the end of a notification message.
	 */
	private static final String MESSAGE_END = "<<notification_end>>";
	
	/**
	 * The name (URL) of the host.
	 */
	private String host;
	/**
	 * The port to contact for the notification service.
	 */
	private int port;
	/**
	 * The name, this client will use when subscribing to the notification service
	 */
	private String username;
	
	/**
	 * The socket that makes the connection to the notifier service
	 */
	private Socket socket;
	/**
	 * The input stream that reads from the socket connection
	 */
	private InputStream inStream;
	/**
	 * The output stream to send to the notification
	 */
	private OutputStream outStream;
	
	private NotifierService notifierService;
	
	private Thread notificationListenerThread;
	
	/**
	 * Create a new client that loads the configurations (host, port and username), creates the connection and subscribes to the notification service.
	 * 
	 * Also a listener thread is started to handle the notifications from the service.
	 * 
	 * @throws IOException
	 *         An {@link IOException} is thrown if the creation of the client fails.
	 */
	protected NotifierSubscriberClient(String username, NotifierService notifierService) throws IOException {
		this.username = username;
		this.notifierService = notifierService;
		loadConfig();
		LOGGER.info("NotifierSubscriberClient: loaded configuration: [host: {}   port: {}   username: {}]", host, port, username);
		
		subscribeToNotifierService();
		startNotificationListener();
		LOGGER.info("NotifierSubscriberClient started");
	}
	
	@Override
	public String toString() {
		return "NotifierSubscriberClient [host=" + host + ", port=" + port + ", username=" + username + ", socket=" + socket + ", inStream="
				+ inStream + ", outStream=" + outStream + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotifierSubscriberClient other = (NotifierSubscriberClient) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		}
		else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		}
		else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	/**
	 * Create an start a notification listener to handle messages from the service.
	 */
	private void startNotificationListener() {
		if (notificationListenerThread != null && notificationListenerThread.isAlive()) {
			throw new IllegalStateException("A notification listener thread has already been started");
		}
		notificationListenerThread = new Thread(() -> {
			int available = 0;
			StringBuilder sb = new StringBuilder();
			while (!Thread.currentThread().isInterrupted()) {
				try {
					if ((available = inStream.available()) > 0) {
						LOGGER.trace("Received input (bytes available: {})", available);
						//read the input into a string
						byte[] buffer = new byte[available];
						inStream.read(buffer);
						String str = new String(buffer);
						
						//append the new text till the end of the message is reached
						sb.append(str);
						
						//check for message end(s)
						String rest;//rest of a next message
						//checks for multiple messages in one buffer
						while ((rest = handleMessageEnd(sb)) != null) {
							sb = new StringBuilder(rest);
						}
					}
				}
				catch (IOException ioe) {
					//this exception should be logged...
					ioe.printStackTrace();
				}
			}
		}, "notification_listener_thread");
		
		//start the listener thread
		notificationListenerThread.setDaemon(true);
		notificationListenerThread.start();
	}
	
	/**
	 * Stop this subscriber and close the connection.
	 */
	public void closeConnection() throws IOException {
		if (notificationListenerThread != null) {
			notificationListenerThread.interrupt();
			socket.close();
			notificationListenerThread = null;
		}
		else {
			throw new IllegalStateException("The notification listener thread has either not yet been started or was already closed");
		}
	}
	
	/**
	 * Check for the end of a message.<br>
	 * If a message end is contained the message is handled and the rest of the string builder content is returned. Otherwise null is returned.
	 */
	private String handleMessageEnd(StringBuilder sb) {
		int messageEnd = 0;
		if ((messageEnd = sb.indexOf(MESSAGE_END)) > 0) {
			//split message in message content, message end identifier and rest (of a next message)
			String wholeMessage = sb.toString();
			String message = wholeMessage.substring(0, messageEnd);
			String rest = wholeMessage.substring(messageEnd + MESSAGE_END.length());
			
			LOGGER.debug("received message: {}", message);
			
			//handle the message content
			handleMessage(message);
			
			//put the rest of the message back in the string builder
			return rest;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Handle the message
	 */
	private void handleMessage(String message) {
		LOGGER.debug("handling message: {}", message);
		if (message.equals(USERNAME_REQUEST)) {
			//notification service requests a name for this user -> send the name
			try {
				LOGGER.debug("received name request. answering with username: {}", username);
				outStream.write(username.getBytes());
				outStream.flush();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				//if the name can't be send the program can't go on
				LOGGER.fatal("failed to send username to service. service will not be working correctly");
			}
		}
		else {
			LOGGER.debug("received notification message from service: {}", message);
			notifierService.handleMessageFromService(message);
		}
	}
	
	/**
	 * Open the connection to subscribe to the notification service
	 */
	private void subscribeToNotifierService() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		inStream = socket.getInputStream();
		outStream = socket.getOutputStream();
	}
	
	/**
	 * Load configuration (host, port and username)
	 */
	private void loadConfig() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties configProperties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(CONFIG_RESOURCE_FILE)) {
			configProperties.load(resourceStream);
		}
		
		host = configProperties.getProperty("host", "localhost");
		String portValue = null;
		try {
			portValue = configProperties.getProperty("port", "<<not_found>>");
			port = Integer.parseInt(portValue);
		}
		catch (NumberFormatException nfe) {
			throw new IOException("port couldn't be interpreted as integer value (was: " + portValue + ")", nfe);
		}
		if (port < 1024) {
			throw new IOException("the port can't be a \"well known port\" (port number < 1024)");
		}
		if (host.equals("")) {
			throw new IOException("host mussn't be an empty string");
		}
	}
}