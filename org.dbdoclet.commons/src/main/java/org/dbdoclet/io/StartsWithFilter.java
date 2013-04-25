/* 
 * $Id: StartsWithFileNameFilter.java,v 1.1.1.1 2004/12/21 14:06:38 mfuchs Exp $
 *
 * ### Copyright (C) 2001-2004 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 *
 * Unico Media GmbH, Görresstr. 12, 80798 München, Germany
 * http://www.unico-group.com
 *
 * RCS Information
 * Author..........: $Author: mfuchs $
 * Date............: $Date: 2004/12/21 14:06:38 $
 * Revision........: $Revision: 1.1.1.1 $
 * State...........: $State: Exp $
 */
package org.dbdoclet.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Die Klasse <code>EndsWithFilter</code> überprüft ob der Name einer
 * Datei mit dem definierten Anfang übereinstimmt.
 *
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class StartsWithFilter
    implements FilenameFilter {

    /** Der Anfang, der überprüft werden soll. */
    private String start;
    
    /**
     * Erzeugt eine neue Instanz der Klasse
     * <code>StartsWithFileNameFilter</code>.
     *
     * @param start <code>String</code>
     */
    public StartsWithFilter(String start) {
        this.start = start;
    }
    
    /**
     * Die Methode <code>accept</code> überprüft ob der angegebene Dateiname auf
     * den definierten Anfang passt.
     *
     * Die Überprüfung des Anfangs erfolgt ohne Beachtung der Groß- und
     * Kleinschreibung.
     *
     * @param file <code>File</code>
     * @param name <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean accept(File file, String name) {

        String s1 = name.toLowerCase();
        String s2 = start.toLowerCase();

        return s1.startsWith(s2);
    }
}
/*
 * $Log: StartsWithFileNameFilter.java,v $
 * Revision 1.1.1.1  2004/12/21 14:06:38  mfuchs
 * Reimport
 *
 * Revision 1.3  2004/09/03 08:26:54  mfuchs
 * Sicherung
 *
 * Revision 1.2  2004/08/27 19:30:32  mfuchs
 * Dokumentation
 *
 * Revision 1.1.1.1  2004/05/13 17:14:37  mfuchs
 * Services
 *
 */
