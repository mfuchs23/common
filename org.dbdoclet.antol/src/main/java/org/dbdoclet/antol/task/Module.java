package org.dbdoclet.antol.task;

import java.io.File;

import org.apache.tools.ant.Project;

class Module {
    
    private Project project;
    
    private int pos;
    private String bundleName;
    private String libName;
    private String version;
    private String type;
    private String subsystem;
    private String level;
    private String packageName;
    private String description;

    public Module(Project project) {

        if (project == null) {
            throw new IllegalArgumentException("The argument project may not be null!");
        }
 
        this.project = project;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setBundleName(String repositoryPath) {
        this.bundleName = repositoryPath;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setLibName(String localPath) {
        this.libName = localPath;
    }

    public String getLibName() {
        return libName;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setType(String arch) {
        this.type = arch;
    }

    public String getType() {
        return type;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        if (description == null) {
            description = "";
        }
 
        return description;
    }

    public boolean hasDescription() {

        if (description != null && description.length() > 0) {
            return true;
        }
        
        return false;
    }

    public void addText(String text) {

        if (text == null) {
            return;
        }
 
        if (description == null) {
            description = "";
        }
        
        description += text + "\n";
    }

    public File getBuildFile() {
        
        String buildFileName = getDir().getAbsolutePath();
        buildFileName = SokoTask.appendFileName(buildFileName, "build.xml");

        return new File(buildFileName);
    }

    public File getDir() {

        if (project == null) {
            throw new IllegalStateException("The field project may not be null!");
        }
 
        if (subsystem == null) {
            throw new IllegalStateException("The field level may not be null!");
        }
 
        if (bundleName == null) {
            throw new IllegalStateException("The field name may not be null!");
        }
 
        String dirName = project.getBaseDir().getAbsolutePath();
        dirName = SokoTask.appendPath(dirName, libName);

        return new File(dirName);
    }
}
