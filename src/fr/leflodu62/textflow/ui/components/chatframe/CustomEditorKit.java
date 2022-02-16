package fr.leflodu62.textflow.ui.components.chatframe;

import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;

public class CustomEditorKit extends HTMLEditorKit  {

	private static final long serialVersionUID = 1L;
	
	ViewFactory factory = new CustomViewFactory();
	@Override
	public ViewFactory getViewFactory() {
		return factory;
	}
	
}
