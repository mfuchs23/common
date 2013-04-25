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
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.service.FileServices;

public class Copier {

    private static Log logger = LogFactory.getLog(Copier.class);

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
    
    public void doCopy(Map<String, String> filterSet)
        throws IOException {

        CopyJob job;
        File dest;
        
        Iterator<File> iterator = jobList.keySet().iterator();
        
        while (iterator.hasNext()) {
            
            dest = iterator.next();
            job = jobList.get(dest);
            
            //fireProgressEvent(new ProgressEvent(index, "Source File", job.getFrom().getAbsolutePath()));

            if (filterSet == null) {
                FileServices.copyFileToFile(job.getFrom(), job.getTo());
            } else {
                FileServices.copyFileToFile(job.getFrom(), job.getTo(), filterSet);
            }
        }
    }
    
    public void add(String src, String dest)
        throws IOException {
        add(new File(src), new File(dest));
    }
    
    public void add(File src, File dest)
        throws IOException {

        File destFile;
        String destFileName;
        String path;
        
        logger.debug("src=" + src + ", dest=" + dest);

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

            for (int i = 0; i < files.length; i++) {

                logger.debug("files[" + i + "]=" + files[i]);

                destFileName = FileServices.appendFileName(dest, files[i].getName());
                destFile = new File(destFileName);

                if (files[i].isDirectory()) {
                    FileServices.createPath(destFile);
                    add(files[i], destFile);
                }
                
                if (files[i].isFile()) {
                    addToJobList(destFile, files[i]);
                }
            }
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

        ProgressListener listener;
        Iterator<ProgressListener> iterator = progressListenerList.iterator();
        
        while (iterator.hasNext()) {
            
            listener = iterator.next();
            listener.progress(event);
        }
    }
            
    private void addToJobList(File dest, File src) {
        
        fireProgressEvent(new ProgressEvent().setStage(ProgressEvent.STAGE_PREPARE).setUserObject(dest));
                    
        CopyJob job = jobList.get(dest);
        
        if (job != null) {
            logger.debug("File " + job.getFrom() + " is overriden by file " + src);
        }
        
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
