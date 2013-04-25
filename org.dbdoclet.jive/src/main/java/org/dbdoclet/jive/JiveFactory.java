/* 
 * ### Copyright (C) 2006-2010 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.TreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Identifier;
import org.dbdoclet.jive.action.ActionSetLookAndFeel;
import org.dbdoclet.jive.model.SpinnerDistanceModel;
import org.dbdoclet.jive.sheet.Sheet;
import org.dbdoclet.jive.sheet.SheetContainer;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.IdentifierTextField;
import org.dbdoclet.jive.widget.LanguageListBox;
import org.dbdoclet.jive.widget.MimeTypeListBox;
import org.dbdoclet.jive.widget.NumberTextField;
import org.dbdoclet.jive.widget.PathList;
import org.dbdoclet.jive.widget.RichLabel;
import org.dbdoclet.jive.widget.SpinnerDistanceEditor;
import org.dbdoclet.jive.widget.UpperCaseTextField;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.unit.Length;

/**
 * Fabrikklasse zu Erzeugung von Swing-Komponenten. JiveFactory ist eine
 * Singleton-Klasse die es nur einmal innerhalb einer Swing-Applikation geben
 * darf. Alle erzeugten Komponenten werden in einer Hashtabelle gespeichert und
 * können so von überall unter ihrem Namen angesprochen werden.
 * 
 * @author michael
 */
