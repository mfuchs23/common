/* 
 * $Id$
 *
 * ### Copyright (C) 2005-2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Frame;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.dbdoclet.progress.ReportItem;

public class ReportBox {

    public static void show(String info, String msg, List<ReportItem> eventList) {
        
        show(null, info, msg, eventList);
    }

    public static void show(Frame parent, 
                            String info, 
                            String msg,
                            List<ReportItem> eventList) {

        show(parent, info, msg, null, eventList);
    }

    public static void show(Frame parent, 
                            String info, 
                            String msg,
                            URL backgroundImageUrl,
                            List<ReportItem> eventList) {

        try {

            if (info == null) {
                throw new IllegalArgumentException("The argument info may not be null!");
            }

            if (msg == null) {
                throw new IllegalArgumentException("The argument msg may not be null!");
            }

            if (eventList == null) {
                eventList = new ArrayList<ReportItem>();
            }

            ReportDialog dlg = new ReportDialog(parent, info, msg, backgroundImageUrl, eventList);
            
            dlg.pack();
            dlg.center(parent);
            dlg.setVisible(true);
        
        } catch (Throwable oops) {

            oops.printStackTrace();


            JOptionPane.showMessageDialog(parent,
                                          msg, 
                                          info,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
