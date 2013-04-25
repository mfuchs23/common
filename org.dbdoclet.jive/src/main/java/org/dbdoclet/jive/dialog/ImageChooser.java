/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.imagechooser.AccessoryPanel;
import org.dbdoclet.service.ResourceServices;

public class ImageChooser extends JFileChooser {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static File lastDirectory;

    private ResourceBundle res;
    private JiveFactory wm = JiveFactory.getInstance();
    
    public ImageChooser(File currentDirectory) {

        super(currentDirectory);
        lastDirectory = currentDirectory;
        
        init();
    }
    
    public ImageChooser() {

        super();
        init();
    }
    
    private void init() {
        
        res = wm.getResourceBundle();
        
        if (lastDirectory != null) {
            setCurrentDirectory(lastDirectory);
        }

        setFileSelectionMode(FILES_ONLY);
        setApproveButtonText(ResourceServices.getString(res,"C_CHOOSE_IMAGE"));

        setAccessory(new AccessoryPanel(this));
    }
}
