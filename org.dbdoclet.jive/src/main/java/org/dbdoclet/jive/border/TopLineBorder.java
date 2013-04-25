package org.dbdoclet.jive.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.AbstractBorder;

public class TopLineBorder extends AbstractBorder {

    private static final long serialVersionUID = 1L;
    private final Color color;

    public TopLineBorder(Color color) {
        this.color = color;
    }

    @Override
    public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {

        g.setColor(color);
        g.drawLine(x, y, width -1, y);
    }
}
