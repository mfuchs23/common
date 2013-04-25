/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.UpperCaseTextField;
import org.dbdoclet.service.ResourceServices;

public class LicenseKeyDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JiveFactory widgetMap;
    private ResourceBundle res;
    private JTextComponent text;
    private UpperCaseTextField entry1;
    private UpperCaseTextField entry2;
    private UpperCaseTextField entry3;
    private UpperCaseTextField entry4;
    private UpperCaseTextField entry5;
    private JButton okButton;

    public LicenseKeyDialog(JFrame parent) {

        super(parent, true);

        widgetMap = JiveFactory.getInstance();

        res = widgetMap.getResourceBundle();

        setTitle(ResourceServices.getString(res, "C_LICENSE_KEY"));

        createUI();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int width = getWidth();
        int height = getHeight();

        setSize(width, height);
        setLocation((dimension.width - width) / 2, (dimension.height - height) / 2);
    }

    public String getLicenseKey() {

        String licenseKey = entry1.getText() + "-" + entry2.getText() + "-" + entry3.getText() + "-" + entry4.getText()
                + "-" + entry5.getText();

        return licenseKey;
    }

    public void failed() {

        text.setText(ResourceServices.getString(res, "C_ERROR_INVALID_LICENSE"));
        text.setForeground(Color.red);

        invalidate();
    }

    public void reset() {

        entry1.setText("");
        entry2.setText("");
        entry3.setText("");
        entry4.setText("");
        entry5.setText("");
    }

    private void createUI() {

        JPanel buttonPanel = createButtonPanel();
        JPanel licensePanel = createLicensePanel();

        JPanel panel = widgetMap.createPanel(new Identifier("jive.dialog.license-key"));
        getContentPane().add(panel);

        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EtchedBorder());

        GridBagConstraints gbc = new GridBagConstraints();

        int row = 0;
        int col = 0;

        gbc.ipady = 6;
        gbc.ipadx = 6;
        gbc.gridx = col;
        gbc.gridy = row++;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        text = widgetMap.createHelpArea(null, panel, ResourceServices.getString(res, "C_ENTER_LICENSE_KEY"));
        panel.add(text, gbc);

        gbc.gridx = col;
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        panel.add(licensePanel, gbc);

        gbc.gridx = col;
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        panel.add(buttonPanel, gbc);

        pack();
    }

    private JPanel createLicensePanel() {

        JPanel panel = widgetMap.createPanel(new Identifier("jive.license-key.license"));

        entry5 = widgetMap.createUpperCaseTextField(new Identifier("jive.license.value5"), 5);
        entry5.setMaxLength(5);
        entry5.setNext(okButton);

        entry4 = widgetMap.createUpperCaseTextField(new Identifier("jive.license.value4"), 5);
        entry4.setMaxLength(5);
        entry4.setNext(entry5);

        entry3 = widgetMap.createUpperCaseTextField(new Identifier("jive.license.value3"), 5);
        entry3.setMaxLength(5);
        entry3.setNext(entry4);

        entry2 = widgetMap.createUpperCaseTextField(new Identifier("jive.license.value2"), 5);
        entry2.setMaxLength(5);
        entry2.setNext(entry3);

        entry1 = widgetMap.createUpperCaseTextField(new Identifier("jive.license.value1"), 5);
        entry1.setMaxLength(5);
        entry1.setNext(entry2);

        JLabel label;
        GridBagConstraints gbc = new GridBagConstraints();

        int row = 0;
        int col = 0;

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        panel.add(entry1, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        label = widgetMap.createLabel(new Identifier("jive.license.label1"), "-");
        panel.add(label, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        panel.add(entry2, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        label = widgetMap.createLabel(new Identifier("jive.license.label2"), "-");
        panel.add(label, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        panel.add(entry3, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        label = widgetMap.createLabel(new Identifier("jive.license.label3"), "-");
        panel.add(label, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        panel.add(entry4, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        label = widgetMap.createLabel(new Identifier("jive.license.label4"), "-");
        panel.add(label, gbc);

        gbc.gridx = col++;
        gbc.gridy = row;
        gbc.insets = new Insets(2, 4, 2, 2);
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        panel.add(entry5, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {

        JPanel panel = widgetMap.createPanel(new Identifier("jive.license.buttons"));

        okButton = widgetMap.createButton(new Identifier("jive.license.ok"), ResourceServices.getString(res, "C_OK"));
        panel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        return panel;
    }
}
