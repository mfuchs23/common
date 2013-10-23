/** 
 * ### Copyright (C) 2009 Michael Fuchs
 * All Rights Reserved.               
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL: http://www.dbdoclet.org
 */
package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.dialog.AbstractDialog;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.unit.Length;

public class FoAttributeSetDialog extends AbstractDialog implements ActionListener, ChangeListener {

    public static final String COLOR_CHAR = "\u25FC";

    public final static int MONOSPACED = 2;
    public final static int STANDARD = 1;

    private static final long serialVersionUID = 1L;

    private FoAttributeSet attributeSet;
    private JButton cancelButton;
    private boolean canceled = false;
    private JSpinner endIndentSpinner;
    private JCheckBox frameBottomCheckBox;
    private JButton frameColorButton;
    private JCheckBox frameEnabledCheckBox;
    private JCheckBox frameLeftCheckBox;
    private GridPanel framePanel;
    private JCheckBox frameRightCheckBox;
    private JComboBox frameStyleComboBox;
    private JCheckBox frameTopCheckBox;
    private JSpinner frameWidthSpinner;
    private FontPanel fontPanel;
    private LinePanel linePanel;
    private boolean mapGenericFontNames = true;
    private JSpinner paddingSpinner;
    private ResourceBundle res;
    private JSpinner spaceAfterMaximumSpinner;
    private JSpinner spaceAfterMinimumSpinner;
    private JSpinner spaceAfterOptimumSpinner;
    private JSpinner spaceBeforeMaximumSpinner;
    private JSpinner spaceBeforeMinimumSpinner;
    private JSpinner spaceBeforeOptimumSpinner;
    private JCheckBox spacingEnabledCheckBox;
    private GridPanel spacingPanel;
    private JSpinner startIndentSpinner;
    private JSpinner textIndentSpinner;
    private JiveFactory jf;

	private String[] fontList;

    public FoAttributeSetDialog(Frame frame, FoAttributeSet attributeSet) {

        super(frame, "FontChooser", true);

        if (attributeSet == null) {
            attributeSet = new FoAttributeSet(new Font("Dialog", Font.PLAIN, 12), Color.black);
        }

        this.attributeSet = (FoAttributeSet) attributeSet.clone();
    }

    public static void main(String[] args) {

        JiveFactory.getInstance(Locale.getDefault());

        FoAttributeSet attributeSet = new FoAttributeSet(new Font("Dialog", Font.PLAIN, 12), Color.black);
        attributeSet.setFontEnabled(true);

        FoAttributeSetDialog dlg = new FoAttributeSetDialog(null, attributeSet);
        dlg.setFontList(new String[] { "Arial", "Courier", "Helvetica", "Times Roman", "monospaced", "sans-serif",
        "serif" });
        dlg.createGui();
        dlg.setVisible(true);

        FoAttributeSet aset = dlg.getAttributeSet();
        System.out.println("Aktiviert: " + aset.isActivated());
        System.out.println("Register Zeile: " + aset.isLineEnabled());
        System.out.println("Zeile - text-align: " + aset.getTextAlign());
        System.out.println("Zeile - wrap-option: " + aset.getWrapOption());
        System.out.println("Zeile - background: " + aset.getBackground());
        System.out.println("Zeile - line-height: " + aset.getLineHeight());
        System.out.println("Schriftart: " + aset.getFont());
        System.out.println("Abstand davor: " + aset.getSpaceBeforeOptimum());
        System.out.println("Abstand danach: " + aset.getSpaceAfterOptimum());
        System.out.println("Rahmenbreite: " + aset.getFrameWidth());
        System.out.println("Rahmenart: " + aset.getFrameStyle());
        System.out.println("Rahmenfarbe: " + aset.getFrameColor());
        System.out.println("Zeileneinzug 1. Zeile: " + aset.getTextIndent());
        System.out.println("Zeileneinzug Startbreich: " + aset.getStartIndent());
        System.out.println("Zeileneinzug Endbereich: " + aset.getEndIndent());
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();
        Object source = event.getSource();

        if (cmd == null) {
            return;
        }

        if (source != null && source == spacingEnabledCheckBox) {

            attributeSet.setSpacingEnabled(spacingEnabledCheckBox.isSelected());
            spacingPanel.setLock(spacingEnabledCheckBox.isSelected());
        }

        if (source != null && source == frameEnabledCheckBox) {

            attributeSet.setFrameEnabled(frameEnabledCheckBox.isSelected());
            framePanel.setLock(frameEnabledCheckBox.isSelected());
        }

        if (source != null && source == frameTopCheckBox) {
            attributeSet.setFrameTop(frameTopCheckBox.isSelected());
        }

        if (source != null && source == frameBottomCheckBox) {
            attributeSet.setFrameBottom(frameBottomCheckBox.isSelected());
        }

        if (source != null && source == frameRightCheckBox) {
            attributeSet.setFrameRight(frameRightCheckBox.isSelected());
        }

        if (source != null && source == frameLeftCheckBox) {
            attributeSet.setFrameLeft(frameLeftCheckBox.isSelected());
        }

        if (source != null && source == frameStyleComboBox) {
            attributeSet.setFrameStyle(frameStyleComboBox.getSelectedItem().toString());
        }

        if (source != null && source == frameColorButton) {

            Color color = JColorChooser.showDialog(this, ResourceServices.getString(res, "C_COLOR"), attributeSet
                    .getFrameColor());

            if (color != null) {
                attributeSet.setFrameColor(color);
                frameColorButton.setForeground(color);
            }
        }

        if (cmd.equalsIgnoreCase("cancel")) {
            setCanceled(true);
            setVisible(false);
        }

        if (cmd.equalsIgnoreCase("ok")) {

            setCanceled(false);
            setVisible(false);
        }
    }

