/* 
 * ### Copyright (C) 2007-2008 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.dialog.property.Property;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.PropertyPanel;
import org.dbdoclet.service.ResourceServices;

public class PropertyDialog extends DataDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private PropertyPanel propertyPanel;
	private JCheckBox showAgainCheckBox;
	private JButton okButton;
	private boolean showAgainEnabled;
	private boolean canceled;
	private boolean doHideOnClose = true;

	public PropertyDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		init();
	}

	public PropertyDialog(Frame parent, String title) {
		super(parent, title);
		init();
	}

	public PropertyDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		init();
	}

	public PropertyDialog(String title) {
		super(null, title);
		init();
	}

	@Override
	protected void init() {

		super.init();
		
		GridPanel panel = getGridPanel();

		propertyPanel = new PropertyPanel();
		// propertyPanel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		panel.addComponent(propertyPanel, Anchor.WEST, Fill.HORIZONTAL);
		panel.incrRow();
		
		ButtonPanel buttonPanel = new ButtonPanel(ButtonPanel.OK
				| ButtonPanel.CANCEL, this);
		panel.addComponent(buttonPanel, Anchor.WEST,
				Fill.HORIZONTAL);
		panel.incrRow();

        if (isShowAgainEnabled() == true) {

            showAgainCheckBox = new JCheckBox(ResourceServices.getString(res,"C_DONT_SHOW_THIS_DIALOG_AGAIN"));
            showAgainCheckBox.setSelected(false);

            panel.addComponent(showAgainCheckBox, Colspan.CS_2, Anchor.WEST, Fill.HORIZONTAL);
            panel.incrRow();
        }

		okButton = buttonPanel.getOkButton();
		getRootPane().setDefaultButton(okButton);
	}

	public void addProperty(Property property) {
		propertyPanel.addProperty(property);
	}

	@Override
	public void setVisible(boolean visible) {

		if (visible == true) {

			propertyPanel.setVisible(visible);
			pack();
			center(getParentWindow());
			okButton.requestFocus();
		}

		super.setVisible(visible);
	}

	public boolean showAgain() {

		if (showAgainCheckBox != null) {
			return showAgainCheckBox.isSelected();
		}

		return true;
	}

	public boolean isShowAgainEnabled() {
		return showAgainEnabled;
	}

	@Override
    public boolean isCanceled() {
        return canceled;
    }

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public void setDoHideOnClose(boolean doHideOnClose) {
		this.doHideOnClose = doHideOnClose;
	}

	public void setShowAgainEnabled(boolean showAgainEnabled) {
		this.showAgainEnabled = showAgainEnabled;
	}
	
	public boolean getDoHideOnClose() {
		return doHideOnClose;
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd != null && cmd.equals("ok")) {

			propertyPanel.stopEditing();
			setCanceled(false);

			if (doHideOnClose == true) {
				setVisible(false);
			}
		}

		if (cmd != null && cmd.equals("cancel")) {

			setCanceled(true);
			setVisible(false);
		}
	}

}
