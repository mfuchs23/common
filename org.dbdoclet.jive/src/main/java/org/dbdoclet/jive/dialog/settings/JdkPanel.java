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
import java.io.File;
import java.util.Enumeration;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.settings.jdk.JdkChooser;
import org.dbdoclet.jive.dialog.settings.jdk.JdkData;
import org.dbdoclet.jive.dialog.settings.jdk.JdkTableModel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

public class JdkPanel extends AbstractSettingsPanel implements ActionListener {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(JdkPanel.class);

	private final JiveFactory wm;
	private final ResourceBundle res;
	private final JButton newButton;
	private final JButton deleteButton;
	private final JTable table;
	private final JdkTableModel model;

	public JdkPanel() {

		wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();

		setName("settings.panel.jdk");
		setBorder(new EmptyBorder(4, 4, 4, 4));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		model = new JdkTableModel();

		table = wm.createTable(null);
		table.setAutoCreateColumnsFromModel(false);
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableCellRenderer renderer;
		TableColumn tableColumn;

		ColumnData[] columns = model.getColumnData();

		for (int i = 0; i < model.getColumnCount(); i++) {

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

		newButton = wm.createButton(null,
				ResourceServices.getString(res, "C_NEW"));
		newButton.setActionCommand("new");
		newButton.addActionListener(this);
		buttonPanel.addComponent(newButton);

		deleteButton = wm.createButton(null,
				ResourceServices.getString(res, "C_DELETE"));
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);
		buttonPanel.addComponent(deleteButton);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		add(buttonPanel, gbc);
	}

	public String getNamespace() {
		return "java.jdk.";
	}

	@Override
	public Properties getProperties() {

		if (table == null) {
			throw new IllegalStateException("The field table must not be null!");
		}

		JdkTableModel model = (JdkTableModel) table.getModel();
		JdkData entry;

		Properties properties = new Properties();

		for (int i = 0; i < model.getRowCount(); i++) {

			entry = model.getEntry(i);
			properties.put("java.jdk." + entry.getLabel(),
					FileServices.normalizePath(entry.getJavaHomePath()));
		}

		return properties;
	}

	@Override
	public void setProperties(Properties properties) {

		if (table == null) {
			throw new IllegalStateException("The field table must not be null!");
		}

		if (properties == null) {
			throw new IllegalArgumentException(
					"The argument properties must not be null!");
		}

		JdkTableModel model = (JdkTableModel) table.getModel();
		String name;
		String value;

		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {

			name = (String) e.nextElement();

			if (name.startsWith("java.jdk.")) {

				value = properties.getProperty(name);
				name = StringServices.cutPrefix(name, "java.jdk.");

				JdkData data = new JdkData(name, new File(value));
				model.addLabeledComponent(data);
			}
		}

		model.reload();
	}

	public void actionPerformed(ActionEvent event) {

		if (table == null) {
			return;
		}

		String cmd = event.getActionCommand();

		if (cmd.equals("new") == true) {
			addJdk();
			return;
		}

		if (cmd.equals("delete") == true) {

			int selectedRow = table.getSelectedRow();

			if (selectedRow != -1) {
				removeJdk(selectedRow);
			}
		}
	}

	private void addJdk() {

		if (table == null) {
			throw new IllegalStateException("The field table must not be null!");
		}

		JdkTableModel model = (JdkTableModel) table.getModel();

		JdkChooser fc = new JdkChooser(new File("/"), res);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int rc = fc.showOpenDialog(this);

		if (rc == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();

			logger.debug("file=" + file);

			JdkData jdkData = new JdkData(file.getName(), file);
			model.addLabeledComponent(jdkData);
		}
	}

	private void removeJdk(int row) {

		if (table == null) {
			throw new IllegalStateException("The field table must not be null!");
		}

		JdkTableModel model = (JdkTableModel) table.getModel();
		model.removeEntry(row);
	}
}
