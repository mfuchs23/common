package org.dbdoclet.io;

import java.io.File;
import java.io.IOException;

import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;

public class FileSet {

	public static final int FILTER_NONE = 0;
	public static final int FILTER_INCLUDE_FILES = 1;
	public static final int FILTER_EXCLUDE_FILES = 2;
	public static final int FILTER_INCLUDE_DIRECTORIES = 3;
	public static final int FILTER_EXCLUDE_DIRECTORIES = 4;
	public static final int FILTER_INCLUDE_PACKAGES = 5;
	public static final int FILTER_EXCLUDE_PACKAGES = 6;

	public static final int STATUS_OK = 0;
	public static final int STATUS_NOT_FOUND = 1;
	public static final int STATUS_NOT_READABLE = 2;
	public static final int STATUS_DUPLICATE = 3;
	public static final int STATUS_VARIABLE = 4;

	private Integer id;
	private File cwd = null;
	private File path = null;
	private File canonicalPath = null;
	private Boolean caseSensitive = Boolean.TRUE;
	private int filterType = FILTER_NONE;
	private int status = STATUS_OK;
	private String filter = "";

	public FileSet() throws IOException {

		this(new File("."), new File("."));
	}

	public FileSet(File cwd, File path) {

		if (cwd == null) {
			throw new IllegalArgumentException(
					"The argument cwd may not be null!");
		}

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path may not be null!");
		}

		this.cwd = cwd;
		this.caseSensitive = Boolean.TRUE;
		this.filterType = FILTER_NONE;
		this.filter = "";

		setPath(path);
	}

	public FileSet(File cwd, String path) {

		this(cwd, new File(path));
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {

			return false;
		}

		if (obj instanceof FileSet == false) {

			return false;
		}

		FileSet comp = (FileSet) obj;

		if (id == comp.getId()) {
			return true;
		}

		return false;
	}

	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	public String getDirName() {

		if (path == null) {
			throw new IllegalStateException("The field path may not be null!");
		}

		String dirName = path.getPath();
		dirName = StringServices.replace(dirName, File.separator, "/");

		return dirName;
	}

	public String getFilter() {
		return filter;
	}

	public int getFilterType() {
		return filterType;
	}

	public Integer getId() {
		return id;
	}

	public String getPackageFilter() {

		String str = filter;

		if (str != null && str.length() > 0) {
			str = StringServices.replace(str, ".", "/");
		}

		return str;
	}

	public File getPath() {
		return path;
	}

	public File getRelativePath() {

		String relativePath;

		try {
			relativePath = FileServices.relativePath(cwd, getQualifiedPath());
		} catch (IOException e) {
			relativePath = path.getAbsolutePath();
		}

		return new File(relativePath);
	}

	public File getQualifiedPath() {
		return canonicalPath;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public int hashCode() {

		return id.intValue();
	}

	public boolean isCaseSensitive() {
		return caseSensitive.booleanValue();
	}

	public boolean isDirectory() {
		return canonicalPath.isDirectory();
	}

	public boolean isDirectoryFilter() {

		if (filterType == FILTER_INCLUDE_DIRECTORIES
				|| filterType == FILTER_EXCLUDE_DIRECTORIES) {

			return true;
		}

		return false;
	}

	public boolean isFileFilter() {

		if (filterType == FILTER_INCLUDE_FILES
				|| filterType == FILTER_EXCLUDE_FILES) {

			return true;
		}

		return false;
	}

	public boolean isPackageFilter() {

		if (filterType == FILTER_NONE || filterType == FILTER_INCLUDE_PACKAGES
				|| filterType == FILTER_EXCLUDE_PACKAGES) {

			return true;
		}

		return false;
	}

	public boolean isVariable() {

		int status = getStatus();

		if (status == STATUS_VARIABLE) {
			return true;
		}

		return false;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = Boolean.valueOf(caseSensitive);
	}

	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}

	public void setId(int id) {
		this.id = Integer.valueOf(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Der Pfad eines FileSets wird als relativer und als absoluter Pfad
	 * gespeichert. Einzige Ausnahme ist ein Pfad der mit einer Variablen
	 * beginnt. In diesem Fall sind relativer und absoluter Pfad identisch, da
	 * hier keine Aussage über den Inhalt der Variablen gemacht werden kann.
	 * 
	 * @param file
	 */
	public void setPath(File file) {

		path = file;
		canonicalPath = file;

		String strPath = file.getPath();

		if (strPath.matches("^.*\\$\\{\\p{Graph}+\\}.*$") == true) {

			status = STATUS_VARIABLE;
			return;
		}

		strPath = FileServices.normalizePath(strPath);

		if (FileServices.isAbsolutePath(strPath) == false) {

			if (strPath.endsWith("/")) {
				strPath = FileServices.appendPath(cwd, strPath);
			} else {
				strPath = FileServices.appendFileName(cwd, strPath);
			}
		}

		canonicalPath = new File(strPath);

		status = STATUS_OK;

		if (canonicalPath.canRead() == false) {
			status = STATUS_NOT_READABLE;
		}

		if (canonicalPath.exists() == false) {
			status = STATUS_NOT_FOUND;
		}

		try {
			canonicalPath = canonicalPath.getCanonicalFile();
		} catch (IOException oops) {
			oops.printStackTrace();
		}
	}

	public void setPath(File cwd, File path) {

		if (cwd == null) {
			throw new IllegalArgumentException(
					"The argument cwd may not be null!");
		}

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path may not be null!");
		}

		this.cwd = cwd;
		setPath(path);
	}

	@Override
	public String toString() {

		String str = path.getPath() + "[cwd=" + cwd.getAbsolutePath()
				+ ",filterType=" + filterType + "]";

		return str;
	}
}
