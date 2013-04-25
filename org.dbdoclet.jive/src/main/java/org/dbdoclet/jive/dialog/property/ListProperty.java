/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.dbdoclet.jive.model.LabelItem;

public class ListProperty extends AbstractProperty implements ActionListener {

	private ArrayList<LabelItem> itemList;
	private JList list;

	private boolean editable;

	public ListProperty(String label, ArrayList<LabelItem> itemList,
			String selected) {

		super(label, selected);
		this.itemList = itemList;
	}

	public ListProperty(String label, String[] items, String selected) {

		super(label, selected);

		itemList = new ArrayList<LabelItem>();

		if (items != null) {

			for (int i = 0; i < items.length; i++) {
				itemList.add(new LabelItem(items[i], items[i]));
			}
		}
	}

	@Override
	public Component getEditor(Object value) {

		if (isEnabled() == false) {
			return new JLabel("");
		}

		list = new JList(new Vector<LabelItem>(getItemList()));
		list.setCellRenderer(new ListCheckBoxCellRenderer());
		return list;
	}

	@Override
	public Object getEditorValue() {

		if (list == null) {
			return null;
		}

		return null;
	}

	public String getItemLabel() {

		LabelItem item = getSelectedItem();

		if (item != null) {
			return item.getLabel();
		}

		return "";
	}

	public ArrayList<LabelItem> getItemList() {

		return itemList;
	}

	@Override
	public Component getRenderer(Object value) {

		JList list = new JList(new Vector<LabelItem>(getItemList()));
		list.setVisibleRowCount(getItemList().size());
		list.setCellRenderer(new ListCheckBoxCellRenderer());

		return new JScrollPane(list);
	}

	public LabelItem getSelectedItem() {

		String selected = (String) super.getValue();

		LabelItem item;
		Iterator<LabelItem> iterator = itemList.iterator();

		while (iterator.hasNext()) {

			item = iterator.next();

			if (item.equals(selected)) {
				return item;
			}
		}

		return null;
	}

	@Override
	public int getType() {
		return TYPE_SELECT;
	}

	@Override
	public Object getValue() {

		LabelItem item = getSelectedItem();

		if (item != null) {
			return item.getValue();
		}

		return "";
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setItemList(ArrayList<LabelItem> itemList) {
		this.itemList = itemList;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setItemList(String[] items) {

		itemList = new ArrayList<LabelItem>();

		for (String item : items) {
			itemList.add(new LabelItem(item, item));
		}

		getPanel().update();
	}

	public void actionPerformed(ActionEvent e) {

		JComboBox comboBox = (JComboBox) e.getSource();
		Object obj = comboBox.getSelectedItem();

		if (obj instanceof String) {

			LabelItem item = new LabelItem((String) obj, obj);

			if (itemList.contains(item) == false) {
				itemList.add(item);
			}

			setValue(item.getValue());
		}

		if (obj instanceof LabelItem) {
			setValue(((LabelItem) obj).getValue());
		}

	}
}

class ListCheckBoxCellRenderer implements ListCellRenderer {

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		LabelItem item = (LabelItem) value;
		
		JCheckBox checkbox = new JCheckBox(item.getLabel());

		if (item.getValue() instanceof Boolean) {
			checkbox.setSelected((Boolean) item.getValue());
		}
		
		checkbox.setBackground(isSelected ? list.getSelectionBackground()
				: list.getBackground());
		checkbox.setForeground(isSelected ? list.getSelectionForeground()
				: list.getForeground());
		checkbox.setEnabled(true);
		checkbox.setFont(list.getFont());
		checkbox.setFocusPainted(false);
		checkbox.setBorderPainted(true);
		checkbox.setBorder(isSelected ? UIManager
				.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
		return checkbox;
	}
}