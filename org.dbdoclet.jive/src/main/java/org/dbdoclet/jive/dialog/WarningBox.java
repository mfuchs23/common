/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.dbdoclet.FileAccessDeniedException;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.service.ResourceServices;

public class WarningBox {

	public static DialogAction show(String warning, String msg) {

		return show(null, warning, msg);
	}

	public static DialogAction show(Window parent, String warning, String msg) {

		return show(parent, warning, msg, ButtonPanel.OK);
	}

	public static DialogAction show(Window parent, String warning, String msg,
			int buttonMask) {

		try {

			if (warning == null) {
				throw new IllegalArgumentException(
						"The argument warning may not be null!");
			}

			if (msg == null) {
				throw new IllegalArgumentException(
						"The argument msg may not be null!");
			}

			InfoDialog dlg = null;

			if (parent instanceof Frame) {
				dlg = new InfoDialog(parent, warning, msg, buttonMask);
			}

			if (parent instanceof Dialog) {
				dlg = new InfoDialog(parent, warning, msg, buttonMask);
			}

			if (dlg == null) {
				dlg = new InfoDialog((Frame) null, warning, msg, buttonMask);
			}

			dlg.init();
			dlg.setGradient(JiveConstants.COLOUR_DARK_ORANGE,
					JiveConstants.COLOUR_DARK_ORANGE_4);
			dlg.pack();
			dlg.center(parent);
			dlg.setVisible(true);

			return dlg.getPerformedAction();

		} catch (Throwable oops) {

			oops.printStackTrace();

			JOptionPane.showMessageDialog(parent, msg, warning,
					JOptionPane.ERROR_MESSAGE);
		}

		return DialogAction.NONE;
	}

	public static DialogAction showWithCancel(Window parent, String warning,
			String msg) {

		try {

			if (warning == null) {
				throw new IllegalArgumentException(
						"The argument warning may not be null!");
			}

			if (msg == null) {
				throw new IllegalArgumentException(
						"The argument msg may not be null!");
			}

			AbstractDialog dlg = null;

			if (parent instanceof Frame) {
				dlg = new InfoDialog(parent, warning, msg, ButtonPanel.OK
						| ButtonPanel.CANCEL);
			}

			if (parent instanceof Dialog) {
				dlg = new InfoDialog(parent, warning, msg, ButtonPanel.OK
						| ButtonPanel.CANCEL);
			}

			if (dlg == null) {
				dlg = new InfoDialog((Frame) null, warning, msg, ButtonPanel.OK
						| ButtonPanel.CANCEL);
			}

			dlg.setSize(400, 500);
			dlg.pack();
			dlg.center(parent);
			dlg.setVisible(true);

			return dlg.getPerformedAction();

		} catch (Throwable oops) {

			oops.printStackTrace();

			JOptionPane.showMessageDialog(parent, msg, warning,
					JOptionPane.ERROR_MESSAGE);
		}

		return DialogAction.NONE;
	}

	public static DialogAction showYesNo(Window parent, String warning,
			String msg) {

		try {

			if (warning == null) {
				throw new IllegalArgumentException(
						"The argument warning may not be null!");
			}

			if (msg == null) {
				throw new IllegalArgumentException(
						"The argument msg may not be null!");
			}

			AbstractDialog dlg = null;

			if (parent instanceof Frame) {
				dlg = new InfoDialog(parent, warning, msg, ButtonPanel.YES
						| ButtonPanel.NO);
			}

			if (parent instanceof Dialog) {
				dlg = new InfoDialog(parent, warning, msg, ButtonPanel.YES
						| ButtonPanel.NO);
			}

			if (dlg == null) {
				dlg = new InfoDialog((Frame) null, warning, msg,
						ButtonPanel.YES | ButtonPanel.NO);
			}

			dlg.setSize(400, 500);
			dlg.pack();
			dlg.center(parent);
			dlg.setVisible(true);

			return dlg.getPerformedAction();

		} catch (Throwable oops) {

			oops.printStackTrace();

			JOptionPane.showMessageDialog(parent, msg, warning,
					JOptionPane.ERROR_MESSAGE);
		}

		return DialogAction.NONE;
	}

	public static void show(Throwable oops) {

		show(null, oops);
	}

	public static void show(Frame parent, Throwable oops) {

		String msg;

		JiveFactory wm = JiveFactory.getInstance();
		ResourceBundle res = wm.getResourceBundle();

		if (oops instanceof FileAccessDeniedException) {

			msg = MessageFormat.format(ResourceServices.getString(res,
					"C_ERROR_FILE_ACCESS_DENIED"),
					((FileAccessDeniedException) oops).getFile()
							.getAbsolutePath());

			show(parent, ResourceServices.getString(res, "C_ERROR"), msg);
			return;
		}

		ExceptionBox ebox = new ExceptionBox(oops);
		ebox.setVisible(true);
		ebox.toFront();
	}
}
