/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.settings.jdk;

import java.io.File;

public class JdkData {

    private String label;
    private File javaHome;
    
    public JdkData(String label, File javaHome) {
        
        if (label == null) {
            throw new IllegalArgumentException("The argument label must not be null!");
        }

        if (javaHome == null) {
            throw new IllegalArgumentException("The argument javaHome must not be null!");
        }

        this.label = label;
        this.javaHome = javaHome;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    public File getJavaHome() {
        return javaHome;
    }
    
    public String getJavaHomePath() {

        if (javaHome == null) {
            throw new IllegalStateException("The field javaHome must not be null!");
        }

        return javaHome.getPath();
    }
    
    public void setJavaHome(File javaHome) {
        this.javaHome = javaHome;
    }
}
