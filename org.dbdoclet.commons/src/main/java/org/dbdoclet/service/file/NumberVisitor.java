package org.dbdoclet.service.file;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;

import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.service.FileVisitor;

public class NumberVisitor implements FileVisitor {

	private int number = 0;
	private final ProgressListener listener;

	private final String msg;
	private final FilenameFilter filter;

	public NumberVisitor(FilenameFilter filter, String msg,
			ProgressListener listener) {
		super();
		this.filter = filter;
		this.msg = msg;
		this.listener = listener;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public void visit(File file) {

		if (filter != null && filter.accept(file, file.getName()) == false) {
			return;
		}

		number++;

		if (listener != null) {
			listener.progress(new ProgressEvent(MessageFormat.format(msg,
					String.valueOf(number))));
		}
	}
}
