/* 
 * ### Copyright (C) 2005-2009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;

public class HelpPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    public HelpPanel(String msg) {

	Dimension preferredSize = new Dimension(250, 180);
	JiveFactory wm = JiveFactory.getInstance();

	JTextComponent helpArea = wm.createHelpArea(null, this, msg);
	
	helpArea.setMinimumSize(preferredSize);
	helpArea.setPreferredSize(preferredSize);
	// helpArea.setBorder(BorderFactory.createLineBorder(Color.black));
	
	JScrollPane scrollPane = new JScrollPane(helpArea);
	scrollPane.setMinimumSize(preferredSize);
	scrollPane.setPreferredSize(preferredSize);
	scrollPane.setBorder(BorderFactory.createEmptyBorder());
	
	addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
    }
}
