package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleBar extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Point initialClick;
	
	private final JFrame frame;

	public TitleBar(JFrame frame, String title, Consumer<ActionEvent> closeButtonAction) {
		this(frame, title, closeButtonAction, null);
	}
	
	public TitleBar(JFrame frame, String title, Consumer<ActionEvent> closeButtonAction, Consumer<ActionEvent> reduceButtonAction) {
		this.frame = frame;
		setSize(frame.getWidth(), 28);
		setBackground(new Color(44, 62, 80));
		setLayout(null);
		addDragWindow();
		
		final JLabel titleLabel = new TitleLabel(title, getBounds().width - 25 - 2);
		titleLabel.setLocation(4, 2);
		add(titleLabel);
		
		if(closeButtonAction != null) {
			final JButton closeButton = new DecorationButton("cross", getBounds().width - 25 - 2, 2, closeButtonAction);
			add(closeButton);
		}
		
		if(reduceButtonAction != null) {
			final JButton reduceButton = new DecorationButton("bar", getBounds().width - 2 * (25 + 2), 2, reduceButtonAction);
			add(reduceButton);
		}
	}
	
	private void addDragWindow() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				frame.getComponentAt(initialClick);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				final int thisX = frame.getLocation().x;
				final int thisY = frame.getLocation().y;

				final int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				final int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				final int X = thisX + xMoved;
				final int Y = thisY + yMoved;
				frame.setLocation(X, Y);
			}
		});
	}
	
}
