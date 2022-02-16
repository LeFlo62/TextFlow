package fr.leflodu62.textflow.ui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.lang3.StringUtils;

import fr.leflodu62.textflow.TextFlow;
import fr.leflodu62.textflow.packets.message.ClientMessagePacket;
import fr.leflodu62.textflow.ui.components.ClickableLabel;
import fr.leflodu62.textflow.ui.components.ColorButton;
import fr.leflodu62.textflow.ui.components.RoundedBorder;
import fr.leflodu62.textflow.ui.components.TitleBar;
import fr.leflodu62.textflow.ui.components.chatframe.ChatColor;
import fr.leflodu62.textflow.ui.components.chatframe.CustomEditorKit;
import fr.leflodu62.textflow.ui.components.chatframe.ScrollBarFlatUI;

public class ChatFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JLayeredPane contentPane;
	private final JButton colorButton;

	private final JTextPane textPane;
	private final JTextArea messageArea;
	private final TextFlow instance;

	private final HTMLDocument doc;
	private final HTMLEditorKit hek;

	/**
	 * Create the frame.
	 */
	public ChatFrame(TextFlow instance) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatFrame.class.getResource("/assets/icon.png")));
		this.instance = instance;
		setResizable(false);
		setTitle("TextFlow");
		setBackground(new Color(52, 73, 94));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setSize(450, 300);
		contentPane = new JLayeredPane();
		contentPane.setBackground(new Color(52, 73, 94));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setLayeredPane(contentPane);

		final TitleBar bar = new TitleBar(this, "TextFlow", (e) -> instance.close(), (e) -> setState(JFrame.ICONIFIED));

		textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.PLAIN, 11));
		// textPane.setForeground(Color.WHITE);
		textPane.setBounds(10, bar.getBounds().height + 10, getBounds().width - 2 * 10, 200);
		textPane.setBackground(new Color(44, 62, 80));
		textPane.setCaretColor(Color.WHITE);
		hek = new CustomEditorKit();
		textPane.setEditorKit(hek);

		try {
			hek.getStyleSheet()
					.loadRules(new InputStreamReader(TextFlow.class.getResourceAsStream("/assets/style.css")), null);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		textPane.setMargin(new Insets(5, 7, 5, 7));
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		doc = (HTMLDocument) textPane.getDocument();

		final JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(textPane.getBounds());
		scrollPane.getVerticalScrollBar().setUI(new ScrollBarFlatUI());
		scrollPane.setBackground(new Color(44, 62, 80));

		messageArea = new JTextArea();
		final int messageAreaHeight = getBounds().height - 3 * 10 - textPane.getBounds().height - bar.getBounds().height;
		messageArea.setBounds(textPane.getBounds().x, textPane.getBounds().y + textPane.getBounds().height + 10,
				textPane.getWidth() - messageAreaHeight + textPane.getBounds().x,
				messageAreaHeight);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		messageArea.setForeground(Color.WHITE);
		messageArea.setBackground(new Color(44, 62, 80));
		messageArea.setCaretColor(Color.WHITE);
		messageArea.setMargin(new Insets(5, 7, 5, 7));

		final JScrollPane messageScrollPane = new JScrollPane(messageArea);
		messageScrollPane.setBorder(BorderFactory.createEmptyBorder());
		messageScrollPane.setBounds(messageArea.getBounds());
		messageScrollPane.getVerticalScrollBar().setUI(new ScrollBarFlatUI());
		messageScrollPane.setBackground(new Color(44, 62, 80));

		final KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					sendMessage();
				}
			}
		};

		messageArea.addKeyListener(keyListener);
		messageArea.requestFocusInWindow();

		final JPanel colorPanel = new JPanel();
		colorPanel.setBackground(new Color(48, 68, 87));
		colorPanel.setBounds(300, 71, 140, 180);
		colorPanel.setBorder(new RoundedBorder(Color.WHITE, 16, 1));
		final int colorWidth = 16;
		final int gap = (colorPanel.getBounds().width - 4*colorWidth)/5;
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 4; x++) {
				final ChatColor c = ChatColor.values()[y*4 + x];
				if(c.isColor()) {
					final ColorButton btn = new ColorButton(c.getColor(), e -> messageArea.insert(c.toString(), messageArea.getCaretPosition()), x*colorWidth + (x+1)*gap + colorWidth/2, y*colorWidth + (y+1)*gap + colorWidth/2, colorWidth);
					btn.setFocusable(false);
					colorPanel.add(btn);
				} else if(!c.equals(ChatColor.RESET)){
					final JLabel l = new ClickableLabel("<html>" + c.getStarting() + "Abc" + c.getEnding() + "</html>", e -> messageArea.insert(c.toString(), messageArea.getCaretPosition()));
					l.setFocusable(false);
					l.setBounds(x*colorWidth + (x+1)*gap, y*colorWidth + (y+1)*gap, colorPanel.getBounds().width/4, colorWidth);
					colorPanel.add(l);
				} else {
					final JLabel l = new ClickableLabel("Reset", e -> messageArea.insert(c.toString(), messageArea.getCaretPosition()), SwingConstants.CENTER);
					l.setFocusable(false);
					l.setBounds(0, y*(colorWidth+gap), colorPanel.getBounds().width, colorWidth);
					colorPanel.add(l);
					break;
				}
			}
		}
