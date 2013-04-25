/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.dbdoclet.jive.widget.PropertyPanel;

public abstract class AbstractProperty implements Property {

	private AbstractAction action;
	private boolean enabled = true;
	private JTextField entry;
	private String label;
	private PropertyPanel panel;
	private Font plainFont;
	private String toolTip;
	private Object value;

	public AbstractProperty(String label, Object value) {
		this(label, value, null);
	}

	public AbstractProperty(String label, Object value, AbstractAction action) {

		if (label == null) {
			throw new IllegalArgumentException(
					"The parameter label must not be null!");
		}

		this.label = label;
		this.value = value;
		this.action = action;

		JLabel comp = new JLabel();
		plainFont = comp.getFont();
		plainFont = plainFont.deriveFont(Font.PLAIN);
	}

	public AbstractAction getAction() {
		return action;
	}

	public Component getEditor(Object value) {

		entry = new JTextField();

		if (value != null) {
			entry.setText(value.toString());
		}

		if (action != null) {
			entry.setAction(action);
		}

		return entry;
	}

	public Object getEditorValue() {

		if (entry == null) {
			throw new IllegalStateException("The field entry must not be null!");
		}

		return entry.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dbdoclet.jive.dialog.property.Property#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	public PropertyPanel getPanel() {
		return panel;
	}

	public Font getPlainFont() {
		return plainFont;
	}

	public Component getRenderer(Object value) {

		if (value == null) {
			value = "";
		}

		JLabel label = new JLabel();
		label.setFont(getPlainFont());
		label.setText(value.toString());
		return label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dbdoclet.jive.dialog.property.Property#getToolTip()
	 */
	public String getToolTip() {

		if (toolTip == null) {
			toolTip = "";
		}

		return toolTip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dbdoclet.jive.dialog.property.Property#getType()
	 */
	public abstract int getType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dbdoclet.jive.dialog.property.Property#getValue()
	 */
	public Object getValue() {
		return value;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setAction(AbstractAction action) {
		this.action = action;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		panel.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dbdoclet.jive.dialog.property.Property#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public void setPanel(PropertyPanel dialog) {
		this.panel = dialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dbdoclet.jive.dialog.property.Property#setToolTip(java.lang.String)
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dbdoclet.jive.dialog.property.Property#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		this.value = value;
		panel.update();
	}
}