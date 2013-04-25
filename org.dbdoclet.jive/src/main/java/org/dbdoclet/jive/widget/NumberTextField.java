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

public class NumberTextField extends AbstractTextField {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor mit Angabe der gewünschten Anzahl an Spalten für die Eingabe.
	 * 
	 * @param cols
	 */
	public NumberTextField(int cols) {
		super(cols);
	}

	public Integer getNumber() {
	
		String buffer = getText();
		
		if (buffer == null || buffer.trim().length() == 0) {
			return null;
		}
		
		return Integer.parseInt(buffer); 
	}
	
	@Override
	protected Document createDefaultModel() {
		return new NumberDocument();
	}
	
	class NumberDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {

			char[] buffer = str.toCharArray();

			boolean valid = true;
			boolean fit = true;

			if (buffer.length + getLength() <= getMaxLength()) {

				for (int i = 0; i < buffer.length; i++) {

					if (Character.isDigit(buffer[i]) == false) {
						valid = false;
						break;
					}
				}

			} else {

				fit = false;
			}

			if (valid && fit) {
				super.insertString(offset, str, a);
			} else {
				getToolkit().beep();
			}
		}
	}
}
