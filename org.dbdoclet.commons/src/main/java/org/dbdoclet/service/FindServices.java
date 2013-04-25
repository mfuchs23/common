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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dbdoclet.comparator.PathTokenCountComparator;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;

public class FindServices {

    /**
     * Die Methode <code>findFileInDirectory</code> durchsucht ein Verzeichnis rekursiv nach
     * Unterverzeichnissen und Dateien.
     *
     * Die gefundenen Dateien werden als <code>java.io.File</code>-Objekte in
     * der übergebenen Liste abgelegt.
     *
     * @param dir Das Verzeichnis
     * @param dpattern Der reguläre Ausdruck für die Auswahl der Verzeichnisse.
     * @param fpattern Der reguläre Ausdruck für die Auswahl der Dateien.
     * @param list Die Liste der gefundenen Dateien als <code>File</code>-Objekte.
     *
     * @see java.io.File
     */
    public static void findFileInDirectory(File dir,
                                           String dpattern, 
                                           String fpattern,
                                           List<File> list) {
    
        findFileInDirectory(dir, dpattern, fpattern, null, list);
    }
    
    public static void findFileInDirectory(File dir,
                                           String dpattern, 
                                           String fpattern,
                                           ProgressListener listener,
                                           List<File> list) {
        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }
 
        if (dpattern == null) {
            throw new IllegalArgumentException("The argument dpattern must not be null!");
        }
 
        if (fpattern == null) {
            throw new IllegalArgumentException("The argument fpattern must not be null!");
        }
 
        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }
 
        File[] files1 = dir.listFiles();
        File[] files2;
        File file1;
        File file2;
        
        Pattern red = Pattern.compile(dpattern, Pattern.CASE_INSENSITIVE); 
        Pattern ref = Pattern.compile(fpattern, Pattern.CASE_INSENSITIVE); 

        Matcher dirMatcher;
        Matcher fileMatcher;

        if (files1 == null) {
            return;
        }
        
        for (int i = 0; i < files1.length; i++) {
            
            file1 = files1[i];

            dirMatcher = red.matcher(file1.getName());

            if (file1.isDirectory() && dirMatcher.matches()) {

                if (listener != null) {
                    listener.progress(new ProgressEvent().setStage(ProgressEvent.STAGE_PREPARE).setUserObject(file1));
                }

                files2 = file1.listFiles();
                
                if (files2 != null && files2.length > 0) {

                    for (int j = 0; j < files2.length; j++) {

                        file2 = files2[j];
                        fileMatcher = ref.matcher(file2.getName());

                        if (file2.isFile() && fileMatcher.matches()) {
                            list.add(file2);
                        }
                    }
                }
                
                findFileInDirectory(file1, dpattern, fpattern, listener, list);
                continue;
            }
            
            if (file1.isDirectory()) {

                if (listener != null) {
                    listener.progress(new ProgressEvent().setStage(ProgressEvent.STAGE_PREPARE).setUserObject(file1));
                }
                
                findFileInDirectory(file1, dpattern, fpattern, listener, list);
            }
        }
    }
    
    public static void findFile(String path, ArrayList<File> list) {

        if (path == null) {
            throw new IllegalArgumentException("The argument path must not be null!");
        }

        findFile(new File(path), null, list);
    }

    public static void findFile(File dir, ArrayList<File> list) {
        findFile(dir, null, list);
    }

    public static void findFile(File dir, String fpattern, ArrayList<File> list) {
        findFile(dir, fpattern, true, list);
    }

    /**
     * Die Methode <code>findFile</code> sucht nach Dateien im Verzeichnis
     * <code>dir</code> die auf den regulären Ausdruck <code>fpattern</code>
     * passen.
     *
     * @param dir Das Verzeichnis.
     * @param fpattern Der reguläre Ausdruck.
     * @param list Die Liste der gefundenen Dateien
     */
    public static void findFile(File dir, String fpattern, boolean recursive, ArrayList<File> list) {
    
        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }

        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }

        File[] files = dir.listFiles();
        File file;
        
        Pattern ref = null;
        Matcher fileMatcher = null;

        if (fpattern != null) {
            ref = Pattern.compile(fpattern, Pattern.CASE_INSENSITIVE); 
        }

        if (files == null) {
            return;
        }
        
        for (int i = 0; i < files.length; i++) {
            
            file = files[i];
            
            if (file.isDirectory()) {

                if (recursive == true) {
                    findFile(file, fpattern, list);
                }

            } else {

                if (ref != null) {

                    fileMatcher = ref.matcher(file.getName());

                    if (fileMatcher.matches()) {
                        list.add(file); 
                    }

                } else {

                    list.add(file);
                }
            }
        }
    }
    
    /**
     * Die Methode <code>findDirectory</code> sucht nach Unterverzeichnissen im
     * Verzeichnis <code>dir</code> die auf den regulären Ausdruck
     * <code>dpattern</code> passen. Die Liste wird, bevor sie an die aufrufende
     * Methode zurückgegeben wird, nach der Anzahl der Pfadelemente
     * sortiert. Und zwar so, daß die längsten Pfade am Anfang der Liste
     * stehen. Dies ermöglicht eine Verarbeitung, z.B. Umbenennung, der Pfade
     * vom Ende her, in der Art, daß möglichst alle Pfade ihre Gültigkeit
     * behalten. Würde ein Pfad zuerst am Anfang manipuliert, z.B. durch
     * Umbennenung, wären alle Pfadnamen der Unterverzeichnisse auf einen Schlag
     * ungültig.
     *
     * @param dir Das Verzeichnis
     * @param dpattern Der reguläre Ausdruck.
     * @param list Die Liste der gefundenen Dateien
     */
    public static void findDirectory(File dir, String dpattern, ArrayList<File> list) {
    
        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }
 
        if (dpattern == null) {
            throw new IllegalArgumentException("The argument dpattern must not be null!");
        }
 
        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }
 
        File[] files = dir.listFiles();
        File file;

        Pattern red = Pattern.compile(dpattern, Pattern.CASE_INSENSITIVE); 
        Matcher dirMatcher;

        if (files == null) {
            return;
        }
        
        for (int i = 0; i < files.length; i++) {
            
            file = files[i];
            
            if (file.isDirectory()) {
                
                dirMatcher = red.matcher(file.getName());

                if (dirMatcher.matches()) {
                    list.add(file); 
                }

                findDirectory(file, dpattern, list);
            }                     
        }

        Collections.sort(list, new PathTokenCountComparator());
    }
    
}
