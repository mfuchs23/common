package org.dbdoclet.jive.dialog.property;

import java.awt.Component;

import javax.swing.AbstractAction;

import org.dbdoclet.jive.widget.PropertyPanel;

public interface Property {

    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_SELECT = 3;
    public static final int TYPE_IMAGE = 4;
    public static final int TYPE_DATE = 5;
    public static final int TYPE_COLOR = 6;
    public static final int TYPE_FILE = 7;
    public static final int TYPE_INTEGER = 8;

    public abstract int getType();

    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract void setToolTip(String toolTip);

    public abstract String getToolTip();

    public abstract Object getValue();

    public abstract void setValue(Object value);

    public abstract Component getEditor(Object value);
    
    public abstract Component getRenderer(Object value);
    
    public abstract Object getEditorValue();

    public PropertyPanel getPanel();

    public void setPanel(PropertyPanel dialog);

	public abstract void setAction(AbstractAction action);

	public abstract AbstractAction getAction();

	public abstract void setEnabled(boolean enabled);

	public abstract boolean isEnabled();
}