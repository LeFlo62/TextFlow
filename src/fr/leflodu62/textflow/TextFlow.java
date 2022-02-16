package fr.leflodu62.textflow;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;

import fr.leflodu62.textflow.ClientConnection.State;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketRegistry;
import fr.leflodu62.textflow.packets.connection.DisconnectionPacket;
import fr.leflodu62.textflow.ui.ChatFrame;
import fr.leflodu62.textflow.ui.DisconnectPopup;
import fr.leflodu62.textflow.ui.LoginFrame;
import fr.leflodu62.textflow.ui.RegisterFrame;

public class TextFlow {

	private ClientConnection connection;

	public static Logger LOGGER;

	private JFrame currentFrame;

	private final KeyListener LOGIN_ENTER_KEY_LISTENER = new KeyListener() {
		@Override public void keyTyped(KeyEvent e) {}
		@Override public void keyReleased(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				attemptLogin();
			}
		}
	};
	
	private final KeyListener REGISTER_ENTER_KEY_LISTENER = new KeyListener() {
		@Override public void keyTyped(KeyEvent e) {}
		@Override public void keyReleased(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				attemptRegister();
			}
		}
	};

	public static void main(String[] args) {
		LOGGER = setupLogger();
		PacketRegistry.registerPackets();

		EventQueue.invokeLater(() -> {
			try {
				new TextFlow();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TextFlow() {
		openFrame(new LoginFrame(this));
	}

	public void openFrame(JFrame frame) {
		if (currentFrame != null) {
			currentFrame.setVisible(false);
			currentFrame.dispose();
		}
		currentFrame = frame;
		frame.setVisible(true);
		frame.requestFocus();
	}

	public void attemptLogin() {
		if(currentFrame instanceof LoginFrame) {
			final LoginFrame loginFrame = (LoginFrame) currentFrame;
			
			if (loginFrame.areFieldsEmpty()) {
				loginFrame.setErrorMessage("Vous devez remplir tous les champs.");
				return;
			}

			loginFrame.setConnectButtonEnabled(false);

			connection = new ClientConnection(this, loginFrame.getUsername());
			connection.attemptLogin(loginFrame.getAddress(), () -> {
				loginFrame.giveCreditentials(connection);
			});
		}
	}
	
	public void attemptRegister() {
		if(currentFrame instanceof RegisterFrame) {
			final RegisterFrame registerFrame = (RegisterFrame) currentFrame;
			
			if (registerFrame.areFieldsEmpty()) {
				registerFrame.setErrorMessage("Vous devez remplir tous les champs.");
				return;
			}

			registerFrame.setConnectButtonEnabled(false);

			connection = new ClientConnection(this, registerFrame.getUsername());
			connection.attemptRegister(registerFrame.getAddress(), () -> {
				registerFrame.giveCreditentials(connection);
			});
		}
	}

	private static Logger setupLogger() {
		final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setUseParentHandlers(false);

		logger.addHandler(new ConsoleHandler());

		for (final Handler h : logger.getHandlers()) {
			h.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {

					String content = record.getMessage();

					if (record.getThrown() != null && record.getThrown() instanceof Exception
							&& record.getThrown().getCause() != null) {
						content += " -> " + record.getThrown().getCause().toString() + " Caused by:"
								+ System.lineSeparator();
						for (final StackTraceElement e : record.getThrown().getCause().getStackTrace()) {
							content += "    " + e.toString() + System.lineSeparator();
						}
					}

					return "[" + getCurrentTime(false, true) + "] [" + record.getLevel().getLocalizedName() + "] ["
							+ record.getSourceClassName() + "]: " + content + "\n";
				}
			});
		}

		return logger;
	}

	private static String getCurrentTime(boolean date, boolean hours) {
		if (!date && !hours)
			return null;
		final SimpleDateFormat format = new SimpleDateFormat(
				(date ? "dd-MM-YY" : "") + (date && hours ? " " : "") + (hours ? "HH:mm:ss" : ""));
		final Calendar calendar = Calendar.getInstance();
		return format.format(calendar.getTime());
	}

	public void disconnect() {
		disconnect(false, null);
	}
	
	public void killConnection() {
		connection.close();
	}

	public void disconnect(boolean forced, String reason) {
		if(connection != null && connection.getState().equals(State.LOGGED)) {
			if(reason != null) {
				connection.sendPacket(new DisconnectionPacket(reason), () -> connection.close());
			} else {
				connection.sendPacket(new DisconnectionPacket(), () -> connection.close());
			}
		}
		if (forced) {
			openFrame(new DisconnectPopup(this, reason));
		}
	}

	public void close() {
		disconnect();
		System.exit(-1);
	}
	
	public void appendMessageWithColors(String message) {
		if(currentFrame instanceof ChatFrame) {
			final ChatFrame frame = (ChatFrame) currentFrame;
			frame.appendMessageWithColors(message);
		}
	}
	
	public void addMessage(String username, String message) {
		if(currentFrame instanceof ChatFrame) {
			final ChatFrame frame = (ChatFrame) currentFrame;
			frame.addMessage(username, message);
		}
	}
	
	public void setLoginErrorMessage(String reason) {
		if (currentFrame instanceof LoginFrame) {
			((LoginFrame) currentFrame).setErrorMessage(reason);
		}
	}
	
	public void setRegisterErrorMessage(String reason) {
		if (currentFrame instanceof RegisterFrame) {
			((RegisterFrame) currentFrame).setErrorMessage(reason);
		}
	}
	
	public KeyListener getLoginEnterKeyListener() {
		return LOGIN_ENTER_KEY_LISTENER;
	}
	
	public KeyListener getRegisterEnterKeyListener() {
		return REGISTER_ENTER_KEY_LISTENER;
	}

	public void sendPacket(Packet packet) {
		connection.sendPacket(packet);
	}

	public String getUsername() {
		return connection.getUsername();
	}
}
