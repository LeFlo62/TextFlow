package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.Border;

public class TextField extends JTextField {

	private static final long serialVersionUID = 3698333991889001131L;
	
	private String textHint;
	
	public TextField() {
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				onFocusLost();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				onFocusGained();
			}
		});
	}
	
	public void setTextHint(String textHint) {
		this.textHint = textHint;
		setText(textHint);
		setFont(new Font("Tahoma", Font.ITALIC, 14));
	}
	
	public void onFocusGained() {
		if(textHint != null && super.getText().equals(textHint)) {
			setText("");
			setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		setBackground(new Color(236, 240, 241));
	}
	
	public void onFocusLost() {
		if(textHint != null && super.getText().isEmpty()) {
			setText(textHint);
			setFont(new Font("Tahoma", Font.ITALIC, 14));
		}
		setBackground(new Color(189, 195, 199));
	}
	
	public String getTextHint() {
		return textHint;
	}
	
	@Override
	public void setBorder(Border border) {}
	
	@Override
	public String getText() {
		if(textHint == null || !super.getText().equals(textHint)) {
			return super.getText();
		}
		return new String();
	}
				
}

