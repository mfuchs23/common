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
import javax.swing.JLabel;
import javax.swing.JTextField;

public abstract class AbstractTextField extends JTextField {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int maxLength = 64;
    private JComponent next = null;
    private JLabel label;

    public AbstractTextField(int cols) {

        super(cols);
        this.maxLength = cols;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setNext(JComponent next) {
        this.next = next;
    }

    public JComponent getNext() {
        return next;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
    
    public JLabel getLabel() {
        return label;
    }
 
}
/*
 * $Log$
 */
