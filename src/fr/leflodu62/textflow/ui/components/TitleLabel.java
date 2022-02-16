package fr.leflodu62.textflow.ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class TitleLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	public TitleLabel(String title, int width) {
		super(title);
		setForeground(new Color(189, 195, 199));
		setFont(new Font("Tahoma", Font.PLAIN, 20));
		setBounds(4, 2, width, 23);
	}

}
