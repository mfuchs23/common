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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.dbdoclet.service.StringServices;

public class IdentifierTextField extends AbstractTextField {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public IdentifierTextField(int cols) {

        super(cols);
    }

    @Override
    protected Document createDefaultModel() {

        return new IdentifierDocument();
    }

    class IdentifierDocument extends PlainDocument {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int offset, String str, AttributeSet a)
            throws BadLocationException {

            char[] buffer = str.toCharArray();
            char c;

            boolean valid = true;
            boolean fit = true;

            if (buffer.length + getLength() <= getMaxLength()) {

                for (int i = 0; i < buffer.length; i++) {
             
                    c = Character.toLowerCase(buffer[i]);

                    if (c != 'a'
                        && c != 'b'
                        && c != 'c'
                        && c != 'd'
                        && c != 'e'
                        && c != 'f'
                        && c != 'g'
                        && c != 'h'
                        && c != 'i'
                        && c != 'j'
                        && c != 'k'
                        && c != 'l'
                        && c != 'm'
                        && c != 'n'
                        && c != 'o'
                        && c != 'p'
                        && c != 'q'
                        && c != 'r'
                        && c != 's'
                        && c != 't'
                        && c != 'u'
                        && c != 'v'
                        && c != 'w'
                        && c != 'x'
                        && c != 'y'
                        && c != 'z'
                        && c != '0'
                        && c != '1'
                        && c != '2'
                        && c != '3'
                        && c != '4'
                        && c != '5'
                        && c != '6'
                        && c != '7'
                        && c != '8'
                        && c != '9'
                        && c != '-'
                        && c != '_') {

                        valid = false;
                        break;
                    }
                }

            } else {

                fit = false;
            }


            if (valid && fit) {

                str = StringServices.replace(str, "_", "-");
                super.insertString(offset, str, a);

            } else {

                getToolkit().beep();
            }
        }
    }
}
/*
 * $Log$
 */
