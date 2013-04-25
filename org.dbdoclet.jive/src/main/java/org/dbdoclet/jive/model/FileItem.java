/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: FileItem.java,v 1.1.1.1 2004/12/21 13:56:55 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 13:56:55 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.jive.model;

import java.io.File;


public class FileItem {

    private String label;
    private File file;

    public FileItem(File file) {

        if (file == null) {
            throw new IllegalArgumentException
                ("Parameter file is null!");
        }

        this.file = file;

        label = file.getName();

        if (label.matches(".+\\.xsl(?i)")) {
            label = label.substring(0, label.length() - 4);
            label = label.replace('_', ' ');
        }
    }

    public String getLabel() {
        return label;
    }

    protected void setLabel(String label) {
        this.label = label;
    }

    public File getFile() {
        return file;
    }

    protected void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {

            return false;
        }

        if (obj instanceof String) {

            String file = (String) obj;

            if (file.equals(this.file)) {

                return true;
            } else {

                return false;
            }
        }

        if (obj instanceof FileItem) {

            FileItem item = (FileItem) obj;
            File file = item.getFile();

            if (file.equals(this.file)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {

        int hashCode = file.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return label;
    }
}

/*
 * $Log: FileItem.java,v $
 * Revision 1.1.1.1  2004/12/21 13:56:55  mfuchs
 * Reimport
 *
 * Revision 1.3  2004/10/13 05:32:58  mfuchs
 * Korremturen
 *
 * Revision 1.2  2004/10/05 13:09:49  mfuchs
 * PathList
 *
 * Revision 1.1  2004/08/27 14:26:31  mfuchs
 * Weiterentwicklung
 *
 */
