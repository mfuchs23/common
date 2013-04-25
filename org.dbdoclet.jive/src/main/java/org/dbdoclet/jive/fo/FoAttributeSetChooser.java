package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.dbdoclet.jive.JiveFactory;

public class FoAttributeSetChooser extends JTable implements ActionListener {

	private static final long serialVersionUID = 1L;

	private ArrayList<RowData> dataList;
	private final String[] fontFamilyList;
	private FontListModel model;
	private JCheckBox[] switches;
	private JiveFactory wm;

	public FoAttributeSetChooser() {

		String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		FoAttributeSet[] attributeSetList = new FoAttributeSet[fontList.length];
		int index = 0;

		for (String fontFamily : fontList) {

			Font font = new Font(fontFamily, Font.PLAIN, 12);
			FoAttributeSet preview = new FoAttributeSet(fontFamily, font,
					Color.black);
			attributeSetList[index++] = preview;
		}

		this.fontFamilyList = null;
		init(attributeSetList);
	}

	public FoAttributeSetChooser(FoAttributeSet[] attributeSetList,
			String[] fontFamilyList) {

		this.fontFamilyList = fontFamilyList;
		init(attributeSetList);
	}

	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source != null && source instanceof JCheckBox) {

			JCheckBox onOff = (JCheckBox) source;

			int index = getOnOffIndex(onOff);
			if (index < 0 || index >= dataList.size()) {
				return;
			}

			FoAttributeSet attributeSet = dataList.get(index).getAttributeSet();

			if (onOff.isSelected()) {

				attributeSet.setActivated(true);

				FoAttributeSetDialog fontChooser = new FoAttributeSetDialog(
						JiveFactory.findParentFrame(this), attributeSet);

				if (fontFamilyList != null) {
					fontChooser.setFontList(fontFamilyList);
				}

				fontChooser.createGui();
				fontChooser.setVisible(true);

				if (fontChooser.isCanceled() == false) {
					attributeSet.copy(fontChooser.getAttributeSet());
				}
			
			} else {
				
				attributeSet.setActivated(false);
				attributeSet.reset();
			}

			model.fireTableDataChanged();
		}
	}

	/**
	 * Gibt den Index der Zeile zurück, die die angegebene Checkbox enthält.
	 * 
	 * @param onOff
	 * @return
	 */
	private int getOnOffIndex(JCheckBox onOff) {

		int index = 0;

		for (RowData data : dataList) {

			if (onOff == data.getOnOff()) {
				return index;
			}

			index++;
		}

		return -1;
	}

	private JCheckBox findOnOff(FoAttributeSet aset) {

		for (RowData data : dataList) {

			if (aset == data.getAttributeSet()) {
				return data.getOnOff();
			}
		}

		return null;
	}

	private void init(FoAttributeSet[] attributeSetList) {

		int maxHeight = 12;
		wm = JiveFactory.getInstance();

		switches = new JCheckBox[attributeSetList.length];
		dataList = new ArrayList<RowData>();

		if (attributeSetList != null) {

			int index = 0;
			for (FoAttributeSet attributeSet : attributeSetList) {

				JLabel label = attributeSet.toJLabel();
				int height = label.getPreferredSize().height;

				if (height > maxHeight) {
					maxHeight = height;
				}

				attributeSet.setChooser(this);
				switches[index] = wm.createCheckBox();
				switches[index].setSelected(attributeSet.isActivated());
				switches[index].addActionListener(this);
				dataList.add(new RowData(switches[index], attributeSet));
				index++;
			}
		}

		model = new FontListModel(attributeSetList, switches);

		setModel(model);
		setColumnModel(new FontListColumnModel());
		setRowHeight(maxHeight);
		setShowGrid(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(true);
		setCellSelectionEnabled(false);

		// setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		setTableHeader(null);
		setDefaultRenderer(FoAttributeSet.class, new FontListCellRenderer());
		setDefaultEditor(FoAttributeSet.class, new FontListEditor(this,
				fontFamilyList));
	}

	public void refreshAttributeSet(FoAttributeSet attributeSet) {

		if (attributeSet == null) {
			return;
		}

		JCheckBox onOff = findOnOff(attributeSet);

		if (onOff != null) {
			onOff.setSelected(attributeSet.isActivated());
		}
	}
}

class FontListCellRenderer implements TableCellRenderer {

	private static String defText = "Gallia est omnis divisa in partes tres, quarum unam incolunt Belgae, aliam Aquitani, tertiam qui ipsorum lingua Celtae, nostra Galli appellantur.";

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean cellHasFocus, int row, int column) {

		if (value instanceof FoAttributeSet) {

			FoAttributeSet preview = (FoAttributeSet) value;
			JLabel label = preview.toJLabel();

			if (column == FontListModel.COLUMN_INDEX_NAME) {

				// JCheckBox onOff = (JCheckBox) table.getValueAt(row, 0);
				// label.setEnabled(onOff.isSelected());
				return label;
			}

			if (column == FontListModel.COLUMN_INDEX_PREVIEW) {

				label.setFont(preview.getFont());
				label.setForeground(preview.getForeground());
				label.setBackground(preview.getBackground());
				label.setText(defText);
				return label;
			}

		}

		if (value instanceof JCheckBox) {

			JCheckBox onOff = (JCheckBox) value;
			onOff.setOpaque(true);
			return onOff;
		}

		return null;
	}

};

