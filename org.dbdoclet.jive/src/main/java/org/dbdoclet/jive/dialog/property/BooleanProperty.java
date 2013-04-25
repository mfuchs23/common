/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;

import javax.swing.JCheckBox;

public class BooleanProperty extends AbstractProperty {

	private JCheckBox checkBox;

	public BooleanProperty(String label, boolean value) {
		super(label, new Boolean(value));
	}

	@Override
	public int getType() {
		return TYPE_BOOLEAN;
	}

	public Boolean getBoolean() {
		return (Boolean) getValue();
	}

	@Override
	public Component getRenderer(Object value) {

		if (value == null) {
			value = Boolean.FALSE;
		}

		if (value instanceof Boolean == false) {
			throw new IllegalArgumentException(
					"Parameter value must be of type Boolean!");
		}

		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(((Boolean) value).booleanValue());
		return checkBox;
	}

	@Override
	public Component getEditor(Object value) {

		if (value instanceof Boolean == false) {
			throw new IllegalStateException("Invalid value " + value);
		}

		checkBox = new JCheckBox();
		checkBox.setSelected(((Boolean) value).booleanValue());
		return checkBox;
	}

	@Override
	public Object getEditorValue() {

		if (checkBox == null) {
			throw new IllegalStateException(
					"The field checkBox must not be null!");
		}

		if (checkBox.isSelected()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
