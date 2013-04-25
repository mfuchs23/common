/* 
 * ### Copyright (C) 2006-20079 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.View;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.dialog.action.ActionCloseDialog;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.TopPanel;

public class InfoDialog extends AbstractDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String buttonPressed = "";
	private Font font;
	private JEditorPane info;
	private GridPanel panel;
	private TopPanel topPanel;
	private int buttonMask = DialogAction.getFlag(DialogAction.OK);
	private final String text;

	public InfoDialog(Window parent, String title, String text) {
		super(parent, title, true);
		this.text = text;
	}

	public InfoDialog(Window parent, String title, String msg, int buttonMask) {

		this(parent, title, msg);
		this.buttonMask = buttonMask;
	}

	public void actionPerformed(ActionEvent event) {

		if (event != null && event.getSource() instanceof JButton) {

			buttonPressed = event.getActionCommand();

			if (buttonPressed.equals("ok")) {
				setPerformedAction(DialogAction.OK);
			}

			if (buttonPressed.equals("cancel")) {
				setPerformedAction(DialogAction.CANCEL);
			}

			if (buttonPressed.equals("yes")) {
				setPerformedAction(DialogAction.YES);
			}

			if (buttonPressed.equals("no")) {
				setPerformedAction(DialogAction.NO);
			}
		}
	}

	@Override
	public Font getFont() {
		return font;
	}

	public GridPanel getPanel() {
		return panel;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	public void setGradient(Color gradientColorFrom, Color gradientColorTo) {
		topPanel.setGradient(gradientColorFrom, gradientColorTo);
	}

	public void setHeaderBackground(Color headerBackground) {
		topPanel.setBackground(headerBackground);
	}

	@Override
	protected void init() {

		super.init();

		panel = new GridPanel(new Insets(0, 0, 0, 0));
		panel.setBorder(BorderFactory.createEtchedBorder());

		getContentPane().add(panel);

		topPanel = createTopPanel(getTitle());

		panel.addComponent(topPanel, Anchor.NORTHWEST, Fill.HORIZONTAL);
		panel.incrRow();

		info = new JEditorPane();
		info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		info.setBackground(Color.white);
		info.setEditable(false);
		info.setFocusable(false);

		if (font != null) {
			info.setFont(font);
		}

		TextServices.setText(info, text);
		int lines = TextServices.countLines(text);

		if (lines < 40) {
			View view = info.getUI().getRootView(info);
			view.setSize(PREFERRED_WIDTH, Integer.MAX_VALUE);
			int preferredHeight = (int) view.getPreferredSpan(View.Y_AXIS);
			info.setPreferredSize(new Dimension(PREFERRED_WIDTH + 20,
					preferredHeight + 20));
		} else {
			info.setPreferredSize(new Dimension(PREFERRED_WIDTH + 20,
					PREFERRED_HEIGHT + 20));
		}

		JScrollPane scrollPane = new JScrollPane(info);
		scrollPane.setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
		panel.incrRow();

		ButtonPanel buttonPanel = new ButtonPanel(buttonMask, this);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		JButton okButton = buttonPanel.getOkButton();

		if (okButton != null) {

			okButton.addActionListener(new ActionCloseDialog(this, "ok"));
			getRootPane().setDefaultButton(okButton);
		}

		JButton yesButton = buttonPanel.getYesButton();
		if (yesButton != null) {
			yesButton.addActionListener(new ActionCloseDialog(this, "yes"));
		}

		JButton noButton = buttonPanel.getNoButton();
		if (noButton != null) {
			noButton.addActionListener(new ActionCloseDialog(this, "no"));
		}

		JButton cancelButton = buttonPanel.getCancelButton();
		if (cancelButton != null) {
			cancelButton
					.addActionListener(new ActionCloseDialog(this, "cancel"));
		}

		panel.addComponent(buttonPanel, Anchor.CENTER, Fill.HORIZONTAL);
		pack();
		center(getParentWindow());
	}
}
