package org.dbdoclet.jive.dialog.settings;

import java.util.Properties;

import javax.swing.JPanel;

public interface SettingsPanel {

    public JPanel getPanel();
    public Properties getProperties();
    public String getName();
    public String getNamespace();
    public void setProperties(Properties properties);
}
