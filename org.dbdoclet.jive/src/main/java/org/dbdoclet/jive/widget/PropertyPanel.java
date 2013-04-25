/* 
 * ### Copyright (C) 2010 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.dialog.property.Property;

public class PropertyPanel extends GridPanel {

	class PropertyTableCellEditor extends AbstractCellEditor implements
			TableCellEditor {

		private static final long serialVersionUID = 1L;

		private Property property;

		public PropertyTableCellEditor(PropertyPanel dlg) {
			super();
		}

		public Object getCellEditorValue() {
			return property.getEditorValue();
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			JPanel panel = new JPanel();

			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			// gbc.insets = new Insets(0, 0, 0, 0);

			PropertyTableData model = (PropertyTableData) table.getModel();
			property = model.getPropertyAt(row);

			Component component = property.getEditor(value);
			component.setBackground(Color.white);
			component.setEnabled(property.isEnabled());
			panel.add(component, gbc);

			return panel;
		}
	}

	class PropertyTableCellRenderer extends JPanel implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		private final GridBagConstraints gbc;

		public PropertyTableCellRenderer() {

			setLayout(new GridBagLayout());
			gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			// gbc.insets = new Insets(1, 2, 1, 1);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			setOpaque(false);
			removeAll();

			PropertyTableData model = (PropertyTableData) table.getModel();
			Property property = model.getPropertyAt(row);
			setToolTipText(property.getToolTip());

			if (column == 0) {

				JLabel label = new JLabel();
				label.setFont(label.getFont().deriveFont(Font.PLAIN));
				label.setBackground(Color.white);
				label.setText(value.toString());
				label.setEnabled(property.isEnabled());
				add(label, gbc);
				return this;
			}

			Component comp = property.getRenderer(value);
			comp.setBackground(Color.white);
			add(comp, gbc);
			return this;
		}
	}

	class PropertyTableColumnData {

		public String title;
		public int width;
		public int alignment;

		public PropertyTableColumnData(String title, int width, int alignment) {

			this.title = title;
			this.width = width;
			this.alignment = alignment;
		}
	}

	class PropertyTableData extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private final PropertyTableColumnData columns[] = {
				new PropertyTableColumnData("Property", 100,
						SwingConstants.LEFT),
				new PropertyTableColumnData("Value", 100, SwingConstants.LEFT) };

		private final Vector<Property> vector;

		public PropertyTableData() {
			vector = new Vector<Property>();

		}

		public void add(Property property) {
			vector.add(property);
		}

		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public String getColumnName(int column) {
			return columns[column].title;
		}

		public PropertyTableColumnData[] getColumns() {
			return columns;
		}

		public Property getPropertyAt(int rowIndex) {

			if (rowIndex < 0 || rowIndex >= vector.size()) {
				return null;
			}

			Property property = vector.get(rowIndex);
			return property;
		}

		public int getRowCount() {
			return vector.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {

			if (rowIndex < 0 || rowIndex >= vector.size()) {
				return "";
			}

			Property property = vector.get(rowIndex);

			if (columnIndex == 0) {
				return property.getLabel();
			}

			logger.debug("[" + rowIndex + "," + columnIndex + "] "
					+ property.getValue());
			return property.getValue();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {

			if (columnIndex == 1) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {

			Property property = vector.get(rowIndex);
			logger.debug("[" + rowIndex + "," + columnIndex + "] Property="
					+ property + ", Value=" + value);
			property.setValue(value);
		}

		public void clear() {
			vector.clear();
		}
	}

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(PropertyPanel.class);

	private Font plainFont;
	private JTable propertyTable;
	private PropertyTableData propertyModel;
	private TableColumn labelColumn;
	private TableColumn widgetColumn;

	private HashMap<String, Property> propertyMap;

	public PropertyPanel() {
		init();
	}

	public void addProperty(Property property) {

		if (property != null) {
			addPropertyTableRow(property);
		}
	}

	private void addPropertyTableRow(Property property) {

		String label = property.getLabel();
		JLabel widget = new JLabel(label);
		Dimension widgetSize = widget.getPreferredSize();
		int width = (int) widgetSize.getWidth() + 10;
		logger.debug("width=" + width);

		int maxWidth = labelColumn.getWidth();

		if (width > maxWidth) {
			labelColumn.setWidth(width);
			labelColumn.setPreferredWidth(width);
		}

		property.setPanel(this);
		propertyModel.add(property);
		propertyModel.fireTableDataChanged();
	}

	public void init() {

		JLabel label = new JLabel();
		plainFont = label.getFont();
		plainFont = plainFont.deriveFont(Font.PLAIN);

		propertyModel = new PropertyTableData();
		propertyTable = new JTable();
		// propertyTable.setBackground(getBackground());
		propertyTable.setBackground(Color.white);
		propertyTable.setAutoCreateColumnsFromModel(false);
		propertyTable.setModel(propertyModel);
		propertyTable.setRowHeight(20);

		PropertyTableColumnData[] columns = propertyModel.getColumns();

		PropertyTableCellRenderer renderer = new PropertyTableCellRenderer();
		PropertyTableCellEditor editor = new PropertyTableCellEditor(this);
		labelColumn = new TableColumn(0, columns[0].width, renderer, editor);
		propertyTable.addColumn(labelColumn);

		widgetColumn = new TableColumn(1, columns[1].width, renderer, editor);
		propertyTable.addColumn(widgetColumn);

		addComponent(new JScrollPane(propertyTable), Anchor.WEST, Fill.BOTH);
	}

	@Override
	public void setVisible(boolean visible) {

		if (visible == true) {

			Dimension size = propertyTable.getPreferredSize();
			size.width = labelColumn.getPreferredWidth()
					+ widgetColumn.getPreferredWidth();
			logger.debug("width=" + size.width);

			propertyTable.setPreferredScrollableViewportSize(size);
		}

		super.setVisible(visible);
	}

	public void stopEditing() {

		int row = propertyTable.getEditingRow();
		int col = propertyTable.getEditingColumn();

		if (row >= 0 && col >= 0) {

			TableCellEditor editor = propertyTable.getCellEditor(row, col);
			editor.stopCellEditing();
		}
	}

	public void clear() {
		propertyModel = new PropertyTableData();
		propertyTable.setModel(propertyModel);
	}

	public void update() {

		if (propertyModel != null) {
			propertyModel.fireTableDataChanged();
		}
	}

	public void addProperty(String key, Property property) {

		addProperty(property);

		if (propertyMap == null) {
			propertyMap = new HashMap<String, Property>();
		}

		propertyMap.put(key, property);
	}

	public Property getProperty(String key) {

		if (propertyMap != null) {
			return propertyMap.get(key);
		}

		return null;
	}
}
