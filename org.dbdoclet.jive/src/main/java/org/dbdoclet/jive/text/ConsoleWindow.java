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

import java.awt.Dimension;
import java.awt.Frame;

import org.dbdoclet.io.Screen;
import org.dbdoclet.jive.dialog.AbstractDialog;

public class ConsoleWindow extends AbstractDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Terminal term;
    private Screen screen;

    public ConsoleWindow(Frame parent, String title, Dimension dim) {

        super(parent, title, false);

        if (dim == null) {
            throw new IllegalArgumentException("The argument dim must not be null!");
        }

        term = new Terminal(false, dim);
        screen = term.getScreen();

        getContentPane().add(term);
        setSize(dim);
        center(parent);
    }

    public Screen getScreen() {
        return screen;
    }

    public void println(String msg) {
        screen.println(msg);
    }
}
