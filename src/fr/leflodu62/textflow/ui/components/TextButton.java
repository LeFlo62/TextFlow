package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class TextButton extends Button {

	private static final long serialVersionUID = 1L;
	
	private final Consumer<ActionEvent> action;
	
	public TextButton(String text, Consumer<ActionEvent> action) {
		super(text);
		this.action = action;
		setForeground(Color.WHITE);
		setBackground(new Color(44, 62, 80));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(new Font("Tahoma", Font.PLAIN, 15));
		setContentAreaFilled(false);
		setFocusable(false);
	}
	
	@Override
	public void paint(Graphics g) {
		if(getModel().isPressed()) {
			g.setColor(new Color(34, 52, 70));
		} else {
			g.setColor(new Color(44, 62, 80));
		}
		 g.fillRect(0, 0, getWidth(), getHeight());
         super.paintComponent(g);
	}
	
	@Override
	public void onAction(ActionEvent e) {
		action.accept(e);
	}
	
}
