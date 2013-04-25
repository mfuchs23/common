/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import javax.swing.AbstractAction;

public class TextProperty extends AbstractProperty {

    public TextProperty(String label, String text) {
        super(label, text);
    }

    public TextProperty(String label, String text,
			AbstractAction action) {
    	super(label, text, action);
    }

	@Override
    public int getType() {
        return TYPE_TEXT;
    }    

    public String getText() {
        return (String) getValue();
    }
}
