/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.progress.ProgressVetoListener;
import org.dbdoclet.service.FileServices;

public class Eraser {

    private ArrayList<ProgressVetoListener> progressListenerList = new ArrayList<ProgressVetoListener>();
    private LinkedHashMap<File, File> jobList = new LinkedHashMap<File, File>();

    public int getJobCount() {
        return jobList.size();
    }
    
    public void doDelete()
        throws IOException {

        File file;
        
        Iterator<File> iterator = jobList.keySet().iterator();
        int index = 1;
        
        while (iterator.hasNext()) {
            
            file = iterator.next();
            
            fireProgressEvent(new ProgressEvent(index, "File", file.getAbsolutePath()));
            FileServices.delete(file);
            index++; 
        }
    }
    
    public void add(String path)
        throws IOException {
        add(new File(path));
    }
    
    public void add(File file)
        throws IOException {

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        if (file.exists() == false) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        if (file.isFile()) {
            addToJobList(file);
            return;
        }

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {

                FileServices.setWritable(files[i]);

                if (files[i].isDirectory()) {
                    FileServices.setWritable(file);
                    add(files[i]);
                }
                
                if (files[i].isFile()) {
                    addToJobList(files[i]);
                }
            }

            addToJobList(file);
        }
    }

    public void addProgressListener(ArrayList<ProgressVetoListener> list) {
        
        if (list != null) {
            progressListenerList.addAll(list);
        }
    }
    
    public void addProgressListener(ProgressVetoListener progressListener) {

        if (progressListenerList.contains(progressListener) == false) {
            progressListenerList.add(progressListener);
        }
    }
    
    public void removeProgressListener(ProgressListener progressListener) {
        progressListenerList.remove(progressListener);
    }

    private void fireProgressEvent(ProgressEvent event) {
        
        if (progressListenerList == null || progressListenerList.size() == 0) {
            return;
        }

        ProgressListener listener;
        Iterator<ProgressVetoListener> iterator = progressListenerList.iterator();
        
        while (iterator.hasNext()) {
            
            listener = iterator.next();
            listener.progress(event);
        }
    }

    private void addToJobList(File file) {
        
        fireProgressEvent(new ProgressEvent().setStage(ProgressEvent.STAGE_PREPARE).setUserObject(file));
        jobList.put(file, file);
    }
}
