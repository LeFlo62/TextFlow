package fr.leflodu62.textflow.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fr.leflodu62.textflow.TextFlow;
import fr.leflodu62.textflow.ui.components.TitleBar;

public class DisconnectPopup extends JFrame {

	private static final long serialVersionUID = -2961614374428132557L;
	private final JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DisconnectPopup(TextFlow instance, String reason) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TextFlow.class.getResource("/assets/icon.png")));
		setResizable(false);
		setForeground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(400, 200);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(52, 73, 94));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JLabel lblTest = new JLabel(reason);
		lblTest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblTest.setForeground(Color.WHITE);
		lblTest.setBounds(10, 39, 380, 150);
		contentPane.add(lblTest);
		
		final TitleBar bar = new TitleBar(this, "Disconnected", (e) -> instance.openFrame(new LoginFrame(instance)));
		contentPane.add(bar);
	}
}
