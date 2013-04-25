/** 
 * ### Copyright (C) 2009 Michael Fuchs
 * All Rights Reserved.               
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL: http://www.dbdoclet.org
 */
package org.dbdoclet.jive.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JSpinner;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.MarginSet;
import org.dbdoclet.jive.model.SpinnerDistanceModel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class MarginChooser extends AbstractDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    public final static int STANDARD = 1;
    public final static int MONOSPACED = 2;

    public static void main(String[] args) {

        JiveFactory.getInstance(Locale.getDefault());
        MarginChooser dlg = new MarginChooser(null, null);
        // dlg.setFontList(new String[] { "Arial", "Courier", "Helvetica",
        // "Times", "monospace", "sans-serif", "serif" });
        dlg.createGui();
        dlg.setVisible(true);
    }
    
    private boolean canceled = false;
    private JButton cancelButton;
    private JSpinner spaceBeforeSpinner;
    private ResourceBundle res;
    private JiveFactory wm;

    private JSpinner spaceAfterSpinner;

    public MarginChooser(Frame frame, MarginSet marginSet) {

        super(frame, "MarginChooser", true);
    }
    
    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();

        if (cmd == null) {
            return;
        }

        if (cmd.equalsIgnoreCase("cancel")) {
            setCanceled(true);
            setVisible(false);
        }

        if (cmd.equalsIgnoreCase("ok")) {
            setCanceled(false);
            setVisible(false);
        }
    }


    public void createGui() {
    
        wm = JiveFactory.getInstance();
        this.res = wm.getResourceBundle();
    
        GridPanel panel = new GridPanel();
        getRootPane().setContentPane(panel);

        panel.addSeparator(2, ResourceServices.getString(res,"C_SPACING"));
        panel.incrRow();
        
        spaceBeforeSpinner = new JSpinner(new SpinnerDistanceModel(wm.getLocale()));
        panel.addLabeledComponent(ResourceServices.getString(res,"C_BEFORE"), spaceBeforeSpinner);

        panel.incrRow();
        
        spaceAfterSpinner = new JSpinner(new SpinnerDistanceModel(wm.getLocale()));
        panel.addLabeledComponent(ResourceServices.getString(res,"C_AFTER"), spaceAfterSpinner);
        
        JButton okButton = wm.createButton(null, ResourceServices.getString(res,"C_OK"));
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);
        panel.addButton(okButton);
    
        cancelButton = wm.createButton(null, ResourceServices.getString(res,"C_CANCEL"));
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        panel.addButton(cancelButton);
    
        panel.prepare();
        pack();
        center();
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
