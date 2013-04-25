package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class FontPanel extends FoAttributePanel implements ActionListener,
		ChangeListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private final FoAttributeSet attributeSet;
	private JButton fontColorButton;
	private JCheckBox fontEnabledCheckBox;
	private JList fontFamilySelect;
	private String[] fontList;
	private GridPanel paramPanel;
	private JTextField fontSize;
	private JSlider fontSizeSlider;
	private JList fontStyleSelect;
	private JiveFactory jf;
	private ResourceBundle res;
	private final FoAttributeSetDialog dialog;

	private JLabel label;

	private JButton backgroundColorButton;

	public FontPanel(FoAttributeSetDialog dialog, FoAttributeSet attributeSet) {

		this.dialog = dialog;
		this.attributeSet = attributeSet;
		createGui();

		fontFamilySelect.setSelectedValue(attributeSet.getFontFamily(), true);

		if (fontFamilySelect.isSelectionEmpty()) {
			fontFamilySelect.setSelectedValue("Times Roman", true);
		}

		fontEnabledCheckBox.setSelected(attributeSet.isFontEnabled());
	}

	public String getFontFamily() {
		return (String) fontFamilySelect.getSelectedValue();
	}

	public int getFontStyle() {

		int style = Font.PLAIN;

		LabelItem item = (LabelItem) fontStyleSelect.getSelectedValue();

		if (item != null) {
			style = (Integer) item.getValue();
		}

		return style;
	}

	public int getFontSize() {
		return fontSizeSlider.getValue();
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();
		Object source = event.getSource();

		if (cmd == null) {
			return;
		}

		if (cmd.equalsIgnoreCase("chooseColor")) {
			Color color = JColorChooser.showDialog(this,
					ResourceServices.getString(res, "C_COLOR"),
					attributeSet.getForeground());
			attributeSet.setChanged(true);
			attributeSet.setForeground(color);
			updateLabel();
		}

		if (cmd.equalsIgnoreCase("chooseBackgroundColor")) {
			Color color = JColorChooser.showDialog(this,
					ResourceServices.getString(res, "C_BACKGROUND_COLOR"),
					attributeSet.getBackground());
			attributeSet.setChanged(true);
			attributeSet.setBackground(color);
			updateLabel();
		}

		if (source != null && source == fontEnabledCheckBox) {

			attributeSet.setFontEnabled(fontEnabledCheckBox.isSelected());
			paramPanel.setLock(fontEnabledCheckBox.isSelected());
		}

	}

	public String[] getFontList() {

		if (fontList == null) {
			fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames();
		}

		return fontList;
	}

	public void setFontList(String[] fontList) {
		this.fontList = fontList;
	}

	public void setFontSize(int size) {
		fontSize.setText(String.valueOf(size));
	}

	public void stateChanged(ChangeEvent event) {

		JComponent comp = (JComponent) event.getSource();
		attributeSet.setChanged(true);

		if (comp == fontSizeSlider) {

			fontSize.setText(String.valueOf(fontSizeSlider.getValue()));
			dialog.updateAttributeSet();

			if (label != null) {
				updateLabel();
			}
		}

	}

	public void valueChanged(ListSelectionEvent event) {

		if (event.getValueIsAdjusting() == false) {

			dialog.updateAttributeSet();

			if (label != null) {
				updateLabel();
			}
		}
	}

	private void updateLabel() {

		label.setFont(attributeSet.getFont());
		label.setForeground(attributeSet.getForeground());
		label.setBackground(attributeSet.getBackground());
	}

	private JComponent createFontColorChooser() {

		fontColorButton = jf.createButton(null,
				ResourceServices.getString(res, "C_COLOR") + "...");
		fontColorButton.setActionCommand("chooseColor");
		fontColorButton.addActionListener(this);

		return fontColorButton;
	}

	private JComponent createBackgroundColorChooser() {

		backgroundColorButton = jf.createButton(null,
				ResourceServices.getString(res, "C_BACKGROUND_COLOR") + "...");
		backgroundColorButton.setActionCommand("chooseBackgroundColor");
		backgroundColorButton.addActionListener(this);

		return backgroundColorButton;
	}

	private JComponent createFontFamilyChooser() {

		GridPanel panel = new GridPanel();

		panel.addComponent(jf.createLabel(null,
				ResourceServices.getString(res, "C_FONT")));
		panel.incrRow();

		DefaultListModel model = new DefaultListModel();
		fontFamilySelect = jf.createList(null);
		fontFamilySelect.setModel(model);
		fontFamilySelect.addListSelectionListener(this);

		for (String fontName : getFontList()) {
			model.addElement(fontName);
		}

		JScrollPane scrollPane = new JScrollPane(fontFamilySelect);
		scrollPane.setPreferredSize(new Dimension(200, 210));
		panel.addComponent(scrollPane, Anchor.CENTER, Fill.HORIZONTAL);

		return panel;
	}

	private JComponent createFontSizeChooser() {

		GridPanel panel = new GridPanel();

		JLabel fontSizeLabel = jf.createLabel(null,
				ResourceServices.getString(res, "C_FONT_SIZE"));
		panel.addComponent(fontSizeLabel);

		fontSizeSlider = new JSlider(SwingConstants.HORIZONTAL, 5, 129,
				attributeSet.getFontSize());
		fontSizeSlider.setMajorTickSpacing(10);
		fontSizeSlider.setMinorTickSpacing(1);
		fontSizeSlider.setPaintTicks(true);
		fontSizeSlider.setPaintLabels(false);

		panel.addComponent(fontSizeSlider, Anchor.CENTER, Fill.HORIZONTAL);
		fontSizeSlider.addChangeListener(this);

		fontSize = jf.createTextField(new Identifier("font.size"), 4);
		fontSize.setHorizontalAlignment(SwingConstants.RIGHT);
		fontSize.setText(String.valueOf(attributeSet.getFontSize()));
		fontSize.setEditable(false);

		panel.addComponent(fontSize);
		return panel;
	}

	private JComponent createFontStyleChooser() {

		GridPanel panel = new GridPanel();

		panel.addComponent(jf.createLabel(null,
				ResourceServices.getString(res, "C_STYLE")));
		panel.incrRow();

		DefaultListModel model = new DefaultListModel();
		fontStyleSelect = jf.createList(null);
		fontStyleSelect.setModel(model);
		fontStyleSelect.addListSelectionListener(this);

		model.addElement(new LabelItem(ResourceServices.getString(res,
				"C_REGULAR"), Font.PLAIN));
		model.addElement(new LabelItem(ResourceServices.getString(res,
				"C_ITALIC"), Font.ITALIC));
		model.addElement(new LabelItem(ResourceServices
				.getString(res, "C_BOLD"), Font.BOLD));
		model.addElement(new LabelItem(ResourceServices.getString(res,
				"C_BOLD_ITALIC"), Font.BOLD | Font.ITALIC));

		JScrollPane scrollPane = new JScrollPane(fontStyleSelect);
		scrollPane.setPreferredSize(new Dimension(200, 210));
		panel.addComponent(scrollPane, Anchor.CENTER, Fill.HORIZONTAL);

		switch (attributeSet.getFontStyle()) {

		case Font.BOLD:
			fontStyleSelect.setSelectedIndex(2);
			break;

		case Font.ITALIC:
			fontStyleSelect.setSelectedIndex(1);
			break;

		case Font.BOLD | Font.ITALIC:
			fontStyleSelect.setSelectedIndex(3);
			break;

		default:
			fontStyleSelect.setSelectedIndex(0);
			break;
		}

		return panel;
	}

	private void createGui() {

		jf = JiveFactory.getInstance();
		this.res = jf.getResourceBundle();

		addSeparator(4, ResourceServices.getString(res, "C_FONT"));

		fontEnabledCheckBox = jf.createCheckBox(null,
				ResourceServices.getString(res, "C_ACTIVATE"));
		fontEnabledCheckBox.setSelected(false);
		fontEnabledCheckBox.setActionCommand("font-enabled");
		fontEnabledCheckBox.addActionListener(this);
		addComponent(fontEnabledCheckBox);

		incrRow();

		paramPanel = new GridPanel();
		addComponent(paramPanel);

		paramPanel.addComponent(createFontFamilyChooser(), Anchor.NORTHWEST,
				Fill.BOTH);
		paramPanel.addComponent(createFontStyleChooser(), Anchor.NORTHEAST,
				Fill.BOTH);
		paramPanel.incrRow();

		paramPanel.addComponent(createFontSizeChooser(), Colspan.CS_2,
				Anchor.CENTER, Fill.HORIZONTAL);

		paramPanel.incrRow();

		GridPanel colorPanel = new GridPanel();

		paramPanel.addComponent(colorPanel, Anchor.WEST, Fill.NONE);

		colorPanel.addComponent(createFontColorChooser(), Anchor.WEST,
				Fill.NONE);

		colorPanel.addComponent(createBackgroundColorChooser(), Anchor.WEST,
				Fill.NONE);

		paramPanel.incrRow();

		paramPanel.addComponent(createPreview(), Colspan.CS_2,
				Anchor.SOUTHWEST, Fill.NONE);

		addVerticalGlue();
		paramPanel.setLock(attributeSet.isFontEnabled());

		if (attributeSet.isFontColorEnabled() == false) {
			fontColorButton.setEnabled(false);
		}

		if (attributeSet.isFontStyleEnabled() == false) {
			fontStyleSelect.setEnabled(false);
		}
	}

	private JComponent createPreview() {

		GridPanel panel = new GridPanel();
		attributeSet.setFixedSize(new Dimension(500, 72));
		label = attributeSet.toJLabel();
		label.setForeground(attributeSet.getForeground());
		label.setBackground(attributeSet.getBackground());
		panel.addComponent(label, Anchor.SOUTHWEST, Fill.HORIZONTAL);
		return panel;
	}

}
