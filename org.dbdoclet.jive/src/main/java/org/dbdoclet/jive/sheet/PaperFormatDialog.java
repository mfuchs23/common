package org.dbdoclet.jive.sheet;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.RegionFrame;
import org.dbdoclet.jive.dialog.DataDialog;
import org.dbdoclet.jive.dialog.DialogAction;
import org.dbdoclet.jive.sheet.PaperFormat.Orientation;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;

public class PaperFormatDialog extends DataDialog implements ActionListener,
		ChangeListener, SheetContainer {

	private static final long serialVersionUID = 1L;

	private PaperFormatComboBox formatComboBox;
	private JRadioButton orientationPortrait;
	private JRadioButton orientationLandscape;
	private JSpinner widthSpinner;
	private JSpinner heightSpinner;
	private JSpinner leftMarginSpinner;
	private JSpinner rightMarginSpinner;
	private JSpinner topMarginSpinner;
	private JSpinner bottomMarginSpinner;
	private Sheet sheet;
	private PaperFormat paperFormat;

	public PaperFormatDialog(Frame parent, String title) {

		super(parent, title);
		paperFormat = new PaperFormat(PaperFormat.A4);
	}

	public void createGui() {

		JiveFactory jive = JiveFactory.getInstance();

		GridPanel panel = getGridPanel();

		GridPanel dataPanel = new GridPanel();
		panel.addComponent(dataPanel, Anchor.NORTHWEST, Fill.NONE);

		GridPanel previewPanel = new GridPanel();
		panel.addComponent(previewPanel, Anchor.NORTHWEST, Fill.BOTH);

		dataPanel.startSubPanel(Fill.HORIZONTAL);
		dataPanel.addSeparator(1,
				ResourceServices.getString(res, JiveMessages.PAPER_FORMAT));

		dataPanel.startSubPanel();

		formatComboBox = new PaperFormatComboBox();
		formatComboBox.addActionListener(this);
		formatComboBox.setUserDefinedLabel(ResourceServices.getString(res,
				JiveMessages.USER_DEFINED));
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.FORMAT),
				formatComboBox);

		dataPanel.incrRow();

		widthSpinner = jive.createDistanceSpinner();
		widthSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.WIDTH),
				widthSpinner);

		dataPanel.incrRow();

		heightSpinner = jive.createDistanceSpinner();
		heightSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.HEIGHT),
				heightSpinner);

		dataPanel.startSubPanel();

		orientationPortrait = new JRadioButton(ResourceServices.getString(res,
				JiveMessages.PAPER_ORIENTATION_PORTRAIT));
		orientationPortrait.addActionListener(this);
		orientationPortrait.setSelected(true);
		dataPanel.addComponent(orientationPortrait);

		orientationLandscape = new JRadioButton(ResourceServices.getString(res,
				JiveMessages.PAPER_ORIENTATION_LANDSCAPE));
		orientationLandscape.addActionListener(this);
		dataPanel.addComponent(orientationLandscape);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(orientationPortrait);
		buttonGroup.add(orientationLandscape);

		dataPanel.startSubPanel(Fill.HORIZONTAL);
		dataPanel.addSeparator(1,
				ResourceServices.getString(res, JiveMessages.PAPER_MARGINS));

		dataPanel.startSubPanel();

		leftMarginSpinner = jive
				.createDistanceSpinner(Length.valueOf("1.0 cm"));
		leftMarginSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.LEFT),
				leftMarginSpinner);

		rightMarginSpinner = jive.createDistanceSpinner(Length
				.valueOf("1.0 cm"));
		rightMarginSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.RIGHT),
				rightMarginSpinner);

		dataPanel.incrRow();

		topMarginSpinner = jive.createDistanceSpinner(Length.valueOf("1.0 cm"));
		topMarginSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.TOP),
				topMarginSpinner);

		bottomMarginSpinner = jive.createDistanceSpinner(Length
				.valueOf("1.0 cm"));
		bottomMarginSpinner.addChangeListener(this);
		dataPanel.addLabeledComponent(
				ResourceServices.getString(res, JiveMessages.BOTTOM),
				bottomMarginSpinner);

		dataPanel.startSubPanel(Fill.BOTH);

		sheet = jive.createSheet(this);
		sheet.setMinimumSize(new Dimension(sheet.getDesktopWidth(), sheet
				.getDesktopHeight()));
		sheet.setPreferredSize(new Dimension(sheet.getDesktopWidth(), sheet
				.getDesktopHeight()));
		sheet.setAutoZoom(false);
		sheet.setFitToDesktopSize(true);
		sheet.setVisibleMargins(true);
		previewPanel.addComponent(sheet);

		JButton button = new JButton(ResourceServices.getString(res, "C_OK"));
		button.setMnemonic(KeyEvent.VK_O);
		button.setName("OkButton");
		button.setActionCommand("ok");
		button.addActionListener(this);
		panel.addButton(button);

		button = new JButton(ResourceServices.getString(res, "C_CANCEL"));
		button.setActionCommand("cancel");
		button.addActionListener(this);
		panel.addButton(button);
		panel.prepare();

		if (paperFormat != null) {

			if (formatComboBox.contains(paperFormat)) {
				formatComboBox.setSelectedItem(PaperFormat.valueOf(paperFormat
						.getSize()));
			}

			Length width = paperFormat.getWidth();
			Length height = paperFormat.getHeight();

			if (paperFormat.getOrientation() == Orientation.LANDSCAPE) {

				orientationLandscape.setSelected(true);
				widthSpinner.setValue(height);
				heightSpinner.setValue(width);
			}

			// } else {
			//
			// orientationPortrait.setSelected(true);
			// widthSpinner.setValue(paperFormat.getWidthAsText());
			// heightSpinner.setValue(paperFormat.getHeightAsText());
			// }

			topMarginSpinner.setValue(paperFormat.getMargins().getTop());
			rightMarginSpinner.setValue(paperFormat.getMargins().getRight());
			bottomMarginSpinner.setValue(paperFormat.getMargins().getBottom());
			leftMarginSpinner.setValue(paperFormat.getMargins().getLeft());

		}

		// formatComboBox.setSelectedItem(PaperFormat.A4);

		pack();
		center(getParentWindow());
	}

	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source == null) {
			return;
		}

		if (source == formatComboBox) {
			updateFormat(source);
			return;
		}

		if (source == orientationPortrait || source == orientationLandscape) {
			updateFormat(source);
			return;
		}

		String cmd = event.getActionCommand();

		if (cmd == null) {
			return;
		}

		if (cmd.equals("ok")) {
			setPerformedAction(DialogAction.OK);
			setVisible(false);
		}

		if (cmd.equals("cancel")) {
			setPerformedAction(DialogAction.CANCEL);
			setVisible(false);
		}
	}

	private void updateFormat(Object source) {

		if (source == formatComboBox) {

			PaperFormat selectedFormat = (PaperFormat) formatComboBox
					.getSelectedItem();

			if (selectedFormat != PaperFormat.USER_DEFINED) {

				if (orientationPortrait.isSelected()) {

					widthSpinner.removeChangeListener(this);
					widthSpinner.setValue(selectedFormat.getWidth());
					widthSpinner.addChangeListener(this);

					heightSpinner.removeChangeListener(this);
					heightSpinner.setValue(selectedFormat.getHeight());
					heightSpinner.addChangeListener(this);

				} else {

					widthSpinner.removeChangeListener(this);
					widthSpinner.setValue(selectedFormat.getHeight());
					widthSpinner.addChangeListener(this);

					heightSpinner.removeChangeListener(this);
					heightSpinner.setValue(selectedFormat.getWidth());
					heightSpinner.addChangeListener(this);
				}
			}

		}

		if (source == widthSpinner || source == heightSpinner) {

			Length width = (Length) widthSpinner.getValue();
			Length height = (Length) heightSpinner.getValue();

			PaperFormat paperFormat = PaperFormat.valueOf(new PaperSize(width,
					height));

			formatComboBox.removeActionListener(this);

			if (paperFormat.isUserDefined()) {
				formatComboBox.setSelectedItem(PaperFormat.USER_DEFINED);
			} else {
				formatComboBox.setSelectedItem(paperFormat);
			}

			formatComboBox.addActionListener(this);
		}

		if (source == orientationPortrait || source == orientationLandscape) {

			Length width = (Length) widthSpinner.getValue();
			Length height = (Length) heightSpinner.getValue();

			Length tmp = width;
			width = height;
			height = tmp;

			widthSpinner.removeChangeListener(this);
			widthSpinner.setValue(width);
			widthSpinner.addChangeListener(this);

			heightSpinner.removeChangeListener(this);
			heightSpinner.setValue(height);
			heightSpinner.addChangeListener(this);
		}

		Length width = (Length) widthSpinner.getValue();
		Length height = (Length) heightSpinner.getValue();

		sheet.setPaperSize(new PaperSize(width, height));

		validate();
		repaint();
	}

	public void stateChanged(ChangeEvent event) {

		Object source = event.getSource();

		if (source == null) {
			return;
		}

		if (source == leftMarginSpinner || source == rightMarginSpinner
				|| source == topMarginSpinner || source == bottomMarginSpinner) {

			sheet.setMargins(new RegionFrame((Length) topMarginSpinner
					.getValue(), (Length) rightMarginSpinner.getValue(),
					(Length) bottomMarginSpinner.getValue(),
					(Length) leftMarginSpinner.getValue()));
		}

		if (source == widthSpinner || source == heightSpinner) {
			updateFormat(source);
		}
	}

	public PaperFormat getPaperFormat() {

		PaperFormat paperFormat = new PaperFormat("", new PaperSize(
				Length.toMillimeter(widthSpinner.getValue()),
				Length.toMillimeter(heightSpinner.getValue()),
				LengthUnit.MILLIMETER));

		if (orientationLandscape.isSelected()) {
			paperFormat.setOrientation(PaperFormat.Orientation.LANDSCAPE);
		} else {
			paperFormat.setOrientation(PaperFormat.Orientation.PORTRAIT);
		}

		paperFormat.setMargins(sheet.getMargins());

		return paperFormat;
	}

	public void setPaperFormat(PaperFormat paperFormat) {

		this.paperFormat = paperFormat;
	}

	public void show(Sheet sheet) {
		//
	}
}
