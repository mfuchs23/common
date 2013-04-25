package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

public class ImagePreview extends JComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final int SIZE = 400;

    private ImageIcon image = null;
    private ImageIcon thumbnail = null;
    private File file = null;
    private int size = SIZE;

    public ImagePreview() {

        this(SIZE);
    }

    public ImagePreview(int size ) {

        setMinimumSize(new Dimension(size + 10, size + 10));
        setPreferredSize(new Dimension(size + 20, size + 20));
        setMaximumSize(new Dimension(size + 30, size + 30));

        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

    public int getImageWidth() {
        
        if (image != null) {
            return image.getIconWidth();
        }
        
        return 0;
    }

    public int getImageHeight() {
        
        if (image != null) {
            return image.getIconHeight();
        }
        
        return 0;
    }

    public void loadImage() {

        if (file == null) {

            image = null;
            thumbnail = null;
            return;
        }

        image = new ImageIcon(file.getPath());

        if (image != null) {

            if (image.getIconWidth() > size) {

                thumbnail = new ImageIcon(image.getImage().
                                          getScaledInstance(size, -1,
                                                            Image.SCALE_DEFAULT));
            } else {
                
                thumbnail = image;
            }
        }
    }

    public void setImage(File imageFile) {

        thumbnail = null;
        file = imageFile;

        loadImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.white);

        Rectangle visibleRect = getVisibleRect();
        g.clearRect(0,0, visibleRect.width, visibleRect.height);

        if (thumbnail == null) {
            loadImage();
        }

        if (thumbnail != null) {

            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }

            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
