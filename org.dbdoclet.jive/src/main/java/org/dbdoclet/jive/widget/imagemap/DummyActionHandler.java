package org.dbdoclet.jive.widget.imagemap;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class DummyActionHandler extends AbstractAction{

    private static final long serialVersionUID = 1L;

    public void actionPerformed(ActionEvent e) {
        System.out.println("Dummy-Action called");
    }

}
