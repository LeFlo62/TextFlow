package fr.leflodu62.textflow.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.ImageIcon;

import fr.leflodu62.textflow.TextFlow;

public class DecorationButton extends Button {

	private static final long serialVersionUID = 1241649698352616813L;
	
	private final String texture;
	private final Consumer<ActionEvent> action;
	
	public DecorationButton(String texture, int x, int y, Consumer<ActionEvent> action) {
		this.texture = texture;
		this.action = action;
		setIcon(new ImageIcon(TextFlow.class.getResource("/assets/"+texture+".png")));
		setContentAreaFilled(false);
		setBounds(x, y, 25, 25);
		setOpaque(false);
		setContentAreaFilled(false);
	}
	
	@Override
	public void onHover(MouseEvent e, boolean exit) {
		if(!exit) {
			setIcon(new ImageIcon(TextFlow.class.getResource("/assets/"+texture+"_hovered.png")));
		} else {
			setIcon(new ImageIcon(TextFlow.class.getResource("/assets/"+texture+".png")));
		}
	}
	
	@Override
	public void onAction(ActionEvent e) {
		action.accept(e);
	}

}
