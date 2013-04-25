package org.dbdoclet.jive.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class TopEtchedBorder extends AbstractBorder {

    private static final long serialVersionUID = 1L;
    private static final Color darkColor = new Color(199, 199, 199);
    private static final Color lightColor = Color.white;

    public TopEtchedBorder() {
        super();
    }

    @Override
    public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {

        g.setColor(darkColor);
        g.drawLine(x, y, width -1, y);
        g.setColor(lightColor);
        g.drawLine(x , y + 1, width -1, y + 1);
    }
    
    @Override
    public Insets getBorderInsets(Component comp) {
        return new Insets(15, 1, 1, 1);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
