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
package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.action.ActionCloseDialog;
import org.dbdoclet.jive.text.ScreenPane;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

public class ProcessBox extends AbstractDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int MAX_COLUMNS = 120;

	private static Log logger = LogFactory.getLog(ProcessBox.class);

	private GridPanel panel;
	private boolean canceled = false;

	private ResourceBundle res;
	private JiveFactory wm;

	private ScreenPane screen;
	private JButton cancelButton;
	private JLabel headLabel;

	public ProcessBox(Frame owner, String title) {
		this(owner, title, false);
	}

	public ProcessBox(final Frame owner, final String title,
			final boolean isModal) {

		super(owner, title, isModal);

		try {

			if (SwingUtilities.isEventDispatchThread()) {

				init(owner, title, isModal);

			} else {

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						init(owner, title, isModal);
					}
				});
			}

		} catch (Exception oops) {
			logger.fatal("ProcessBox.ProcessBox", oops);
		}
	}

	private void init(Frame owner, String title, boolean isModal) {

		wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		Container contentPane = getContentPane();

		panel = new GridPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		contentPane.add(panel);

		URL iconUrl = ResourceServices.getResourceAsUrl("/images/Time16.gif",
				ProcessBox.class.getClassLoader());
		ImageIcon icon = new ImageIcon(iconUrl, "header background");

		headLabel = new JLabel(ResourceServices.getString(res,
				"C_PROCESSBOX_HEADER"), icon, SwingConstants.LEADING);
		panel.addComponent(headLabel, Anchor.CENTER, Fill.HORIZONTAL);
		panel.incrRow();

		screen = new ScreenPane(MAX_COLUMNS, 10);
		screen.setBorder(BorderFactory.createLoweredBevelBorder());
		screen.setBackground(Color.white);

		JScrollPane scrollPane = new JScrollPane(screen);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		panel.addComponent(scrollPane, Anchor.CENTER, Fill.BOTH);
		panel.incrRow();

		ButtonPanel buttonPanel = new ButtonPanel(ButtonPanel.CANCEL, this);
		cancelButton = buttonPanel.getCancelButton();

		panel.addComponent(buttonPanel, Anchor.CENTER, Fill.HORIZONTAL);

		canceled = false;

		pack();
		center(owner);
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void waitForClose(final Object mutex) {

		try {

			if (SwingUtilities.isEventDispatchThread()) {

				ActionCloseDialog action = new ActionCloseDialog(this,
						"close-dialog");
				action.setMutex(mutex);

				cancelButton.setAction(action);
				cancelButton
						.setText(ResourceServices.getString(res, "C_CLOSE"));

				headLabel.setText(ResourceServices.getString(res,
						"C_PROCESS_FINSIHED"));
				headLabel.setIcon(null);

			} else {

				final AbstractDialog dlg = this;

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {

						ActionCloseDialog action = new ActionCloseDialog(dlg,
								"close");
						action.setMutex(mutex);

						cancelButton.setAction(action);
						cancelButton.setText(ResourceServices.getString(res,
								"C_CLOSE"));

						headLabel.setText(ResourceServices.getString(res,
								"C_PROCESS_FINISHED"));
						headLabel.setIcon(null);
					}
				});
			}

		} catch (Exception oops) {
			logger.fatal("ProcessBox.waitForClose", oops);
		}
	}

	public void info(String msg) {

		if (msg == null) {
			return;
		}

		if (msg.length() > MAX_COLUMNS) {
			msg = StringServices.shorten(msg, (MAX_COLUMNS - 4));
		}

		try {

			if (SwingUtilities.isEventDispatchThread()) {

				screen.info(msg);

			} else {

				final String buf = msg;

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						screen.info(buf);
					}
				});
			}

		} catch (Exception oops) {
			logger.fatal("ProcessBox.info", oops);
		}
	}

	public final void actionPerformed(ActionEvent actionEvent) {

		String cmd = actionEvent.getActionCommand();

		if (cmd != null && cmd.equals("cancel") == true) {
			canceled = true;
		}
	}
}
