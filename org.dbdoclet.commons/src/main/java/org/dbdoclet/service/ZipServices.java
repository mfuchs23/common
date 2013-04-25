package org.dbdoclet.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipServices {

    private static Log logger = LogFactory.getLog(ZipServices.class);

    private static final int BUFFER = 2048;

    public static void zip(File archiveFile, File srcDir) 
        throws IOException {

        zip(archiveFile, srcDir, "");
    }

    public static void zip(File archiveFile, File srcDir, String prefix) 
        throws IOException {

        zip(archiveFile, srcDir, srcDir, prefix);
    }

    public static void zip(File archiveFile, File srcDir, File baseDir) 
        throws IOException {

        zip(archiveFile, srcDir, baseDir, "");
    }
    
    public static void zip(File archiveFile, File srcDir, File baseDir, String prefix) 
        throws IOException {

        FileOutputStream fos = new FileOutputStream(archiveFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        
        ArrayList<File> fileList = new ArrayList<File>();

        FindServices.findFile(srcDir, "^.*$", fileList);

        BufferedInputStream bis;
        File file;
        FileInputStream fis;
        String path;
        ZipEntry entry;

        byte data[] = new byte[BUFFER];

        for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {

            file = iterator.next();
            path = file.getCanonicalPath();
            path = StringServices.cutPrefix(path, baseDir.getCanonicalPath());
            path = StringServices.cutPrefix(path, File.separator);

            if (prefix != null && prefix.trim().length() > 0) {
                path = FileServices.appendFileName(prefix, path);
            }
            
            path = FileServices.normalizePath(path);

            entry = new ZipEntry(path);
            zos.putNextEntry(entry);

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis, BUFFER);

            int count;

            while((count = bis.read(data, 0, BUFFER)) != -1) {
                zos.write(data, 0, count);
            }

            bis.close();
            fis.close();
        }

        zos.close();
    }

    public static void unzip(File archiveFile, File destDir)
        throws IOException {

        if (archiveFile == null) {
            throw new IllegalArgumentException("The argument archiveFile must not be null!");
        }

        unzip(new FileInputStream(archiveFile), destDir);
    }

    public static void unzip(URL url, File destDir)
        throws IOException {

        if (url == null) {
            throw new IllegalArgumentException("The argument url must not be null!");
        }

        logger.debug("url = " + url);
        logger.debug("destDir = " + destDir);

        InputStream instr = null;
        
        try {

            instr = url.openStream();
            unzip(instr, destDir);
            instr.close();

        } catch (IOException oops) {

            logger.error("Can't unzip URL " + url.toString(), oops);
            throw oops;
        }
    }

    public static void unzip(InputStream instr, File destDir)
        throws IOException {

        if (instr == null) {
            throw new IllegalArgumentException("The argument instr must not be null!");
        }

        if (destDir == null) {
            throw new IllegalArgumentException("The argument destDir may not be null!");
        }

        ZipInputStream zis = null;

	try {

	    FileServices.createPath(destDir);
	    
            ZipEntry entry;
            String destName;
            String fileName;

            zis = new ZipInputStream(instr);

	    while ((entry = zis.getNextEntry()) != null) {
		
                logger.debug("entry = " + entry);

                fileName = FileServices.normalizePath(entry.getName());

		if (entry.isDirectory()) {
		    
                    destName = FileServices.appendPath(destDir, fileName);
		    FileServices.createPath(destName);
            
		} else {
            
		     destName = FileServices.appendFileName(destDir, fileName);
		    
                     File file = new File(destName);
                     File parent = file.getParentFile();
		    
                     if (parent != null) {
                         FileServices.createPath(parent.getAbsolutePath());
                     }
		    
                     FileOutputStream outstr = new FileOutputStream(destName);
		
                     byte[] buffer = new byte[4092];
                     int nread;
		    
                     while ((nread = zis.read(buffer)) != -1) {
                         outstr.write(buffer, 0, nread);
                     }
		    
                     outstr.flush();
                     outstr.close();
		}
	    }

	} finally {
             
            if (zis != null) {

                try {
                    zis.close();
                } catch (Exception oops) {
                    oops.printStackTrace();
                }
            }
        }
    }


    
    public static void unpackJar(String resource, String destPath) throws IOException {

	unpackJar(resource, new File(destPath), false);
    }

    public static void unpackJar(String resource, String destPath, boolean ifNewer) throws IOException {

	unpackJar(resource, new File(destPath), ifNewer);
    }

    public static void unpackJar(String resource, File dir) throws IOException {

	unpackJar(resource, dir, false);
    }

    public static void unpackJar(String resource, File dir, boolean ifNewer) throws IOException {

	if (resource == null) {
	    throw new IllegalArgumentException("The argument resource must not be null!");
	}

	if (dir == null) {
	    throw new IllegalArgumentException("The argument dir must not be null!");
	}

	String tsFileName = FileServices.appendFileName(dir, "unpackJar.ts");
	File tsFile = new File(tsFileName);

	URL url = ResourceServices.getResourceAsUrl(resource);

	if (url == null) {
	    throw new IOException("Cant find resource " + resource + ".");
	}

	String protocol = url.getProtocol();
	logger.debug("protocol = " + protocol);

	File jarFile;

	if (protocol.equals("jar")) {

	    boolean unpack = true;

	    String path = url.getFile();
	    int index = path.indexOf(".jar!");

	    if (index != -1) {
		path = path.substring(0, index + 4);
	    }

	    url = new URL(path);

	    protocol = url.getProtocol();

	    if (protocol.equals("file") == true && ifNewer == true) {

		jarFile = new File(url.getFile());

		if (jarFile.exists() && FileServices.newer(jarFile, tsFile) == false && ifNewer == true) {

		    unpack = false;
		}
	    }

	    if (unpack == true) {

		ZipServices.unzip(url, dir);
		FileServices.touch(tsFileName);
	    }
	}
    }

}
