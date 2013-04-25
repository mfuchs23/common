package org.dbdoclet.jive.text;

import java.awt.Font;
import java.net.URL;
import java.text.MessageFormat;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

/**
 * TextServices.java
 * 
 * 
 * Created: Wed Oct 8 13:59:36 2003
 * 
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */
public class TextServices {

	public static void setText(final JEditorPane info, String text) {
		setText(info, text, null);
	}

	public static void setText(final JEditorPane info, String text,
			URL backgroundImageUrl) {

		if (text == null) {
			text = "";
		}

		text = text.trim();

		final String msg = text;

		if (msg.toLowerCase().startsWith("<html>") == true) {

			info.setContentType("text/html");
			Document doc = info.getDocument();

			if (doc instanceof HTMLDocument) {

				HTMLDocument htmlDoc = (HTMLDocument) doc;
				StyleSheet styleSheet = htmlDoc.getStyleSheet();

				Font font = info.getFont();
				String fontFamily = font.getFamily();
				int fontSize = font.getSize();

				String rule = "body {";

				if (backgroundImageUrl != null) {
					rule += "background-image: url("
							+ backgroundImageUrl.toString() + ");";
					rule += "background-repeat: no-repeat;";
					rule += "background-position: top right;";
				}

				rule += " font-family: \"" + fontFamily + "\";"
						+ " font-size: " + fontSize + ";" + "}";

				styleSheet.addRule(rule);

				styleSheet.addRule("h3 {" + " color: #999999;" + " font-size: "
						+ fontSize + ";" + " font-weight: bold;"
						+ " margin-bottom: 2pt;" + " padding-bottom: 2pt;"
						+ " }");

				styleSheet.addRule("h4 { color: silver; font-size: " + fontSize
						+ ";}");

				styleSheet.addRule("code {" + " font-family: Monospaced;"
						+ " font-size: " + fontSize + ";" + "}");

				styleSheet.addRule("p {" + " margin: 7px;" + " padding: 7px;"
						+ "}");

				styleSheet.addRule("p.error {" + " color: maroon;" + "}");

				styleSheet.addRule("p.success {" + " color: olive;" + "}");

				styleSheet.addRule("pre.dir {" + " background-color: #cecece;"
						+ " padding: 4pt;" + " margin: 4pt;"
						+ " border-top-width: 1pt;" + " border-style: solid;"
						+ " border-color: #999999;" + "}");

				styleSheet.addRule("pre.file {" + " background-color: #cecece;"
						+ " padding: 4pt;" + " margin: 4pt;"
						+ " border-top-width: 1pt;" + " border-style: solid;"
						+ " border-color: #999999;" + "}");

				styleSheet.addRule("th {" + " background-color: #cecece;"
						+ " text-align: left;" + " border-bottom-width: 1px;"
						+ " border-bottom-style: solid;"
						+ " border-bottom-color: black;" + "}");

				info.setText(msg);
				info.setCaretPosition(0);
			}

		} else {

			info.setContentType("text/plain");
			info.setText(msg);
			info.setCaretPosition(0);
		}

	}

	public static String format(String pattern, String arg) {

		if (pattern == null) {

			throw new IllegalArgumentException("Parameter pattern is null!");
		}

		if (arg == null) {

			throw new IllegalArgumentException("Parameter arg is null!");
		}

		Object[] arguments = { arg };

		return MessageFormat.format(pattern, arguments);
	}

	public static String format(String pattern, String arg1, String arg2) {

		if (pattern == null) {

			throw new IllegalArgumentException("Parameter pattern is null!");
		}

		if (arg1 == null) {

			throw new IllegalArgumentException("Parameter arg1 is null!");
		}

		if (arg2 == null) {

			throw new IllegalArgumentException("Parameter arg2 is null!");
		}

		Object[] arguments = { arg1, arg2 };

		return MessageFormat.format(pattern, arguments);
	}

	public static int countLines(String msg) {

		int counter = 0;
		int index = msg.indexOf('\n');

		while (index != -1 && index < (msg.length() - 1)) {
			counter++;
			index = msg.indexOf('\n', index + 1);
		}

		return counter;
	}
} // TextServices
