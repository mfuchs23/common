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

public class RenameFileException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String from;
    private String to;

    public RenameFileException(File from, File to) {

        super(from.getAbsolutePath() + " -> " + to.getAbsolutePath());

        this.from = from.getAbsolutePath();
        this.to = to.getAbsolutePath();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
