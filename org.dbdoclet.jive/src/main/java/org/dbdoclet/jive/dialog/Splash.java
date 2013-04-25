/*
 * ### Copyright (C) 2001-2005 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: Splash.java,v 1.1.1.1 2004/12/21 13:56:58 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 13:56:58 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Splash extends Window {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Splash splash;
    private static Frame frame;

    private Font fontBold;
    private Font fontMonospaced;
    private Image image;
    private String msg = "";
    private String version;
    private boolean ready = false;
    private int height;
    private int padding = 25;
    private int width;
    private int lineHeight = 20;

    private Splash(Frame frame) {

        super(frame);
    }
        
    public static Splash getSplash() {

        if (splash == null) {

            frame = new Frame();
            splash = new Splash(frame);
        }

        
        return splash;
    }

    public void setMessage(String msg) {

        if (msg == null) {
            msg = "";
        }

        this.msg = msg;

        repaint(0, height - lineHeight, width, height);
    }

    public void close() {

        if (frame != null) {
            frame.dispose();
        }
    }

    public void isReady(boolean ready) {

        this.ready = ready;
    }

    public boolean isReady() {

        return ready;
    }

    @Override
    public void update(Graphics g) {

        paint(g);
    }

    @Override
    public void paint(Graphics graphics) {

        int strWidth;

        graphics.setFont(fontBold);

        graphics.drawImage(image, 0, 0, this);

        FontMetrics metrics = graphics.getFontMetrics();

        strWidth = getStringWidth(version, metrics);
        graphics.drawString(version, width - strWidth, height - padding);

        graphics.setFont(fontMonospaced);
        strWidth = getStringWidth(msg, metrics);
        graphics.clearRect(0, height - lineHeight, width, height);
        graphics.drawString(msg, 10, height - 4);

        graphics.drawRect(0, 0, width - 1, height - 1);

        if (ready == false) {

            synchronized (splash) {

                ready = true;
                notifyAll();
            }
        }
    }

    public Splash splashImage(Image image) {

        return splashImage(image, "");
    }

    public Splash splashImage(Image image, String version) {
        
        init(image, version);

        splash.setVisible(true);
        splash.toFront();

        while (splash.isReady() == false) {

            try {

                synchronized (splash) {
                    splash.wait();
                }

            } catch (InterruptedException oops) {
                // Jetzt gehts weiter
            }
        }

        return splash;
    }

    private void init(Image image, String version) {

        if (image == null) {
            throw new IllegalArgumentException("Parameter image is null!");
        }

        if (version == null) {
            throw new IllegalArgumentException
                ("The argument version may not be null!");
        }

        this.image = image;
        this.version = version;

        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 0);

        try {

            tracker.waitForID(0);

        } catch (InterruptedException oops) {

            oops.printStackTrace();
        }

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        width = image.getWidth(this);
        height = image.getHeight(this);
        height += lineHeight;

        setSize(width, height);
        setLocation((dimension.width - width) / 2,
                    (dimension.height - height) / 2);
        
        fontBold = new Font("SansSerif", Font.BOLD, 14);
        fontMonospaced = new Font("Monospaced", Font.PLAIN, 12);
        
        addMouseListener(new DisposeListener());

    }

    private int getStringWidth(String str, FontMetrics metrics) {

        if (str == null) {

            throw new IllegalArgumentException(
                "The argument str may not be null!");
        }

        if (metrics == null) {

            throw new IllegalArgumentException(
                "The argument metrics may not be null!");
        }

        char[] chars = str.toCharArray();
        int width = 0;

        for (int i = 0; i < chars.length; i++) {

            width += metrics.charWidth(chars[i]);
        }

        width += padding;

        return width;
    }

    class DisposeListener extends MouseAdapter {

        @Override
	public void mouseClicked(MouseEvent evt) {
            
            synchronized (splash) {

                isReady(true);
                notifyAll();
            }

            dispose();
        }
    }
}