    public void createGui() {

        jf = JiveFactory.getInstance();
        this.res = jf.getResourceBundle();

        GridPanel panel = new GridPanel();
        getRootPane().setContentPane(panel);

        JTabbedPane tabbedPane = new JTabbedPane();
        panel.addComponent(tabbedPane, Anchor.CENTER, Fill.BOTH);

        fontPanel = new FontPanel(this, attributeSet);
        
        if (fontList != null) {
        	fontPanel.setFontList(fontList);
        }
        
        JPanel spacingPanel = createSpacingPanel();
        JPanel framePanel = createFramePanel();
        linePanel = new LinePanel(this, attributeSet);

        if (attributeSet.isFontType()) {
            tabbedPane.addTab(ResourceServices.getString(res, "C_FONT"), fontPanel);
        }

        if (attributeSet.isSpacingType()) {
            tabbedPane.addTab(ResourceServices.getString(res, "C_SPACING"), spacingPanel);
        }

        if (attributeSet.isFrameType()) {
            tabbedPane.addTab(ResourceServices.getString(res, "C_FRAME"), framePanel);
        }

        if (attributeSet.isLineType()) {
            tabbedPane.addTab(ResourceServices.getString(res, "C_LINE"), linePanel);
        }

        JButton okButton = jf.createButton(null, ResourceServices.getString(res, "C_OK"));
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);
        panel.addButton(okButton);

        cancelButton = jf.createButton(null, ResourceServices.getString(res, "C_CANCEL"));
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        panel.addButton(cancelButton);

        panel.prepare();
        
        getRootPane().setDefaultButton(okButton);
        
