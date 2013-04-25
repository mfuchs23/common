/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.View;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.action.ActionCloseDialog;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.TopPanel;
import org.dbdoclet.service.ResourceServices;

public class ContinueDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	private ResourceBundle res;
	private JEditorPane info;
	private GridPanel panel;
	private boolean doContinue = false;
	private JButton continueButton;
	private JButton cancelButton;

	private TopPanel topPanel;

	public ContinueDialog(Dialog parent, String title, String question)
			throws IOException {

		super(parent, title, true);
		init(parent, title, question);
	}

	public ContinueDialog(Frame parent, String title, String question)
			throws IOException {

		super(parent, title, true);
		init(parent, title, question);
	}

	public boolean doContinue() {
		return doContinue;
	}

	private void init(Window parent, String title, String question)
			throws IOException {

		JiveFactory wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();

		panel = new GridPanel(new Insets(0, 0, 0, 0));
		panel.setBorder(BorderFactory.createEtchedBorder());

		getContentPane().add(panel);

		topPanel = createTopPanel(title);
		topPanel.setGradient(JiveConstants.COLOUR_DARK_ORANGE,
				JiveConstants.COLOUR_DARK_ORANGE_4);
		
		panel.addComponent(topPanel, Anchor.NORTHWEST, Fill.HORIZONTAL);
		panel.incrRow();

		info = new JEditorPane();
		info.setBackground(Color.white);
		info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		info.setBackground(Color.white);
		info.setEditable(false);
		info.setFocusable(false);
		TextServices.setText(info, question);

		View view = info.getUI().getRootView(info);
		view.setSize(PREFERRED_WIDTH, Integer.MAX_VALUE);
		int preferredHeight = (int) view.getPreferredSpan(View.Y_AXIS);
		info.setPreferredSize(new Dimension(PREFERRED_WIDTH + 14, preferredHeight + 14));

		JScrollPane scrollPane = new JScrollPane(info);
		scrollPane.setBackground(Color.white);
		panel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
		panel.incrRow();

		GridPanel buttonPane = new GridPanel();
		buttonPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		continueButton = new JButton(ResourceServices.getString(res,
				"C_CONTINUE"));
		continueButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				doContinue = true;
				setVisible(false);
			}
		});

		buttonPane.addComponent(continueButton, Anchor.CENTER, Fill.NONE);

		cancelButton = new JButton(ResourceServices.getString(res, "C_CANCEL"));
		cancelButton.addActionListener(new ActionCloseDialog(this,
				"cancel-dialog"));
		buttonPane.addComponent(cancelButton, Anchor.CENTER, Fill.NONE);

		panel.addComponent(buttonPane, Anchor.CENTER, Fill.HORIZONTAL);
		panel.incrRow();

		getRootPane().setDefaultButton(cancelButton);
		pack();
		center(parent);
	}

	public void setCancelButtonText(String cancelButtonText) {

		if (cancelButton != null) {
			cancelButton.setText(cancelButtonText);
		}
	}

	public void setContinueButtonText(String continueButtonText) {

		if (continueButton != null) {
			continueButton.setText(continueButtonText);
		}
	}

}
