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
package org.dbdoclet.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class HashcodeServices {

    public static long createHashcode(String value) {

        if (value == null) {
            return 0;
        }

        long hc = 0;

        for (int i = 0; i < value.length(); i++) {
            hc = ((hc * 128) + value.charAt(i)) % 99991; 
        }

        return hc;
    }

    public static long createHashcode2(String value) {

        if (value == null) {
            return 0;
        }

        long hc = 0;

        for (int i = 0; i < value.length(); i++) {
            hc = ((65599 * hc) + value.charAt(i)) % 102001;
        }
        
        return hc;
    }

    public static long createHashcode(String value, int multiplicator, int prim) {

        if (value == null) {
            return 0;
        }

        long hc = 0;

        for (int i = 0; i < value.length(); i++) {
            hc = ((multiplicator * hc) + value.charAt(i)) % prim;
        }
        
        return hc;
    }

    public static void main(String[] args) 
        throws Exception {

        String fileName = args[0];
        int multiplicator = Integer.parseInt(args[1]);
        int prim = Integer.parseInt(args[2]);
        
        System.out.println("Ok");

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();

        String value;
        
        int counter = 0;
        HashMap<Long, String> hcMap = new HashMap<Long, String>();
        
        while (line != null) {

            counter++;
            
            long hc = createHashcode(line, multiplicator, prim);
            System.out.println("hc=" + hc);

            value = (String) hcMap.get(Long.valueOf(hc));
            
            if (value == null) {
                hcMap.put(Long.valueOf(hc), line);
            } else {
                System.out.println("Doppelter Hashwert für Wert \"" + value + "\" und Wert \"" + line + "\".");
            }

            line = reader.readLine();
        }

        reader.close();

        System.out.println("Anzahl der Einträge: " + counter);
    }
}
