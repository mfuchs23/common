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
package org.dbdoclet.jive.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MessageBox {

    public static void msg(String msg) {

        msg(null, msg);
    }

    public static void msg(Component parent, String msg) {
        
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
        
        JOptionPane.showMessageDialog(parent,
                                      msg,
                                      "Information", 
                                      JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean confirm(String msg) {

        return confirm(null, msg);
    }

    public static boolean confirm(Component parent, String msg) {

        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }

        int answ = JOptionPane.showConfirmDialog(parent,
                                                 msg,
                                                 "Information", 
                                                 JOptionPane.YES_NO_OPTION);

        if (answ == JOptionPane.OK_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
/*
 * $Log$
 */
