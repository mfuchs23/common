package org.dbdoclet.jive.dialog.settings.jdk;

import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.settings.ColumnData;
import org.dbdoclet.service.ResourceServices;

public class JdkTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JiveFactory wm;
    private ResourceBundle res;
    private ColumnData columns[];
    private Vector<JdkData> vector;
    
    public JdkTableModel() {
        
        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

        columns = new ColumnData[2];
        
        columns[0] = new ColumnData(ResourceServices.getString(res,"C_NAME"), 100, SwingConstants.LEFT);
        columns[1] = new ColumnData(ResourceServices.getString(res,"C_JAVA_HOME"), 100, SwingConstants.LEFT);

        vector = new Vector<JdkData>();
    }
    
    public int getColumnCount() {
        
        return columns.length;
    }
    
    public ColumnData[] getColumnData() {
        
        return columns;
    }
    
    @Override
    public String getColumnName(int index) {

        String title = columns[index].title;
        return title;
    }
    
    public void addLabeledComponent(JdkData data) {

        vector.add(data);
        fireTableRowsInserted(vector.size() - 1, vector.size() - 1);
    }

    public void removeEntry(int row) {

        if (row >= 0 && row < vector.size()) {

            vector.removeElementAt(row);
            fireTableRowsDeleted(row, row);
        }
    }
    
    public JdkData getEntry(int index) {

        JdkData data = vector.get(index); 
        return data;
    }
    
    public void reloadRow(int index) {
        
        fireTableRowsUpdated(index, index);
    }
    
    public void reload() {
        
        fireTableRowsUpdated(0, vector.size() - 1);
    }
    
    public int getRowCount() {
        
        if (vector == null) {
            return 0;
        }
        
        return vector.size();
    }
    
    public Object getValueAt(int row, int column) {
        
        if (row < 0 || row >= getRowCount()) {
            return "";
        }
        
        JdkData data = vector.elementAt(row);
        
        switch (column) {
            
        case 0: return data.getLabel();
        case 1: return data.getJavaHome();
            
        }
        
        return "";
    }

    @Override
    public void setValueAt(Object value, int row, int col) {

        if (value != null 
            && col == 0
            && row >= 0
            && row < vector.size()) {

            String buffer = value.toString();
            
            if (buffer.trim().length() > 0) {

                JdkData data = vector.get(row);
                data.setLabel(buffer.trim());
            }
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        
        if (col == 0) {
            return true;
        }

        return false;
    }
}

