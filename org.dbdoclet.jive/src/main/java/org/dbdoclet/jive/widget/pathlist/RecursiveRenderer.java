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

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.model.PathListModel;

public class RecursiveRenderer extends JCheckBox
    implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    PathListModel model;
    
    public RecursiveRenderer(PathListModel model) {

        super();

        if (model == null) {
            throw new IllegalArgumentException("The argument model may not be null!");
        }
 
        this.model = model;
        
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, 
                                                   Object value,
                                                   boolean isSelected, 
                                                   boolean hasFocus,
                                                   int row, 
                                                   int column) {
        
        FileSet fileset;
        boolean recursive = true;
        
        if (value != null && value instanceof Boolean) {

            recursive = ((Boolean) value).booleanValue();
        }
        
        setSelected(recursive);

        if (row > -1) {

            fileset = model.getEntry(row);

            if (fileset.isDirectory()) {
                setEnabled(true);
            } else {
                setSelected(false);
                setEnabled(false);
            }
        }
        
        return this;
    }
}
/*
 * $Log$
 */
