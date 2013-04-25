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
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.progress.ReportItem;
import org.dbdoclet.service.ArrayServices;
import org.dbdoclet.service.ResourceServices;

public class ReportDialog extends AbstractDialog {

    private static final long serialVersionUID = 1L;

    private JEditorPane info;
    private JList eventListBox;
    private GridPanel panel;
    private URL backgroundImageUrl;
    private int width = 700;

    private int errors = 0;
    private int warnings = 0;
    private int successful = 0;
    private int total = 0;
    private Object[] listData;

    public ReportDialog(Frame parent, String title, String msg, List<ReportItem> eventList) throws IOException {

	this(parent, title, msg, null, eventList);
    }

    public ReportDialog(Frame parent, String title, String msg, URL backgroundImageUrl, List<ReportItem> eventList)
	    throws IOException {

	super(parent, title, true);

	if (title == null) {
	    throw new IllegalArgumentException("The argument title must not be null!");
	}

	if (msg == null) {
	    throw new IllegalArgumentException("The argument msg must not be null!");
	}

	if (eventList == null) {
	    throw new IllegalArgumentException("The argument eventList must not be null!");
	}

	this.backgroundImageUrl = backgroundImageUrl;

	init(parent, title, msg, eventList);
    }

    private void processEventList(List<ReportItem> eventList) {

	Object obj;
	ReportItem item;

	ArrayList<ReportItem> errorList = new ArrayList<ReportItem>();
	ArrayList<ReportItem> warningList = new ArrayList<ReportItem>();
	ArrayList<ReportItem> successList = new ArrayList<ReportItem>();

	Iterator<ReportItem> iterator = eventList.iterator();

	while (iterator.hasNext()) {

	    obj = iterator.next();

	    if (obj instanceof ReportItem) {

		item = (ReportItem) obj;

		if (item.isError()) {
		    errorList.add(item);
		}

		if (item.isWarning()) {
		    warningList.add(item);
		}

		if (item.isSuccess()) {
		    successList.add(item);
		}
	    }
	}

	listData = errorList.toArray();
	listData = ArrayServices.concat(listData, warningList.toArray());
	listData = ArrayServices.concat(listData, successList.toArray());

	errors = errorList.size();
	warnings = warningList.size();
	successful = successList.size();
	total = listData.length;
    }

    private void init(Frame parent, String title, String msg, List<ReportItem> eventList) throws IOException {

	processEventList(eventList);

	JLabel label;
	URL iconUrl;
	ImageIcon icon;

	if (errors > 0) {
	    iconUrl = ResourceServices.getResourceAsUrl("/images/errorBoxHeaderBackground.jpg", ReportDialog.class
		    .getClassLoader());
	} else if (warnings > 0) {
	    iconUrl = ResourceServices.getResourceAsUrl("/images/warningBoxHeaderBackground.jpg", ReportDialog.class
		    .getClassLoader());
	} else {
	    iconUrl = ResourceServices.getResourceAsUrl("/images/infoBoxHeaderBackground.jpg", ReportDialog.class
		    .getClassLoader());
	}

	icon = new ImageIcon(iconUrl, "header background");

	setResizable(false);

	JScrollPane scrollPane;

	JiveFactory wm = JiveFactory.getInstance();
	ResourceBundle res = wm.getResourceBundle();

	panel = new GridPanel(new Insets(0, 0, 0, 0));
	panel.setBorder(BorderFactory.createEtchedBorder());

	getContentPane().add(panel);

	JLabel background = new JLabel(icon);

	JLabel heading = new JLabel(title);

	Font font = heading.getFont();
	font = font.deriveFont(18.0F);

	heading.setFont(font);
	heading.setForeground(Color.white);

	JLayeredPane headerPane = new JLayeredPane();

	headerPane.setOpaque(true);
	headerPane.setBackground(Color.white);

	headerPane.setPreferredSize(new Dimension(width, 50));
	headerPane.setMinimumSize(new Dimension(width, 50));

	headerPane.add(background, new Integer(Integer.MIN_VALUE));
	headerPane.add(heading, new Integer(100));

	background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
	heading.setBounds(20, 0, width, 50);

	panel.addComponent(headerPane, Anchor.NORTHWEST, Fill.HORIZONTAL);
	panel.incrRow();
	
	info = new JEditorPane();
	info.setPreferredSize(new Dimension(600, 200));
	info.setBackground(Color.white);
	info.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray), BorderFactory
		.createEmptyBorder(10, 10, 10, 10)));
	info.setBackground(Color.white);
	info.setEditable(false);
	info.setFocusable(false);
	setText(msg);

	scrollPane = new JScrollPane(info);
	scrollPane.setBackground(Color.white);
	scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	panel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
	panel.incrRow();
	
	GridPanel counterPanel = new GridPanel();

	label = new JLabel(ResourceServices.getString(res,"C_ERRORS") + ": " + errors);
	counterPanel.addComponent(label);

	label = new JLabel(ResourceServices.getString(res,"C_WARNINGS") + ": " + warnings);
	counterPanel.addComponent(label);

	label = new JLabel(ResourceServices.getString(res,"C_SUCCESSFUL") + ": " + successful);
	counterPanel.addComponent(label);

	label = new JLabel(ResourceServices.getString(res,"C_TOTAL") + ": " + total);
	counterPanel.addComponent(label);

	panel.addComponent(counterPanel, Anchor.NORTHWEST, Fill.HORIZONTAL);
	panel.incrRow();
	
	eventListBox = new JList(listData);
	eventListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	eventListBox.setCellRenderer(new ReportItemRenderer());
	eventListBox.addListSelectionListener(new ReportSelectionListener(this, res));

	scrollPane = new JScrollPane(eventListBox);
	scrollPane.setBorder(BorderFactory.createEtchedBorder());
	scrollPane.setPreferredSize(new Dimension(width, 160));
	scrollPane.setMinimumSize(new Dimension(width, 160));

	panel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.HORIZONTAL,
		new Insets(2, 2, 2, 2));
	panel.incrRow();
	
	GridPanel buttonPane = new GridPanel();
	buttonPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

	JButton okButton = new JButton(ResourceServices.getString(res,"C_OK"));
	okButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
	    }
	});

	buttonPane.addComponent(okButton, Anchor.CENTER, Fill.NONE);
	panel.addComponent(buttonPane, Anchor.NORTHWEST, Fill.HORIZONTAL);

	getRootPane().setDefaultButton(okButton);
    }

    public void setText(String text) {

	if (text == null) {
	    text = "";
	}

	text = text.trim();

	if (text.toLowerCase().startsWith("<html>") == true) {

	    info.setContentType("text/html");
	    Document doc = info.getDocument();

	    if (doc instanceof HTMLDocument) {

		HTMLDocument htmlDoc = (HTMLDocument) doc;
		StyleSheet styleSheet = htmlDoc.getStyleSheet();

		Font font = panel.getFont();
		String fontFamily = font.getFamily();
		int fontSize = font.getSize();

		String rule = "body {";

		if (backgroundImageUrl != null) {
		    rule += "background-image: url(" + backgroundImageUrl.toString() + ");";
		    rule += "background-repeat: no-repeat;";
		    rule += "background-position: top right;";
		}

		rule += " font-family: " + fontFamily + ";" + " font-size: " + fontSize + ";" + "}";

		styleSheet.addRule(rule);

		styleSheet.addRule("h3 {" + " color: #999999;" + " font-size: " + fontSize + ";"
			+ " font-weight: bold;" + " margin-bottom: 2pt;" + " padding-bottom: 2pt;"
			+ " border: 1px solid black;" + "}");

		styleSheet.addRule("h4 {" + " color: silver;" + " font-size: " + fontSize + ";" + "}");

		styleSheet.addRule("p.error {" + " color: maroon;" + "}");

		styleSheet.addRule("p.success {" + " color: olive;" + "}");

		info.setText(text);
	    }

	} else {

	    info.setContentType("text/plain");
	    info.setText(text);
	}
    }
}

