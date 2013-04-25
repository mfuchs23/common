/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.service.ResourceServices;

public class AboutDialog extends AbstractDialog {

    private static final long serialVersionUID = 1L;
    
    private JEditorPane editor;
    private ResourceBundle res;
    private JiveFactory wm;

    public AboutDialog(Frame frame, 
                       String title, 
                       URL resourceUrl,
                       String type) 
        throws IOException {
        
        this(frame, title, ResourceServices.readUrl(resourceUrl), type);
    }
    
    public AboutDialog(Frame frame, 
                       String title, 
                       String text,
                       String type) 
        throws IOException {

        super(frame, title, true);

        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);

        GridBagLayout layoutMgr = new GridBagLayout();

        JPanel textPanel = new JPanel(layoutMgr);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        getContentPane().add(textPanel, gridBagConstraints);

        JPanel buttonPanel = new JPanel();
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;
        getContentPane().add(buttonPanel, gridBagConstraints);

        editor = new JEditorPane();
        editor.setEditable(false);

        editor.setContentType(type);
        TextServices.setText(editor, text);

        JScrollPane editorScrollPane = new JScrollPane(editor);
        editorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(550, 300));

        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        textPanel.add(editorScrollPane, gridBagConstraints);

        JButton okButton = new JButton();
        buttonPanel.add(okButton);

        okButton.setText(ResourceServices.getString(res,"C_OK"));
        okButton.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent evt) {
                    
                    setVisible(false);
                }
            });

        getRootPane().setDefaultButton(okButton);

        pack();
        center();
    }
}
/*
 * $Log$
 */
