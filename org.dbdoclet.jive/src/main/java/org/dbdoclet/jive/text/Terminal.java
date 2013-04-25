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
package org.dbdoclet.jive.text;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.dbdoclet.io.Screen;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class Terminal extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ResourceBundle res;
    private ScreenPane screen;
    private boolean hasCloseButton;

    public Terminal(boolean hasCloseButton, Dimension preferredSize) {

        this.hasCloseButton = hasCloseButton;

        JiveFactory widgetMap = JiveFactory.getInstance();
        res = widgetMap.getResourceBundle();
        
        screen = new ScreenPane();
        screen.setPreferredSize(preferredSize);
        screen.setMinimumSize(new Dimension(40, 10));
        screen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(screen);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(scrollPane);
        add(Box.createRigidArea(new Dimension(0, 4)));

        if (hasCloseButton) {

            JButton closeButton = new JButton(ResourceServices.getString(res,"C_CLOSE"));
            closeButton.setActionCommand("close");
            closeButton.addActionListener(this);
            closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(closeButton);
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public Terminal() {
        this(false, new Dimension(700, 100));
    }

    public Terminal(boolean hasCloseButton) {
        this(hasCloseButton, new Dimension(700, 100));
    }

    public Terminal(Dimension dim) {
        this(false, dim);
    }

    @Override
    public void setPreferredSize(Dimension dim) {

        super.setSize(dim);

        Dimension screenDim;

        if (hasCloseButton) {

            screenDim = new Dimension(dim.width - 5, dim.height - 20);
        } else {

            screenDim = new Dimension(dim.width - 5, dim.height - 5);
        } // end of else

        screen.setSize(screenDim);
    }

    public Screen getScreen() {

        return screen;
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd.equals("close")) {

            Container parent = getParent();

            while (parent != null) {

                if (parent instanceof Window) {

                    Window wnd = (Window) parent;
                    wnd.setVisible(false);
                    wnd.dispose();

                    break;
                } // end of if ()

                parent = parent.getParent();
            } // end of while ()
        }
    }
}
