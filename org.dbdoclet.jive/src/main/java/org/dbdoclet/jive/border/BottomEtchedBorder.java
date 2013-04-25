package org.dbdoclet.jive.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class BottomEtchedBorder extends AbstractBorder {

    private static final long serialVersionUID = 1L;
    private static final Color darkColor = new Color(199, 199, 199);
    private static final Color lightColor = Color.white;

    public BottomEtchedBorder() {
        super();
    }

    @Override
    public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {

        int ypos = y + height - 2;
        
        g.setColor(darkColor);
        g.drawLine(x, ypos, width - 1, ypos);
        g.setColor(lightColor);
        g.drawLine(x, ypos + 1, width - 1, ypos + 1);
    }
    
    @Override
    public Insets getBorderInsets(Component comp) {
        return new Insets(2, 2, 2, 2);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
