/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.test;


import java.awt.Dimension;
import java.util.Locale;
import java.util.Properties;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.SettingsDialog;
import org.dbdoclet.jive.dialog.settings.JdkPanel;
import org.dbdoclet.jive.dialog.settings.LanguageFilterPanel;

public class SettingsTest {
    
    public void execute() {
        
        SettingsDialog dlg = new SettingsDialog(null, "Einstellungen");
        dlg.setSize(new Dimension(800, 600));
        
        dlg.addGroup("Allgemein");
        dlg.addPanel("Allgemein", "Sprachauswahl", new LanguageFilterPanel(Locale.getDefault()));

        Properties properties = new Properties();
        properties.setProperty("java.jdk.JDK-1.5", "/usr/lib/java");
        
        JdkPanel jdkPanel = new JdkPanel();
        jdkPanel.setProperties(properties);
        
        dlg.addGroup("Java");
        dlg.addPanel("Java", "JDK", jdkPanel);
        
        dlg.setVisible(true);
    }
    
    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());
        
        SettingsTest app = new SettingsTest();
        app.execute();

        Thread.sleep(10000);
        System.exit(0);
    }
}
