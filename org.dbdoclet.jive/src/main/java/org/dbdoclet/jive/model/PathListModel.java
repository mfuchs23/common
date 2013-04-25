package org.dbdoclet.jive.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class PathListModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(PathListModel.class);

	public static final int COLUMN_ID = 0;
	public static final int COLUMN_PATH = 1;
	public static final int COLUMN_STATUS = 2;
	public static final int COLUMN_FILTER_TYPE = 3;
	public static final int COLUMN_CASE_SENSITIVE = 4;
	public static final int COLUMN_FILTER = 5;
	public static final int COLUMN_COUNT = 6;

	private final ArrayList<FileSet> data;
	private final JiveFactory widgetMap;
	private final ResourceBundle res;

	/** Das aktuelle Arbeitsverzeichnis. */
	private File cwd;

	public PathListModel(JiveFactory widgetMap) {

		super();

		if (widgetMap == null) {
			throw new IllegalArgumentException(
					"The argument widgetMap must not be null!");
		}

		this.widgetMap = widgetMap;
		res = widgetMap.getResourceBundle();

		data = new ArrayList<FileSet>();
		cwd = new File(".");
	}

	public void setWorkingDir(File cwd) {

		if (cwd == null) {
			throw new IllegalArgumentException(
					"The argument cwd may not be null!");
		}

		this.cwd = cwd;
	}

	public ArrayList<FileSet> getFileSets() {

		if (data == null) {

			throw new IllegalStateException("The field data may not be null!");
		}

		return data;
	}

	public void changed() {

		fireTableStructureChanged();

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public void rowChanged(int rowIndex) {

		if (rowIndex < 0 || rowIndex >= data.size()) {
			return;
		}

		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	public synchronized void clear() {

		if (data == null) {

			throw new IllegalStateException("The field data may not be null!");
		}

		data.clear();
		fireTableStructureChanged();

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public boolean contains(String path) throws IOException {

		FileSet fileset = new FileSet(cwd, new File(path));
		return contains(fileset);
	}

	public boolean contains(FileSet fileset) {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		if (fileset == null) {
			throw new IllegalArgumentException(
					"The argument fileset may not be null!");
		}

		return data.contains(fileset);
	}

	public int size() {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		return data.size();
	}

	public FileSet getEntry(int index) {

		if (data == null) {

			throw new IllegalStateException("The field data may not be null!");
		}

		if (index < 0) {

			throw new IllegalArgumentException(
					"The argument index may not be less than 0!");
		}

		return data.get(index);
	}

	public synchronized void addEntry(String path) {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path may not be null!");
		}

		logger.debug("cwd=" + cwd);
		logger.debug("path=" + path);

		addEntry(new FileSet(cwd, new File(path)));

	}

	public synchronized void addEntry(FileSet fileset) {

		addEntry(fileset, -1);
	}

	public synchronized void addEntry(FileSet fileset, int pos) {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		if (fileset == null) {

			throw new IllegalArgumentException(
					"The argument fileset may not be null!");
		}

		if (pos > -1 && pos < data.size()) {
			data.add(pos, fileset);
		} else {
			data.add(fileset);
		}

		validate();

		int row = data.size() - 1;

		fireTableRowsInserted(row, row);

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public synchronized void removeEntry(int selectedId) {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		FileSet entry;
		Integer id;

		for (int i = 0; i < data.size(); i++) {

			entry = data.get(i);
			id = entry.getId();

			if (id != null && id.intValue() == selectedId) {
				data.remove(i);
				fireTableStructureChanged();
				break;
			}
		}

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}

		validate();
	}

	public synchronized void removeEntry(FileSet fileset) {

		if (data == null) {

			throw new IllegalStateException("The field data may not be null!");
		}

		FileSet entry;

		for (int i = 0; i < data.size(); i++) {

			entry = data.get(i);

			if (fileset.equals(entry)) {
				data.remove(i);
				fireTableStructureChanged();
				break;
			}
		}

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}

		validate();
	}

	public int getRowCount() {

		return data.size();
	}

	public int getColumnCount() {

		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int column) {

		if ((column < 0) || (column >= COLUMN_COUNT)) {

			throw new IllegalArgumentException("Illegal value " + column
					+ " for argument column!");
		}

		switch (column) {

		case COLUMN_ID:
			return ResourceServices.getString(res, "C_SEQUENCE");

		case COLUMN_PATH:
			return ResourceServices.getString(res, "C_PATH");

		case COLUMN_STATUS:
			return ResourceServices.getString(res, "C_STATUS");

		case COLUMN_CASE_SENSITIVE:
			return ResourceServices.getString(res, "C_CASE_SENSITIVE");

		case COLUMN_FILTER_TYPE:
			return ResourceServices.getString(res, "C_FILTER_TYPE");

		case COLUMN_FILTER:
			return ResourceServices.getString(res, "C_FILTER");
		}

		return "???";
	}

	@Override
	public Class<?> getColumnClass(int column) {

		if ((column < 0) || (column >= COLUMN_COUNT)) {

			throw new IllegalArgumentException("Illegal value " + column
					+ " for argument column!");
		}

		switch (column) {

		case COLUMN_ID:
			return Integer.class;

		case COLUMN_PATH:
			return File.class;

		case COLUMN_CASE_SENSITIVE:
			return Boolean.class;

		case COLUMN_STATUS:
			return Integer.class;

		case COLUMN_FILTER_TYPE:
			return LabelItem.class;

		case COLUMN_FILTER:
			return String.class;
		}

		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int column) {

		if ((column < 0) || (column >= COLUMN_COUNT)) {

			throw new IllegalArgumentException("Illegal value " + column
					+ " for argument column!");
		}

		if (row < 0) {

			throw new IllegalArgumentException("Illegal value " + row
					+ " for argument row!");
		}

		FileSet fileset = getEntry(row);

		switch (column) {

		case COLUMN_ID:
		case COLUMN_PATH:
			return true;

		case COLUMN_STATUS:
			return false;

		case COLUMN_CASE_SENSITIVE:
		case COLUMN_FILTER_TYPE:
		case COLUMN_FILTER:

			if (fileset.isDirectory() == true) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public void setValueAt(Object obj, int row, int column) {

		if (data == null) {

			throw new IllegalStateException("The field data may not be null!");
		}

		if (obj == null) {

			throw new IllegalArgumentException(
					"The argument obj may not be null!");
		}

		if ((row < 0) || (row >= data.size())) {

			throw new IllegalArgumentException(
					"The argument row may not be null!");
		}

		if ((column < 0) || (column >= COLUMN_COUNT)) {

			throw new IllegalArgumentException("Illegal value " + column
					+ " for argument column!");
		}

		FileSet entry = data.get(row);
		Integer id = entry.getId();

		switch (column) {

		case COLUMN_ID:

			if (obj instanceof Integer) {

				int pos = ((Integer) obj).intValue();
				if (pos < 1 || pos > data.size()) {
					return;
				}

				removeEntry(id);
				addEntry(entry, pos - 1);
				validate();

			} else {

				throw new IllegalArgumentException("Invalid id type "
						+ obj.getClass().getName() + ".");
			}

			break;

		case COLUMN_PATH:

			if (obj instanceof String) {
				entry.setPath(new File((String) obj));
			} else if (obj instanceof File) {
				entry.setPath((File) obj);
			} else {

				throw new IllegalArgumentException("Invalid path type "
						+ obj.getClass().getName() + ".");
			}

			break;

		case COLUMN_CASE_SENSITIVE:

			if (obj instanceof Boolean) {
				entry.setCaseSensitive((Boolean) obj);
			}

			break;

		case COLUMN_FILTER_TYPE:

			logger.info("setValueAt: Filter type = " + obj.getClass().getName());

			if (obj instanceof LabelItem) {

				LabelItem item = (LabelItem) obj;
				Integer type = (Integer) item.getValue();

				entry.setFilterType(type.intValue());
			}

			break;

		case COLUMN_FILTER:

			if (obj instanceof String) {

				String filter = (String) obj;
				int filterType = entry.getFilterType();

				if (filterType == FileSet.FILTER_NONE
						|| filter.trim().length() > 0) {
					entry.setFilter(filter);
				}
			}

			break;
		}

		fireTableRowsUpdated(row, row);

		if (widgetMap != null) {
			widgetMap.enableSaveWidgets();
		}
	}

	public Object getValueAt(int row, int column) {

		FileSet entry = data.get(row);

		switch (column) {

		case COLUMN_ID:
			return entry.getId();

		case COLUMN_PATH:
			return entry.getPath();

		case COLUMN_STATUS:
			return String.valueOf(entry.getStatus());

		case COLUMN_CASE_SENSITIVE:
			return entry.getCaseSensitive();

		case COLUMN_FILTER_TYPE:
			return getFilterTypeLabel(entry);

		case COLUMN_FILTER:
			return entry.getFilter();
		}

		return "???";
	}

	private String getFilterTypeLabel(FileSet entry) {

		switch (entry.getFilterType()) {

		case FileSet.FILTER_NONE:
			return ResourceServices.getString(res, "C_NO_FILTER");

		case FileSet.FILTER_INCLUDE_FILES:
			return ResourceServices.getString(res, "C_INCLUDE_FILES");

		case FileSet.FILTER_EXCLUDE_FILES:
			return ResourceServices.getString(res, "C_EXCLUDE_FILES");

		case FileSet.FILTER_INCLUDE_DIRECTORIES:
			return ResourceServices.getString(res, "C_INCLUDE_DIRECTORIES");

		case FileSet.FILTER_EXCLUDE_DIRECTORIES:
			return ResourceServices.getString(res, "C_EXCLUDE_DIRECTORIES");

		case FileSet.FILTER_INCLUDE_PACKAGES:
			return ResourceServices.getString(res, "C_INCLUDE_PACKAGES");

		case FileSet.FILTER_EXCLUDE_PACKAGES:
			return ResourceServices.getString(res, "C_EXCLUDE_PACKAGES");

		}

		return ResourceServices.getString(res, "C_UNKNOWN_FILTER_TYPE");
	}

	private void validate() {

		if (data == null) {
			throw new IllegalStateException("The field data may not be null!");
		}

		FileSet entry;

		for (int i = 0; i < data.size(); i++) {

			entry = data.get(i);
			entry.setId(i + 1);
			try {
				fireTableCellUpdated(i, COLUMN_ID);
			} catch (Throwable oops) {
				// ignore
			}
		}
	}

	public ArrayList<FileSet> findEntry(File path) {

		ArrayList<FileSet> hits = new ArrayList<FileSet>();

		for (int i = 0; i < data.size(); i++) {
			if (path.equals(data.get(i).getQualifiedPath())) {
				hits.add(data.get(i));
			}
		}

		if (hits.size() == 0) {
			return null;
		}

		return hits;
	}
}
