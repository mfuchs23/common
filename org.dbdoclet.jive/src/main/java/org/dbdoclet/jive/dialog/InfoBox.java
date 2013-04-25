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

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JOptionPane;

public class InfoBox {

	public static void show(String info, String msg) {

		show(null, info, msg, -1);
	}

	public static void show(Window parent, String info, String msg) {

		show(parent, info, msg, -1);
	}

	public static void show(Window parent, String info, String msg, int timeout) {

		try {

			if (info == null) {
				throw new IllegalArgumentException(
						"The argument info may not be null!");
			}

			if (msg == null) {
				throw new IllegalArgumentException(
						"The argument msg may not be null!");
			}

			AbstractDialog dlg = null;

			if (parent instanceof Frame) {
				dlg = new InfoDialog((Frame) parent, info, msg);
			}

			if (parent instanceof Dialog) {
				dlg = new InfoDialog((Dialog) parent, info, msg);
			}

			if (dlg == null) {
				dlg = new InfoDialog((Frame) null, info, msg);
			}

			if (timeout > 300) {
				dlg.setTimeout(timeout);
			}

			dlg.init();
			dlg.pack();
			dlg.center(parent);
			dlg.setVisible(true);

		} catch (Throwable oops) {

			oops.printStackTrace();

			JOptionPane.showMessageDialog(parent, msg, info,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
/*
 * $Log$
 */
