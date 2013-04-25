package org.dbdoclet.jive.dialog.settings;

import java.io.File;

public class PathData {

    private String key;
    private String label;
    private File file;
    private File standard;
    
    public PathData(String key, String label, File file, File standard) {
        
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("The argument key must not be null!");
        }

        if (label == null) {
            throw new IllegalArgumentException("The argument label must not be null!");
        }

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        if (standard == null) {
            throw new IllegalArgumentException("The argument standard must not be null!");
        }

        this.key = key;
        this.label = label;
        this.file = file;
        this.standard = standard;
    }
    
    
    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
    
    public File getFile() {
        return file;
    }
    
    public String getFilePath() {

        if (file == null) {
            throw new IllegalStateException("The field file must not be null!");
        }

        return file.getPath();
    }
    
    public void setFile(File file) {
        this.file = file;
    }

    public File getStandard() {
        return standard;
    }
}