public final class JiveFactory implements ItemListener, DocumentListener,
		ActionListener, ChangeListener {

	private static Log logger = LogFactory.getLog(JiveFactory.class);
	private static JiveFactory singleton;

	private ImageIcon applicationIcon;
	private URL backgroundImageUrl = null;
	private Font defaultFont = null;
	private Color defaultForeground;
	private Color helpAreaBackground = null;
	private Border helpAreaBorder = null;
	private HelpBroker helpBroker;
	private HelpSet helpSet;
	private final Locale locale;
	private ResourceBundle res = ResourceBundle
			.getBundle("org/dbdoclet/jive/Resources");
	private ArrayList<JComponent> saveWidgetList;
	private final HashMap<Identifier, JComponent> widgetMap;
	private Color defaultBackground;

	/**
	 * Erzeugt eine neue Instanz der Klasse <code>JiveFactory</code>. Da es nur
	 * eine Instanz dieser Klasse geben darf ist der Konstruktur für andere
	 * Objekte nicht sichtbar.
	 * 
	 * @param locale
	 */
	private JiveFactory(Locale locale) {

		if (locale == null) {
			throw new IllegalArgumentException(
					"The argument locale must not be null!");
		}

		this.locale = locale;
		widgetMap = new HashMap<Identifier, JComponent>();
		res = ResourceBundle.getBundle("org/dbdoclet/jive/Resources", locale);
	}

	/**
	 * Ermittelt die Objektinstanz des Fensterrahmens innerhalb dessen sich die
	 * übergebene Kompontente befindet.
	 * 
	 * @param comp
	 * @return Frame
	 */
	public static Frame findParentFrame(JComponent comp) {

		if (comp == null) {
			return null;
		}

		Container parent = comp.getParent();

		while (parent != null) {

			if (parent instanceof Frame) {
				return (Frame) parent;
			}

			parent = parent.getParent();
		}

		return null;
	}

	/**
	 * Liefert die Instanz der Fabrikklasse.
	 * 
	 * @return JiveFactory
	 */
	public static JiveFactory getInstance() {

		if (singleton == null) {
			singleton = getInstance(Locale.getDefault());
		}

		return singleton;
	}

	/**
	 * Liefert eine Instanz der Fabrikklasse mit der angegebenen Lokalisierung.
	 * 
	 * @param locale
	 * @return JiveFactory
	 */
	public static JiveFactory getInstance(Locale locale) {

		System.setProperty("swing.aatext", "true");

		if (singleton != null) {

			logger.warn("Changing locale of global singleton WidgetMap to "
					+ locale.toString());
			singleton.setLocale(locale);

		} else {

			singleton = new JiveFactory(locale);
		}

		return singleton;
	}

	/**
	 * Wird derzeit nur zum Zweck des Debuggens verwendet.
	 */
	public void actionPerformed(ActionEvent event) {

		logger.debug("event=" + event);
		logger.debug("ActionEvent: \n" + "  Source        = '"
				+ event.getSource().getClass().getName() + "'\n"
				+ "  ID            = '" + event.getID() + "'\n"
				+ "  ActionCommand = '" + event.getActionCommand() + "'\n");
	}

	/**
	 * Fügt eine Komponente der Liste der Speicher-Komponenten hinzu.
	 * 
	 * @param widget
	 */
	public void addSaveWidget(JComponent widget) {

		if (saveWidgetList == null) {
			saveWidgetList = new ArrayList<JComponent>();
		}

		if (widget != null && saveWidgetList.contains(widget) == false) {
			saveWidgetList.add(widget);
		}
	}

	public void changedUpdate(DocumentEvent documentEvent) {
		enableSaveWidgets();
	}

	/**
	 * Erzeugt einen Button mit der ID <code>key</code> und der angegebenen
	 * <code>action</code>.
	 * 
	 * @param key
	 * @param action
	 * @return JButton
	 */
	public JButton createButton(Identifier key, AbstractAction action) {

		JButton button = new JButton(action);
		addWidget(key, button);

		return button;
	}

	/**
	 * Erzeugt einen Button mit der ID <code>key</code> und der angegebenen
	 * Aufschrift.
	 * 
	 * @param key
	 * @param text
	 * @return JButton
	 */
	public JButton createButton(Identifier key, String text) {
		return createButton(key, text, null);
	}

	public JButton createButton(Identifier key, String text, String icon) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		URL url = null;

		if (icon != null && icon.length() > 0) {

			String location = "toolbarButtonGraphics/" + icon + "24.gif";
			url = ClassLoader.getSystemResource(location);

			if (url == null) {
				url = getClass().getResource(location);
			}
		}

		JButton button;

		if (url != null) {
			button = new JButton(text, new ImageIcon(url));
		} else {
			button = new JButton(text);
		}

		addWidget(key, button);

		return button;
	}

	public JButton createButton(String label) {
		return createButton(null, label);
	}

	public JCheckBox createCheckBox() {
		return createCheckBox(null, null);
	}

	public JCheckBox createCheckBox(Identifier key, String label) {

		JCheckBox checkBox = new JCheckBox(label);
		checkBox.addItemListener(this);

		addWidget(key, checkBox);

		return checkBox;
	}

	public JCheckBox createCheckBox(String label) {
		return createCheckBox(null, label);
	}

	public <T> JComboBox<T> createComboBox(Identifier key) {
		return createComboBox(key, null);
	}

	public <T> JComboBox<T> createComboBox(Identifier key, T[] list) {

		JComboBox<T> comboBox;

		if (list == null) {
			comboBox = new JComboBox<T>();
		} else {
			comboBox = new JComboBox<T>(list);
		}

		comboBox.addItemListener(this);
		addWidget(key, comboBox);

		return comboBox;
	}

	public JSpinner createDistanceSpinner() {
		return createDistanceSpinner(null, null);
	}

	public JSpinner createDistanceSpinner(Identifier key) {
		return createDistanceSpinner(key, null, true);
	}

	public JSpinner createDistanceSpinner(Identifier key, boolean excludePercent) {
		return createDistanceSpinner(key, null, excludePercent);
	}

	public JSpinner createDistanceSpinner(Identifier key, Length distance) {
		return createDistanceSpinner(key, distance, true);
	}

	public JSpinner createDistanceSpinner(Identifier key, Length distance,
			boolean excludePercent) {

		SpinnerDistanceModel model = new SpinnerDistanceModel(getLocale());
		model.setExcludePercent(excludePercent);

		JSpinner spinner = new JSpinner(model);

		if (distance != null) {
			spinner.setValue(distance);
		}

		Dimension preferredSize = spinner.getPreferredSize();

		if (preferredSize.width < 100) {
			preferredSize.width = 100;
		}

		spinner.setPreferredSize(preferredSize);
		spinner.setEditor(new SpinnerDistanceEditor(spinner));
		spinner.addChangeListener(this);

		addWidget(key, spinner);

		return spinner;
	}

	public JSpinner createDistanceSpinner(Length length) {
		return createDistanceSpinner(null, length);
	}

	public JLabel createEntryHelp(String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		text = createToolTipText(text);

		JLabel label = new JLabel("Help24", JiveServices.getJlfgrIcon(
				"general", "Help24.gif"), SwingConstants.CENTER);

		label.setToolTipText(text);
		label.setForeground(new Color(0, 0, 205));

		return label;
	}

	public JTextComponent createErrorArea(Identifier key, JComponent parent) {
		return createErrorArea(key, parent, "<html>");
	}

	public JTextComponent createErrorArea(Identifier key, JComponent parent,
			String text) {

		if (parent == null) {
			throw new IllegalArgumentException(
					"The argument parent must not be null!");
		}

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JEditorPane errorArea = new JEditorPane();
		errorArea.setEditable(false);
		errorArea.setFocusable(false);
		errorArea.setBackground(Color.white);
		errorArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(139, 0, 0)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		if (text.toLowerCase().startsWith("<html>") == true) {

			errorArea.setContentType("text/html");
			Document doc = errorArea.getDocument();

			if (doc instanceof HTMLDocument) {

				HTMLDocument htmlDoc = (HTMLDocument) doc;
				StyleSheet styleSheet = htmlDoc.getStyleSheet();

				Font defaultFont = parent.getFont();
				String fontFamily = defaultFont.getFamily();
				int fontSize = defaultFont.getSize();

				styleSheet.addRule("body {" + " color: #8b0000;"
						+ " font-family: " + fontFamily + ";" + " font-size: "
						+ fontSize + ";" + "}");

				styleSheet.addRule("h1, h2 {" + " font-family: " + fontFamily
						+ ";" + " font-size: " + fontSize + ";"
						+ " margin: 2px;" + "}");

				styleSheet.addRule("p {" + " text-indent: 8px;"
						+ " margin: 2px;" + "}");

				errorArea.setText(text);
			}

		} else {

			errorArea.setContentType("text/plain");
			errorArea.setText(text);
		}

		addWidget(key, errorArea);

		return errorArea;
	}

	public JTextComponent createErrorArea(JComponent parent, String text) {
		return createErrorArea(null, parent, text);
	}

	public JLabel createHeading(Identifier key, String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JLabel label = new JLabel(text);

		Font font = label.getFont();

		String fontName = font.getName();
		int fontSize = (int) (font.getSize() * 1.6);
		Font headingFont = new Font(fontName, Font.BOLD, fontSize);

		label.setFont(headingFont);
		label.setForeground(new Color(01, 48, 114));

		addWidget(key, label);

		return label;
	}

	public JTextComponent createHelpArea(Identifier key, JComponent parent) {
		return createHelpArea(key, parent, "<html>");
	}

	public JTextComponent createHelpArea(Identifier key, JComponent parent,
			String text) {

		if (parent == null) {
			throw new IllegalArgumentException(
					"The argument parent must not be null!");
		}

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JEditorPane helpArea = new JEditorPane();
		helpArea.setEditable(false);
		helpArea.setFocusable(false);

		if (helpAreaBackground == null) {
			helpArea.setBackground(parent.getBackground());
		} else {
			helpArea.setBackground(helpAreaBackground);
		}

		if (helpAreaBorder == null) {
			helpArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		} else {
			helpArea.setBorder(helpAreaBorder);
		}

		// helpArea.setMinimumSize(new Dimension(400, 100));
		// helpArea.setPreferredSize(new Dimension(400, 100));

		if (text.toLowerCase().startsWith("<html>") == true) {

			helpArea.setContentType("text/html");
			Document doc = helpArea.getDocument();

			if (doc instanceof HTMLDocument) {

				HTMLDocument htmlDoc = (HTMLDocument) doc;
				StyleSheet styleSheet = htmlDoc.getStyleSheet();

				Font defaultFont = parent.getFont();
				String fontFamily = defaultFont.getFamily();
				int fontSize = defaultFont.getSize();

				styleSheet.addRule("body {" + " font-family: " + fontFamily
						+ ";" + " font-size: " + (fontSize - 2) + ";" + "}");

				styleSheet.addRule("h1, h2 {" + " font-family: " + fontFamily
						+ ";" + " font-size: " + (fontSize - 1) + ";"
						+ " color: #031071;" + " margin: 2px;" + "}");

				styleSheet.addRule("p {"
				// + " text-indent: 8px;"
						+ " margin: 2px;" + "}");

				styleSheet.addRule("p.error {" + " color: red;" + "}");

				helpArea.setText(text);
			}

		} else {

			helpArea.setContentType("text/plain");
			helpArea.setText(text);
		}

		addWidget(key, helpArea);

		return helpArea;
	}

	public JTextComponent createHelpArea(JComponent parent) {
		return createHelpArea(null, parent, "<html>");
	}

	public JTextComponent createHelpArea(JComponent parent, String text) {
		return createHelpArea(null, parent, text);
	}

	public JEditorPane createHtmlArea(Identifier key, JComponent parent,
			String text) {

		if (parent == null) {
			throw new IllegalArgumentException(
					"The argument parent must not be null!");
		}

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JEditorPane htmlArea = new JEditorPane("text/html", text);
		htmlArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		htmlArea.setEditable(false);
		// htmlArea.setFocusable(false);

		htmlArea.setMinimumSize(new Dimension(200, 100));

		addWidget(key, htmlArea);

		if (text.toLowerCase().startsWith("<html>") == true) {

			htmlArea.setContentType("text/html");
			Document doc = htmlArea.getDocument();

			if (doc instanceof HTMLDocument) {

				HTMLDocument htmlDoc = (HTMLDocument) doc;
				StyleSheet styleSheet = htmlDoc.getStyleSheet();

				Font defaultFont = parent.getFont();
				String fontFamily = defaultFont.getFamily();
				int fontSize = defaultFont.getSize();

				styleSheet.addRule("body {" + " font-family: " + fontFamily
						+ ";" + " font-size: " + fontSize + ";" + "}");
			}
		}

		return htmlArea;
	}

	public JButton createIconButton(Identifier key, ImageIcon icon) {

		if (icon == null) {
			throw new IllegalArgumentException(
					"The argument icon must not be null!");
		}

		JButton button = new JButton(icon);
		button.setActionCommand("icon-button");
		button.addActionListener(this);

		addWidget(key, button);

		return button;
	}

	public IdentifierTextField createIdentifierTextField(Identifier key,
			int width) {

		IdentifierTextField textfield = new IdentifierTextField(width);
		addWidget(key, textfield);

		return textfield;
	}

	public IdentifierTextField createIdentifierTextField(int width) {
		return createIdentifierTextField(null, width);
	}

	public JLabel createLabel(Identifier key, String text) {
		return createLabel(key, text, Font.BOLD);
	}

	public JLabel createLabel(Identifier key, String text, int style) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JLabel label = new JLabel(text);

		Font font = label.getFont();
		label.setFont(font.deriveFont(style));
		addWidget(key, label);

		return label;
	}

	public JLabel createLabel(String text) {
		return createLabel(null, text, Font.BOLD);
	}

	public JLabel createLabel(String text, int style) {
		return createLabel(null, text, style);
	}

	public LanguageListBox createLanguageListBox(Identifier key, Locale locale) {

		LanguageListBox llb = new LanguageListBox(locale);
		addWidget(key, llb);
		return llb;
	}

	public <T> JList<T> createList(Identifier key) {

		JList<T> list = new JList<T>();
		addWidget(key, list);

		if (defaultBackground != null) {
			list.setBackground(defaultBackground.brighter());
		}

		return list;
	}

	public JMenu createLookAndFeelMenu(ArrayList<Component> componentList) {

		if (componentList == null) {
			throw new IllegalArgumentException(
					"The argument frame must not be null!");
		}

		JMenuItem menuItem;

		JMenu menu = new JMenu(ResourceServices.getString(res,
				"C_LOOK_AND_FEEL"));

		LookAndFeelInfo[] infoList = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < infoList.length; i++) {

			menuItem = new JMenuItem(infoList[i].getName());
			menuItem.setAction(new ActionSetLookAndFeel(infoList[i].getName(),
					infoList[i], componentList));
			menu.add(menuItem);
		}

		return menu;
	}

	public JMenu createMenu(Identifier key, String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JMenu menu = new JMenu(text);
		// menu.setFont(font);

		addWidget(key, menu);

		return menu;
	}

	public JMenu createMenu(String text) {
		return createMenu(null, text);
	}

	/**
	 * Erzeugt einen Menüeintrag.
	 * 
	 * @param key
	 * @param action
	 * @return JMenuItem
	 */
	public JMenuItem createMenuItem(Identifier key, AbstractAction action) {

		JMenuItem menuItem = new JMenuItem(action);
		addWidget(key, menuItem);
		return menuItem;
	}

	public JMenuItem createMenuItem(Identifier key, String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		JMenuItem menuitem = new JMenuItem(text);
		addWidget(key, menuitem);

		return menuitem;
	}

	public MimeTypeListBox createMimeTypeListBox(Identifier key) {

		MimeTypeListBox mtlb = new MimeTypeListBox();
		addWidget(key, mtlb);
		return mtlb;
	}

	public JSpinner createNumberSpinner(Identifier key, int value, int min,
			int max, int step) {

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max,
				step));
		addWidget(key, spinner);
		return spinner;
	}

	public NumberTextField createNumberTextField(Identifier key, int width) {

		NumberTextField textfield = new NumberTextField(width);

		textfield.getDocument().addDocumentListener(this);
		textfield.addActionListener(this);

		addWidget(key, textfield);

		return textfield;
	}

	public NumberTextField createNumberTextField(int width) {
		return createNumberTextField(null, width);
	}

	public JPanel createPanel(Identifier key) {
		return createPanel(key, null);
	}

	public JPanel createPanel(Identifier key, LayoutManager layoutManager) {

		JPanel panel;

		if (layoutManager != null) {
			panel = new JPanel(layoutManager);
		} else {
			panel = new JPanel();
		}

		addWidget(key, panel);
		return panel;
	}

	public JPasswordField createPasswordField(Identifier key, int width) {

		JPasswordField passwordfield = new JPasswordField(width);
		passwordfield.setMinimumSize(passwordfield.getPreferredSize());

		addWidget(key, passwordfield);

		return passwordfield;
	}

	public PathList createPathList(Identifier key, int filterMask) {

		PathList pathlist = new PathList(this, filterMask);
		addWidget(key, pathlist);

		return pathlist;
	}

	public JRadioButton createRadioButton(Identifier key, ImageIcon icon) {

		JRadioButton button = new JRadioButton(icon);
		addWidget(key, button);

		return button;
	}

	public JRadioButton createRadioButton(Identifier key, String label) {

		JRadioButton button = new JRadioButton(label);
		addWidget(key, button);

		return button;
	}

	public RichLabel createRichLabel(String text) {

		RichLabel label = new RichLabel(text);
		addWidget(null, label);

		if (defaultForeground != null) {
			label.setForeground(defaultForeground);
		}

		if (defaultFont != null) {
			label.setFont(defaultFont.deriveFont(Font.BOLD));
		}

		return label;
	}

	public JScrollPane createScrollPane(Component client) {
		return createScrollPane(null, client);
	}

	public JScrollPane createScrollPane(Identifier key, Component client) {

		if (client == null) {
			throw new IllegalArgumentException(
					"The argument client must not be null!");
		}

		JScrollPane scrollPane = new JScrollPane(client);
		addWidget(key, scrollPane);

		return scrollPane;
	}

	public Sheet createSheet(SheetContainer container) {
		return new Sheet(container, res);
	}

	public JSplitPane createSplitPane(Identifier key) {

		JSplitPane splitPane = new JSplitPane();
		addWidget(key, splitPane);

		return splitPane;
	}

	public JTabbedPane createTabbedPane() {
		return createTabbedPane(null);
	}

	public JTabbedPane createTabbedPane(Identifier key) {

		JTabbedPane tabbedPane = new JTabbedPane();
		addWidget(key, tabbedPane);
		return tabbedPane;
	}

	public JTable createTable(Identifier key) {

		JTable table = new JTable();
		addWidget(key, table);
		return table;
	}

	// public JTextField createTextField(int width) {
	//
	// return createTextField(null, width);
	// }

	public JTextArea createTextArea(Identifier key) {

		JTextArea textarea = new JTextArea();
		textarea.getDocument().addDocumentListener(this);

		addWidget(key, textarea);

		return textarea;
	}

	public JTextArea createTextArea(Identifier key, int rows, int cols) {

		JTextArea textarea = new JTextArea(rows, cols);

		textarea.getDocument().addDocumentListener(this);

		addWidget(key, textarea);

		return textarea;
	}

	public JTextField createTextField(Identifier key, int width) {

		JTextField textfield = new JTextField(width);

		textfield.getDocument().addDocumentListener(this);
		textfield.addActionListener(this);
		textfield.setMinimumSize(textfield.getPreferredSize());

		addWidget(key, textfield);

		return textfield;
	}

	public JTextPane createTextPane() {
		return createTextPane(null);
	}

	public JTextPane createTextPane(Identifier key) {

		JTextPane textPane = new JTextPane();
		addWidget(key, textPane);
		return textPane;
	}

	public JToggleButton createToggleButton(Identifier key, String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument icon must not be null!");
		}

		JToggleButton button = new JToggleButton(text);
		button.setActionCommand("toggle-button");
		button.addActionListener(this);

		addWidget(key, button);

		return button;
	}

	public JToolBar createToolBar(Identifier key) {

		JToolBar toolbar = new JToolBar();
		addWidget(key, toolbar);

		return toolbar;
	}

	public String createToolTipText(String text) {

		if (text == null) {
			throw new IllegalArgumentException(
					"The argument text must not be null!");
		}

		text = text.trim();
		text = StringServices.splitAt(text, " ", 40, "<br>");

		if (text.startsWith("<html>") == false) {
			text = "<html>" + text;
		}

		return text;
	}

	public JTree createTree(Identifier key) {
		return createTree(key, null);
	}

	public JTree createTree(Identifier key, TreeModel model) {

		JTree tree;

		if (model != null) {
			tree = new JTree(model);
		} else {
			tree = new JTree();
		}

		addWidget(key, tree);
		return tree;
	}

	public UpperCaseTextField createUpperCaseTextField(Identifier key, int width) {

		UpperCaseTextField textfield = new UpperCaseTextField(width);

		addWidget(key, textfield);

		return textfield;
	}

	public JSpinner createWidthSpinner(Identifier key) {
		return createWidthSpinner(key, null);
	}

	public JSpinner createWidthSpinner(Identifier key, Length width) {

		JSpinner spinner = new JSpinner(new SpinnerDistanceModel(getLocale()));

		if (width != null) {
			spinner.setValue(width);
		}

		Dimension preferredSize = spinner.getPreferredSize();

		if (preferredSize.width < 75) {
			preferredSize.width = 75;
		}

		spinner.setPreferredSize(preferredSize);
		spinner.setEditor(new SpinnerDistanceEditor(spinner));
		spinner.addChangeListener(this);

		addWidget(key, spinner);

		return spinner;
	}

	/**
	 * Deaktiviert die Komponente <code>comp</code> und alle in ihr enthaltenen
	 * Kindkomponenten. Kindkomponenten, die aktiv waren und deaktiviert wurden
	 * werden markiert. Dadurch weiß die Methode {@link #enableAll} welche
	 * Komponenten aktiviert werden müssen und welche bereits beim Aufruf von
	 * <code>disableAll</code> deaktiviert waren und deshalb nicht verändert
	 * werden dürfen. müssen. Nach der Ausführung der Methoden
	 * <code>disableAll</code> und <code>enableAll</code> soll der
	 * Ausgangszustand möglichst genau wiederhergestellt werden. Dazu gehört
	 * auch, dass keine Komponente plötzlich aktiviert wurde, die es vorher
	 * nicht war.
	 * 
	 * @param comp
	 */
	public void disableAll(Component comp) {

		if (comp == null) {
			return;
		}

		if (comp instanceof Container) {

			Container parent = (Container) comp;

			Component[] compList = parent.getComponents();

			for (int i = 0; i < compList.length; i++) {

				disableAll(compList[i]);
			}
		}

		String name;

		if (comp instanceof AbstractButton || comp instanceof JComboBox
				|| comp instanceof JTextComponent) {

			name = comp.getName();

			if (name == null) {
				name = "";
			}

			logger.debug("disable name = " + name);

			if (comp.isEnabled() == true) {

				logger.debug("disabling name = " + name);
				comp.setEnabled(false);

				if (name.endsWith("toggled") == false) {
					comp.setName(name + ":toggled");
				}
			}
		}
	}

	public void disableSaveWidgets() {

		if (saveWidgetList != null && saveWidgetList.size() > 0) {

			JComponent comp;
			Iterator<JComponent> iterator = saveWidgetList.iterator();

			while (iterator.hasNext()) {

				comp = iterator.next();
				comp.setEnabled(false);
			}
		}
	}

	public void enableAll(Component comp) {

		if (comp == null) {
			return;
		}

		if (comp instanceof Container) {

			Container parent = (Container) comp;

			Component[] compList = parent.getComponents();

			for (int i = 0; i < compList.length; i++) {

				enableAll(compList[i]);
			}
		}

		String name;

		if (comp instanceof AbstractButton || comp instanceof JComboBox
				|| comp instanceof JTextComponent) {

			name = comp.getName();

			if (name == null) {
				name = "";
			}

			logger.debug("enable name = " + name);

			if (comp.isEnabled() == false) {

				if (name.endsWith(":toggled") == true) {

					logger.debug("enabling name = " + name);
					comp.setEnabled(true);

				} else {

					comp.setName(name + ":enabled");
				}
			}
		}
	}

	public void enableSaveWidgets() {

		if (saveWidgetList != null && saveWidgetList.size() > 0) {

			JComponent comp;
			Iterator<JComponent> iterator = saveWidgetList.iterator();

			while (iterator.hasNext()) {

				comp = iterator.next();
				comp.setEnabled(true);
			}
		}
	}

	public ImageIcon getApplicationIcon() {
		return applicationIcon;
	}

	public URL getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public HelpBroker getHelpBroker() {
		return helpBroker;
	}

	public Color getLabelForeground() {
		return defaultForeground;
	}

	public Locale getLocale() {
		return locale;
	}

	// public void setLookandFeel(String laf) {
	//
	// }

	public ResourceBundle getResourceBundle() {
		return res;
	}

	public JComponent getWidget(Identifier key) {
		return widgetMap.get(key);
	}

	public void insertUpdate(DocumentEvent documentEvent) {
		enableSaveWidgets();
	}

	public void itemStateChanged(ItemEvent itemEvent) {
		enableSaveWidgets();
	}

	public Font registerEuterpeFont() {

		try {

			InputStream instr = ResourceServices.getResourceAsStream(
					"/fonts/Euterpe.ttf", JiveFactory.class.getClassLoader());

			if (instr != null) {
				Font font = Font.createFont(Font.TRUETYPE_FONT, instr);
				// GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
				// return new Font("Euterpe", Font.PLAIN, 12);
				return font;
			}

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void removeUpdate(DocumentEvent documentEvent) {
		enableSaveWidgets();
	}

	public void setApplicationIcon(ImageIcon applicationIcon) {
		this.applicationIcon = applicationIcon;
	}

	public void setBackgroundImageUrl(URL backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public void setDefaultForeground(Color foreground) {
		this.defaultForeground = foreground;
	}

	public void setDefaultFont(Font defaultFont) {

		if (defaultFont == null) {
			throw new IllegalArgumentException(
					"Parameter font must not be null!");
		}

		this.defaultFont = defaultFont;
	}

	public void setHelpAreaBackground(Color helpAreaBackground) {
		this.helpAreaBackground = helpAreaBackground;
	}

	public void setHelpAreaBorder(Border helpAreaBorder) {
		this.helpAreaBorder = helpAreaBorder;
	}

	public void setHelpBroker(HelpBroker helpBroker) {
		this.helpBroker = helpBroker;

	}

	public void setHelpSet(HelpSet helpSet) {
		this.helpSet = helpSet;
	}

	public void setLocale(Locale locale) {

		if (locale == null) {
			throw new IllegalArgumentException(
					"The argument locale must not be null!");
		}

		res = ResourceBundle.getBundle("org/dbdoclet/jive/Resources", locale);
	}

	public void stateChanged(ChangeEvent e) {
		enableSaveWidgets();
	}

	private void addContextSensitiveHelp(Identifier key, JComponent comp) {

		if (helpBroker != null && helpSet != null) {

			try {

				CSH.setHelpIDString(comp, key.getValue());
				CSH.setHelpSet(comp, helpSet);
				helpBroker.enableHelpKey(comp, key.getValue(), helpSet,
						"javax.help.Popup", "popup");

				// comp.setToolTipText(key);

			} catch (Throwable oops) {
				// logger.fatal("Can't add context sensitive help of key " +
				// key, oops);
			}
		}

		comp.setName(key.getValue());
	}

	/**
	 * Fügt eine Oberflächenkomponente zur globalen Hashtabelle hinzu. Der
	 * Identifikator wird auch verwendet um den Namen der Komponente (
	 * {@link Component#setName(String)}) und den Schlüssel für die
	 * kontext-sensitive Hilfe zu setzen.
	 * 
	 * Falls bereits eine Komponente mit gleichem Namen in der Hashtabelle
	 * existiert, wird die bereits vorhandene Komponente überschrieben.
	 * 
	 * @param key
	 * @param comp
	 */
	private void addWidget(Identifier key, JComponent comp) {

		if (defaultFont != null) {
			Font compFont = comp.getFont();
			comp.setFont(defaultFont.deriveFont(compFont.getStyle()));
		}

		if (defaultForeground != null) {
			comp.setForeground(defaultForeground);
		}

		if (defaultBackground != null) {
			comp.setBackground(defaultBackground);
		}

		if (key != null && key.length() > 0 && comp != null) {

			comp.setName(key.toString());
			addContextSensitiveHelp(key, comp);

			widgetMap.put(key, comp);
		}
	}

	public void setDefaultBackground(Color defaultBackground) {
		this.defaultBackground = defaultBackground;
	}

	public GridPanel createGridPanel(Identifier key) {

		GridPanel panel = new GridPanel();
		addWidget(key, panel);
		return panel;
	}
}
