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
package org.dbdoclet.jive.text;

import org.dbdoclet.io.Screen;

public class ScreenConsole implements Screen {

    public void println(String string) {
        System.out.println(string);
    }

    public void clear() {
        // Kein Löschen möglich
    }

    public void error(String string) {
        System.out.println(string);
    }

    public void error(String string, String string1) {
        System.out.println(string);
    }

    public void warning(String string) {

        System.out.println(string);
    }

    public void info(String string) {

        System.out.println(string);
    }

    public void command(String string) {

        System.out.println(string);
    }

    public void exception(Throwable exception) {

        exception.printStackTrace();
    }

    public void exception(Throwable exception, String string) {

        System.out.println(string);
        exception.printStackTrace();
    }

    public void message(String string) {

        System.out.println(string);
    }

    public void section(String string) {

        System.out.println(string);
    }
}
