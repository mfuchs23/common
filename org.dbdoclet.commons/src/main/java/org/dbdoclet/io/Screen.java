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
package org.dbdoclet.io;

public interface Screen {

    public void clear();

    public void println(String text);

    public void exception(Throwable oops);

    public void exception(Throwable oops, String buffer);

    public void command(String text);

    public void error(String text);

    public void error(String label, String text);

    public void warning(String text);

    public void section(String text);

    public void message(String text);

    public void info(String text);
}
