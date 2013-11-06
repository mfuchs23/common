package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
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

	private HashMap<String, FoAttributeSet> dataList;
	private final String[] fontFamilyList;
	private FontListModel model;

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

		if (source != null && source instanceof JButton) {

			String name = event.getActionCommand();
			FoAttributeSet attributeSet = dataList.get(name);

			FoAttributeSetDialog fontChooser = new FoAttributeSetDialog(
					JiveFactory.findParentFrame(this), attributeSet);

			if (fontFamilyList != null) {
				fontChooser.setFontList(fontFamilyList);
			}

			fontChooser.createGui();
			fontChooser.setVisible(true);

			if (fontChooser.isCanceled() == false) {
				FoAttributeSet as = fontChooser.getAttributeSet();
				
				if (as.isActivated()) {
					attributeSet.copy(as);
				} else {
					attributeSet.reset();
				}
			}

			model.fireTableDataChanged();
		}
	}

	private void init(FoAttributeSet[] attributeSetList) {

		int maxHeight = 12;

		dataList = new HashMap<String, FoAttributeSet>();

		if (attributeSetList != null) {

			for (FoAttributeSet attributeSet : attributeSetList) {

				JLabel label = attributeSet.toJLabel();
				int height = label.getPreferredSize().height;

				if (height > maxHeight) {
					maxHeight = height;
				}

				attributeSet.setChooser(this);
				dataList.put(attributeSet.getText(), attributeSet);
			}
		}

		model = new FontListModel(attributeSetList);

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

	private JButton createButton(FoAttributeSet preview) {

		JButton button = new JButton(preview.getText());
		button.setHorizontalAlignment(JButton.LEFT);
		button.setFocusPainted(true);
		button.setActionCommand(preview.getText());
		button.addActionListener(this);
		return button;
	}

	class FontListCellRenderer implements TableCellRenderer {

		private static final String defText = "Gallia est omnis divisa in partes tres, quarum unam incolunt Belgae, aliam Aquitani, tertiam qui ipsorum lingua Celtae, nostra Galli appellantur.";

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean cellHasFocus,
				int row, int column) {

			if (value instanceof FoAttributeSet) {

				FoAttributeSet preview = (FoAttributeSet) value;
				JLabel label = preview.toJLabel();

				if (column == FontListModel.COLUMN_INDEX_NAME) {

					JButton button = createButton(preview);
					return button;
				}

				if (column == FontListModel.COLUMN_INDEX_PREVIEW) {

					label.setText(defText);
					label.setFont(preview.getFont());
					label.setForeground(preview.getForeground());
					label.setBackground(preview.getBackground());

					if (preview.isActivated()) {	
						label.setEnabled(true);
					} else {
						label.setEnabled(false);
					}

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

		private static final int COLUMN_WIDTH_1 = 2500;
		private static final int COLUMN_WIDTH_2 = 2500;
		private static final long serialVersionUID = 1L;

		public FontListColumnModel() {

			TableColumn col = new TableColumn(FontListModel.COLUMN_INDEX_NAME);
			col.setPreferredWidth(COLUMN_WIDTH_1);
			addColumn(col);

			col = new TableColumn(FontListModel.COLUMN_INDEX_PREVIEW);
			col.setPreferredWidth(COLUMN_WIDTH_2);
			addColumn(col);
		}
	}

	class FontListEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 1L;
		private FoAttributeSet attributeSet;
		private final String[] fontFamilyList;
		private final FoAttributeSetChooser fontTable;
		private JButton button;
		private int row;

		public FontListEditor(FoAttributeSetChooser table,
				String[] fontFamilyList) {

			this.fontTable = table;
			this.fontFamilyList = fontFamilyList;
		}

		public Object getCellEditorValue() {
			return attributeSet;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			if (value instanceof FoAttributeSet
					&& column == FontListModel.COLUMN_INDEX_NAME) {

				attributeSet = (FoAttributeSet) value;
				this.row = row;

				button = createButton(attributeSet);

				// if (isSelected) {
				// button.setBackground(table.getSelectionBackground());
				// } else {
				// button.setBackground(table.getBackground());
				// }

				return button;
			}

			return null;
		}

		public void mouseClicked(MouseEvent event) {

			if (event.isConsumed() == false && event.getClickCount() == 2) {

				showFoAttributeSetDialog();
				// event.consume();
				fireEditingStopped();
				((FontListModel) fontTable.getModel()).fireTableCellUpdated(
						row, FontListModel.COLUMN_INDEX_PREVIEW);
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
	}

	class FontListModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public static final int COLUMN_INDEX_NAME = 0;
		public static final int COLUMN_INDEX_PREVIEW = 1;
		private static final int COLUMN_COUNT = 2;

		private final FoAttributeSet[] previews;

		FontListModel(FoAttributeSet[] previews) {
			this.previews = previews;
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
				return previews[row];
			}

			return null;
		}

		@Override
		public boolean isCellEditable(int row, int col) {

			if (col == COLUMN_INDEX_NAME) {
				return true;
			}

			return false;
		}
	}
}