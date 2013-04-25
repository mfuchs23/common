/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.dbdoclet.service.FileServices;

public class Settings extends Properties {

    private static final long serialVersionUID = 1L;
    private File file;

    public Settings(File file) {

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        this.file = file;
    }

    public Settings(File file, Properties defProperties) {

        super(defProperties);
        
        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        this.file = file;
    }

    public void load() 
        throws IOException {

        load(new FileInputStream(file));
    }

    public synchronized void store() 
        throws IOException {

        File dir = file.getParentFile();

        if (dir != null) {
            FileServices.createPath(dir);
        }

        FileOutputStream fos = new FileOutputStream(file);
        store(fos, "Settings");
        fos.close();

        FileServices.sort(file);
    }

    public void setFlag(String key, boolean flag) {
	
        setProperty(key, String.valueOf(flag));
    }
    
    public boolean getFlag(String key, boolean def) {
	
	String value = getProperty(key);
	
	if (value == null) {
	    return def;
	}
	
	return Boolean.valueOf(value);
    }
    
    public void setPreference(String key, Object pref) {

        if (key == null) {
            throw new IllegalArgumentException("The argument key must not be null!");
        }

        if (pref == null) {
            throw new IllegalArgumentException("The argument pref must not be null!");
        }

        Preferences prefs = Preferences.userRoot();
        prefs.put(key, pref.toString());
    }

    public String getPreference(String key) {

        Preferences prefs = Preferences.userRoot();
        return prefs.get(key, "");
    }
    
    
}
