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
package org.dbdoclet.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class JvmServices {

    public static void listSystemProperties() {

        Properties props = System.getProperties();
        props.list(System.out);
    }

    public static String getOperatingSystem() {

        String os = System.getProperty("os.name");
        return os;
    }

    public static String getLinuxDistribution()
        throws IOException {

        File file;

        file = new File("/etc/lsb-release");

        if (file.exists() == true) {

            String value = FileServices.getValueOfKey(file, "DISTRIB_ID");
            
            if (value != null && value.length() > 0) {
                return value;
            }
        }

        file = new File("/etc/redhat-release");

        if (file.exists() == true) {
            return "RedHat";
        }

        file = new File("/etc/SuSE-release");

        if (file.exists() == true) {
            return "SUSE";
        }

        return null;
    }

    public static boolean isUnix() {

        String os = System.getProperty("os.name");

        if (os.equalsIgnoreCase("Linux")) {
            return true;
        }

        return false;
    }

    public static boolean isWindows() {

        String os = System.getProperty("os.name");

        if (os.startsWith("Windows")) {
            return true;
        }

        return false;
    }

    public static boolean isJdk() {

        File binDir;
        File libDir;
        File jreDir;
        File srcFile;
        String path;
        
        String jreHome = System.getProperty("java.home");
        File jreHomeDir = new File(jreHome);

        File jdkHomeDir = jreHomeDir.getParentFile();
        
        if (jdkHomeDir == null) {
            return false;
        }

        path = FileServices.appendPath(jdkHomeDir, "bin");
        binDir = new File(path);
        
        path = FileServices.appendPath(jdkHomeDir, "lib");
        libDir = new File(path);
        
        path = FileServices.appendPath(jdkHomeDir, "jre");
        jreDir = new File(path);
        
        path = FileServices.appendFileName(jdkHomeDir, "src.zip");
        srcFile = new File(path);
        
        if (binDir.exists() == false 
            || libDir.exists() == false
            || jreDir.exists() == false
            || srcFile.exists() == false) {
            
            return false;
        }

        return true;
    }
    
    public static File getTmpDirectory() {

        return new File(System.getProperty("java.io.tmpdir", "/tmp"));
    }

    public static File getJavaHomeDirectory() {

        return new File(System.getProperty("java.home"));
    }

    public static File getJdkHomeDirectory() {

        File jreHomeDir = getJavaHomeDirectory();
        return jreHomeDir.getParentFile();
    }

    public static File getHomeDirectory() {

        return new File(System.getProperty("user.home", "/tmp"));
    }
}
