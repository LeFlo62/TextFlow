package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ClickableLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	

	public ClickableLabel(String text, Consumer<MouseEvent> c) {
		this(text, c, SwingConstants.LEFT);
	}
	
	public ClickableLabel(String text, Consumer<MouseEvent> c, int m) {
		super(text, m);
		setForeground(Color.WHITE);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}	
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				c.accept(e);
			}
		});
	}

}
