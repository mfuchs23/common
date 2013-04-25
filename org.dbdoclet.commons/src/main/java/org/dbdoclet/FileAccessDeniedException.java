package org.dbdoclet;

import java.io.File;

public class FileAccessDeniedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private File file;

    public FileAccessDeniedException(File file) {

        super(file.getAbsolutePath());
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