        pack();
        center();

        
        spacingEnabledCheckBox.setSelected(attributeSet.isSpacingEnabled());
        frameEnabledCheckBox.setSelected(attributeSet.isFrameEnabled());
    }

    public FoAttributeSet getAttributeSet() {
        updateAttributeSet();
        return attributeSet;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public void setFontList(String[] fontList) {
        
    	this.fontList = fontList;
        
    	if (fontPanel != null) {
    		fontPanel.setFontList(fontList);
    	}
    }

    public void setFontSize(int size) {
        fontPanel.setFontSize(size);
    }

    public void stateChanged(ChangeEvent event) {

        JComponent comp = (JComponent) event.getSource();
        attributeSet.setChanged(true);

        if (comp == spaceBeforeMinimumSpinner) {
            attributeSet.setSpaceBeforeMinimum(FoAttributePanel.getDistance(spaceBeforeMinimumSpinner));
        }

        if (comp == spaceBeforeOptimumSpinner) {
            attributeSet.setSpaceBeforeOptimum(FoAttributePanel.getDistance(spaceBeforeOptimumSpinner));
        }

        if (comp == spaceBeforeMaximumSpinner) {
            attributeSet.setSpaceBeforeMaximum(FoAttributePanel.getDistance(spaceBeforeMaximumSpinner));
        }

        if (comp == spaceAfterMinimumSpinner) {
            attributeSet.setSpaceAfterMinimum(FoAttributePanel.getDistance(spaceAfterMinimumSpinner));
        }

        if (comp == spaceAfterOptimumSpinner) {
            attributeSet.setSpaceAfterOptimum(FoAttributePanel.getDistance(spaceAfterOptimumSpinner));
        }

        if (comp == spaceAfterMaximumSpinner) {
            attributeSet.setSpaceAfterMaximum(FoAttributePanel.getDistance(spaceAfterMaximumSpinner));
        }

        if (comp == paddingSpinner) {
            attributeSet.setPadding(FoAttributePanel.getDistance(paddingSpinner));
        }

        if (comp == textIndentSpinner) {
            attributeSet.setTextIndent(FoAttributePanel.getDistance(textIndentSpinner));
        }

        if (comp == startIndentSpinner) {
            attributeSet.setStartIndent(FoAttributePanel.getDistance(startIndentSpinner));
        }

        if (comp == endIndentSpinner) {
            attributeSet.setEndIndent(FoAttributePanel.getDistance(endIndentSpinner));
        }

        if (comp == frameWidthSpinner) {
            attributeSet.setFrameWidth(FoAttributePanel.getDistance(frameWidthSpinner));
        }
    }

    public void updateAttributeSet() {

        if (attributeSet != null && frameTopCheckBox != null) {

            String family = fontPanel.getFontFamily();

            if (family != null) {

                if (mapGenericFontNames) {

                    if (family.equalsIgnoreCase("times roman")) {
                        family = "Serif";
                    }

                    if (family.equalsIgnoreCase("helvetica") || family.equalsIgnoreCase("dialog")) {
                        family = "SansSerif";
                    }

                    if (family.equalsIgnoreCase("courier")) {
                        family = "Monospaced";
                    }
                }

                int style = fontPanel.getFontStyle();
                int size = fontPanel.getFontSize();
                
                Font font = new Font(family, style, size);
                attributeSet.setFont(font);
            }

            attributeSet.setSpaceBeforeMinimum(FoAttributePanel.getDistance(spaceBeforeMinimumSpinner));
            attributeSet.setSpaceBeforeOptimum(FoAttributePanel.getDistance(spaceBeforeOptimumSpinner));
            attributeSet.setSpaceBeforeMaximum(FoAttributePanel.getDistance(spaceBeforeMaximumSpinner));
            attributeSet.setSpaceAfterMinimum(FoAttributePanel.getDistance(spaceAfterMinimumSpinner));
            attributeSet.setSpaceAfterOptimum(FoAttributePanel.getDistance(spaceAfterOptimumSpinner));
            attributeSet.setSpaceAfterMaximum(FoAttributePanel.getDistance(spaceAfterMaximumSpinner));
            attributeSet.setPadding(FoAttributePanel.getDistance(paddingSpinner));

            attributeSet.setFrameWidth(FoAttributePanel.getDistance(frameWidthSpinner));
            attributeSet.setFrameTop(frameTopCheckBox.isSelected());
            attributeSet.setFrameBottom(frameBottomCheckBox.isSelected());
            attributeSet.setFrameRight(frameRightCheckBox.isSelected());
            attributeSet.setFrameLeft(frameLeftCheckBox.isSelected());

            attributeSet.setWrapOption(linePanel.getWrapOption());
            attributeSet.setTextAlign(linePanel.getTextAlign());
            attributeSet.setLineWidth(FoAttributePanel.getDistance(linePanel.getWidthSpinner()));
        }
    }

    private JPanel createFramePanel() {

        GridPanel panel = new GridPanel();

        panel.addSeparator(4, ResourceServices.getString(res, "C_FRAME"));
        panel.incrRow();

        frameEnabledCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_ACTIVATE"));
        frameEnabledCheckBox.setSelected(false);
        frameEnabledCheckBox.setActionCommand("frame-enabled");
        frameEnabledCheckBox.addActionListener(this);
        panel.addComponent(frameEnabledCheckBox);
        panel.incrRow();

        framePanel = new GridPanel();

        GridPanel frameSidesPanel = new GridPanel();
        frameSidesPanel.setBorder(BorderFactory.createTitledBorder(ResourceServices.getString(res, "C_VISIBLE_SIDES")));
        framePanel.addComponent(frameSidesPanel, Colspan.CS_4);
        framePanel.incrRow();

        frameTopCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_TOP"));
        frameTopCheckBox.setSelected(attributeSet.isFrameTop());
        frameTopCheckBox.addActionListener(this);
        frameSidesPanel.addComponent(frameTopCheckBox);

        frameLeftCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_LEFT"));
        frameLeftCheckBox.setSelected(attributeSet.isFrameLeft());
        frameLeftCheckBox.addActionListener(this);
        frameSidesPanel.addComponent(frameLeftCheckBox);

        frameBottomCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_BOTTOM"));
        frameBottomCheckBox.setSelected(attributeSet.isFrameBottom());
        frameBottomCheckBox.addActionListener(this);
        frameSidesPanel.addComponent(frameBottomCheckBox);

        frameRightCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_RIGHT"));
        frameRightCheckBox.setSelected(attributeSet.isFrameRight());
        frameRightCheckBox.addActionListener(this);
        frameSidesPanel.addComponent(frameRightCheckBox);

        frameWidthSpinner = jf.createDistanceSpinner(new Identifier("frame.width"));
        frameWidthSpinner.setValue(Length.valueOf(attributeSet.getFrameWidth()));
        frameWidthSpinner.addChangeListener(this);
        framePanel.addLabeledComponent(ResourceServices.getString(res, "C_WIDTH"), frameWidthSpinner);

        frameStyleComboBox = jf.createComboBox(new Identifier("fo.frame.style"), FoAttributeSet.FRAME_STYLE_LIST);
        frameStyleComboBox.setSelectedItem(attributeSet.getFrameStyle());
        frameStyleComboBox.addActionListener(this);
        framePanel.addLabeledComponent(ResourceServices.getString(res, "C_FRAME_STYLE"), frameStyleComboBox);

        framePanel.incrRow();

        frameColorButton = jf.createButton(null, COLOR_CHAR + " " + ResourceServices.getString(res, "C_COLOR") + "...");
        frameColorButton.addActionListener(this);
        framePanel.addComponent(frameColorButton, Colspan.CS_4, Rowspan.RS_1);

        panel.addComponent(framePanel);
        panel.incrRow();

        panel.addVerticalGlue();

        framePanel.setLock(attributeSet.isFrameEnabled());

        return panel;
    }

    private JPanel createSpacingPanel() {

        GridPanel panel = new GridPanel();

        panel.addSeparator(4, ResourceServices.getString(res, "C_SPACING"));

        spacingEnabledCheckBox = jf.createCheckBox(null, ResourceServices.getString(res, "C_ACTIVATE"));
        spacingEnabledCheckBox.setSelected(false);
        spacingEnabledCheckBox.setActionCommand("spacing-enabled");
        spacingEnabledCheckBox.addActionListener(this);
        panel.addComponent(spacingEnabledCheckBox);

        panel.incrRow();

        spacingPanel = new GridPanel();
        spacingPanel.addSeparator(4, ResourceServices.getString(res, "C_BEFORE"));

        spaceBeforeMinimumSpinner = jf.createDistanceSpinner(new Identifier("space-before.minimum"));
        spaceBeforeMinimumSpinner.setValue(Length.valueOf(attributeSet.getSpaceBeforeMinimum()));

        if (attributeSet.isSpaceBeforeMinimumVisible()) {
            spaceBeforeMinimumSpinner.addChangeListener(this);
            spacingPanel.addLabeledComponent("space-before.minimum", spaceBeforeMinimumSpinner);
            // spacingPanel.incrRow();
        }

        spaceBeforeOptimumSpinner = jf.createDistanceSpinner(new Identifier("space-before.optimum"));
        spaceBeforeOptimumSpinner.setValue(Length.valueOf(attributeSet.getSpaceBeforeOptimum()));
        spaceBeforeOptimumSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("space-before.optimum", spaceBeforeOptimumSpinner);
        spacingPanel.incrRow();

        spaceBeforeMaximumSpinner = jf.createDistanceSpinner(new Identifier("space-before.maximum"));
        spaceBeforeMaximumSpinner.setValue(Length.valueOf(attributeSet.getSpaceBeforeMaximum()));

        if (attributeSet.isSpaceBeforeMaximumVisible()) {
            spaceBeforeMaximumSpinner.addChangeListener(this);
            spacingPanel.addLabeledComponent("space-before.maximum", spaceBeforeMaximumSpinner);
            spacingPanel.incrRow();
        }

        spacingPanel.addSeparator(4, ResourceServices.getString(res, "C_AFTER"));

        spaceAfterMinimumSpinner = jf.createDistanceSpinner(new Identifier("space-after.minimum"));
        spaceAfterMinimumSpinner.setValue(Length.valueOf(attributeSet.getSpaceAfterMinimum()));

        if (attributeSet.isSpaceAfterMinimumVisible()) {
            spaceAfterMinimumSpinner.addChangeListener(this);
            spacingPanel.addLabeledComponent("space-after.minimum", spaceAfterMinimumSpinner);
            // spacingPanel.incrRow();
        }

        spaceAfterOptimumSpinner = jf.createDistanceSpinner(new Identifier("space-after.optimum"));
        spaceAfterOptimumSpinner.setValue(Length.valueOf(attributeSet.getSpaceAfterOptimum()));
        spaceAfterOptimumSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("space-after.optimum", spaceAfterOptimumSpinner);
        spacingPanel.incrRow();

        spaceAfterMaximumSpinner = jf.createDistanceSpinner(new Identifier("space-after.maximum"));
        spaceAfterMaximumSpinner.setValue(Length.valueOf(attributeSet.getSpaceAfterMaximum()));

        if (attributeSet.isSpaceAfterMaximumVisible()) {
            spaceAfterMaximumSpinner.addChangeListener(this);
            spacingPanel.addLabeledComponent("space-after.maximum", spaceAfterMaximumSpinner);
            spacingPanel.incrRow();
        }

        spacingPanel.incrRow();

        spacingPanel.addSeparator(4, ResourceServices.getString(res, "C_PADDING"));

        paddingSpinner = jf.createDistanceSpinner(new Identifier("padding"));
        paddingSpinner.setValue(Length.valueOf(attributeSet.getPadding()));
        paddingSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("padding", paddingSpinner);
        // spacingPanel.incrRow();

        textIndentSpinner = jf.createDistanceSpinner(new Identifier("text-indent"));
        textIndentSpinner.setValue(Length.valueOf(attributeSet.getTextIndent()));
        textIndentSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("text-indent", textIndentSpinner);
        spacingPanel.incrRow();
        
        startIndentSpinner = jf.createDistanceSpinner(new Identifier("start-indent"));
        startIndentSpinner.setValue(Length.valueOf(attributeSet.getStartIndent()));
        startIndentSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("start-indent", startIndentSpinner);
        // spacingPanel.incrRow();
        
        endIndentSpinner = jf.createDistanceSpinner(new Identifier("end-indent"));
        endIndentSpinner.setValue(Length.valueOf(attributeSet.getEndIndent()));
        endIndentSpinner.addChangeListener(this);
        spacingPanel.addLabeledComponent("end-indent", endIndentSpinner);
        spacingPanel.incrRow();
        
        panel.addComponent(spacingPanel);
        panel.incrRow();

        panel.addVerticalGlue();

        spacingPanel.setLock(attributeSet.isSpacingEnabled());

        return panel;
    }
}
