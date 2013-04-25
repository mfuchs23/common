/* 
 *
 * ### Copyright (C) 2005,2011 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.io.Screen;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class ScreenPane extends JTextPane implements Screen {

	private static final String FONT_FAMILY = "Monospaced";
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ScreenPane.class);

	private final ResourceBundle res;
	private Font font;
	private Style regular;
	private Style error;
	private Style errorLabel;
	private Style warning;
	private Style exception;
	private Style section;
	private Style command;
	private int cols = 40;
	private int rows = 25;
	private final int maxLength = 1048576;

	public ScreenPane(Font font, int cols, int rows) {

		super();

		JiveFactory widgetMap = JiveFactory.getInstance();
		res = widgetMap.getResourceBundle();

		if (font == null) {
			font = new Font(FONT_FAMILY, Font.PLAIN, 12);
		}

		setFont(font);

		if ((cols < 1) || (cols > 500)) {
			cols = 40;
		}

		if (rows < 1) {
			rows = 25;
		}

		this.font = font;
		this.cols = cols;
		this.rows = rows;

		setEditable(false);
	}

	public ScreenPane(Font font) {
		this(font, 40, 25);
	}

	public ScreenPane() {
		this(new Font(FONT_FAMILY, Font.PLAIN, 12), 40, 25);
	}

	public ScreenPane(int width, int height) {
		this(new Font(FONT_FAMILY, Font.PLAIN, 12), width, height);
	}

	public void clear() {

		try {

			Document doc = getDocument();

			if (doc != null) {
				doc.remove(0, doc.getLength());
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	public void println(String text) {
		message(text);
	}

	public void append(String text) {

		if (text == null) {
			text = "";
		}

		append(text, getStyle("regular"));
	}

	public void append(String text, Style style) {

		if (text == null) {
			text = "";
		}

		if (style == null) {
			throw new IllegalArgumentException("Parameter style is null!");
		}

		append(text, style, true);
	}

	public void append(String text, Style style, boolean appendLinefeed) {

		if (text == null) {
			text = "";
		}

		if (style == null) {
			throw new IllegalArgumentException("Parameter style is null!");
		}

		try {

			Document doc = getDocument();

			if (doc == null) {
				throw new NullPointerException("Variable doc is null!");
			}

			if (appendLinefeed) {
				text += "\n";
			}

			doc.insertString(doc.getLength(), text, style);
			seeEnd();

		} catch (BadLocationException oops) {
			oops.printStackTrace();
		}
	}

	public void appendSpace() {

		try {

			Document doc = getDocument();

			if (doc == null) {
				throw new NullPointerException("Variable doc is null!");
			}

			int length = doc.getLength();

			doc.insertString(length, " ", regular);

		} catch (BadLocationException oops) {
			oops.printStackTrace();
		}
	}

	public void exception(Throwable oops) {
		exception(oops, null);
	}

	public void exception(Throwable oops, String buffer) {

		String text = "[Exception] Class: " + oops.getClass().getName();

		String msg = oops.getMessage();

		if ((msg != null) && (msg.length() > 0)) {

			text += (" Message: " + msg);
		}

		if (buffer != null) {

			text += buffer;
		}

		StringWriter stack = new StringWriter();
		oops.printStackTrace(new PrintWriter(stack));
		text += ("\n" + "===================\n" + stack.toString());

		append(text, getStyle("exception"));
	}

	public void command(String text) {

		append(text, getStyle("command"));
	}

	public void error(String text) {

		error(ResourceServices.getString(res, "C_ERROR"), text);
	}

	public void error(String label, String text) {

		append("[" + label + "]", getStyle("errorLabel"), false);
		appendSpace();
		append(text, getStyle("error"));
	}

	public void warning(String text) {

		append(text, getStyle("warning"));
	}

	public void section(String text) {

		append(text, getStyle("section"));
	}

	public void message(String text) {
		append(text, getStyle("regular"));
	}

	public void info(String text) {

		Document doc = getDocument();

		if (doc.getLength() > maxLength) {
			try {
				doc.remove(0, text.length());
			} catch (BadLocationException oops) {
				logger.fatal(oops);
			}
		}

		append(text, getStyle("regular"));
	}

	public void seeEnd() {

		Document doc = getDocument();

		if (doc == null) {
			throw new NullPointerException("Variable doc is null!");
		}

		int length = doc.getLength();
		setCaretPosition(length);
	}

	@Override
	public void setFont(Font font) {

		if (font == null) {

			throw new IllegalArgumentException("Parameter font is null!");
		}

		this.font = font;

		StyledDocument doc = getStyledDocument();

		if (doc == null) {

			return;
		}

		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);

		if (def != null) {

			StyleConstants.setFontFamily(def, font.getFamily());
			StyleConstants.setFontSize(def, font.getSize());

			regular = addStyle("regular", def);

			command = addStyle("command", def);
			StyleConstants.setBackground(command, Color.lightGray);

			error = addStyle("error", def);
			StyleConstants.setForeground(error, Color.red);

			errorLabel = addStyle("errorLabel", def);
			StyleConstants.setForeground(errorLabel, Color.white);
			StyleConstants.setBackground(errorLabel, Color.red);

			warning = addStyle("warning", def);
			StyleConstants.setForeground(warning, Color.orange);

			exception = addStyle("exception", def);
			StyleConstants.setForeground(exception, Color.red);
			StyleConstants.setBold(exception, true);

			section = addStyle("section", def);
			StyleConstants.setSpaceAbove(section, 2.0F);
			StyleConstants.setUnderline(section, true);
			StyleConstants.setBold(section, true);
		}
	}

	@Override
	public Dimension getPreferredSize() {

		Dimension dim = super.getPreferredSize();

		Graphics graphics = getGraphics();
		FontMetrics metrics = graphics.getFontMetrics(font);

		double minWidth = metrics.charWidth('m') * cols;
		double minHeight = metrics.getHeight() * rows;

		double width = dim.getWidth();
		double height = dim.getHeight();

		if (width < minWidth) {
			width = minWidth;
		}

		if (height < minHeight) {
			height = minHeight;
		}

		dim.setSize(width, height);
		return dim;
	}

}
