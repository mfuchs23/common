package org.dbdoclet.jive;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.net.URL;

import javax.swing.ImageIcon;

import org.dbdoclet.service.StringServices;

public class JiveServices {

    /**
     * Zentriert eine Fenster in einem übergeordneten Fenster, oder auf dem
     * Bildschirm.
     * 
     * @param parent
     * @param wnd
     */
    public static void center(Window parent, Window wnd) {

        Dimension dimension;

        int width = wnd.getWidth();
        int height = wnd.getHeight();

        int xpos;
        int ypos;

        if (parent == null || parent.isShowing() == false) {

            dimension = Toolkit.getDefaultToolkit().getScreenSize();
            xpos = (int) ((dimension.getWidth() - width) / 2);
            ypos = (int) ((dimension.getHeight() - height) / 2);

        } else {

            dimension = parent.getSize();
            Point point = parent.getLocationOnScreen();

            xpos = (int) ((dimension.getWidth() - width) / 2);
            ypos = (int) ((dimension.getHeight() - height) / 2);

            xpos += point.x;
            ypos += point.y;
        }

        wnd.setSize(width, height);
        wnd.setLocation(xpos, ypos);
    }

    public static void center(Window wnd) {
        center(null, wnd);
    }
    
    public static ImageIcon getJlfgrIcon(String iconUrl) {

        String buffer = StringServices.cutPrefix(iconUrl, "jlfgr:");
        String[] tokens = buffer.split("/");
        
        if (tokens != null && tokens.length == 2) {
            return getJlfgrIcon(tokens[0], tokens[1]);
        }
        
        throw new IllegalArgumentException("Invalid iconUrl '" + iconUrl + "'!");
    }

    public static ImageIcon getJlfgrIcon(String category, String name) {

        if (category == null) {
            throw new IllegalArgumentException("The argument category must not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String path = "/toolbarButtonGraphics/" + category + "/" + name;

        URL iconUrl = loader.getResource(path);

        if (iconUrl == null) {
            iconUrl = JiveFactory.class.getResource(path);
        }

        if (iconUrl == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(iconUrl, path);

        return icon;
    }


}
