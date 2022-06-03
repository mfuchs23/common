/* 
 * ### Copyright (C) 2001-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.service;

import java.io.File;
import java.util.List;

import org.dbdoclet.Sfv;

/**
 * Die Klasse <code>StringServices</code> stellt eine Sammlung statischer
 * Methoden zur Bearbeitung von Zeichenketten und/oder deren
 * Internationalisierung zur Verfügung.
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * 
 */
public class StringServices {

	public static final String ZERO_WIDTH_SPACE = "\u200b";
	public static final String SIX_PER_EM_SPACE = "\u2006";
	public static final String SOFT_HYPHEN = "\u00ad";

	/**
	 * Die Methode <code>replace</code> ersetzt alle Vorkomnisse eines
	 * bestimmten Musters durch eine Zeichenkette.
	 * 
	 * Die orginale Zeichenkette bleibt unverändert.
	 * 
	 * Das Muster kann in der Zeichenkette durch geschweifte Klammern begrenzt
	 * sein, um Anfang und Ende einer Ersetzung zu kennzeichnen.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @param pattern
	 *            Das Suchmuster
	 * @param subst
	 *            Die Ersetzung
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String replace(String str, String pattern, String subst) {

		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return str;
		}

		if (subst == null) {
			subst = "";
		}

		if (pattern.equals(subst)) {
			return str;
		}

		int fromIndex = 0;
		int toIndex = 0;

		String pattern2 = "{" + pattern + "}";

		if (str.indexOf(pattern) == -1 && str.indexOf(pattern2) == -1) {
			return str;
		}

		StringBuffer buffer = new StringBuffer();

		while ((toIndex = str.indexOf(pattern2, toIndex)) != -1) {

			buffer.append(str.substring(fromIndex, toIndex));
			buffer.append(subst);

			fromIndex = toIndex + pattern2.length();
			toIndex = fromIndex;

		} // end of while ()

		buffer.append(str.substring(fromIndex));

		fromIndex = 0;
		toIndex = 0;

		str = buffer.toString();

		buffer = new StringBuffer();

		while ((toIndex = str.indexOf(pattern, toIndex)) != -1) {

			buffer.append(str.substring(fromIndex, toIndex));
			buffer.append(subst);

			fromIndex = toIndex + pattern.length();
			toIndex = fromIndex;

		} // end of while ()

		buffer.append(str.substring(fromIndex));

		return buffer.toString();
	}

	/**
	 * Die Methode <code>replace</code> ersetzt alle Vorkomnisse eines
	 * bestimmten Musters durch eine Zeichenkette ohne die Groß/Kleinscrebung zu
	 * beachten.
	 * 
	 * Die orginale Zeichenkette bleibt unverändert.
	 * 
	 * Das Muster kann in der Zeichenkette durch geschweifte Klammern begrenzt
	 * sein, um Anfang und Ende einer Ersetzung zu kennzeichnen.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @param pattern
	 *            Das Suchmuster
	 * @param subst
	 *            Die Ersetzung
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String replaceIgnoreCase(String str, String pattern,
			String subst) {

		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return str;
		}

		if (subst == null) {
			subst = "";
		}

		int fromIndex = 0;
		int toIndex = 0;

		String buffer = "";

		String lowerStr = str.toLowerCase();
		String lowerPattern = pattern.toLowerCase();

		String pattern2 = "{" + lowerPattern + "}";

		while ((toIndex = lowerStr.indexOf(pattern2, toIndex)) != -1) {
			buffer += str.substring(fromIndex, toIndex);
			buffer += subst;

			fromIndex = toIndex + pattern2.length();
			toIndex = fromIndex;

		} // end of while ()

		buffer += str.substring(fromIndex);

		fromIndex = 0;
		toIndex = 0;
		str = buffer;
		buffer = "";

		while ((toIndex = lowerStr.indexOf(lowerPattern, toIndex)) != -1) {
			buffer += str.substring(fromIndex, toIndex);
			buffer += subst;

			fromIndex = toIndex + lowerPattern.length();
			toIndex = fromIndex;

		} // end of while ()

		buffer += str.substring(fromIndex);

		return buffer;
	}

	/**
	 * Die Methode <code>createIndent</code> erzeugt eine Einrückung aus
	 * Leerzeichen der Länge <code>len</code>.
	 * 
	 * @param len
	 *            <code>int</code>
	 * @return <code>String</code>
	 */
	public static String createIndent(int len) {

		StringBuffer indent = new StringBuffer();

		for (int i = 0; i < len; i++) {
			indent.append(' ');
		}

		return indent.toString();
	}

