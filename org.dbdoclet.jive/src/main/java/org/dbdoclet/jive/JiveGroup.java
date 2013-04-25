package org.dbdoclet.jive;

import org.dbdoclet.jive.dialog.ExceptionBox;

public class JiveGroup extends ThreadGroup {

    public JiveGroup() {
        super("JiveGroup");
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable oops) {

        ExceptionBox ebox = new ExceptionBox(thread.toString(), oops);
        ebox.setVisible(true);
        ebox.toFront();

        oops.printStackTrace();
    }
}
