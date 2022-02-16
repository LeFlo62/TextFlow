package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PasswordField extends JPasswordField {

	private static final long serialVersionUID = 1L;
	
	private final String textHint = "mot de passe";
	
	public PasswordField() {
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(76, 186, 168, 28);
		setBackground(new Color(189, 195, 199));
		
		reset();
		
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(textHint != null && new String(getPassword()).isEmpty()) {
					setText(textHint);
					setFont(new Font("Tahoma", Font.ITALIC, 14));
				}
				setBackground(new Color(189, 195, 199));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(textHint != null && new String(getPassword()).equals(textHint)) {
					setText("");
					setFont(new Font("Tahoma", Font.PLAIN, 16));
				}
				setBackground(new Color(236, 240, 241));
			}
		});
	}
	
	public void reset() {
		setText(textHint);
		setFont(new Font("Tahoma", Font.ITALIC, 14));
	}
	
	@Override
	public void setBorder(Border border) {}
	
	public String getTextHint() {
		return textHint;
	}

}
