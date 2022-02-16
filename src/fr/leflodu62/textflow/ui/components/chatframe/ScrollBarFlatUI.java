package fr.leflodu62.textflow.ui.components.chatframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarFlatUI extends BasicScrollBarUI {
	
	private static final Dimension ZERO_DIM = new Dimension(0,0);
	
	protected JButton createZeroButton() {
	    final JButton button = new JButton("zero button");
	    button.setPreferredSize(ZERO_DIM);
	    button.setMinimumSize(ZERO_DIM);
	    button.setMaximumSize(ZERO_DIM);
	    return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
	    return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
	    return createZeroButton();
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(new Color(44, 62, 80));
		
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.setColor(new Color(189, 195, 199));
		g.fillRect(thumbBounds.x+thumbBounds.width-8-4, thumbBounds.y, 8, thumbBounds.height);
	}

		
}
