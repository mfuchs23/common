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

import javax.swing.JLabel;

import org.dbdoclet.jive.widget.NumberTextField;

/**
 * Numerische Eigenschaft.
 * 
 * @author michael
 */
public class IntegerProperty extends AbstractProperty {

	private NumberTextField numberTextField;
	private final int cols;

	public IntegerProperty(String label, int value, int cols) {
		super(label, new Integer(value));
		this.cols = cols;
	}

	@Override
	public int getType() {
		return TYPE_INTEGER;
	}

	public Integer getInteger() {
		return (Integer) getValue();
	}

	@Override
	public Component getRenderer(Object value) {

		JLabel label = new JLabel();
		label.setFont(getPlainFont());

		if (value != null) {
			label.setText(value.toString());
		}
		
        return label;
	}

	@Override
	public Component getEditor(Object value) {

		numberTextField = new NumberTextField(cols); 
		
		if (value != null) {
			numberTextField.setText(value.toString());
		}
		
		if (getAction() != null) {
			numberTextField.setAction(getAction());
		}
		
		return numberTextField;
	}

	@Override
	public Object getEditorValue() {

		if (numberTextField == null) {
			throw new IllegalStateException(
					"The field checkBox must not be null!");
		}

		return numberTextField.getText();
	}
}
