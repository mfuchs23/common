package org.dbdoclet.jive.dialog.settings;

import java.util.Properties;

import javax.swing.JPanel;

import org.dbdoclet.jive.widget.GridPanel;

public abstract class AbstractSettingsPanel extends GridPanel
    implements SettingsPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public JPanel getPanel() {
        return this;
    }

    public abstract Properties getProperties();
    public abstract void setProperties(Properties properties);
}
