/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.jive.dialog;

import java.awt.Frame;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.dbdoclet.service.ResourceServices;

public class OverwriteBox {

    public static int showSingle(Frame parent, String title, String msg) {

	return show(parent, title, msg, OverwriteDialog.SINGLE);
    }

    public static int showMultiple(Frame parent, String title, String msg) {

	return show(parent, title, msg, OverwriteDialog.MULTIPLE);
    }

    private static int show(Frame parent, String title, String msg, int type) {

	if (title == null) {
	    throw new IllegalArgumentException("The argument title may not be null!");
	}

	if (msg == null) {
	    throw new IllegalArgumentException("The argument msg may not be null!");
	}

	try {

	    URL iconUrl = ResourceServices.getResourceAsUrl("/images/warningBoxHeaderBackground.jpg",
		    OverwriteBox.class.getClassLoader());
	    ImageIcon icon = new ImageIcon(iconUrl, "header background");

	    OverwriteDialog dlg = new OverwriteDialog(parent, title, icon, type);

	    dlg.setMessage(msg);

	    dlg.setSize(400, 500);
	    dlg.pack();
	    dlg.center(parent);
	    dlg.setVisible(true);

	    return dlg.getStatus();

	} catch (Throwable oops) {

	    oops.printStackTrace();

	    JOptionPane.showMessageDialog(parent, "Can't create OverwriteBox!!!", title, JOptionPane.ERROR_MESSAGE);
	}

	return -1;
    }
}
/*
 * $Log$
 */
