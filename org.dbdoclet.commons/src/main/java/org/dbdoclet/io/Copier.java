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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.service.FileServices;

public class Copier {

    private ArrayList<ProgressListener> progressListenerList = new ArrayList<ProgressListener>();
    private LinkedHashMap<File, CopyJob> jobList = new LinkedHashMap<File, CopyJob>();

    public int getJobCount() {
        return jobList.size();
    }
    
    public Set<File> getJobList() {	
        return jobList.keySet();
    }
    
    public void doCopy()
        throws IOException {
        
        doCopy(null);
    }
    
    public void doCopy(Map<String, String> filterSet) {

        jobList.values().stream().forEach(job -> {
        	try {
        		FileServices.copyFileToFile(job.getFrom(), job.getTo(), filterSet);
        	} catch (IOException oops) {
        		throw new RuntimeException(oops);
        	}
        });
    }
    
    public void add(String src, String dest)
        throws IOException {
        add(new File(src), new File(dest));
    }
    
    public void add(File src, File dest)
        throws IOException {

        String path;
        
        if (src == null) {
            throw new IllegalArgumentException("The argument src must not be null!");
        }

        if (dest == null) {
            throw new IllegalArgumentException("The argument dest must not be null!");
        }

        if (src.exists() == false) {
            throw new FileNotFoundException(src.getAbsolutePath());
        }

        if (src.isDirectory() && dest.isFile()) {
            throw new IllegalArgumentException("Can't copy a directory (" 
                                               + src.getAbsolutePath() + " to a file (" 
                                               + dest.getAbsolutePath() + ")!");
        }

        if (src.isFile() && (dest.exists() == false || dest.isFile())) {
            addToJobList(dest, src);
            return;
        }

        if (src.isFile() && dest.isDirectory()) {

            path = FileServices.appendFileName(dest, src.getName());
            addToJobList(new File(path), src);
            return;
        }

        if (src.isDirectory() && dest.exists() == false) {
            FileServices.createPath(dest);
        }
        
        if (src.isDirectory() && dest.isDirectory()) {

            File[] files = src.listFiles();

            Arrays.stream(files).forEach(file -> {;

                String destFileName = FileServices.appendFileName(dest, file.getName());
                File destFile = new File(destFileName);

                if (file.isDirectory()) {
                	try {
                		FileServices.createPath(destFile);
                		add(file, destFile);
                	} catch (IOException oops) {
                		throw new RuntimeException(oops);
                	}
                }
                
                if (file.isFile()) {
                    addToJobList(destFile, file);
                }
            });
        }
    }

    public void addProgressListener(ArrayList<ProgressListener> list) {
        
        if (list != null) {
            progressListenerList.addAll(list);
        }
    }
    
    public void addProgressListener(ProgressListener progressListener) {

        if (progressListener != null && progressListenerList.contains(progressListener) == false) {
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

        progressListenerList.stream().forEach(l -> l.progress(event));
    }
            
    private void addToJobList(File dest, File src) {
        
        fireProgressEvent(new ProgressEvent().setStage(ProgressEvent.STAGE_PREPARE).setUserObject(dest));
        jobList.put(dest, new CopyJob(src, dest));
    }
}

class CopyJob {

    private File from;
    private File to;
    
    public CopyJob(File from, File to) {

        if (from == null) {
            throw new IllegalArgumentException("The argument from must not be null!");
        }

        if (to == null) {
            throw new IllegalArgumentException("The argument to must not be null!");
        }

        this.from = from;
        this.to = to;
    }

    
    public File getFrom() {
        return from;
    }

    
    public File getTo() {
        return to;
    }
}