//		colorPanel.addFocusListener(new FocusListener() {
//			
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(e.getOppositeComponent() == null || (!e.getOppositeComponent().equals(colorPanel) && !(e.getOppositeComponent() instanceof ColorButton) && !(e.getOppositeComponent() instanceof ClickableLabel))) {
//					System.out.println(e.getOppositeComponent());
//					colorPanel.setVisible(false);
//				}
//			}
//			
//			@Override
//			public void focusGained(FocusEvent e) {
//			}
//		});
		colorPanel.setLayout(null);
		colorPanel.setVisible(false);
		
		colorButton = new ColorButton(Color.RED, e -> {
			colorPanel.setVisible(!colorPanel.isVisible());
			if(colorPanel.isVisible()) {
				colorPanel.requestFocus();
			}
		}, messageArea.getBounds().x + messageArea.getBounds().width + (messageArea.getBounds().height) / 2,
				messageArea.getBounds().y + (messageArea.getBounds().height) / 2, 15);
		
		contentPane.add(colorButton, JLayeredPane.PALETTE_LAYER);
		contentPane.add(bar, JLayeredPane.MODAL_LAYER);
		contentPane.add(colorPanel, JLayeredPane.POPUP_LAYER);
		contentPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
		contentPane.add(messageScrollPane, JLayeredPane.DEFAULT_LAYER);
		Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			if(event instanceof MouseEvent && event.getID() == MouseEvent.MOUSE_CLICKED) {
				final MouseEvent e = (MouseEvent) event;
				if(colorPanel.isVisible() && !colorPanel.contains(e.getPoint())) {
					colorPanel.setVisible(false);
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK);
	}

	private void sendMessage() {
		if (!messageArea.getText().isEmpty()) {
			final String message = messageArea.getText();
			instance.sendPacket(new ClientMessagePacket(message));
			if (!message.startsWith("/")) {
				addMessage(instance.getUsername(), message);
			}
			messageArea.setText(null);
		}
	}

	public void addMessage(String username, String message) {
		appendMessageWithColors("§e" + username + "§r : " + message);
	}

	public void appendMessageWithColors(String message) {
		try {
			final String out = ChatColor.toHTML(StringUtils.replaceEach(message, new String[] { "&", "\"", "<", ">" },
					new String[] { "&amp;", "&quot;", "&lt;", "&gt;" }));
			hek.insertHTML(doc, doc.getLength(), out, 0, 0, null);
			messageArea.setCaretPosition(messageArea.getDocument().getLength());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
