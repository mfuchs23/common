/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.jive.widget;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


public class UpperCaseTextField extends JTextField {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int maxLength = 1024;
    private JComponent next = null;

    public UpperCaseTextField(int cols) {
        super(cols);
    }

    public void setMaxLength(int maxLength) {

        this.maxLength = maxLength;
    }

    public void setNext(JComponent next) {

        this.next = next;
    }

    @Override
    protected Document createDefaultModel() {

        return new UpperCaseDocument();
    }

    class UpperCaseDocument extends PlainDocument {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {

            if ((str == null) || (getLength() >= maxLength)) {

                return;
            }

            char[] upper = str.toCharArray();

            for (int i = 0; i < upper.length; i++) {

                upper[i] = Character.toUpperCase(upper[i]);
            }

            super.insertString(offs, new String(upper), a);

            if (getLength() >= maxLength) {

                if (next != null) {

                    next.requestFocus();
                }

                return;
            }
        }
    }
}
/*
 * $Log$
 */
