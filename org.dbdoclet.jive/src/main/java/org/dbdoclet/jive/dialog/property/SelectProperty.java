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

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.dbdoclet.jive.model.LabelItem;

public class SelectProperty extends AbstractProperty implements ActionListener {

	private ArrayList<LabelItem> itemList;
	private JComboBox comboBox;

	private boolean editable;

	public SelectProperty(String label, ArrayList<LabelItem> itemList,
			String selected) {

		super(label, selected);

		this.itemList = itemList;
	}

	public SelectProperty(String label, String[] items, String selected) {

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

		comboBox = new JComboBox(new Vector<LabelItem>(getItemList()));
		comboBox.setSelectedItem(getSelectedItem());
		comboBox.setEditable(isEditable());
		comboBox.addActionListener(this);

		if (getAction() != null) {
			comboBox.setAction(getAction());
		}
		
		return comboBox;
	}

	@Override
	public Object getEditorValue() {

		if (comboBox == null) {
			return null;
		}

		Object obj = comboBox.getSelectedItem();
		
		if (obj == null) {
			return null;
		}
		
		if (obj instanceof LabelItem) {
			LabelItem item = (LabelItem) comboBox.getSelectedItem();
			return item.getValue();
		}

		return obj;
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

		JLabel label = new JLabel();
		label.setFont(getPlainFont());
		label.setText(getItemLabel());
		return label;
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

	private boolean isEditable() {
		return editable;
	}

	public void setItemList(String[] items) {
		
		itemList = new ArrayList<LabelItem>();
		
		for (String item : items) {
			itemList.add(new LabelItem(item ,item));
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
