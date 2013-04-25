/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.action;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.dbdoclet.jive.dialog.ExceptionBox;

public class ActionSetLookAndFeel extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private ArrayList<Component> componentList;
    private LookAndFeelInfo info;
    private String lnfClassName;
    
    public ActionSetLookAndFeel(String label, LookAndFeelInfo info, ArrayList<Component> componentList) {
        
        super(label);
        
        if (componentList == null) {
            throw new IllegalArgumentException("The argument frame must not be null!");
        }

        this.componentList = componentList;
        this.info = info;
    }
    
    public ActionSetLookAndFeel(String label, String lnfClassName, ArrayList<Component> componentList) {
        
        super(label);
        
        if (componentList == null) {
            throw new IllegalArgumentException("The argument frame must not be null!");
        }

        this.componentList = componentList;
        this.lnfClassName = lnfClassName;
    }
    
    public void actionPerformed(ActionEvent event) {

        try {
            
            if (info != null) {
                lnfClassName = info.getClassName();
            }

            if (lnfClassName != null) {

                UIManager.setLookAndFeel(lnfClassName);
                
                for (Component component : componentList) {
                	
                	SwingUtilities.updateComponentTreeUI(component);
                	
                	if (component instanceof Window) {
                		((Window) component).pack();
                	}
                }
            }
            
        } catch (Throwable oops) {

            ExceptionBox ebox = new ExceptionBox(oops);
            ebox.setVisible(true);
            ebox.toFront();
            
        }
    }
}
