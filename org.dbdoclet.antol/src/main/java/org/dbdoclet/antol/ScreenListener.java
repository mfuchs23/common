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
package org.dbdoclet.antol;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.dbdoclet.io.Screen;

public class ScreenListener 
    implements BuildListener {

    private Screen screen;
    private int verbosity = AntRunner.INFO;

    public ScreenListener(Screen screen, int verbosity) {

        if (screen == null) {
            throw new IllegalArgumentException("The argument screen may not be null!");
        }

	this.screen = screen;
        this.verbosity = verbosity;

    }

    public ScreenListener(Screen screen) {

        this(screen, AntRunner.INFO);
    }
    
    public void setVerbosity(int verbosity) {
        this.verbosity = verbosity;
    }
    
    public int getVerbosity() {
        return verbosity;
    }
    

    public void buildStarted(BuildEvent buildEvent) {
        
        String msg = "";

        if (buildEvent != null) {
        
            Project project = buildEvent.getProject();
            if (project != null) {
                 
                String antfile = project.getProperty("ant.file");
                if (antfile != null) {
                     msg = "Buildfile " + antfile;
                }
            }
        }

	screen.message(msg);
    }

    public void buildFinished(BuildEvent buildEvent) {

        String msg = buildEvent.getMessage();
        Throwable oops = buildEvent.getException();

        if (msg == null && oops == null) {
            msg = "BUILD SUCCESSFUL";
        }

        if (msg == null && oops != null) {
            msg = "BUILD FAILED";
        }
        
	screen.message(msg);
    }

    /**
     * The method <code>targetStarted</code>.
     *
     * @param buildEvent a <code>BuildEvent</code> value
     */
    public void targetStarted(BuildEvent buildEvent) 
    {
	screen.message("[" + buildEvent.getTarget().getName() + "]");
    }

    /**
     * The method <code>targetFinished</code>.
     *
     * @param buildEvent a <code>BuildEvent</code> value
     */
    public void targetFinished(BuildEvent buildEvent)
    {
	screen.message("");
    }

    /**
     * The method <code>taskStarted</code>.
     *
     * @param buildEvent a <code>BuildEvent</code> value
     */
    public void taskStarted(BuildEvent buildEvent) {

	Task task = buildEvent.getTask();

        if (task != null) {

            String name = task.getTaskName();
            
            if (name == null) {
                name = "";
            }

            // screen.message("    " + name + " ");

        }
    }

    public void taskFinished(BuildEvent buildEvent) {
    }

    public void messageLogged(BuildEvent buildEvent) {

	int priority = buildEvent.getPriority();
	
	switch (priority) {

	case Project.MSG_DEBUG:
            
            if (verbosity == AntRunner.DEBUG) {
                screen.message(buildEvent.getMessage());
            }

	    break;
		
	case Project.MSG_VERBOSE:

            if (verbosity >= AntRunner.VERBOSE) {
                screen.message(buildEvent.getMessage());
            }

	    break;

	case Project.MSG_INFO:

            if (verbosity >= AntRunner.INFO) {
                screen.message(buildEvent.getMessage());
            }

	    break;
		
	case Project.MSG_WARN:

            if (verbosity >= AntRunner.WARN) {
                screen.message(buildEvent.getMessage());
            }

	    break;
		
	case Project.MSG_ERR:

            if (verbosity >= AntRunner.ERROR) {
                screen.error(buildEvent.getMessage());
            }

	    break;
		
	default:
	    screen.message(buildEvent.getMessage());
	    break;
	}
    }
}
