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
package org.dbdoclet.manager;

import java.util.HashMap;

import org.dbdoclet.DuplicateHashCodeException;

public class HashcodeManager {

    private static HashcodeManager singleton = null;
    
    private HashMap<Long, String> hcMap;
    private HashMap<String, Long> moduleMap;

    private HashcodeManager() {

        hcMap = new HashMap<Long, String>();
        moduleMap = new HashMap<String, Long>();
    }

    public static HashcodeManager getInstance() {

        if (singleton == null) {
            singleton = new HashcodeManager();
        }
        
        return singleton;
    }

    public long createHashcode(String value)
        throws DuplicateHashCodeException {

        if (value == null) {
            return 0;
        }

        long hc = 0;

        for (int i = 0; i < value.length(); i++) {
            hc = ( hc * 128 + value.charAt(i)) % 999999937; 
        }

        String str = hcMap.get(Long.valueOf(hc));

        if (str != null && value.equals(str) == false) {

            throw new DuplicateHashCodeException("Duplicate Hash Entry " + hc + " for entries " + value + " and " + str + "!");

        } else {

            hcMap.put(Long.valueOf(hc), value);
            moduleMap.put(value, Long.valueOf(hc));
        }

        return hc;
    }

    public long getHashcode(String value) {

        if (value == null) {
            return -1;
        }

        Long hc = moduleMap.get(value);

        if (hc == null) {
            return -1;
        }

        return hc.longValue();
    }
    
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.out.println("hc <VALUE> <VALUE> ...");
            return;
        }
        
        HashcodeManager hc = getInstance();
        for (String value : args) {
            try {
                System.out.println(hc.createHashcode(value));
            } catch (DuplicateHashCodeException e) {
                e.printStackTrace();
            }
        }
    }
}        

