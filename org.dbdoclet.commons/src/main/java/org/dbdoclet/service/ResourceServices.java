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
package org.dbdoclet.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Die Klasse <code>ResourceServices</code> bietet Methoden zur Bearbeitung von
 * Resourcen die vom Classloader geldaen werden.
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class ResourceServices {

    private static Log logger = LogFactory.getLog(ResourceServices.class);

    public static ImageIcon getButtonIcon(String name) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL iconUrl = loader.getResource(name);

        if (iconUrl == null) {
            iconUrl = ResourceServices.class.getResource(name);
        }

        if (iconUrl == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(iconUrl, name);

        return icon;
    }

    public static String getXmlTemplate(String name) throws IOException {

        return getXmlTemplate(null, name);
    }

    public static String getXmlTemplate(String language, String name) throws IOException {

        String path;
        URL url = null;

        ClassLoader loader = ClassLoader.getSystemClassLoader();

        if (language != null && language.length() > 0) {

            path = "/templates/xml/" + language.toLowerCase() + "/" + name;
            url = loader.getResource(path);

            if (url == null) {
                url = ResourceServices.class.getResource(path);
            }
        }

        if (url == null) {

            path = "/templates/xml/default/" + name;
            url = loader.getResource(path);

            if (url == null) {
                url = ResourceServices.class.getResource(path);
            }
        }

        if (url == null) {
            return null;
        }

        return readUrl(url);
    }

    /**
     * Die Methode <code>getResourceAsString</code> liefert die Resource als
     * Zeichenkette zurück. Die Eingabestrom wird dabei mit der Kodierung UTF-8
     * eingelesen.
     * 
     * @param name <code>String</code>
     * @return <code>String</code>
     * @exception IOException
     */
    public static String getResourceAsString(String name) throws IOException {
        return getResourceAsString(name, null);
    }
    
    public static String getResourceAsString(String name, ClassLoader classLoader) throws IOException {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }
        
        InputStream is = getResourceAsStream(name, classLoader);

        if (is == null) {
            return null;
        }

        return readStream(is);
    }

    /**
     * @param is
     * @return String
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String readStream(InputStream is) throws UnsupportedEncodingException, IOException {
        
        if (is == null) {
            throw new IllegalArgumentException("The argument is must not be null!");
        }
        
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader reader = new BufferedReader(isr);

        StringBuffer buffer = new StringBuffer();

        String lsep = System.getProperty("line.separator");
        String line = reader.readLine();

        while (line != null) {

            buffer.append(line + lsep);
            line = reader.readLine();
        }

        reader.close();
        isr.close();
        is.close();
        
        return buffer.toString();
    }

    public static URL getResourceAsUrl(String name) {

        URL url = null;

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        url = loader.getResource(name);

        if (url == null) {
            url = ResourceServices.class.getResource(name);
        }

        return url;
    }

    public static URL getResourceAsUrl(String name, ClassLoader classLoader) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        if (classLoader == null) {
            throw new IllegalArgumentException("The argument classLoader must not be null!");
        }

        URL url = null;

        url = classLoader.getResource(name);

        if (url == null) {
            return getResourceAsUrl(name);
        }

        return url;
    }

    public static InputStream getResourceAsStream(String name) throws IOException {
        return getResourceAsStream(name, null);
    }

    public static InputStream getResourceAsStream(String name, ClassLoader classLoader) throws IOException {

        URL url;

        if (classLoader != null) {
            url = getResourceAsUrl(name, classLoader);
        } else {
            url = getResourceAsUrl(name);
        }

        if (url == null) {
            return null;
        }

        return url.openStream();
    }

    public static ImageIcon getIcon(String path) {
        return getIcon(path, null);
    }

    public static ImageIcon getIcon(String path, ClassLoader loader) {

        URL iconUrl = null;

        if (loader != null) {
            iconUrl = loader.getResource(path);
        }

        if (iconUrl == null) {
            iconUrl = ClassLoader.getSystemClassLoader().getResource(path);
        }

        if (iconUrl == null) {
            iconUrl = ResourceServices.class.getResource(path);
        }

        if (iconUrl == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(iconUrl, path);
        return icon;
    }

    public static boolean isDirectory(String name) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        String buffer;
        String entry;
        String str;

        BufferedReader reader = null;

        if (name.endsWith("/") == false) {
            name += "/";
        }

        try {

            buffer = getResourceAsString(name);

            if (buffer == null) {
                throw new IOException("Can't find resource " + name);
            }

            reader = new BufferedReader(new StringReader(buffer));
            entry = reader.readLine();

            while (entry != null) {

                entry = name + entry;

                logger.debug("entry=" + entry);
                str = getResourceAsString(entry);

                if (str == null) {
                    return false;
                }

                break;
                // entry = reader.readLine();
            }

            return true;

        } catch (IOException oops) {

            logger.error(oops);
            return false;

        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException oops) {
                    logger.error(oops);
                }
            }
        }
    }

    public static String[] list(String name) {

        logger.debug("list " + name);

        if (isDirectory(name) == false) {
            logger.error("Resource " + name + " is not a directory!");
            return new String[0];
        }

        String buffer;
        String entry;
        BufferedReader reader = null;

        try {

            ArrayList<String> list = new ArrayList<String>();

            buffer = getResourceAsString(name);
            reader = new BufferedReader(new StringReader(buffer));
            entry = reader.readLine();

            while (entry != null) {

                list.add(entry);
                entry = reader.readLine();
            }

            String[] listing = new String[list.size()];
            Iterator<String> iterator = list.iterator();

            int i = 0;
            while (iterator.hasNext()) {

                entry = iterator.next();
                listing[i++] = entry;
            }

            return listing;

        } catch (IOException oops) {

            logger.error(oops);
            return null;

        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException oops) {
                    logger.error(oops);
                }
            }
        }
    }

    public static void copyToDir(String name, String path) throws IOException {

        if (path == null) {
            throw new IllegalArgumentException("The argument path must not be null!");
        }

        copyToDir(name, new File(path));
    }

    public static void copyToDir(String name, File dir) throws IOException {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }

        logger.debug("Resource: " + name + ", Directory: " + dir.getPath());

        FileServices.createPath(dir);

        String resourceName = name;
        resourceName = StringServices.chop(resourceName, "/");

        int index = resourceName.lastIndexOf('/');

        if (index != -1) {
            resourceName = resourceName.substring(index + 1);
        }

        if (isDirectory(name)) {

            if (name.endsWith("/") == false) {
                name += "/";
            }

            logger.debug("Examination of directory " + name + "...");
            String[] listing = list(name);

            for (int i = 0; i < listing.length; i++) {

                logger.debug("listing[" + i + "] = " + listing[i]);
                copyToDir(name + listing[i], FileServices.appendFileName(dir, resourceName));
            }

        } else {

            InputStream instr = getResourceAsStream(name);

            if (instr == null) {
                throw new IOException("Can't find resource " + name);
            }

            String fileName = FileServices.appendFileName(dir, resourceName);
            logger.debug("Writing resource " + fileName + "...");

            FileOutputStream outstr = new FileOutputStream(fileName);

            int n = 0;
            byte[] buffer = new byte[4096];

            while ((n = instr.read(buffer, 0, 4096)) != -1) {
                outstr.write(buffer, 0, n);
            }

            instr.close();
            outstr.close();
        }
    }

    public static void copyToFile(String name, String fileName) throws IOException {

        if (fileName == null) {
            throw new IllegalArgumentException("The argument fileName must not be null!");
        }

        copyToFile(name, new File(fileName));
    }

    public static void copyToFile(String name, File file) throws IOException {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        if (file.exists() == true && file.isFile() == false) {
            throw new IllegalArgumentException("The argument file must be a regular file!");
        }

        if (isDirectory(name)) {
            throw new IllegalArgumentException("The resource must not be a directory!");
        }

        logger.debug("Resource: " + name + ", Datei: " + file.getPath());

        File dir = file.getParentFile();

        if (dir != null && dir.exists() == false) {
            FileServices.createPath(dir);
        }

        String resourceName = name;
        resourceName = StringServices.chop(resourceName, "/");

        int index = resourceName.lastIndexOf('/');

        if (index != -1) {
            resourceName = resourceName.substring(index + 1);
        }

        InputStream instr = getResourceAsStream(name);

        if (instr == null) {
            throw new IOException("Can't find resource " + name);
        }

        String fileName = FileServices.appendFileName(dir, file.getName());
        logger.debug("Writing resource " + fileName + "...");

        FileOutputStream outstr = new FileOutputStream(fileName);

        int n = 0;
        byte[] buffer = new byte[4096];

        while ((n = instr.read(buffer, 0, 4096)) != -1) {
            outstr.write(buffer, 0, n);
        }

        instr.close();
        outstr.close();
    }

    public static String getString(ResourceBundle res, String key) {

        if (res == null && key == null) {
            return "(null)";
        }

        if (res == null) {
            return key;
        }

        if (key == null) {
            return "";
        }

        try {
            return res.getString(key);
        } catch (MissingResourceException oops) {
            logger.error("Missing resource '" + key + "'.", oops);
            return key;
        }
    }

    public static String readUrl(URL resourceUrl) throws UnsupportedEncodingException, IOException {
        
        if (resourceUrl == null) {
            throw new IllegalArgumentException("The argument resourceUrl must not be null!");
        }
        
        return readStream(resourceUrl.openStream());
    }
}
