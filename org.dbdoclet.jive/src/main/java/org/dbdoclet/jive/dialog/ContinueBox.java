/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

public class ContinueBox {

    public static boolean show(String title, String question) {
        
        return show(null, title, question);
    }

    public static boolean show(Window parent, String title, String question) {

        try {

            if (title == null) {
                throw new IllegalArgumentException("The argument title may not be null!");
            }

            if (question == null) {
                throw new IllegalArgumentException("The argument question must not be null!");
            }


            ContinueDialog dlg = null;

            if (parent instanceof Frame) {
                dlg = new ContinueDialog((Frame) parent, title, question); 
            }

            if (parent instanceof Dialog) {
                dlg = new ContinueDialog((Dialog) parent, title, question); 
            }

            if (dlg == null) {
                dlg = new ContinueDialog((Frame) null, title, question); 
            }

            dlg.setVisible(true);
            return dlg.doContinue();
            
        } catch (Throwable oops) {

            oops.printStackTrace();

            ExceptionBox ebox = new ExceptionBox(oops);
            ebox.setVisible(true);
            ebox.toFront();
            
            return false;
        }
    }
}
