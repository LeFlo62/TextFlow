package fr.leflodu62.textflow.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.border.Border;

public class Button extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	public Button() {
		this("");
	}
	
	public Button(String text) {
		super(text);
		
		addActionListener(e -> onAction(e));
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				onHover(e, true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				onHover(e, false);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
	
	public void onHover(MouseEvent e, boolean exit) {}
	
	public void onAction(ActionEvent e) {}
	
	@Override
	public void setBorder(Border border) {}

}
