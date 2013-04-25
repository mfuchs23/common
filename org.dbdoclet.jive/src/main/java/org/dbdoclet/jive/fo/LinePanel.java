package org.dbdoclet.jive.fo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.unit.Length;

public class LinePanel extends FoAttributePanel implements ActionListener,
		ChangeListener {

	private static final long serialVersionUID = 1L;

	private JCheckBox lineEnabledCheckBox;
	private JComboBox wrapOptionComboBox;
	private JComboBox textAlignComboBox;
	private JSpinner widthSpinner;
	private JSpinner lineHeightSpinner;
	private ResourceBundle res;
	private JiveFactory jf;
	private final FoAttributeSet attributeSet;

	private GridPanel paramPanel;

	public LinePanel(FoAttributeSetDialog dialog, FoAttributeSet attributeSet) {
		this.attributeSet = attributeSet;
		createGui();
		lineEnabledCheckBox.setSelected(attributeSet.isLineEnabled());
	}

	private void createGui() {

		jf = JiveFactory.getInstance();
		this.res = jf.getResourceBundle();

		addSeparator(4, ResourceServices.getString(res, "C_LINE"));

		lineEnabledCheckBox = jf.createCheckBox(null,
				ResourceServices.getString(res, "C_ACTIVATE"));
		lineEnabledCheckBox.setSelected(false);
		lineEnabledCheckBox.setActionCommand("line-enabled");
		lineEnabledCheckBox.addActionListener(this);
		addComponent(lineEnabledCheckBox);

		incrRow();

		paramPanel = new GridPanel();

		wrapOptionComboBox = jf.createComboBox(
				new Identifier("fo.wrap.option"), new String[] { "inherit",
						"wrap", "no-wrap" });
		wrapOptionComboBox.setSelectedItem(attributeSet.getWrapOption());
		paramPanel.addLabeledComponent("wrap-option", wrapOptionComboBox);

		textAlignComboBox = jf.createComboBox(new Identifier(
				"fo.text-align.option"), new String[] { "left", "right",
				"center", "justify", "start", "end", "inherit" });
		
		textAlignComboBox.setSelectedItem(attributeSet.getTextAlign());
		paramPanel.addLabeledComponent("text-align", textAlignComboBox);

		paramPanel.incrRow();

		widthSpinner = jf.createDistanceSpinner(new Identifier("width"), false);
		widthSpinner.setValue(Length.valueOf(attributeSet.getFoWidth(), false));
		widthSpinner.addChangeListener(this);
		paramPanel.addLabeledComponent("width", widthSpinner);

		lineHeightSpinner = jf.createDistanceSpinner(new Identifier(
				"line-height"));
		lineHeightSpinner.setValue(Length.valueOf(attributeSet.getLineHeight(),
				false));
		lineHeightSpinner.addChangeListener(this);
		paramPanel.addLabeledComponent("line-height", lineHeightSpinner);

		paramPanel.setLock(attributeSet.isLineEnabled());
		addComponent(paramPanel);

		incrRow();
		addVerticalGlue();
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();
		Object source = event.getSource();

		if (cmd == null) {
			return;
		}

		if (source != null && source == lineEnabledCheckBox) {

			attributeSet.setLineEnabled(lineEnabledCheckBox.isSelected());
			paramPanel.setLock(lineEnabledCheckBox.isSelected());
		}

		if (source != null && source == wrapOptionComboBox) {
			attributeSet.setWrapOption(wrapOptionComboBox.getSelectedItem()
					.toString());
		}

		if (source != null && source == textAlignComboBox) {
			attributeSet.setTextAlign(textAlignComboBox.getSelectedItem()
					.toString());
		}

		// if (source != null && source == backgroundColorButton) {
		//
		// Color color = JColorChooser.showDialog(this, ResourceServices.getString(res, "C_BACKGROUND_COLOR"),
		// attributeSet.getFrameColor());
		//
		// if (color != null) {
		// attributeSet.setBackground(color);
		// backgroundColorButton.setBackground(color);
		// Color inverseColor = new Color(color.getRGB() ^ 0xffffff);
		// backgroundColorButton.setForeground(inverseColor);
		// }
		// }
	}

	public String getWrapOption() {
		return (String) wrapOptionComboBox.getSelectedItem();
	}

	public String getTextAlign() {
		return (String) textAlignComboBox.getSelectedItem();
	}

	public JSpinner getWidthSpinner() {
		return widthSpinner;
	}

	public void stateChanged(ChangeEvent event) {

		JComponent comp = (JComponent) event.getSource();
		attributeSet.setChanged(true);

		if (comp == widthSpinner) {
			attributeSet.setFoWidth(getDistance(widthSpinner));
		}

		if (comp == lineHeightSpinner) {
			attributeSet.setLineHeight(getDistance(lineHeightSpinner));
		}
	}

}
