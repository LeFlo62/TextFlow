package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class ColorButton extends Button {

	private static final long serialVersionUID = 1L;
	
	private final Consumer<ActionEvent> c;
	
	public ColorButton(Color color, Consumer<ActionEvent> c, int x, int y, int width) {
		this.c = c;
		setBounds(x - width/2, y - width/2, width, width);
		setBackground(color);
		setBorderPainted(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void onAction(ActionEvent e) {
		c.accept(e);
	}
	
}
