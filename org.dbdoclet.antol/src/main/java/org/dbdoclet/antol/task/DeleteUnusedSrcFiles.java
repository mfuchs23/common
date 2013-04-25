/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.FindServices;

public class DeleteUnusedSrcFiles extends Task {

    private static Log logger = LogFactory.getLog(DeleteUnusedSrcFiles.class);

    private File destDir;
    private File srcDir;

    public void execute()
        throws BuildException {

        try {

            ArrayList<File> srcFileList = new ArrayList<File>();
        
            FindServices.findFile(srcDir, "^.*\\.java", srcFileList);

            System.out.println("Found " + srcFileList.size() + " java files.");
        
            File destFile;
        
            for (File srcFile : srcFileList) {
            
                String destFileName = FileServices.removeParentPath(srcFile, srcDir);
                destFileName = FileServices.getFileBase(destFileName) + ".class";
                destFileName = FileServices.appendFileName(destDir, destFileName);
            
                destFile = new File(destFileName);
                
                if (destFile.exists() == false) {
                    System.out.println("Removing unused source file " + srcFile);
                    FileServices.delete(srcFile);
                }
            }

            deleteEmptyDirs(srcDir);
            
        } catch (IOException oops) {
            logger.fatal("Unexpected Exception!", oops);
        }
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    private void deleteEmptyDirs(File dir)
        throws IOException {
        
        if (dir == null) {
            return;
        }
        
        File[] list = dir.listFiles();
        
        for (int i = 0; i < list.length; i++) {

            if (list[i].isDirectory()) {

                deleteEmptyDirs(list[i]);

                if (list[i].list().length == 0) {
                    FileServices.delete(list[i]);
                }
            }
        }
    }
}
