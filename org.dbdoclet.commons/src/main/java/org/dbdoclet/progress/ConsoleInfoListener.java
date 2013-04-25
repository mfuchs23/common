package org.dbdoclet.progress;

public class ConsoleInfoListener implements InfoListener {

    public void info(String msg) {
        System.out.println(msg);
    }
}
