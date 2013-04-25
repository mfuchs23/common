/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.manager;

import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.dbdoclet.progress.MessageListener;

public class MessageManager {

    private static MessageManager singleton = null;
    private static TreeMap<String, MessageListener> listeners = new TreeMap<String, MessageListener>();

    public static final int FATAL = 1;
    public static final int ERROR = 2;
    public static final int WARN  = 3;
    public static final int INFO  = 4;
    public static final int DEBUG = 5;

    private int level = INFO;
    private ResourceBundle res;

    public static MessageManager getInstance() {

        if (singleton == null) {
            singleton = new MessageManager();
        }
        
        return singleton;
    }

    private MessageManager() {
        super();
    }

    public void setResourceBundle(ResourceBundle res) {
        this.res = res;
    }

    public ResourceBundle getResourceBundle() {
        return res;
    }

    public static void addMessageListener(String name, MessageListener listener) {
        
        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (name == null) {
            throw new IllegalArgumentException("The argument name may not be null!");
        }
 
        if (listener == null) {
            throw new IllegalArgumentException("The argument listener may not be null!");
        }
 
        listeners.put(name, listener);
    }

    public static MessageListener getMessageListener(String name) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (name == null) {
            throw new IllegalArgumentException("The argument name may not be null!");
        }

        return listeners.get(name);
    }

    public void setLevel(int level) {

        if (level >= FATAL && level <= DEBUG) {
            this.level = level;
        }
    }

    public void debug(String msg) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
 
        if (level < DEBUG) {
            return;
        }

        Collection<MessageListener> list = listeners.values();
        Iterator<MessageListener> iterator = list.iterator();

        MessageListener listener;
        
        while (iterator.hasNext()) {

            listener = iterator.next();
            listener.debug(msg);
        }
    }

    public void info(String msg) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
 
        Collection<MessageListener> list = listeners.values();
        Iterator<MessageListener> iterator = list.iterator();

        MessageListener listener;
        
        while (iterator.hasNext()) {

            listener = iterator.next();
            listener.info(msg);
        }
    }

    public void warn(String msg) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
 
        Collection<MessageListener> list = listeners.values();
        Iterator<MessageListener> iterator = list.iterator();

        MessageListener listener;
        
        while (iterator.hasNext()) {

            listener = iterator.next();
            listener.info(msg);
        }
    }

    public void error(String msg) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
 
        Collection<MessageListener> list = listeners.values();
        Iterator<MessageListener> iterator = list.iterator();

        MessageListener listener;
        
        while (iterator.hasNext()) {

            listener = iterator.next();
            listener.error(msg);
        }
    }

    public void fatal(String msg, Throwable oops) {

        if (listeners == null) {
            throw new IllegalStateException("The field listeners may not be null!");
        }
 
        if (msg == null) {
            throw new IllegalArgumentException("The argument msg may not be null!");
        }
 
        if (oops == null) {
            throw new IllegalArgumentException("The argument oops may not be null!");
        }
 
        Collection<MessageListener> list = listeners.values();
        Iterator<MessageListener> iterator = list.iterator();

        MessageListener listener;

        while (iterator.hasNext()) {

            listener = iterator.next();
            listener.fatal(msg, oops);
        }
    }

    public static int valueOf(String name) {

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        if (name.equalsIgnoreCase("fatal")) {
            return FATAL;
        }

        if (name.equalsIgnoreCase("error")) {
            return ERROR;
        }

        if (name.equalsIgnoreCase("warn")) {
            return WARN;
        }

        if (name.equalsIgnoreCase("info")) {
            return INFO;
        }

        if (name.equalsIgnoreCase("debug")) {
            return DEBUG;
        }

        System.err.println("[WARNING] Unknown logging level '" + name + "'. Using INFO."); 
        return INFO;
    }

    public static String levelToString(int level) {

        String label = "";

        switch (level) {

        case FATAL:
            label = "FATAL ";
            break;

        case ERROR:
            label = "ERROR ";
            break;

        case WARN:
            label = "WARN  ";
            break;

        case INFO:
            label = "INFO  ";
            break;

        case DEBUG:
            label = "DEBUG ";
            break;

        default:
            label = String.valueOf(level);
        }

        return label;
    }
}
/*
 * $Log$
 */
