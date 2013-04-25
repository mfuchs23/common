/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class InputDialog extends DataDialog {

	private static final long serialVersionUID = 1L;
	private String value = "";
	private String label = "";
	private JTextField entry;
	private JLabel description;

	public InputDialog(Frame parent, String title) {
		super(parent, title);
		init(parent);
	}

	public InputDialog(Frame parent, String title, boolean isModal) {
		super(parent, title, isModal);
		init(parent);
	}

	private void init(Frame parent) {

		JiveFactory wm = JiveFactory.getInstance();
		ResourceBundle res = wm.getResourceBundle();

		GridPanel g = super.getGridPanel();

		JPanel panel = new JPanel();
		description = new JLabel();
		entry = new JTextField();
		Font f = entry.getFont();
		entry.setPreferredSize(new Dimension(300, f.getSize() + 8));
		entry.setAlignmentY((float) 0.5);

		entry.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				value = entry.getText();
				setVisible(false);
			}
		});

		panel.add(description);
		panel.add(entry);

		GridPanel buttons = new GridPanel();

		JButton ok = new JButton(ResourceServices.getString(res, "C_OK"));
		ok.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				value = entry.getText();
				setVisible(false);
				setPerformedAction(DialogAction.OK);
			}
		});

		JButton cancel = new JButton(
				ResourceServices.getString(res, "C_CANCEL"));
		cancel.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				setPerformedAction(DialogAction.CANCEL);
			}
		});

		buttons.addComponent(ok, Anchor.WEST);
		buttons.addComponent(cancel, Anchor.EAST);

		g.addComponent(panel, Anchor.CENTER, Fill.BOTH);
		g.incrRow();

		g.addComponent(buttons, Anchor.WEST, Fill.HORIZONTAL);

		pack();
		center(parent);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		entry.setText(value);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
		description.setText(label);
	}

	@Override
	public boolean isCanceled() {
		return getPerformedAction() == DialogAction.CANCEL ? true : false;
	}
}
