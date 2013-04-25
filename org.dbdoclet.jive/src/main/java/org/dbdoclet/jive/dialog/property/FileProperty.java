/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

import org.dbdoclet.service.StringServices;


public class FileProperty extends AbstractProperty {

    private FileFilter fileFilter;
    private JLabel label;
    private File baseDir;

    public FileProperty(String label) {
        super(label, null);
    }

    public FileProperty(String label, File value) {
        super(label, value);
    }

    @Override
    public int getType() {
        return TYPE_FILE;
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }
    
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    /**
     * Setzt das Basisverzeichnis, in dem nach Bilddateien gesucht wird.
     * 
     * @param baseDir  <code>File</code> Das Basisverzeichnis.
     */
    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public String getFilePath() {

        File file = getFile();

        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

    public File getFile() {

        Object obj = getValue();

        if (obj == null) {
            return null;
        }

        if (obj instanceof File) {
            return (File) obj;
        }

        if (obj instanceof String) {

            String str = (String) obj;

            if (str.trim().length() == 0) {
                return null;
            }

            return new File(str);
        }

        throw new IllegalStateException("The field value must be of type File or String, not "
                                        + obj.getClass().getName() + "!");
    }
    
    @Override
    public Component getEditor(Object value) {

	File file;
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(getFileFilter());
        
        file = getBaseDir();
        
        if (file != null && file.exists() == true) {
            chooser.setCurrentDirectory(file);
        }
        
        file = getFile();

        if (file != null && file.exists() == true) {
            chooser.setSelectedFile(getFile());
        }
        
        int rc = chooser.showOpenDialog(getPanel());
        
        label = new JLabel();
        label.setFont(getPlainFont());
        label.setText(getFilePath());
        
        label.setName("fileType://" + getFilePath());
        
        if (rc == JFileChooser.APPROVE_OPTION) {
            
            file = chooser.getSelectedFile();
            label.setText(file.getAbsolutePath());
            label.setName("fileType://" + file.getAbsolutePath());
        }
        
        return label;
    }

    @Override
    public Object getEditorValue() {
	
	if (label == null) {
	    throw new IllegalStateException("The field label must not be null!");
	}
    
        String name = label.getName();
        if (name != null && name.startsWith("fileType://")) {
            return StringServices.cutPrefix(name, "fileType://");
        }

        return "";
    }
}
