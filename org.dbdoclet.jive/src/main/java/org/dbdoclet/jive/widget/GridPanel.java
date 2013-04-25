/*
 * Copyright (C) 2005-2012 Michael Fuchs 
 * All Rights Reserved.
 * 
 * Author: Michael Fuchs 
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL: http://www.dbdoclet.org
 */
package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.Border;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.border.BottomEtchedBorder;
import org.dbdoclet.jive.border.TopEtchedBorder;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ArrayList<JButton> buttonList;

	private GridCell cell;
	private GridBagConstraints constraints;
	private Insets insets = new Insets(4, 4, 4, 4);
	private int maxCol = 0;
	private int maxRow = 0;
	private int columnOffset = 0;
	private GridPanel rowPanel;
	private Image backgroundImage;

	private Color gradientFrom;

	private Color gradientTo;

	public GridPanel() {

		this(null, new Insets(4, 4, 4, 4));
	}

	public GridPanel(Insets insets) {

		this(null, insets);
	}

	public GridPanel(String title) {

		this(title, new Insets(4, 4, 4, 4));
	}

	public GridPanel(String title, Insets insets) {

		super(new GridBagLayout());

		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;

		if (insets != null) {
			this.insets = insets;
		}

		if (title != null) {
			setBorder(BorderFactory.createTitledBorder(title));
		}

		cell = new GridCell();
		buttonList = new ArrayList<JButton>();
	}

	public static void lock(Container container, boolean flag) {

		for (int i = 0; i < container.getComponentCount(); i++) {

			Component comp = container.getComponent(i);

			if (comp instanceof JList || comp instanceof JButton
					|| comp instanceof JCheckBox || comp instanceof JComboBox
					|| comp instanceof JSpinner || comp instanceof JTable
					|| comp instanceof JLabel) {

				comp.setEnabled(flag);
				continue;
			}

			if (comp instanceof Container) {

				lock((Container) comp, flag);
				// comp.setEnabled(flag);
				continue;
			}

			comp.setEnabled(flag);
		}
	}

	public void addButton(JButton button) {
		buttonList.add(button);
	}

	public void addComponent(JComponent comp) {
		addComponent(comp, Colspan.CS_1, Rowspan.RS_1, Anchor.WEST, Fill.NONE);
	}

	public void addComponent(JComponent comp, Anchor anchor) {
		addComponent(comp, Colspan.CS_1, Rowspan.RS_1, anchor, Fill.NONE, null);
	}

	public void addComponent(JComponent comp, Anchor anchor, Fill fill) {
		addComponent(comp, Colspan.CS_1, Rowspan.RS_1, anchor, fill, null);
	}

	public void addComponent(JComponent comp, Anchor anchor, Fill fill,
			Insets insets) {
		addComponent(comp, Colspan.CS_1, Rowspan.RS_1, anchor, fill, insets);
	}

	public void addComponent(JComponent comp, Colspan width) {
		addComponent(comp, width, Rowspan.RS_1, Anchor.WEST, Fill.NONE);
	}

	public void addComponent(JComponent comp, Colspan width, Anchor anchor) {
		addComponent(comp, width, Rowspan.RS_1, anchor, Fill.NONE);
	}

	public void addComponent(JComponent comp, Colspan width, Anchor anchor,
			Fill fill) {
		addComponent(comp, width, Rowspan.RS_1, anchor, fill);
	}

	public void addComponent(JComponent comp, Colspan width, Rowspan height) {
		addComponent(comp, width, height, Anchor.WEST, Fill.NONE);
	}

	public void addComponent(JComponent comp, Colspan width, Rowspan height,
			Anchor anchor) {
		addComponent(comp, width, height, anchor, Fill.NONE);
	}

	public void addComponent(JComponent comp, Colspan width, Rowspan height,
			Anchor anchor, Fill fill) {
		addComponent(comp, width, height, anchor, fill, null);
	}

	public void addComponent(JComponent comp, Colspan width, Rowspan height,
			Anchor anchor, Fill fill, Insets insets) {

		if (comp == null) {
			throw new IllegalArgumentException(
					"The argument comp may not be null!");
		}

		if (width == null) {
			throw new IllegalArgumentException(
					"The argument width must not be null!");
		}

		if (height == null) {
			throw new IllegalArgumentException(
					"The argument height must not be null!");
		}

		if (anchor == null) {
			throw new IllegalArgumentException(
					"The argument anchor must not be null!");
		}

		if (fill == null) {
			throw new IllegalArgumentException(
					"The argument fill must not be null!");
		}

		// comp.setBorder(BorderFactory.createLineBorder(Color.red));

		if (cell.getCol() > maxCol) {
			maxCol = cell.getCol();
		}

		if (cell.getRow() > maxRow) {
			maxRow = cell.getRow();
		}

		constraints.gridx = cell.getCol();
		constraints.gridy = cell.getRow();

		constraints.gridwidth = width.getValue();
		constraints.gridheight = height.getValue();

		constraints.anchor = anchor.getGbc();

		constraints.weightx = 0.0;
		constraints.weighty = 0.0;

		constraints.fill = fill.getGbc();

		switch (constraints.fill) {

		case GridBagConstraints.NONE:
			constraints.weightx = 0.0;
			constraints.weighty = 0.0;
			break;

		case GridBagConstraints.BOTH:
			constraints.weightx = 1.0;
			constraints.weighty = 1.0;
			break;

		case GridBagConstraints.HORIZONTAL:
			constraints.weightx = 1.0;
			constraints.weighty = 0.0;
			break;

		case GridBagConstraints.VERTICAL:
			constraints.weightx = 0.0;
			constraints.weighty = 1.0;
			break;

		default:
			constraints.weightx = 0.0;
			constraints.weighty = 0.0;
			break;
		}

		if (insets != null) {
			constraints.insets = insets;
		} else {
			constraints.insets = this.insets;
		}

		if (rowPanel != null) {

			rowPanel.addComponent(comp, anchor, fill);

		} else {

			add(comp, constraints);
			cell.incrCol(width.getValue());
		}
	}

	public void addComponent(JComponent comp, Fill fill) {
		addComponent(comp, Colspan.CS_1, Rowspan.RS_1, Anchor.WEST, fill, null);
	}

	public void addComponent(JComponent comp, Rowspan height, Anchor anchor) {
		addComponent(comp, Colspan.CS_1, height, anchor, Fill.NONE);
	}

	public void addComponent(JComponent comp, Rowspan height, Anchor anchor,
			Fill fill) {
		addComponent(comp, Colspan.CS_1, height, anchor, fill);
	}

	public void addHorizontalGlue() {

		JLabel label = new JLabel();
		addComponent(label, new Colspan(maxCol + 1), Rowspan.RS_1,
				Anchor.CENTER, Fill.HORIZONTAL);
	}

	public void addLabeledComponent(JLabel label, JComponent component) {
		addLabeledComponent(label, component, Fill.NONE);
	}

	public void addLabeledComponent(JLabel label, JComponent component,
			Fill fill) {

		if (label == null) {
			throw new IllegalArgumentException(
					"The argument label may not be null!");
		}

		if (component == null) {
			throw new IllegalArgumentException(
					"The argument comboBox must not be null!");
		}

		addComponent(label);
		addComponent(component, fill);
	}

	public void addLabeledComponent(String labelText, JComponent component) {

		JiveFactory jf = JiveFactory.getInstance();
		JLabel label = jf.createLabel(labelText, Font.PLAIN);

		addLabeledComponent(label, component, Fill.NONE);
	}

	public void addSeparator(int colspan) {
		addSeparator(colspan, "  ");
	}

	public void addSeparator(int colspan, String text) {

		JLabel label = new JLabel(text);

		Dimension size = label.getPreferredSize();
		size.setSize(size.getWidth() * 1.5, size.getHeight() * 1.5);
		label.setPreferredSize(size);

		label.setForeground(Color.darkGray);
		label.setBorder(new BottomEtchedBorder());

		addComponent(label, new Colspan(colspan), Rowspan.RS_1, Anchor.WEST,
				Fill.HORIZONTAL);
		incrRow();
	}

	public void addVerticalGlue() {

		JLabel label = new JLabel("");
		// label.setBorder(BorderFactory.createLineBorder(Color.blue));

		cell.nextRow();
		addComponent(label, new Colspan(maxCol + 1), Rowspan.RS_1,
				Anchor.CENTER, Fill.BOTH);
	}

	public void addVerticalGlue(int height) {

		JLabel label = new JLabel();

		Dimension dim = new Dimension(96, height);

		label.setMinimumSize(dim);
		label.setPreferredSize(dim);
		label.setMaximumSize(dim);

		cell.nextRow();
		addComponent(label, new Colspan(maxCol + 1), Rowspan.RS_1,
				Anchor.CENTER, Fill.HORIZONTAL);
	}

	public void appendToRow(int rowNum, JComponent comp) {

		Component[] compList = this.getComponents();

		int nr = 0;

		for (Component child : compList) {

			if (child instanceof GridPanel && nr == rowNum) {

				GridPanel rowPanel = (GridPanel) child;
				rowPanel.addComponent(comp);
			}

			nr++;
		}
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public GridCell getLastCell() {
		return cell;
	}

	public void incrRow() {

		if (rowPanel != null) {
			rowPanel.incrRow();
		} else {
			cell.nextRow();
			cell.setCol(columnOffset);
		}
		// rowPanel = null;
	}

	public void leaveSubPanel() {
		rowPanel = null;
	}

	public void lock() {
		lock(this, false);
	}

	public void prepare() {

		if (buttonList != null && buttonList.size() > 0) {

			addVerticalGlue();

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.setBorder(new TopEtchedBorder());

			for (JButton button : buttonList) {
				buttonPanel.add(button);
			}

			cell.setRow(maxRow + 1);
			cell.setCol(0);

			addComponent(buttonPanel, new Colspan(maxCol + 1), Rowspan.RS_1,
					Anchor.CENTER, Fill.HORIZONTAL);
		}
	}

	@Override
	public void removeAll() {
		super.removeAll();
		setCell(0, 0);
	}

	public void replaceComponent(JComponent oldComponent,
			JComponent newComponent) {

		GridBagLayout layout = (GridBagLayout) getLayout();
		GridBagConstraints gbc = layout.getConstraints(oldComponent);
		remove(oldComponent);
		add(newComponent, gbc);
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setColumnOffset(int columnOffset) {
		this.columnOffset = columnOffset;
		cell.setCol(columnOffset);
	}

	public void setGradientBackground(Color gradientFrom, Color gradientTo) {
		this.gradientFrom = gradientFrom;
		this.gradientTo = gradientTo;
	}

	public void setLock(boolean flag) {
		lock(this, flag);
	}

	/**
	 * Die Methode <code>setShowCellBorders</code> zeichnet um alle Komponenten des Panels vom Typ
	 * <code>JComponent</code> einen schwarzen Rahmen. Dabei werden alle Komponenten berücksichtigt, die sich zum
	 * Zeitpunkt des Aufrufs im Panel befinden. Komponten, die später zum Panel hinzugefügt werden erhalten keinen
	 * Rahmen. Die Methode dient hauptsächlich der Fehlersuche in komplexen Layouts.
	 * 
	 * @param showCellBorders
	 *            <code>boolean</code>
	 */
	public void setShowCellBorders(boolean showCellBorders) {

		Component[] compList = getComponents();
		Border border = null;

		if (showCellBorders == true) {
			border = BorderFactory.createLineBorder(Color.black, 2);
		}

		for (int i = 0; i < compList.length; i++) {
			if (compList[i] instanceof JComponent) {
				((JComponent) compList[i]).setBorder(border);
			}
		}
	}

	public void startSubPanel() {
		startSubPanel(Fill.NONE);
	}

	public void startSubPanel(Fill fill) {

		if (rowPanel != null) {
			rowPanel.addHorizontalGlue();
			rowPanel = null;
		}

		if (cell.getCol() != 0) {
			incrRow();
		}

		GridPanel panel = new GridPanel();
		// panel.setBorder(BorderFactory.createLineBorder(Color.green));
		addComponent(panel, Anchor.WEST, fill, new Insets(0, 0, 0, 0));

		rowPanel = panel;
	}

	public void unlock() {
		lock(this, true);
	}

	@Override
	protected void paintComponent(Graphics g) {

		int width = getWidth();
		int height = getHeight();

		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, null);
		
		} else if (gradientFrom != null && gradientTo != null) {
		
			GradientPaint paint = new GradientPaint(width/2, 0, gradientFrom, width/2, height,
					gradientTo, true);

			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(paint);
			g2d.fillRect(0, 0, width, height);
			
		} else {
			super.paintComponent(g);
		}
	}

	protected void setCell(int row, int col) {

		cell.setRow(row);
		cell.setCol(col);
	}
}
