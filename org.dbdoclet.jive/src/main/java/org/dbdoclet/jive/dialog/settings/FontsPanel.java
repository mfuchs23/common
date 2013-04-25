package org.dbdoclet.jive.dialog.settings;

import java.util.Properties;

public class FontsPanel extends AbstractSettingsPanel {

    private static final long serialVersionUID = 1L;
    private Properties properties;

    public String getNamespace() {
        return "fonts.";
    }
    
    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