	/**
	 * Die Methode <code>capFirstLetter</code> wandelt den ersten Buchstaben
	 * einer Zeichenkette in einen Großbuchstaben um.
	 * 
	 * Die orginale Zeichenkette bleibt unverändert.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String capFirstLetter(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}

		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);

		return new String(chars);
	}

	/**
	 * Liefert den ersten Buchstaben einer Zeichenkette.
	 */
	public static String getFirstLetter(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}

		char fc = str.toCharArray()[0];
		char[] chars = new char[1];
		chars[0] = fc;

		return new String(chars);
	}

	/**
	 * Die Methode <code>lowerFirstLetter</code> wandelt den ersten Buchstaben
	 * einer Zeichenkette in einen Kleinbuchstaben um.
	 * 
	 * Die orginale Zeichenkette bleibt unverändert.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String lowerFirstLetter(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}

		char[] chars = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);

		return new String(chars);
	}

	/**
	 * Die Methode <code>createJavaIdentifier</code> erzeugt aus einer
	 * Zeichenkette einen gültiges Java Schlüsselwort.
	 * 
	 * Falls der Parameter <code>mangleUnderscores</code> auf wahr gesetzt ist,
	 * werden auch Unterstriche entfernt. Dies ist notwendig, um
	 * <code>jspc.sh</code> konforme Klassennamen zu erzeugen.
	 * <code>jspc.sh</code> ist der JSP-Compiler aus der Tomcat-Distribution.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @param mangleUnderscores
	 *            <code>boolean</code>
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String createJavaIdentifier(String str,
			boolean mangleUnderscores) {

		if (str == null || str.length() == 0) {
			return str;
		}

		StringBuffer identifier = new StringBuffer();

		if (Character.isJavaIdentifierStart(str.charAt(0)) == false) {
			identifier.append("i");
		}

		for (int i = 0; i < str.length(); i++) {

			if (Character.isJavaIdentifierPart(str.charAt(i)) == false) {

				if (mangleUnderscores == true) {
					identifier.append(mangleChar(str.charAt(i), "0x"));
				} else {
					identifier.append(mangleChar(str.charAt(i), "_"));
				}

			} else {

				char c = str.charAt(i);

				if (c == '_' && mangleUnderscores == true) {
					identifier.append(mangleChar(str.charAt(i), "0x"));
				} else {
					identifier.append(str.charAt(i));
				}

			}

		} // end of for ()

		return identifier.toString();
	}

	/**
	 * Die Methode <code>createJavaIdentifier</code> erzeugt aus einer
	 * Zeichenkette einen gültiges Java Schlüsselwort.
	 * 
	 * @param str
	 *            Die Zeichenkette
	 * @return Die bearbeitete Zeichenkette
	 */
	public static String createJavaIdentifier(String str) {
		return createJavaIdentifier(str, false);
	}

	/**
	 * Die Methode <code>mangleChar</code> übersetzt ein Schriftzeichen in eine
	 * numerische Darstellung.
	 * 
	 * Die resultierende Zeichenkette startet mit einem Vorspann dem ein 5
	 * Spalten breiter, hexadezimaler Wert folgt. starts with a prefix, the
	 * 
	 * Beispiel: <code>_0002f</code>
	 * 
	 * @param c
	 *            a <code>char</code> value
	 * @param prefix
	 *            a <code>String</code> value
	 * @return <code>String</code>
	 */
	public static String mangleChar(char c, String prefix) {

		String s = Integer.toHexString(c);

		int nzeros = 5 - s.length();

		char[] result = new char[5];

		for (int i = 0; i < nzeros; i++) {
			result[i] = '0';
		}

		for (int i = nzeros, j = 0; i < 5; i++, j++) {
			result[i] = s.charAt(j);
		}

		return (prefix + new String(result));
	}

	/**
	 * Die Methode <code>createHeadline</code> erzeugt eine Überschrift der Form
	 * <code>**** title ****</code>.
	 * 
	 * @param title
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String createHeadline(String title) {

		if (title == null) {
			throw new IllegalArgumentException(
					"The argument title may not be null!");
		}

		StringBuffer sep = new StringBuffer();

		for (int i = 0; i < title.length() + 4; i++) {
			sep.append('*');
		}

		String buffer = sep + "\n" + "* " + title + " *\n" + sep + "\n\n";

		return buffer;
	}

	/**
	 * Die Methode <code>info</code> erzeugt eine Informationszeile der Form
	 * <code>info.... ..: </code>.
	 * 
	 * Die Länge der erzeugten Zeichenkette beträgt 50 Zeichen.
	 * 
	 * @param line
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String info(String line) {
		return align(line, 50, '.') + ": ";
	}

	/**
	 * Die Methode <code>info</code> erzeugt eine Informationszeile der Form
	 * <code>info.... ..</code>.
	 * 
	 * Die Länge der erzeugten Zeichenkette beträgt 50 Zeichen.
	 * 
	 * @param line
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String align(String line) {
		return align(line, 50, '.');
	}

	/**
	 * Die Methode <code>info</code> erzeugt eine Informationszeile der Form
	 * <code>info.... ..</code>.
	 * 
	 * Die Länge der erzeugten Zeichenkette beträgt <code>width</code> Zeichen.
	 * 
	 * @param line
	 *            <code>String</code>
	 * @param width
	 *            <code>int</code>
	 * @return <code>String</code>
	 */
	public static String align(String line, int width) {
		return align(line, width, '.');
	}

	/**
	 * Die Methode <code>info</code> erzeugt eine Informationszeile der Form
	 * <code>info... ..</code>.
	 * 
	 * Die Länge der erzeugten Zeichenkette beträgt <code>width</code> Zeichen.
	 * Das verwendete Trennzeichen kann mit Hilfe des Parameters
	 * <code>fill</code> angegeben werden.
	 * 
	 * @param line
	 *            <code>String</code>
	 * @param width
	 *            <code>int</code>
	 * @param fill
	 *            <code>char</code>
	 * @return <code>String</code>
	 */
	public static String align(String line, int width, char fill) {

		if (line == null || line.length() == 0) {
			throw new IllegalArgumentException(
					"The argument line may not be null!");
		}

		StringBuffer buffer = new StringBuffer(line);

		if (line.length() >= width) {

			buffer.append(fill);
			buffer.append(fill);
			buffer.append(fill);

			return buffer.toString();
		}

		for (int i = line.length(); i < width; i++) {
			buffer.append(fill);
		}

		return buffer.toString();
	}

	/**
	 * The method <code>align</code> returns a string prefix for the output of a
	 * double. If the double is greater than 100 the method returns an empty
	 * string. If the double is between 100 and 10 the method returns one space,
	 * and if the double is less than 10 it returns two spaces.
	 * 
	 * @param d
	 *            a <code>double</code> value
	 * @return <code>String</code>
	 * */
	public static String align(double d) {
		return align((int) d);
	}

	/**
	 * The method <code>align</code> returns a string prefix for the output of
	 * an integer. If the integer is greater than 100 the method returns an
	 * empty string. If the integer is between 100 and 10 the method returns one
	 * space, and if the integer is less than 10 it returns two spaces.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @return <code>String</code>
	 * */
	public static String align(int i) {

		if (i >= 1000) {
			return "";
		} // end of if ()

		if (i >= 100) {
			return " ";
		} // end of if ()

		if (i >= 10) {
			return "  ";
		} // end of if ()

		return "   ";
	}

	/**
	 * Die Methode <code>chop</code> bzw. <code>cutSuffix</code> schneidet die
	 * mit <code>suffix</code> angegebene Zeichenkette vom Ende der Zeichenkette
	 * <code>suffix</code> ab.
	 * 
	 * @param text
	 *            <code>String</code>
	 * @param suffix
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String cutSuffix(String text, String suffix) {

		if (suffix == null) {
			return text;
		}

		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return text;
		}

		int index;

		if (text.endsWith(suffix)) {

			index = text.lastIndexOf(suffix);

			if (index != -1) {
				text = text.substring(0, index);
			}
		}

		return text;
	}

	/**
	 * Ein Alias für <code>cutSuffix</code>.
	 */
	public static String chop(String text, String suffix) {
		return cutSuffix(text, suffix);
	}

	/**
	 * Löscht den Buchstaben <code>c</code> vom Anfang und Ende der Zeichenkette
	 * <code>text</code>.
	 */
	public static String trim(String text, char c) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(c);

		return trim(text, buffer.toString());
	}

	/**
	 * Löscht das Muster <code>pattern</code> vom Anfang und Ende der
	 * Zeichenkette <code>text</code>.
	 */
	public static String trim(String text, String pattern) {

		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return text;
		}

		while (text.startsWith(pattern)) {
			text = cutPrefix(text, pattern);
		}

		while (text.endsWith(pattern)) {
			text = cutSuffix(text, pattern);
		}

		return text;
	}

	/**
	 * Die Methode <code>cut</code> schneidet die erste mit <code>pattern</code>
	 * angegebene Zeichenkette aus der Zeichenkette <code>text</code> aus.
	 * 
	 * @param text
	 *            <code>String</code>
	 * @param pattern
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String cut(String text, String pattern) {

		if (text == null) {
			throw new IllegalArgumentException("Parameter text is null!");
		}

		if (pattern == null) {
			throw new IllegalArgumentException("Parameter pattern is null!");
		}

		String buffer;
		int start;
		int end;

		buffer = text;

		start = text.indexOf(pattern);

		if (start != -1) {

			buffer = text.substring(0, start);

			end = start + pattern.length();

			if (end < text.length()) {
				buffer += text.substring(end, text.length());
			}
		}

		return buffer;
	}

	/**
	 * Die Methode <code>cutPrefix</code> entfernt die Zeichenkette
	 * <code>prefix</code> vom Anfang der Zeichenkette <code>text</code>.
	 * 
	 * @param text
	 *            <code>String</code>
	 * @param prefix
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String cutPrefix(String text, String prefix) {

		if (text == null) {
			throw new IllegalArgumentException("Parameter text is null!");
		}

		if (prefix == null) {
			throw new IllegalArgumentException("Parameter prefix is null!");
		}

		if (text.length() < prefix.length()) {
			return text;
		}

		if (text.equals(prefix)) {
			return "";
		}

		String buffer = text;

		if (text.startsWith(prefix)) {

			if (text.length() == prefix.length()) {
				buffer = "";
			} else {
				buffer = text.substring(prefix.length());
			}
		}

		return buffer;
	}

	/**
	 * Kürzt die Zeichenkette <code>text</code> auf die maximale Länge von
	 * <code>cols</code>.
	 * 
	 * Ist die Zeichenkette <code>text</code> länger als <code>cols</code>
	 * werden die letzten 3 Zeichen durch Punkte ersetzt.
	 */
	public static String shorten(String text, int cols) {

		if (text == null || text.length() == 0) {
			return "";
		}

		if (cols < 5) {
			return text;
		}

		if (text.length() <= cols) {
			return text;
		}

		String msg = new String(text);
		msg = text.substring(0, (cols / 2) - 3);
		msg += "...";
		msg += text.substring(text.length() - (cols / 2));

		return msg;
	}

	public static String splitAt(String text) {
		return splitAt(text, File.separator, 71, Sfv.LSEP);
	}

	/**
	 * Fügt das Trennzeichen <code>Sfv.LSEP</code> an Positionen des Muster
	 * <code>breakable</code> ein, falls die Position innerhalb der Teilkette
	 * größer als 71 ist.
	 */
	public static String splitAt(String text, String breakable) {
		return splitAt(text, breakable, 71, Sfv.LSEP);
	}

	/**
	 * Fügt in eine Zeichenkette Trennzeichen ein.
	 * 
	 * Die Zeichenkette <code>text</code> daraufhin untersucht ob an bestimmten
	 * Stellen Trennzeichen eingefügt werden können. Der Parameter
	 * <code>breakPos</code> bestimmt die minimale Länge einer Teilkette bevor
	 * deas nächste Trennzeichen eingfügt werden kann. Der Parameter
	 * <code>breakable</code> definiert das Muster an dem das Einfügen von
	 * Trennzeichen möglich ist.
	 */
	public static String splitAt(String text, String breakable, int breakPos,
			String splitter) {

		if (text == null || text.length() == 0) {
			return "";
		}

		if (text.length() <= 3) {
			return text;
		}

		if (breakable == null || breakable.length() == 0) {
			return text;
		}

		if (breakPos <= 0 || breakPos >= text.length()) {
			return text;
		}

		if (splitter == null || splitter.length() == 0) {
			return text;
		}

		boolean doSplit = true;
		StringBuffer buffer = new StringBuffer();

		while (doSplit == true) {

			int lessIndex = 0;
			int greaterIndex = 0;

			int index = text.indexOf(breakable);

			while (index != -1) {

				if (index <= breakPos) {
					lessIndex = index;
				}

				if (index >= breakPos && greaterIndex == 0) {
					greaterIndex = index;
				}

				index = text.indexOf(breakable, index + 1);
			}

			if (greaterIndex == 0) {
				greaterIndex = text.length();
			}

			if (lessIndex == 0) {
				lessIndex = breakPos;
			}

			int lessDistance = breakPos - lessIndex;
			int greaterDistance = greaterIndex - breakPos;

			// StringBuffer buffer = new StringBuffer(text);

			if (lessDistance <= greaterDistance
					|| greaterIndex >= text.length()) {

				buffer.append(text.substring(0, lessIndex));
				buffer.append(splitter);

				text = text.substring(lessIndex);
			}

			if (greaterDistance < lessDistance && greaterIndex < text.length()) {

				buffer.append(text.substring(0, greaterIndex));
				buffer.append(splitter);

				text = text.substring(greaterIndex);
			}

			if (text.length() <= breakPos) {

				buffer.append(text);
				doSplit = false;
			}

			/*
			 * logger.debug("text=" + text); logger.debug("text.length=" +
			 * text.length()); logger.debug("breakPos " + breakPos);
			 * logger.debug("lessIndex " + lessIndex);
			 * logger.debug("greaterIndex " + greaterIndex);
			 * logger.debug("lessDistance " + lessDistance);
			 * logger.debug("greaterDistance " + greaterDistance);
			 */
		}

		return buffer.toString();
	}

	public static String fillInt(int num, int width) {

		if (num < 1) {
			num *= -1;
		}

		String buffer = String.valueOf(num);

		while (width > buffer.length()) {
			buffer = "0" + buffer;
		}

		return buffer;
	}

	public static String fillLeadingZero(String num, int width) {

		String buffer = String.valueOf(num);
		boolean negative = false;

		if (buffer.startsWith("-")) {

			buffer = buffer.substring(1);
			negative = true;
		}

		while (width > buffer.length()) {
			buffer = "0" + buffer;
		}

		if (negative == true) {
			buffer = "-" + buffer;
		}

		return buffer;
	}

	public static String makeWrapable(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}

		String[] tokens = { "/", "\\", "-" };

		String wrapable = new String(str);

		for (String token : tokens) {

			if (token == null || token.length() == 0) {
				continue;
			}

			// zero width space
			wrapable = StringServices.replace(wrapable, token, token
					+ ZERO_WIDTH_SPACE);
		}

		return wrapable;
	}

	public static String arrayToString(String[] array) {

		return arrayToString(array, " ");
	}

	public static String arrayToString(String[] array, String sep) {

		if (array == null || array.length == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < array.length; i++) {

			buffer.append(array[i]);
			buffer.append(sep);
		}

		return buffer.toString();
	}

	public static String listToString(List<? extends Object> list) {

		return listToString(list, Sfv.LSEP);
	}

	public static String listToString(List<? extends Object> list, String sep) {

		if (list == null || list.size() == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {

			buffer.append(list.get(i));
			buffer.append(sep);
		}

		return buffer.toString();
	}

	/**
	 * Erstellt einen Rahmen aus Linienzeichen um den angegebenen Text.
	 * 
	 * @param text
	 * @return
	 */
	public static String createBox(String text) {

		StringBuilder buffer = new StringBuilder();

		buffer.append('\u2554');

		for (int i = 0; i < text.length() + 2; i++) {
			buffer.append('\u2550');
		}

		buffer.append('\u2557');
		buffer.append('\n');
		buffer.append("\u2551 ");
		buffer.append(text);
		buffer.append(" \u2551\n");

		buffer.append('\u255A');

		for (int i = 0; i < text.length() + 2; i++) {
			buffer.append('\u2550');
		}

		buffer.append('\u255D');
		buffer.append('\n');

		return buffer.toString();
	}
}
