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

import java.io.IOException;

public class CreatePathException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final int UNKNOWN = 0;
    public static final int FILE_PARENT = 1;
    public static final int PERMISSION_DENIED = 2;
    public static final int PATH_TOO_LONG = 3;

    private String path;
    private int reason;

    public CreatePathException(String path) {

        super(path);

        this.path = path;
    }

    public CreatePathException(String path, int reason) {

        super(path);

        this.path = path;
        this.reason = reason;
    }

    public String getPath() {
        return path;
    }

    public int getReason() {
        return reason;
    }

    public boolean hasFileParent() {

        if (reason == FILE_PARENT) {
            return true;
        }

        return false;
    }

    public boolean isPermissionDenied() {

        if (reason == PERMISSION_DENIED) {
            return true;
        }

        return false;
    }

    public boolean isPathTooLong() {

        if (reason == PATH_TOO_LONG) {
            return true;
        }

        return false;
    }
}
