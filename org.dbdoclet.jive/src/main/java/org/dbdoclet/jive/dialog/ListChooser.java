/** 
 * ### Copyright (C) 2009 Michael Fuchs
 * All Rights Reserved.               
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL: http://www.dbdoclet.org
 */
package org.dbdoclet.jive.dialog;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class ListChooser<E> extends AbstractDialog implements ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 1L;

	public final static int STANDARD = 1;
	public final static int MONOSPACED = 2;

	public static void main(String[] args) {

		JiveFactory.getInstance(Locale.getDefault());
		ListChooser<String> dlg = new ListChooser<String>(null, new String[] {
				"Dodo", "Strauß", "Emu", "Nachtigall" });
		dlg.setDescription("<html><p>Lorem ispum sed dolor amet.<br> Lorem ispum sed dolor amet.");
		dlg.createGui();
		dlg.setVisible(true);

		String text = dlg.getValue();
		System.out.println(text);
	}

	private ResourceBundle res;
	private JiveFactory wm;
	private String description = "";
	private final Object[] modelData;
	private JList list;
	private E value;

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ListChooser(Frame frame, E[] modelData) {

		super(frame, "ListChooser", true);
		this.modelData = modelData;
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equalsIgnoreCase("cancel")) {
			setPerformedAction(DialogAction.CANCEL);
			setVisible(false);
		}

		if (cmd.equalsIgnoreCase("ok")) {
			setPerformedAction(DialogAction.OK);
			setVisible(false);
		}
	}

	public void createGui() {

		wm = JiveFactory.getInstance();
		this.res = wm.getResourceBundle();

		GridPanel panel = new GridPanel();
		getRootPane().setContentPane(panel);

		panel.startSubPanel();

		JLabel label = new JLabel(description);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		panel.addComponent(label);

		panel.startSubPanel(Fill.HORIZONTAL);

		list = new JList(modelData);
		list.addListSelectionListener(this);

		panel.addComponent(new JScrollPane(list), Anchor.NORTHWEST,
				Fill.HORIZONTAL);

		panel.incrRow();

		JButton okButton = wm.createButton(null,
				ResourceServices.getString(res, "C_OK"));
		okButton.setActionCommand("ok");
		okButton.addActionListener(this);
		panel.addButton(okButton);

		JButton cancelButton = wm.createButton(null,
				ResourceServices.getString(res, "C_CANCEL"));
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		panel.addButton(cancelButton);

		panel.prepare();
		pack();
		center();
	}

	public boolean isCanceled() {
		return getPerformedAction() == DialogAction.CANCEL ? true : false;
	}

	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent event) {

		value = (E) list.getSelectedValue();
	}
}
