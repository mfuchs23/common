/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.imagechooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;

import org.dbdoclet.jive.dialog.ImageChooser;

public class ImagePreview extends JComponent
    implements PropertyChangeListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final int SIZE = 400;

    private ImageIcon image = null;
    private ImageIcon thumbnail = null;
    private File file = null;

    public ImagePreview(ImageChooser chooser) {

        setMinimumSize(new Dimension(SIZE + 10, SIZE + 10));
        setPreferredSize(new Dimension(SIZE + 20, SIZE + 20));
        setMaximumSize(new Dimension(SIZE + 30, SIZE + 30));

        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        chooser.addPropertyChangeListener(this);
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

            if (image.getIconWidth() > SIZE) {

                thumbnail = new ImageIcon(image.getImage().
                                          getScaledInstance(SIZE, -1,
                                                            Image.SCALE_DEFAULT));
            } else {
                
                thumbnail = image;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent e) {

        boolean update = false;
        String prop = e.getPropertyName();

        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {

            file = null;
            update = true;

        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {

            file = (File) e.getNewValue();
            update = true;
        }

        if (update) {

            thumbnail = null;

            if (isShowing()) {

                loadImage();
                repaint();
            }
        }
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
