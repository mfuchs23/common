package org.dbdoclet.jive.monitor;

import java.io.File;
import java.util.ArrayList;

public class FileItem extends ModelItem {

	private static final int RING_BUFFER_SIZE = 10;
	private File file;
	private String[] history;
	
	private int historyIndex = 0;
	
	public FileItem(File file) {
	
		this.file = file;
		history = new String[RING_BUFFER_SIZE];
	}
	
	public boolean equals(FileItem other) {
		return file.equals(other.getFile());
	}

	@Override
	public boolean equals(Object other) {
		return equals((FileItem) other);
	}

	@Override
	public String getName() {
		return String.format(" %s", file.getName());
	}

	@Override
	public String getValue() {
		
		long length = file.length();
		String value = createByteUnit(length);
		addHistory(value);
		return value;
	}

	@Override
	public Boolean hasChanged() {

		ArrayList<String> uniqueList = new ArrayList<String>();
		
		for (String entry : history) {
			
			if (uniqueList.contains(entry) == false) {
				uniqueList.add(entry);
			}
		}
		
		if (uniqueList.size() > 3) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}

	public String toString() {
		
		if (file == null) {
			return "";
		}
		
		return file.getName();
	}

	private void addHistory(String value) {

		if (historyIndex >= 10) {
			historyIndex = 0;
		}
		
		history[historyIndex++] = value;
	}

	private File getFile() {
		return file;
	}

	@Override
	public void setValue(Object value) {

		if (value instanceof File) {
			file = (File) value;
		}
	}
}
