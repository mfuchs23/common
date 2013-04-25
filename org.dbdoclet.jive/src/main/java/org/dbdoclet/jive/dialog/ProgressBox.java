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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressVetoListener;

public class ProgressBox extends AbstractDialog implements ActionListener,
		ProgressVetoListener {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ProgressBox.class);

	private final JProgressBar progressBar = new JProgressBar();
	private final JEditorPane info = new JEditorPane();
	private GridPanel panel;
	private URL backgroundImageUrl;
	private ButtonPanel buttonPanel;

	private boolean canceled = false;
	private int maximum;
	private long startTime;
	private int value = 0;

	public ProgressBox(final Dialog owner, final String title) {

		super(owner, title, false);

		if (SwingUtilities.isEventDispatchThread()) {

			init(owner, title, backgroundImageUrl, false);

		} else {

			try {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						init(owner, title, backgroundImageUrl, false);
					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.ProgressBox", oops);
			}
		}
	}

	public ProgressBox(Frame owner, String title) {

		this(owner, title, null, false);
	}

	public ProgressBox(Frame owner, String title, URL backgroundImageUrl) {

		this(owner, title, backgroundImageUrl, false);
	}

	public ProgressBox(final Frame owner, final String title,
			final URL backgroundImageUrl, final boolean isModal) {

		super(owner, title, isModal);

		if (SwingUtilities.isEventDispatchThread()) {
			init(owner, title, backgroundImageUrl, isModal);
		} else {
			try {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						init(owner, title, backgroundImageUrl, isModal);
					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.ProgressBox", oops);
			}
		}
	}

	private void init(Window owner, String title, URL backgroundImageUrl,
			boolean isModal) {

		this.backgroundImageUrl = backgroundImageUrl;

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		progressBar.setIndeterminate(true);

		Container contentPane = getContentPane();

		panel = new GridPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		contentPane.add(panel);

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < 80; i++) {
			buffer.append(' ');
		}

		info.setBackground(Color.white);
		info.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.darkGray),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		info.setEditable(false);
		info.setFocusable(false);
		info.setText(buffer.toString());

		panel.addComponent(info, Anchor.CENTER, Fill.BOTH);
		panel.incrRow();

		panel.addComponent(progressBar, Anchor.CENTER, Fill.HORIZONTAL);
		panel.incrRow();

		buttonPanel = new ButtonPanel(ButtonPanel.CANCEL, this);
		panel.addComponent(buttonPanel, Anchor.CENTER, Fill.HORIZONTAL);

		canceled = false;
		pack();

		setSize(new Dimension(600, 300));
		center(owner);
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCancelButtonEnabled(boolean enabled) {

		if (buttonPanel != null) {

			JButton cancel = buttonPanel.getCancelButton();

			if (cancel != null) {
				cancel.setEnabled(enabled);
			}
		}
	}

	public void setMinimum(final int min) {

		if (SwingUtilities.isEventDispatchThread()) {

			progressBar.setMinimum(min);

		} else {

			try {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						progressBar.setMinimum(min);

					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.setMinimum", oops);
			}
		}
	}

	public void setIndeterminate(boolean indeterminate) {

		if (progressBar != null) {
			progressBar.setIndeterminate(indeterminate);
		}
	}

	public void setMaximum(final int max) {

		if (SwingUtilities.isEventDispatchThread()) {

			progressBar.setMaximum(max);
			progressBar.setIndeterminate(false);
			progressBar.setStringPainted(true);

		} else {

			try {

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						progressBar.setMaximum(max);
						progressBar.setIndeterminate(false);
						progressBar.setStringPainted(true);
					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.setMaximum", oops);
			}
		}
	}

	public void setValue(final int index) {

		if (SwingUtilities.isEventDispatchThread()) {

			progressBar.setValue(index);

		} else {

			try {

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						progressBar.setValue(index);
					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.setValue", oops);
			}
		}
	}

	public void setText(String text) {

		if (text == null) {
			text = "";
		}

		text = text.trim();

		final String msg = text;

		if (SwingUtilities.isEventDispatchThread()) {

			TextServices.setText(info, msg, backgroundImageUrl);

		} else {

			try {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						TextServices.setText(info, msg, backgroundImageUrl);
					}
				});

			} catch (Exception oops) {
				logger.fatal("ProgressBox.setText", oops);
			}
		}

	}

	public final void actionPerformed(ActionEvent actionEvent) {

		String cmd = actionEvent.getActionCommand();

		if (cmd != null && cmd.equals("cancel") == true) {
			canceled = true;
		}
	}

	public int getProgressMaximum() {
		return maximum;
	}

	public long getProgressStartTime() {
		return startTime;
	}

	public boolean progress(ProgressEvent event) {

		setText(event.getAction());
		progressIncr();
		return true;
	}

	public int progressIncr() {

		value++;
		setValue(value);
		return value;
	}

	public void setProgressMaximum(int maximum) {

		this.maximum = maximum;
		this.value = 0;

		setValue(value);
		setMaximum(maximum);
	}

	public void setProgressStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean veto(ProgressEvent event) {
		return false;
	}
}
