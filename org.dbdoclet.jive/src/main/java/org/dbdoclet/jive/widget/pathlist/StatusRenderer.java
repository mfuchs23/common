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
package org.dbdoclet.jive.widget.pathlist;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.PathListModel;
import org.dbdoclet.service.ResourceServices;

public class StatusRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static ResourceBundle res;
    
    private PathListModel model;
    private ImageIcon errorIcon;

    public StatusRenderer(PathListModel model) {

        super();

        if (model == null) {
            throw new IllegalArgumentException("The argument model may not be null!");
        }
 
        this.model = model;
        
        JiveFactory wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

	URL iconURL = ClassLoader.getSystemResource("images/status-error.png");

        if (iconURL != null) {
            errorIcon = new ImageIcon(iconURL,"Error");
        }
        
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, 
                                                   Object value,
                                                   boolean isSelected, 
                                                   boolean hasFocus,
                                                   int row, 
                                                   int column) {
        
        FileSet fileset;

        if (row > -1) {

            fileset = model.getEntry(row);

            value = getStatusLabel(fileset.getStatus());
            setIcon(null);

            if (fileset.getStatus() == FileSet.STATUS_OK) {

                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }

            if (fileset.getStatus() == FileSet.STATUS_NOT_FOUND) {

                if (errorIcon != null) {

                    setIcon(errorIcon);

                } else {

                    setForeground(Color.WHITE);
                    setBackground(Color.RED);
                }
            }

            if (fileset.getStatus() == FileSet.STATUS_VARIABLE) {

                setForeground(Color.BLACK);
                setBackground(Color.YELLOW);
            }

            setToolTipText(getStatusLabel(fileset.getStatus()));
        }

        return super.getTableCellRendererComponent(table,
                                                   value,
                                                   isSelected,
                                                   hasFocus,
                                                   row,
                                                   column);
        
    }

    private String getStatusLabel(int status) {

        switch (status) {

        case FileSet.STATUS_OK:
            return ResourceServices.getString(res,"C_OK");

        case FileSet.STATUS_NOT_FOUND:
            return ResourceServices.getString(res,"C_NOT_FOUND");

        case FileSet.STATUS_NOT_READABLE:
            return ResourceServices.getString(res,"C_NOT_READABLE");

        case FileSet.STATUS_DUPLICATE:
            return ResourceServices.getString(res,"C_DUPLICATE");

        case FileSet.STATUS_VARIABLE:
            return ResourceServices.getString(res,"C_VARIABLE");

        }

        return ResourceServices.getString(res,"C_UNKNOWN_STATUS");
    }
}
