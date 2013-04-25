/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.log;

import java.io.IOException;
import java.io.PrintWriter;

import org.dbdoclet.io.Screen;

public class ScreenLogger implements Screen {

    private PrintWriter out;

    public ScreenLogger(PrintWriter out)
        throws IOException {
        
        if (out == null) {
            throw new IllegalArgumentException("The argument out may not be null!");
        }
 
        this.out = out;
    }

    public void println(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void clear() {
        // Kann nicht mehr gelöscht werden
    }

    public void error(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void error(String string, String string1) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void warning(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void info(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void command(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void exception(Throwable exception) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        exception.printStackTrace();
    }

    public void exception(Throwable exception, String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
        exception.printStackTrace();
    }

    public void message(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }

        out.println(string);
    }

    public void section(String string) {

        if (out == null) {
            throw new IllegalStateException("The field out may not be null!");
        }
        
        out.println(string);
    }
}
