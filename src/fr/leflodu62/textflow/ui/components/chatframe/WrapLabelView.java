package fr.leflodu62.textflow.ui.components.chatframe;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.html.InlineView;

public class WrapLabelView extends InlineView {

	public WrapLabelView(Element elem) {
		super(elem);
	}

	 @Override
	public float getMinimumSpan(int axis) {
         switch (axis) {
             case View.X_AXIS:
                 return 0;
             case View.Y_AXIS:
                 return super.getMinimumSpan(axis);
             default:
                 throw new IllegalArgumentException("Invalid axis: " + axis);
         }
     }

}
