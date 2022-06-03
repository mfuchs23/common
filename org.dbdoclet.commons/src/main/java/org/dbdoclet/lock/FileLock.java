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
package org.dbdoclet.lock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLock {

    private File file;
    private String data;

    public FileLock(File file) {

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        this.file = file;
    }

    public FileLock(String fileName) {
        this(new File(fileName));
    }

    public synchronized boolean lock(String data) {

        if (file == null) {
            throw new IllegalStateException("The field file must not be null!");
        }

        if (file.exists()) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

        	writer.println(data);
            this.data = data;

        } catch (IOException oops) {

        	return false;
        }


        return true;
    }

    public synchronized boolean unlock() {

        if (file == null) {
            throw new IllegalStateException("The field file must not be null!");
        }

        if (file.exists() == false) {
            return true;
        }

        if (file.delete() == false) {
            return false;
        }

        return true;
    }

    public String getData() {

        return data;
    }
}
/*
 * $Log$
 */
