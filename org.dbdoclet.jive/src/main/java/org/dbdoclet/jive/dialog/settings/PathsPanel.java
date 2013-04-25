/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

public class PathsPanel extends AbstractSettingsPanel 
    implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JiveFactory wm;
    private ResourceBundle res;
    private JButton editButton;
    private JButton standardButton;
    private JTable table;

    public PathsPanel(PathTableData data) {

        if (data == null) {
            throw new IllegalArgumentException("The argument data must not be null!");
        }

        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

        setName("settings.panel.repositories");
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        table = wm.createTable(new Identifier("paths"));
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(data);
        table.addMouseListener(new PathsPanelMouseListener());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer renderer;
        TableColumn tableColumn;

        ColumnData[] columns = data.getColumnData();

        for (int i = 0; i < data.getColumnCount(); i++) {
            
            renderer = new DefaultTableCellRenderer();
            
            renderer.setHorizontalAlignment(columns[i].alignment);
            tableColumn = new TableColumn(i, columns[i].width, renderer, null);
            table.addColumn(tableColumn);
        }

        JTableHeader header = table.getTableHeader();
        header.setUpdateTableInRealTime(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setBackground(table.getBackground());
        scrollPane.getViewport().add(table);

        add(scrollPane, gbc);

        GridPanel buttonPanel = new GridPanel();
        
        editButton = wm.createButton(new Identifier("edit"), ResourceServices.getString(res,"C_EDIT"));
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);
        buttonPanel.addComponent(editButton);

        standardButton = wm.createButton(new Identifier("standard"), ResourceServices.getString(res,"C_STANDARD"));
        standardButton.setActionCommand("standard");
        standardButton.addActionListener(this);
        buttonPanel.addComponent(standardButton);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        
        add(buttonPanel, gbc);
    }
    
    public String getNamespace() {
        return null;
    }
    
    @Override
    public Properties getProperties() {

        if (table == null) {
            throw new IllegalStateException("The field table must not be null!");
        }
        
        PathTableData model = (PathTableData) table.getModel();
        PathData entry;

        Properties properties = new Properties();

        for (int i = 0; i < model.getRowCount(); i++) {

            entry = model.getEntry(i);
            properties.put(entry.getKey(), StringServices.replace(entry.getFilePath(), File.separator, "/"));
        }

        return properties;
    }

    @Override
    public void setProperties(Properties properties) {

        if (table == null) {
            throw new IllegalStateException("The field table must not be null!");
        }
        
        if (properties == null) {
            throw new IllegalArgumentException("The argument properties must not be null!");
        }

        PathTableData model = (PathTableData) table.getModel();
        PathData entry;
        String property;

        for (int i = 0; i < model.getRowCount(); i++) {

            entry = model.getEntry(i);
            property = properties.getProperty(entry.getKey());

            if (property != null) {
                entry.setFile(new File(property));
            }
        }

        model.reload();
    }

    public void actionPerformed(ActionEvent event) {

        if (table == null) {
            return;
        }

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            
            if (event.getSource() == editButton) {
                 editRow(selectedRow);
            }

            if (event.getSource() == standardButton) {

                PathTableData model = (PathTableData) table.getModel();
                PathData entry = model.getEntry(selectedRow);
                entry.setFile(entry.getStandard());
                model.reloadRow(selectedRow);
            }
        }
    }

    private void editRow(int rowIndex) {

        if (table == null) {
            throw new IllegalStateException("The field table must not be null!");
        }

        if (rowIndex < 0 || rowIndex >= table.getRowCount()) {
            return;
        }

        PathTableData model = (PathTableData) table.getModel();
        PathData entry = model.getEntry(rowIndex);
        File file = entry.getFile();

        JFileChooser fc = new JFileChooser(file);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);           
        int rc = fc.showOpenDialog(this);
        
        if (rc == JFileChooser.APPROVE_OPTION) {
            
            file = fc.getSelectedFile();
            entry.setFile(file);
            model.reloadRow(rowIndex);
        }
    }

    class PathsPanelMouseListener extends MouseAdapter {

        @Override
	public void mouseClicked(MouseEvent event) {

            if (event.getClickCount() == 2) {

                int rowIndex = table.rowAtPoint(event.getPoint());

                if (rowIndex != -1) {
                    editRow(rowIndex);
                }
            }
        }
    }
}
