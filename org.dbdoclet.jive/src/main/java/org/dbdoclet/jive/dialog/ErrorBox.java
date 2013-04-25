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
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.FileAccessDeniedException;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class ErrorBox extends InfoDialog {

	private static final long serialVersionUID = 1L;

	public ErrorBox(Frame parent, String title, String msg) {
		super(parent, title, msg);
	}

	@Override
	protected void init() {
		super.init();
		setGradient(JiveConstants.COLOUR_FIREBRICK_1,
				JiveConstants.COLOUR_FIREBRICK_4);
	}

	private static Log logger = LogFactory.getLog(ErrorBox.class);

	public static void show(String error, String msg) {
		show(null, error, msg);
	}

	public static void show(Window parent, String error, String msg) {

		try {

			if (error == null) {
				throw new IllegalArgumentException(
						"The argument error may not be null!");
			}

			if (msg == null) {
				throw new IllegalArgumentException(
						"The argument msg may not be null!");
			}

			InfoDialog dlg = null;

			if (parent instanceof Frame) {
				dlg = new InfoDialog(parent, error, msg);
			}

			if (parent instanceof Dialog) {
				dlg = new InfoDialog(parent, error, msg);
			}

			if (dlg == null) {
				dlg = new InfoDialog((Frame) null, error, msg);
			}

			dlg.init();
			dlg.setGradient(JiveConstants.COLOUR_FIREBRICK_1,
					JiveConstants.COLOUR_FIREBRICK_4);
			dlg.pack();
			dlg.center(parent);
			dlg.setVisible(true);
			dlg.toFront();

			logger.error(msg);

		} catch (Throwable oops) {

			oops.printStackTrace();
			JOptionPane.showMessageDialog(parent, msg, error,
					JOptionPane.ERROR_MESSAGE);
		}
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
/*
 * $Log$
 */
