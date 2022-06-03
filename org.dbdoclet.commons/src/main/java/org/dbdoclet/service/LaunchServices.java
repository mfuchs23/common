/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.dbdoclet.progress.ConsoleInfoListener;
import org.dbdoclet.progress.InfoListener;

/**
 * Die Klasse <code>LaunchServices</code> stellt Methode zur Verfügung, um
 * Java-Klassen und Werkzeuge einer bestimmten Java-Installationen aufzurufen.
 */
public class LaunchServices {

    public static void execJavaClass(String javaHome, String className, InfoListener listener) {

        if (javaHome == null) {
            throw new IllegalArgumentException("The argument javaHome must not be null!");
        }

        if (className == null) {
            throw new IllegalArgumentException("The argument className must not be null!");
        }

        String javaBin = FileServices.appendPath(javaHome, "bin");
        javaBin = FileServices.appendFileName(javaBin, "java");
        
        javaBin = javaBin + " -classpath " + System.getProperty("java.class.path") + " " + className;

        ExecResult res = ExecServices.exec(javaBin, listener);

        if (res.failed() == true) {

            if (res.getThrowable() != null) {
                throw new java.lang.RuntimeException(res.getOutput(), res.getThrowable());
            } else {
                throw new java.lang.RuntimeException(res.getOutput());
            }
        }
    }
    
    public static File getResourceFile(Class<? extends Object> clazz, String resource)
        throws IOException {

        if (clazz == null) {
            throw new IllegalArgumentException("The argument clazz may not be null!");
        }

        if (resource == null) {
            throw new IllegalArgumentException("The argument resource may not be null!");
        }

        String msg;
        String jarPrefix = "jar:file:";
        String filePrefix = "file:";

        ClassLoader loader = clazz.getClassLoader();
        
        URL url = loader.getResource(resource);

        if (url == null) {
            url = ClassLoader.getSystemClassLoader().getResource(resource);
        }

        if (url == null) {
            msg = "Can't find resource [" + resource + "]";
            throw new FileNotFoundException(msg);
        }

        String buffer = url.toString();
        buffer = URLDecoder.decode(buffer, "UTF-8");

        if (buffer.equals(jarPrefix) || buffer.equals(filePrefix)) {
            msg = "Invalid url [" + buffer + "].";
            throw new FileNotFoundException(msg);
        }

        if (buffer.startsWith(jarPrefix) == false && buffer.startsWith(filePrefix) == false) {
            msg = "Unknown url format [" + buffer + "]. Unknown prefix.";
             throw new FileNotFoundException(msg);
        }

        if (buffer.startsWith(jarPrefix)) {
            buffer = buffer.substring(jarPrefix.length());
        }

        if (buffer.startsWith(filePrefix)) {
            buffer = buffer.substring(filePrefix.length());
        }

        int index = buffer.lastIndexOf("!/" + resource);

        if (index != -1) {
            buffer = buffer.substring(0, index);
        }

        File file = new File(buffer);

        if (file.exists() == false) {
            msg = "No such file [" + file.getCanonicalPath() + "]!";
            throw new FileNotFoundException(msg);
        }

        return file;
    }

    public static File getHome(Class<? extends Object> clazz, String pattern)
        throws IOException,
               PatternSyntaxException {
        
        if (clazz == null) {
            throw new IllegalArgumentException("The argument clazz may not be null!");
        }

        if (pattern == null) {
            throw new IllegalArgumentException("The argument pattern may not be null!");
        }

        String resource = clazz.getName();
        resource = StringServices.replace(resource, ".", "/");
        resource += ".class";

        return getHome(clazz, resource, pattern);
    }

    public static File getHome(Class<? extends Object> clazz, String resource, String pattern)
        throws IOException,
               PatternSyntaxException {
        
        if (clazz == null) {
            throw new IllegalArgumentException("The argument clazz must not be null!");
        }

        if (resource == null) {
            throw new IllegalArgumentException("The argument resource must not be null!");
        }

        if (pattern == null) {
            throw new IllegalArgumentException("The argument pattern may not be null!");
        }

        String msg;

        File jarFile = getResourceFile(clazz, resource);

        String path = jarFile.getCanonicalPath();
        path = StringServices.replace(path, "\\", "/");

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(path);

        if (matcher.matches() == false) {
            // throw new IOException("No matches in of pattern [" + pattern + "] in path [" + path + "].");
        	return null;
        }

        if (matcher.groupCount() != 1) {
            throw new IOException("Wrong number of groups [" + matcher.groupCount() + "]");
        }

        path = matcher.group(1);

        File home = new File(path);

        if (home.exists() == false) {

            msg = "No such file [" + jarFile.getCanonicalPath() + "]!";
            throw new FileNotFoundException(msg);
            
        }
        
        return home;
    }

    public static boolean getSnrProperty(String property) {

        if (property == null || property.length() == 0) {
            return false;
        }

        String[] tokens = property.split("-");

        if (tokens.length != 5) {
            return false;
        }

        int value;

        for (int i = 0; i < tokens.length; i++) {

            value = Integer.parseInt(tokens[i], 16);

            switch (i) {

            case 0:
                if (value % 23 != 0) {
                    return false;
                }
                break;

            case 1:
                if (value % 11 != 0) {
                    return false;
                }
                break;

            case 2:
                if (value % 16 != 0) {
                    return false;
                }
                break;

            case 3:
                if (value % 7 != 0) {
                    return false;
                }
                break;

            case 4:
                if (value % 28 != 0) {
                    return false;
                }
                break;

            }
        }

        return true;
    }

    public static void main(String[] args) {

        execJavaClass("/usr/local/java/jdk-1.6", "org.dbdoclet.service.LaunchServices", new ConsoleInfoListener());
    }
    
}
