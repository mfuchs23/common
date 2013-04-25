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
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.PathListModel;
import org.dbdoclet.service.ResourceServices;

public class FilterTypeRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	private PathListModel model;

	public FilterTypeRenderer(PathListModel model) {

		super();

		if (model == null) {
			throw new IllegalArgumentException(
					"The argument model may not be null!");
		}

		this.model = model;

		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		FileSet fileset = null;
		JiveFactory jf = JiveFactory.getInstance();
		ResourceBundle res = jf.getResourceBundle();
		
		if (row > -1) {

			fileset = model.getEntry(row);
			
			if (fileset.getFilterType() == FileSet.FILTER_NONE) {
				value = new String("");
			}
			
			if (fileset != null && fileset.isDirectory() == false) {
				
				File path = fileset.getPath();
				
				if (path != null && path.isFile()) {
					value = ResourceServices.getString(res, "C_FILE");
				}
			
			} else {
			}
		}

		Component comp = super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		
		if (fileset == null || fileset.isDirectory() == false) {
			comp.setEnabled(false);
			// comp.setBackground(Color.lightGray);
		} else {
			comp.setEnabled(true);
			// comp.setBackground(Color.white);
		}

		return comp;
	}
}
