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
package org.dbdoclet.jive.verifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;


public class DirectoryVerifier extends InputVerifier {

    public DirectoryVerifier() {
        super();
    }

    @Override
    public boolean verify(JComponent input) {

        if (input == null) {
            return true;
        }

        if (input instanceof JTextField) {

            JTextField tf = (JTextField) input;
            String text = tf.getText();
            
            if ((text == null) || (text.length() == 0)) {
                return true;
            }

            File file = new File(text);

            if (file.isDirectory()) {

                fireActionEvent(input, "DirectoryVerifier.verified");
                return true;

            } else {

                fireActionEvent(input, "DirectoryVerifier.veto");
                return false;
            }
        }

        return false;
    }

    private void fireActionEvent(JComponent input, String cmd) {

        if (input instanceof JTextField) {
            
            JTextField entry = (JTextField) input;
            
            ActionListener[] listeners = entry.getActionListeners();
            
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].actionPerformed(new ActionEvent(input, ActionEvent.ACTION_PERFORMED, cmd)); 
            }
        }
    }
}
/*
 * $Log$
 */
