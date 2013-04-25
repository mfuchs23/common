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
package org.dbdoclet.antol.test;

import java.io.File;

import org.dbdoclet.antol.AntFileReader;
import org.dbdoclet.antol.ant.Fileset;
import org.dbdoclet.antol.ant.Javadoc;
import org.dbdoclet.antol.ant.JavadocItem;
import org.dbdoclet.antol.ant.Target;

public class AntFileReaderTest {

    public static void log(Object obj) {
        System.out.println(obj);
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args)
        throws Exception {

        AntFileReader ant = new AntFileReader(new File(args[0]));
        
        Target target = ant.findTarget("dbdoclet.docbook");
        log(target.getName());

        Javadoc javadoc = (Javadoc) ant.getTask(target, Javadoc.class);
        log(javadoc);

        Fileset fileset;

        JavadocItem[] items = javadoc.getJavadocItem();

        for (int i = 0; i < items.length; i++) {
            
            fileset = items[i].getFileset();
            
            if (fileset != null) {
                log(fileset.getDir());
            }
        }
    }
}
/*
 * $Log$
 */
