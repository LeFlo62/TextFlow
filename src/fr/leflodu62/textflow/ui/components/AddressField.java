package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class AddressField extends TextField {

	private static final long serialVersionUID = 1L;

	public AddressField() {
		reset();
		setForeground(Color.BLACK);
		setBackground(new Color(189, 195, 199));
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(76, 124, 168, 28);
		setColumns(10);
	}

	public void reset() {
		setTextHint("adresse");
		setFont(new Font("Tahoma", Font.ITALIC, 14));
	}

	@Override
	public void onFocusGained() {
		super.onFocusGained();
	}

	@Override
	public void onFocusLost() {
		super.onFocusLost();
	}

	@Override
	public void setTextHint(String textHint) {
		super.setTextHint(textHint);
	}

}
