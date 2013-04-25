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
package org.dbdoclet;

import java.io.File;
import java.io.IOException;

public class DeleteFileException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String path;

    public DeleteFileException(File file) {

        this(file.getAbsolutePath());
    }

    public DeleteFileException(String path) {

        super(path);

        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
