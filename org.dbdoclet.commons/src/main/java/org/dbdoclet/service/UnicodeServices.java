package org.dbdoclet.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Die Klasse <code>UnicodeServices</code> stellt eine Sammlung statischer
 * Methoden zur Bearbeitung von Unicode zur Verfügung.
 *
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class UnicodeServices {

    private static Pattern pattern = Pattern.compile("\\\\u[0-9a-fA-F]{4}");

    public static String escape(char c) {

        char[] v = new char[1];
        v[0] = c;

        return escape(new String(v));
    }

    /**
     * Die Methode <code>escape</code> wandelt die Zeichenkette
     * <code>str</code> in eine 7-bit ASCII Darstellung um.
     *
     * @param str <code>String</code>
     * @return <code>String</code>
     */
    public static String escape(String str) {

        if (str == null) {
            return "";
        }
        
        StringBuffer buffer = new StringBuffer();
        
        for (int i = 0; i < str.length(); i++) {
            
            char c = str.charAt(i);
            int n = c;

            if (c == '\r') {
                continue;
            }

            if (n >= 0 && n < 128 && c != '\n' && c != '\\') {
 
               buffer.append(c);

            } else {

                buffer.append("\\u");
                String hex = Integer.toHexString(n);

                for (int j = 0; j < 4 - hex.length(); j++) {
                    buffer.append("0");
                }

                buffer.append(hex);
            }
        }

        return buffer.toString();
    }

    public static String unescape(String str) {

        Matcher matcher = pattern.matcher(str);

        char[] c = new char[1];
        String escape;
        String buffer;
        String part1;
        String part2;

        while (matcher.find()) {

            escape = matcher.group();
            c[0] = (char) Integer.parseInt(escape.substring(2), 16);
            buffer = new String(c);
            // System.out.println("buffer = " + buffer);

            part1 = str.substring(0, matcher.start());
            // System.out.println("part1 = " + part1);

            part2 = str.substring(matcher.end());
            // System.out.println("part2 = " + part2);
            
            str = part1 + buffer + part2;
            // System.out.println("str = " + str);

            matcher.reset(str);
        }

        return str;
    }

    public static String removeUndefinedCharacters(String text) {
        
        if (text == null || text.length() == 0) {
            return "";
        }

        StringBuffer textBuffer = new StringBuffer(text);
        StringBuffer buffer = new StringBuffer();
        
        char c;
        
        for (int i = 0; i < textBuffer.length(); i++) {

            c = textBuffer.charAt(i);
            
            if (c == '\n' || c == '\t') {
                buffer.append(c);
                continue;
            }

            if (Character.isISOControl(c) == false 
                && Character.isDefined(c) == true) {

                buffer.append(c);
            }
        }

        return buffer.toString();
    }
}
