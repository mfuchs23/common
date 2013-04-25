/* 
 * $Id: EndsWithFileNameFilter.java,v 1.1.1.1 2004/12/21 14:06:38 mfuchs Exp $
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
 * Die Klasse <code>EndsWithFilter</code> überprüft ob der Name einer Datei mit
 * der definierten Endung übereinstimmt.
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class EndsWithFilter implements FilenameFilter {

    /** Die Endung, die überprüft werden soll. */
    private String[] extensions;

    /**
     * Erzeugt eine neue Instanz der Klasse <code>EndsWithFileNameFilter</code>.
     * 
     * @param extensions
     *            Die Endung, die überprüft werden soll.
     */
    public EndsWithFilter(String... extensions) {

        if (extensions == null) {
            throw new IllegalArgumentException("The argument extensions may not be null!");
        }

        this.extensions = extensions;
    }

    /**
     * Die Methode <code>accept</code> überprüft ob der angegebene Dateiname auf
     * die definierte Endung endet.
     * 
     * Die Überprüfung der Endung erfolgt ohne Beachtung der Groß- und
     * Kleinschreibung.
     * 
     * @param file
     *            <code>File</code>
     * @param name
     *            <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean accept(File file, String name) {

        String s1 = name.toLowerCase();

        for (String extension : extensions) {
            String s2 = extension.toLowerCase();
            if (s1.endsWith(s2)) {
                return true;
            }
        }

        return false;
    }
}
/*
 * $Log: EndsWithFileNameFilter.java,v $ Revision 1.1.1.1 2004/12/21 14:06:38
 * mfuchs Reimport
 * 
 * Revision 1.2 2004/08/27 19:30:32 mfuchs Dokumentation
 * 
 * Revision 1.1.1.1 2004/05/13 17:14:37 mfuchs Services
 */
