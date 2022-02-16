package fr.leflodu62.textflow.ui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.TextFlow;
import fr.leflodu62.textflow.ui.components.AddressField;
import fr.leflodu62.textflow.ui.components.PasswordField;
import fr.leflodu62.textflow.ui.components.TextButton;
import fr.leflodu62.textflow.ui.components.TitleBar;
import fr.leflodu62.textflow.ui.components.UsernameField;

public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final AddressField addressField;
	private final UsernameField usernameField;
	private final JLabel errorMessage;
	private final JButton connectButton;

	private final PasswordField passwordField;
	
	public RegisterFrame(TextFlow instance) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TextFlow.class.getResource("/assets/icon.png")));
		setResizable(false);
		setForeground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		setAlwaysOnTop(true);
		setTitle("Login");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(320, 380);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(52, 73, 94));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final TitleBar bar = new TitleBar(this, "Register", (e) -> instance.close());
		contentPane.add(bar);

		addressField = new AddressField();
		addressField.setLocation(76, 125);
		contentPane.add(addressField);

		usernameField = new UsernameField();
		usernameField.setLocation(76, 187);
		usernameField.setText("");
		usernameField.setTextHint("pseudo");
		contentPane.add(usernameField);

		passwordField = new PasswordField();
		passwordField.setLocation(76, 249);
		contentPane.add(passwordField);

		connectButton = new TextButton("S'enregistrer", e -> instance.attemptRegister());
		connectButton.setBounds(76, 311, 168, 28);
		contentPane.add(connectButton);

		errorMessage = new JLabel("", SwingConstants.CENTER);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(6, 39, 308, 28);
		contentPane.add(errorMessage);
		
		addKeyListener(instance.getRegisterEnterKeyListener());
		usernameField.addKeyListener(instance.getRegisterEnterKeyListener());
		passwordField.addKeyListener(instance.getRegisterEnterKeyListener());
	}
	
	public void setErrorMessage(String text) {
		errorMessage.setText(text);
		connectButton.setEnabled(true);
	}

	public boolean areFieldsEmpty() {
		return addressField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getPassword().length == 0;
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public void setConnectButtonEnabled(boolean enabled) {
		connectButton.setEnabled(enabled);
	}

	public String getAddress() {
		return addressField.getText();
	}

	public void giveCreditentials(ClientConnection connection) {
		connection.register(this, getUsername(), new String(passwordField.getPassword()));
		for (int i = 0; i < passwordField.getPassword().length; i++) {
			passwordField.getPassword()[i] = 0;
		}
		passwordField.reset();
		setConnectButtonEnabled(true);
	}

}
