/* 
 * ### Copyright (C) 2005,2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.progress;

public interface MessageListener {

    public void debug(String msg);
    public void info(String msg);
    public void error(String msg);
    public void fatal(String msg, Throwable oops);
}
