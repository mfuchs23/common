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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dbdoclet.ServiceException;

public class ReplaceServices {

    public static String replaceAll(String buffer, String regexp, String subst) {

        if (buffer == null) {
            throw new IllegalArgumentException("The argument buffer must not be null!");
        }

        if (regexp == null) {
            throw new IllegalArgumentException("The argument regexp must not be null!");
        }

        if (subst == null) {
            throw new IllegalArgumentException("The argument subst must not be null!");
        }

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(buffer);
        return matcher.replaceAll(subst);
    }

    public static String replaceLines(String buffer, String regexp, String subst)
        throws IOException,
               ServiceException {

        if (buffer == null) {
            throw new IllegalArgumentException("The argument buffer must not be null!");
        }

        if (regexp == null) {
            throw new IllegalArgumentException("The argument regexp must not be null!");
        }

        if (subst == null) {
            throw new IllegalArgumentException("The argument subst must not be null!");
        }

        BufferedReader reader = null;
        PrintWriter writer = null;
        StringWriter out = new StringWriter();

        try {

            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher;

            reader = new BufferedReader(new StringReader(buffer));
            writer = new PrintWriter(out);

            String line;
            
            while ((line = reader.readLine()) != null) {
                
                matcher = pattern.matcher(line);

                if (matcher.find()) {
                    writer.println(subst);
                } else {
                    writer.println(line);
                }
            }

            reader.close();
            writer.close();

            return out.toString();

        } catch (Exception oops) {

            throw new ServiceException(oops);

        } finally {

            try {

                if (reader != null) {
                    reader.close();
                }

                if (writer != null) {
                    writer.close();
                }

            } catch (IOException ioe) {

                ioe.printStackTrace();
            }
        }
    }

    public static void replaceLines(File file, String regexp, String subst)
        throws IOException,
               ServiceException {

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        if (regexp == null) {
            throw new IllegalArgumentException("The argument regexp must not be null!");
        }

        if (subst == null) {
            throw new IllegalArgumentException("The argument subst must not be null!");
        }

        PrintWriter writer = null;
        BufferedReader reader = null;

        try {

            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher;

            File tmpFile = File.createTempFile("rsrl",".tmp");
            writer = new PrintWriter(new FileWriter(tmpFile));
            
            reader = new BufferedReader(new FileReader(file));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                
                matcher = pattern.matcher(line);

                if (matcher.find()) {
                    writer.println(subst);
                } else {
                    writer.println(line);
                }
            }

            reader.close();
            writer.close();

            if (file.exists()) {
                file.delete();
            }

            FileServices.copyFileToFile(tmpFile, file);

        } catch (Exception oops) {

            throw new ServiceException(oops);

        } finally {

            try {

                if (reader != null) {
                    reader.close();
                }

                if (writer != null) {
                    writer.close();
                }

            } catch (IOException ioe) {

                ioe.printStackTrace();
            }
        }
    }
}
