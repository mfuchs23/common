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
package org.dbdoclet.antol.task;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ReplaceServices;

public class HtmlInclude extends Task {

    private File htmlFile;
    private File includeFile;

    public void execute()
        throws BuildException {

        if (htmlFile == null) {
            throw new BuildException("The attribute xsd is undefined!");
        }

        if (includeFile == null) {
            throw new BuildException("The attribute destdir is undefined!");
        }

        if (htmlFile.exists() == false) {
            throw new BuildException("The html file doesn't exist!");
        }

        if (includeFile.exists() == false) {
            throw new BuildException("The include file doesn't exist!");
        }

        try {

            String htmlBuffer = FileServices.readToString(htmlFile);
            String includeBuffer = FileServices.readToString(includeFile);

            String name = includeFile.getName();
            String regexp = "<!--\\[html-include " + name + "\\]-->";
            log("Replacing " + regexp + "...");

            String resultBuffer = ReplaceServices.replaceAll(htmlBuffer, regexp, includeBuffer);

            FileServices.writeFromString(htmlFile, resultBuffer);

        } catch (Exception oops) {
            oops.printStackTrace();
            throw new BuildException(oops);
        }
    }

    public void setFile(String htmlFileName) {

        if (htmlFileName != null) {
            htmlFile = new File(htmlFileName);
        }
    }

    public void setInclude(String includeFileName) {

        if (includeFileName != null) {
            includeFile = new File(includeFileName);
        }
    }
}