class ReportSelectionListener implements ListSelectionListener {

    private Dialog dialogOwner;
    private ResourceBundle res;

    public ReportSelectionListener(Dialog dialogOwner, ResourceBundle res) {

	this.dialogOwner = dialogOwner;
	this.res = res;
    }

    public void valueChanged(ListSelectionEvent event) {

	Object obj = event.getSource();

	if (obj instanceof JList) {

	    JList list = (JList) obj;

	    obj = list.getSelectedValue();

	    if (obj instanceof ReportItem) {

		ReportItem item = (ReportItem) obj;
		String msg = item.getMessage();

		if (msg == null) {
		    msg = "";
		}

		if (item.isError() && msg.length() > 0 && item.getCause() == null) {
		    ErrorBox.show(dialogOwner, ResourceServices.getString(res,"C_ERROR"), msg);
		}

		if (item.isWarning() && msg.length() > 0 && item.getCause() == null) {
		    WarningBox.show(dialogOwner, ResourceServices.getString(res,"C_WARNING"), msg);
		}

		if (item.isSuccess() && msg.length() > 0 && item.getCause() == null) {
		    InfoBox.show(dialogOwner, ResourceServices.getString(res,"C_INFORMATION"), msg);
		}

		if (item.getCause() != null) {

		    ExceptionBox ebox = new ExceptionBox(dialogOwner, item.getCause());
		    ebox.setVisible(true);
		    ebox.toFront();
		}
	    }
	}
    }
}

class ReportItemRenderer extends JLabel implements ListCellRenderer {

    private static final long serialVersionUID = 1L;

    private ImageIcon errorIcon;
    private ImageIcon warningIcon;
    private ImageIcon successIcon;

    public ReportItemRenderer() {

	super();
	setOpaque(true);

	URL iconUrl;

	iconUrl = ResourceServices.getResourceAsUrl("/images/errorReportItem.jpg", ReportDialog.class.getClassLoader());
	errorIcon = new ImageIcon(iconUrl, "error report item");

	iconUrl = ResourceServices.getResourceAsUrl("/images/warningReportItem.jpg", ReportDialog.class
		.getClassLoader());
	warningIcon = new ImageIcon(iconUrl, "warning report item");

	iconUrl = ResourceServices.getResourceAsUrl("/images/successReportItem.jpg", ReportDialog.class
		.getClassLoader());
	successIcon = new ImageIcon(iconUrl, "success report item");
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
	    boolean cellHasFocus) {
	if (value instanceof ReportItem) {

	    ReportItem item = (ReportItem) value;

	    if (item.isError() && errorIcon != null) {
		setIcon(errorIcon);
	    }

	    if (item.isWarning() && warningIcon != null) {
		setIcon(warningIcon);
	    }

	    if (item.isSuccess() && successIcon != null) {
		setIcon(successIcon);
	    }

	    setText(" " + value.toString());

	    if (isSelected == true) {
		setBackground(list.getSelectionBackground());
		setForeground(list.getSelectionForeground());
	    } else {
		setBackground(list.getBackground());
		setForeground(list.getForeground());
	    }

	}

	return this;
    }
}
