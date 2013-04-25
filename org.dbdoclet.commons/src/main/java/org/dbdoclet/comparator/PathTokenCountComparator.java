/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.comparator;

import java.io.File;
import java.util.Comparator;

import org.dbdoclet.service.FileServices;

public class PathTokenCountComparator implements Comparator<File> {

    public int compare(File f1, File f2) {

        int i1 = FileServices.getPathTokenCount(f1.getAbsolutePath());
        int i2 = FileServices.getPathTokenCount(f2.getAbsolutePath());

        if (i1 == i2) {
            return 0;
        }

        if (i1 < i2) {
            return 1;
        }

        if (i1 > i2) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other.getClass() != getClass()) {
            return false;
        }

        return true;
    }

}
