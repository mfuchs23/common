/* 
 *
 * ### Copyright (C) 2010 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 *
 */
package org.dbdoclet.io;

import java.io.File;
import java.io.FileFilter;

public class DirectoryFilter
    implements FileFilter {

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
