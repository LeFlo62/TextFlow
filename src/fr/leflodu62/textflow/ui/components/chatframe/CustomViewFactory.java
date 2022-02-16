package fr.leflodu62.textflow.ui.components.chatframe;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class CustomViewFactory extends HTMLEditorKit.HTMLFactory {

	@Override
	public View create(Element elem) {
		final AttributeSet attrs = elem.getAttributes();
		final Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
		final Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
		if (o instanceof HTML.Tag) {
			final HTML.Tag kind = (HTML.Tag) o;
			if (kind == HTML.Tag.CONTENT) {
				return new WrapLabelView(elem);
			}
		}
		return super.create(elem);
	}

}
