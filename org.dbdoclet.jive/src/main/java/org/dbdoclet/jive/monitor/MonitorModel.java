package org.dbdoclet.jive.monitor;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MonitorModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Hashtable<String, ModelItem> dataMap;
	private Vector<ModelItem> dataList;
	
	
	public MonitorModel() {
		dataMap = new Hashtable<String, ModelItem>();
		dataList = new Vector<ModelItem>();
	}
	
	public void addFile(File file) {
		
		ModelItem item = new FileItem(file);
		dataMap.put(file.getAbsolutePath(), item);
		dataList.add(item);
	}

	public void addResource(String key, String label) {

		ResourceItem item = new ResourceItem(key, label);
		dataMap.put(key, item);
		dataList.add(item);
	}

	public void clear() {
		
		dataMap.clear();
		dataList.clear();
	}

	public ModelItem findItem(String key) {
		return dataMap.get(key);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {
			case 0:	return String.class;
			case 1:	return String.class;
			case 2:	return Boolean.class;
		}
		
		return Object.class;
	}

	public int getColumnCount() {
		return 3;
	}

	public String getColumnName(int columnIndex) {
		
		switch (columnIndex) {
			case 0:	return "Name";
			case 1:	return "Monitor";
			case 2:	return "Icon";
		}
		
		return "";
	}

	public int getRowCount() {
		return dataList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		if (rowIndex < 0 || rowIndex >= dataList.size()) {
			return null;
		}
		
		ModelItem item = dataList.get(rowIndex);
		
		switch (columnIndex) {
			case 0:	return item.getName();
			case 1:	return item.getValue();
			case 2:	return item.hasChanged();
		}
		
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void update() {
		
		fireTableDataChanged();
	}

	
}
