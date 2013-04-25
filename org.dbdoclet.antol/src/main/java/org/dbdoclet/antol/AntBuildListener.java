/* 
 * ### Copyright (C) 2001-2009 Michael Fuchs ###
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

public class AntBuildListener implements BuildListener {

    private boolean debug;
    private Screen screen;
    private String taskName = "";
    private boolean verbose;
    
    public AntBuildListener() {
        this.screen = null;
    }

    public AntBuildListener(Screen screen) {

        if (screen == null) {
            throw new IllegalArgumentException("The argument screen may not be null!");
        }

        this.screen = screen;
    }

    private String alignRight(String text, int width) {

        if (text == null || text.length() >= width) {
            return text;
        }

        StringBuilder builder = new StringBuilder(text);

        while (builder.length() < width) {
            builder.insert(0, ' ');
        }

        return builder.toString();
    }

    public void buildFinished(BuildEvent buildEvent) {
        message("[Build finished] " + buildEvent.getMessage());
    }

    public void buildStarted(BuildEvent buildEvent) {
        message("[Build started] " + buildEvent.getMessage());
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isVerbose() {
        return verbose;
    }

    private void message(String msg) {

        if (screen != null) {
            screen.message(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void messageLogged(BuildEvent buildEvent) {

        int priority = buildEvent.getPriority();

        switch (priority) {

        case Project.MSG_DEBUG:
            
            if (debug == true) {
                message("[DEBUG] " + buildEvent.getMessage());
            }
            
            break;

        case Project.MSG_VERBOSE:

            if (verbose || debug) {
                message("[VERBOSE] " + buildEvent.getMessage());
            }
            
            break;

        default:

            String msg = buildEvent.getMessage();

            if (msg == null || msg.trim().length() == 0) {
                return;
            }

            if (taskName != null && taskName.trim().length() > 0) {
                message(alignRight("[" + taskName + "] ", 12) + msg);
            } else {
                message(msg);

            }
            break;
        }

    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void targetFinished(BuildEvent buildEvent) {
    }

    public void targetStarted(BuildEvent buildEvent) {
        message(" ");
        message(buildEvent.getTarget().getName() + ":");
    }

    public void taskFinished(BuildEvent buildEvent) {
    }

    public void taskStarted(BuildEvent buildEvent) {

        Task task = buildEvent.getTask();
        taskName = task.getTaskName();
    }

}
