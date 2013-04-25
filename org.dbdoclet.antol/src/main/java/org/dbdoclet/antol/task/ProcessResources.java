/* 
 * ### Copyright (C) 2007-2008 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.UrlServices;

public class ProcessResources extends SokoTask {

    private static Log logger = LogFactory.getLog(ProcessResources.class);

    public void execute()
        throws BuildException {

        Project project = getProject();

        File baseDir = project.getBaseDir();
        String path = FileServices.appendPath(baseDir, ".ode");
        path = FileServices.appendFileName(path, "jars");

        try {

            File dir;
            File file;
            String fileName;
            String jarUrl;
            String licenseUrl;
            String name;
            URL url;
            
            file = new File(path);

            PropertyHelper helper = PropertyHelper.getPropertyHelper(project);
            String jarsPath = (String) helper.getProperty("", "workspace.jars");
            String licensePath = (String) helper.getProperty("", "workspace.licenses");
            
            logger.debug("jarsPath=" + jarsPath);

            System.out.println("+ Verarbeite Konfigurationsdatei " + file);
            
            if (file.exists() == true) {

                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line = reader.readLine();
                
                while (line != null) {

                    if (line == null || line.trim().length() == 0) {

                        line = reader.readLine();
                        continue;
                    }

                    jarUrl = jarsUrl + line;
                    url = new URL(jarUrl);

                    fileName = FileServices.appendFileName(jarsPath, line);
                    file = new File(fileName);
 
                    System.out.println("*** " + line + " ***");

                    if (file.exists() == false) {

                        dir = file.getParentFile();
                        
                        if (dir != null && dir.exists() == false) {
                            FileServices.createPath(dir);
                        }
                        
                        wget(url, fileName);
                    
                    } else {
                    	
                    	System.out.println(fileName + " [OK]");
                    }
                    
                    name = FileServices.getFileName(fileName);
                    name = FileServices.getFileBase(name);

                    licenseUrl = UrlServices.appendPath(jarsUrl, FileServices.getDirName(line));
                    licenseUrl = UrlServices.appendPath(licenseUrl, "LICENSE." + name);
                    url = new URL(licenseUrl);
                        
                    fileName = FileServices.appendFileName(licensePath, "LICENSE." + name);
                    file = new File(fileName);
 
                    
                    if (file.exists() == false) {
                        
                        dir = file.getParentFile();
                        
                        if (dir != null && dir.exists() == false) {
                            FileServices.createPath(dir);
                        }

                        try {
                            wget(url, fileName);
                        } catch (FileNotFoundException oops) {
                        	FileServices.writeFromString(file, "");
                            // System.out.println("[WARNING] File not found: " + oops.getMessage());
                        }
                        
                    } else {
                    	
                    	System.out.println(fileName + " [OK]");
                    }

                    line = reader.readLine();
                }

                reader.close();
                System.out.println("- Verarbeitung beendet.");
            }
            
        } catch (Exception oops) {
            oops.printStackTrace();
            throw new BuildException(path, oops);
        }
    }
}