class FontListColumnModel extends DefaultTableColumnModel {

	private static final int COLUMN_WIDTH_3 = 1500;
	private static final int COLUMN_WIDTH_2 = 2500;
	private static final int COLUMN_WIDTH_1 = 25;
	private static final long serialVersionUID = 1L;

	public FontListColumnModel() {

		TableColumn col = new TableColumn(FontListModel.COLUMN_INDEX_ACTIVE);
		col.setPreferredWidth(COLUMN_WIDTH_1);
		col.setMinWidth(COLUMN_WIDTH_1);
		col.setMaxWidth(COLUMN_WIDTH_1);
		addColumn(col);

		col = new TableColumn(FontListModel.COLUMN_INDEX_NAME);
		col.setPreferredWidth(COLUMN_WIDTH_2);
		addColumn(col);

		col = new TableColumn(FontListModel.COLUMN_INDEX_PREVIEW);
		col.setPreferredWidth(COLUMN_WIDTH_3);
		addColumn(col);
	}
}

class FontListEditor extends AbstractCellEditor implements TableCellEditor,
		MouseListener {

	private static final long serialVersionUID = 1L;
	private FoAttributeSet attributeSet;
	private final String[] fontFamilyList;
	private final FoAttributeSetChooser fontTable;
	private JLabel label;
	private int row;

	public FontListEditor(FoAttributeSetChooser table, String[] fontFamilyList) {

		this.fontTable = table;
		this.fontFamilyList = fontFamilyList;
	}

	public Object getCellEditorValue() {
		return attributeSet;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		if (value instanceof FoAttributeSet
				&& column == FontListModel.COLUMN_INDEX_NAME) {

			attributeSet = (FoAttributeSet) value;
			this.row = row;
			label = attributeSet.toJLabel();
			label.addMouseListener(this);

			JCheckBox onOff = (JCheckBox) table.getValueAt(row, 0);
			label.setEnabled(onOff.isSelected());

			return label;
		}

		if (value instanceof JCheckBox) {
			return (Component) value;
		}

		return null;
	}

	public void mouseClicked(MouseEvent event) {

		if (event.isConsumed() == false && event.getClickCount() == 2) {

			showFoAttributeSetDialog();
			event.consume();
			fireEditingStopped();
			((FontListModel) fontTable.getModel()).fireTableCellUpdated(row,
					FontListModel.COLUMN_INDEX_PREVIEW);
		}
	}

	private void showFoAttributeSetDialog() {

		FoAttributeSetDialog fontChooser = new FoAttributeSetDialog(
				JiveFactory.findParentFrame(fontTable), attributeSet);

		if (fontFamilyList != null) {
			fontChooser.setFontList(fontFamilyList);
		}

		fontChooser.createGui();
		fontChooser.setVisible(true);

		if (fontChooser.isCanceled() == false) {
			attributeSet.copy(fontChooser.getAttributeSet());
		}
	}

	public void mouseEntered(MouseEvent e) {
		//
	}

	public void mouseExited(MouseEvent e) {
		//
	}

	public void mousePressed(MouseEvent e) {
		//
	}

	public void mouseReleased(MouseEvent e) {
		//
	}

}

class FontListModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public static final int COLUMN_INDEX_ACTIVE = 0;
	public static final int COLUMN_INDEX_NAME = 1;
	public static final int COLUMN_INDEX_PREVIEW = 2;
	private static final int COLUMN_COUNT = 3;

	private final FoAttributeSet[] previews;
	private final JCheckBox[] switches;

	FontListModel(FoAttributeSet[] previews, JCheckBox[] switches) {

		this.previews = previews;
		this.switches = switches;
	}

	@Override
	public Class<FoAttributeSet> getColumnClass(int c) {
		return FoAttributeSet.class;
	}

	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	public int getRowCount() {
		return previews.length;
	}

	public Object getValueAt(int row, int col) {

		switch (col) {
		case COLUMN_INDEX_NAME:
			return previews[row];
		case COLUMN_INDEX_PREVIEW:
			return previews[row].clone();
		case COLUMN_INDEX_ACTIVE:
			return switches[row];
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int row, int col) {

		if (col == COLUMN_INDEX_ACTIVE) {
			return true;
		}

		return switches[row].isSelected();
	}
}

class RowData {

	private FoAttributeSet attributeSet;
	private JCheckBox onOff;

	public RowData(JCheckBox onOff, FoAttributeSet attributeSet) {

		this.onOff = onOff;
		this.attributeSet = attributeSet;
	}

	public FoAttributeSet getAttributeSet() {
		return attributeSet;
	}

	public JCheckBox getOnOff() {
		return onOff;
	}
}
