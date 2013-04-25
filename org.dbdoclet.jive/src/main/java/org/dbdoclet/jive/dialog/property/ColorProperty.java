/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.JLabel;

public class ColorProperty extends AbstractProperty {

    private JLabel label;
    
    public ColorProperty(String label, Color color) {
        super(label, color);
    }

    @Override
    public int getType() {
        return TYPE_COLOR;
    }    

    public Color getColor() {
        return (Color) getValue();
    }

    @Override
    public void setValue(Object value) {

        if (value == null || value instanceof Color == false) {
            return;
        }

        super.setValue(value);
    }

    @Override
    public Component getRenderer(Object value) {
        
	Color color = getColor();
        
	JLabel label = new JLabel();
	label.setFont(getPlainFont());
	label.setText("Color");
        label.setOpaque(true);
        label.setForeground(color);
        label.setBackground(color);

        return label;
    }

    @Override
    public Component getEditor(Object value) {
    
	Color color = getColor();
    
	label = new JLabel();
	label.setText("Color");
	label.setOpaque(true);
	label.setForeground(color);
	label.setBackground(color);
	label.setName("colorType://" + color.getRGB());

	Color newColor = JColorChooser.showDialog(getPanel(),
		"Choose Color",
		color);                
    
	label.setForeground(newColor);
	label.setBackground(newColor);
	label.setName("colorType://" + newColor.getRGB());
	
	return label;
    }

    @Override
    public Object getEditorValue() {
	
	if (label == null) {
	    throw new IllegalStateException("The field label must not be null!");
	}

	return label.getForeground();
    }
}
