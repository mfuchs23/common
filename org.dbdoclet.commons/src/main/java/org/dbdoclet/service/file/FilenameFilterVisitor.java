package org.dbdoclet.service.file;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.service.FileVisitor;

public class FilenameFilterVisitor implements FileVisitor {

	private final ProgressListener listener;
	private final ArrayList<File> fileList;
	private final FilenameFilter filter;
	private final String msg;

	public FilenameFilterVisitor(FilenameFilter filter, String msg,
			ProgressListener listener, ArrayList<File> fileList) {

		super();

		this.filter = filter;
		this.msg = msg;
		this.listener = listener;
		this.fileList = fileList;
	}

	@Override
	public void visit(File file) {

		if (filter.accept(file, file.getName())) {

			fileList.add(file);

			if (listener != null) {
				listener.progress(new ProgressEvent(MessageFormat.format(msg,
						file.getAbsolutePath())));
			}
		}
	}
}
