package org.dbdoclet.jive.dialog.settings;

import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class PathTableData extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JiveFactory wm;
    private ResourceBundle res;
    private ColumnData columns[];
    private Vector<PathData> vector;
    
    public PathTableData() {
        
        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

        columns = new ColumnData[2];
        
        columns[0] = new ColumnData(ResourceServices.getString(res,"C_NAME"), 100, SwingConstants.LEFT);
        columns[1] = new ColumnData(ResourceServices.getString(res,"C_PATH"), 100, SwingConstants.LEFT);

        vector = new Vector<PathData>();
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
    
    public void addLabeledComponent(PathData data) {
        vector.add(data);
    }
    
    public PathData getEntry(int index) {

        PathData data = vector.get(index); 
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
        
        PathData data = vector.elementAt(row);
        
        switch (column) {
            
        case 0: return data.getLabel();
        case 1: return data.getFile();
            
        }
        
        return "";
    }

}

