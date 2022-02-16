package fr.leflodu62.textflow.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedBorder implements Border {
	
     private final Color color;
     private final int radius;
     private final int strokeWeight;
	 
     public RoundedBorder(Color color, int radius, int strokeWeight) {
    	 this.color = color;
    	 this.radius = radius;
    	 this.strokeWeight = strokeWeight;
     }
     
     @Override
     public Insets getBorderInsets(Component c) {
         return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
     }

     @Override
     public boolean isBorderOpaque() {
         return true;
     }

     @Override
     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	 g.setColor(color);
    	 ((Graphics2D)g).setStroke(new BasicStroke(strokeWeight));
         g.drawRoundRect(x,y,width-1,height-1,radius,radius);
     }

}
