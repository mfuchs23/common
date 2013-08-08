/* 
 * ### Copyright (C) 2004-2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.dbdoclet.service.StringServices;
 
public class Env extends Task {

    // private String regex;

    private String headline = "%%% Ant Environment";

    public void execute()
        throws BuildException {

        Project project = getProject();

        Hashtable<?, ?> map = project.getProperties();

        TreeMap<String, String> sortedMap = new TreeMap<String, String>();

        String name;
        String value;
        String line;

        log(headline);

        for (Iterator<?> i = map.keySet().iterator(); i.hasNext();) {

            name = (String) i.next();
            value = (String) map.get(name);
            
            sortedMap.put(name, value);
        }
        
        for (Iterator<String> i = sortedMap.keySet().iterator(); i.hasNext();) {

            name = i.next();

            /*
            if (regex != null && regex.length() > 0) {
                if (name.matches(regex) == false) {
                    continue;
                }
            }
            */

            value = (String) map.get(name);

            line = StringServices.info(name) + value;
            log(line);
        }
        
    }

    /*
    public void setRegex(String regex) {
        this.regex = regex;
    }
    */

    public void addText(String headline) {

        if (headline == null) {
            headline = "";
        }
        
        this.headline = headline;
    }
}
