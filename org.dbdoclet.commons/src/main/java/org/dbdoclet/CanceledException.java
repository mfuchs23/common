/* 
 * $Id$
 *
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet;

/**
 * Diese Ausnahme signalisiert den gewünschten Abbruch einer Aktion,
 * beispielsweise aus einer Fortschrittsanzeige heraus. Die Ausnahme kann aus
 * tief verschachtelten Strukturen heraus geworfen werden. Da es eine
 * <code>RuntimeException</code> ist, muß sie nicht in der Methodesignatur
 * aufgeführt werden.
 * 
 * @author Michael Fuchs
 */
public class CanceledException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CanceledException() {
        super();
    }

    public CanceledException(String msg) {
        super(msg);
    }

    public CanceledException(Throwable cause) {
        super(cause);
    }

    public CanceledException(String message, Throwable cause) {
        super(message, cause);
    }
}
