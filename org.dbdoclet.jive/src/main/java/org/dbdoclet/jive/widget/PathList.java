/* 
 * ### Copyright (C) 2001-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.model.PathListModel;
import org.dbdoclet.jive.widget.pathlist.FilterTypeRenderer;
import org.dbdoclet.jive.widget.pathlist.StatusRenderer;
import org.dbdoclet.service.ResourceServices;

public class PathList extends JTable implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final int FILE_FILTER_MASK = 0x01;
	public static final int DIRECTORY_FILTER_MASK = 0x02;
	public static final int PACKAGE_FILTER_MASK = 0x04;

	private static Log logger = LogFactory.getLog(PathList.class);

	private static ResourceBundle res;

	private final HashMap<String, String> defaultFilterMap;
	private final PathListModel model;
	private final TableColumn caseSensitiveColumn;
	private final TableColumn filterColumn;
	private final TableColumn filterTypeColumn;
	private final TableColumn pathColumn;
	private final TableColumn statusColumn;
	private final TableColumnModel columnModel;
	private final JiveFactory widgetMap;
	private final FilterTypeRenderer filterTypeRenderer;
	private final DefaultCellEditor filterTypeEditor;
	private final StatusRenderer statusRenderer;

	public PathList(JiveFactory widgetMap) {
		this(widgetMap, FILE_FILTER_MASK);
	}

	public PathList(JiveFactory widgetMap, int filterMask) {

		super();

		if (widgetMap == null) {
			throw new IllegalArgumentException(
					"The argument widgetMap must not be null!");
		}

		this.widgetMap = widgetMap;

		Font font;

		res = widgetMap.getResourceBundle();

		model = new PathListModel(widgetMap);
		setModel(model);

		setRowHeight(20);

		columnModel = getColumnModel();
		columnModel.getColumn(PathListModel.COLUMN_ID);
		pathColumn = columnModel.getColumn(PathListModel.COLUMN_PATH);
		statusColumn = columnModel.getColumn(PathListModel.COLUMN_STATUS);
		caseSensitiveColumn = columnModel
				.getColumn(PathListModel.COLUMN_CASE_SENSITIVE);
		filterTypeColumn = columnModel
				.getColumn(PathListModel.COLUMN_FILTER_TYPE);
		filterColumn = columnModel.getColumn(PathListModel.COLUMN_FILTER);

		pathColumn.setPreferredWidth(300);
		statusColumn.setPreferredWidth(50);
		caseSensitiveColumn.setPreferredWidth(50);
		filterTypeColumn.setPreferredWidth(150);
		filterColumn.setPreferredWidth(150);

		JComboBox filterTypeBox = new JComboBox();
		filterTypeBox.addActionListener(this);
		filterTypeBox.setActionCommand("filter-changed");

		font = filterTypeBox.getFont();
		font = font.deriveFont(Font.PLAIN);
		filterTypeBox.setFont(font);

		filterTypeBox.setBackground(getBackground());

		filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
				"C_NO_FILTER"), new Integer(FileSet.FILTER_NONE)));

		if ((filterMask & FILE_FILTER_MASK) != 0) {

			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_INCLUDE_FILES"), new Integer(
					FileSet.FILTER_INCLUDE_FILES)));
			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_EXCLUDE_FILES"), new Integer(
					FileSet.FILTER_EXCLUDE_FILES)));
		}

		if ((filterMask & DIRECTORY_FILTER_MASK) != 0) {

			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_INCLUDE_DIRECTORIES"), new Integer(
					FileSet.FILTER_INCLUDE_DIRECTORIES)));

			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_EXCLUDE_DIRECTORIES"), new Integer(
					FileSet.FILTER_EXCLUDE_DIRECTORIES)));
		}

		if ((filterMask & PACKAGE_FILTER_MASK) != 0) {

			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_INCLUDE_PACKAGES"), new Integer(
					FileSet.FILTER_INCLUDE_PACKAGES)));

			filterTypeBox.addItem(new LabelItem(ResourceServices.getString(res,
					"C_EXCLUDE_PACKAGES"), new Integer(
					FileSet.FILTER_EXCLUDE_PACKAGES)));
		}

		defaultFilterMap = new HashMap<String, String>();
		defaultFilterMap.put("include-files", "**/*");
		defaultFilterMap.put("exclude-files", "**/*");
		defaultFilterMap.put("include-packages", "**");
		defaultFilterMap.put("exclude-packages", "**");

		setAutoCreateRowSorter(true);

		filterTypeRenderer = new FilterTypeRenderer(model);
		filterTypeEditor = new DefaultCellEditor(filterTypeBox);
		statusRenderer = new StatusRenderer(model);
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {

		if (column == PathListModel.COLUMN_STATUS) {
			return statusRenderer;
		}

		if (column == PathListModel.COLUMN_FILTER_TYPE) {
			return filterTypeRenderer;
		}

		return super.getCellRenderer(row, column);
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {

		if (column == PathListModel.COLUMN_FILTER_TYPE) {
			return filterTypeEditor;
		}

		return super.getCellEditor(row, column);
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		logger.debug("cmd=" + cmd);

		if (cmd.equals("filter-changed") == true) {

			int rowIndex = getSelectedRow();

			if (rowIndex != -1) {

				FileSet entry = model.getEntry(rowIndex);

				String filter = entry.getFilter();

				if (filter == null || filter.trim().length() == 0) {

					int filterType = entry.getFilterType();

					switch (filterType) {

					case FileSet.FILTER_INCLUDE_FILES:
						entry.setFilter(defaultFilterMap.get("include-files"));
						break;

					case FileSet.FILTER_EXCLUDE_FILES:
						entry.setFilter(defaultFilterMap.get("exclude-files"));
						break;

					case FileSet.FILTER_INCLUDE_PACKAGES:
						entry.setFilter(defaultFilterMap
								.get("include-packages"));
						break;

					case FileSet.FILTER_EXCLUDE_PACKAGES:
						entry.setFilter(defaultFilterMap
								.get("exclude-packages"));
						break;

					default:
						entry.setFilter("");
						break;
					}

					model.rowChanged(rowIndex);
				}
			}
		}
	}

	public void addEntry(FileSet fileset) {

		logger.debug("fileset=" + fileset);
		model.addEntry(fileset);

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public void addEntry(String path) {

		logger.debug("path=" + path);
		model.addEntry(path);

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public void changed() {
		model.changed();
	}

	public void clear() {
		model.clear();
	}

	public boolean contains(FileSet fileset) {
		return model.contains(fileset);
	}

	public void editSelected() {

		int row = getSelectedRow();

		if (row >= -1) {
			editCellAt(row, PathListModel.COLUMN_PATH);
		}
	}

	public ArrayList<FileSet> getFileSets() {

		return model.getFileSets();
	}

	public int getPathCount() {
		return model.size();
	}

	public boolean isEmpty() {

		if (model.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void removeSelectedEntry() {

		int[] rows = getSelectedRows();
		ArrayList<Integer> idList = new ArrayList<Integer>();

		for (int row : rows) {

			Integer id = (Integer) getValueAt(row, 0);
			idList.add(id);
		}

		for (Integer id : idList) {
			model.removeEntry(id);
		}

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public void setDefaultExcludeFilesFilter(String filter) {

		if (filter != null) {
			defaultFilterMap.put("exclude-files", filter);
		}
	}

	public void setDefaultExcludePackagesFilter(String filter) {

		if (filter != null) {
			defaultFilterMap.put("exclude-packages", filter);
		}
	}

	public void setDefaultIncludeFilesFilter(String filter) {

		if (filter != null) {
			defaultFilterMap.put("include-files", filter);
		}
	}

	public void setDefaultIncludePackagesFilter(String filter) {

		if (filter != null) {
			defaultFilterMap.put("include-packages", filter);
		}
	}

	public void setWorkingDir(File cwd) {

		if (cwd == null) {
			throw new IllegalArgumentException(
					"The argument cwd may not be null!");
		}

		model.setWorkingDir(cwd);
	}

	public ArrayList<FileSet> findEntry(File path) {
		return model.findEntry(path);
	}
}
