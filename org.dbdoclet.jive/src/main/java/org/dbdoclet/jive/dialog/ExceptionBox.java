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
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.TopPanel;
import org.dbdoclet.service.ResourceServices;

public class ExceptionBox extends AbstractDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final int BORDER_SIZE = 5;

	private boolean showDetails = true;
	private JScrollPane detailsPane;
	private JButton detailsButton;

	public ExceptionBox(Throwable oops) {

		super((Frame) null, "ExceptionBox", true);

		if (oops == null) {
			oops = new Throwable();
		}

		createDialog(null, null, oops);
		center();
	}

	public ExceptionBox(String title, Throwable oops) {

		super((Frame) null, title, true);

		if (oops == null) {
			oops = new Throwable();
		}

		createDialog(title, null, oops);
		center();
	}

	public ExceptionBox(String title, String msg, Throwable oops) {

		super((Frame) null, title, true);

		if (oops == null) {
			oops = new Throwable();
		}

		createDialog(title, msg, oops);
		center();
	}

	public ExceptionBox(Frame parent, Throwable oops) {

		this(parent, null, null, oops);
	}

	public ExceptionBox(Frame parent, String title, Throwable oops) {

		this(parent, title, null, oops);
	}

	public ExceptionBox(Frame parent, String title, String msg, Throwable oops) {

		super(parent, "Exception", true);

		if (oops == null) {
			oops = new Throwable();
		}

		createDialog(title, msg, oops);
		center(parent);
	}

	public ExceptionBox(Dialog parent, Throwable oops) {

		this(parent, null, null, oops);
	}

	public ExceptionBox(Dialog parent, String title, String msg, Throwable oops) {

		super(parent, "Exception", true);

		if (oops == null) {
			oops = new Throwable();
		}

		createDialog(title, msg, oops);
		center(parent);
	}

	private void createDialog(String title, String msg, Throwable oops) {

		if (title != null && title.length() > 0) {
			setTitle(title);
		}

		JiveFactory jf = JiveFactory.getInstance();
		ResourceBundle res = jf.getResourceBundle();

		GridPanel panel = new GridPanel(new Insets(0, 0, 0, 0));
		getContentPane().add(panel);
		panel.setLayout(new GridBagLayout());
		
		TopPanel topPanel = createTopPanel("Exception: "
				+ oops.getClass().getSimpleName());
		topPanel.setGradient(JiveConstants.COLOUR_RED_1, JiveConstants.COLOUR_RED_4);
		
		panel.addComponent(topPanel, Colspan.CS_2,
				Anchor.NORTHWEST, Fill.HORIZONTAL);
		panel.incrRow();

		JLabel label;

		label = jf.createLabel(" "
				+ ResourceServices.getString(res, "C_MESSAGE") + ":");
		label.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		panel.addComponent(label);

		if (msg == null || msg.length() == 0) {
			msg = oops.getMessage();
		}

		if (msg == null) {
			msg = "";
		}

		label = jf.createLabel(" " + msg, Font.PLAIN);
		label.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

		panel.addComponent(label);
		panel.incrRow();

		label = jf.createLabel(" " + ResourceServices.getString(res, "C_CLASS")
				+ ":");
		label.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		panel.addComponent(label);

		label = jf.createLabel(" " + oops.getClass().getName(), Font.PLAIN);
		label.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		panel.addComponent(label);
		panel.incrRow();

		JTextArea detailsArea = jf.createTextArea(new Identifier("details"));
		detailsArea.setEditable(false);

		detailsPane = jf.createScrollPane(detailsArea);

		Throwable cause;
		StringWriter buffer;
		String areaText = "";

		buffer = new StringWriter();
		oops.printStackTrace(new PrintWriter(buffer));

		areaText = buffer.toString();

		cause = oops.getCause();

		while (cause != null) {

			buffer = new StringWriter();
			cause.printStackTrace(new PrintWriter(buffer));
			areaText += buffer.toString();

			cause = cause.getCause();
		}

		detailsArea.setText(areaText);
		detailsArea.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

		panel.addComponent(detailsPane, Colspan.CS_2, Anchor.NORTHWEST,
				Fill.BOTH);
		panel.incrRow();
		
		JPanel buttonPanel = jf.createPanel(null);
		JButton closeButton = jf.createButton(null, ResourceServices.getString(res,
				"C_CLOSE"));

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		buttonPanel.add(closeButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
				BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

		if (showDetails == true) {
			
			detailsButton = jf.createButton(new Identifier(
					"jive.dialog.exception.details"), ResourceServices.getString(
							res, "C_HIDE_DETAILS"));
		} else {
			
			detailsButton = jf.createButton(new Identifier(
					"jive.dialog.exception.details"), ResourceServices.getString(
							res, "C_SHOW_DETAILS"));
			detailsArea.setVisible(false);
		}
		
		detailsButton.addActionListener(this);
		detailsButton.setActionCommand("details");
		buttonPanel.add(detailsButton);

		panel.addVerticalGlue();
		panel.incrRow();
		panel.addComponent(buttonPanel, Colspan.CS_2, Anchor.NORTHWEST,
				Fill.HORIZONTAL);

		pack();
	}

	public void actionPerformed(ActionEvent event) {

		JiveFactory widgetMap = JiveFactory.getInstance();
		ResourceBundle res = widgetMap.getResourceBundle();

		String cmd = event.getActionCommand();

		if (cmd != null && cmd.equals("details")) {

			if (showDetails == true) {

				showDetails = false;

				detailsPane.setVisible(false);
				detailsButton.setText(ResourceServices.getString(res,
						"C_SHOW_DETAILS"));
				pack();

			} else {

				showDetails = true;

				detailsPane.setVisible(true);
				detailsButton.setText(ResourceServices.getString(res,
						"C_HIDE_DETAILS"));
				pack();
			}
		}
	}

}
