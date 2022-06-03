/* 
 * ### Copyright (C) 2005-2008 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.service;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlServices {

    public static final String USEP = "/";
    public static final String PROTOCOL_SEPARATOR = "://";
    
    public static String appendPath(URL url, String path) {
        return appendPath(url.toString(), path, false);
    }

    public static String appendPath(String url, String path) {
        return appendPath(url, path, false);
    }
    
    public static String appendPath(String url, String path, boolean endsWithSep) {

        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException("The argument url must not be null!");
        }
 
        if (path == null || path.length() == 0) {
            throw new IllegalStateException("The field path must not be null!");
        }
 
        if (url.endsWith(USEP) == false) {
            url += USEP;
        }

        if (path.startsWith(USEP) == true) {

            if (path.length() > USEP.length()) {
                path = path.substring(USEP.length());
            }
        }

        if (endsWithSep == true && path.endsWith(USEP) == false) {
            path += USEP;
        }
        
        return url + path;
    }

    public static String getProtocol(String url) {

        String protocol = "";
        
        if (url == null || url.trim().length() == 0) {
            return protocol;
        }

        try {

            URL jnu = new URL(url);
            return jnu.getProtocol();
            
        } catch (MalformedURLException oops) { /* Ignorieren, es geht weiter */ }

        int index = url.indexOf("://");
        
        if (index != -1) {
            return url.substring(0, index);
        }

        return "";
    }
    
    public static String getPath(String url) {

        String path = "";
        
        if (url == null || url.trim().length() == 0) {
            return path;
        }

        try {

            URL jnu = new URL(url);
            return jnu.getPath();
            
        } catch (MalformedURLException oops) { /* Ignorieren, es geht weiter */ }

        if (url.startsWith("cocs://")) {
            
            path = StringServices.cutPrefix(url, "cocs://");            
            int index = path.indexOf('?');
            
            if (index != -1) {
                path = path.substring(0, index);
            }

            return path;
        }

        return path;
    }

    public static String getQuery(String url) {

        String query = "";
        
        if (url == null || url.trim().length() == 0) {
            return query;
        }

        try {

            URL jnu = new URL(url);
            return jnu.getQuery();
            
        } catch (MalformedURLException oops) { /* Ignorieren, es geht weiter */ }

        if (url.startsWith("cocs://")) {
            
            int index = url.indexOf('?');
            
            if (index != -1) {

                query = url.substring(index);
                query = StringServices.cutPrefix(query, "?");
            }

            return query;
        }

        return query;
    }

    public static String getParameter(String url, String key) {

        String param = "";
        
        if (url == null || url.trim().length() == 0) {
            return param;
        }

        if (key == null || key.trim().length() == 0) {
            return param;
        }

        String query = getQuery(url);
        
        query = StringServices.cutPrefix(query, "?");

        String[] paramList = query.split("&");

        for (int i = 0; i < paramList.length; i++) {

            String[] token = paramList[i].split("=");
            
            if (token.length == 2) {

                if (token[0].equals(key)) {
                    param = token[1];
                    break;
                }
            }
        }

        return param;
    }

    public static String getName(String url) {

        if (url == null || url.length() == 0) {
            return url;
        }

        while (url.endsWith("/") == true && url.length() > 0) {
            url = StringServices.chop(url, "/");
        }

        int index = url.lastIndexOf("/");

        if (index == -1) {
            return url;
        }
        
        return url.substring(index + 1);
    }
    
    public static String getParent(String url) {

        if (url == null || url.length() == 0 || url.equals("/")) {
            return url;
        }

        while (url.endsWith("/") == true && url.length() > 0) {
            url = StringServices.chop(url, "/");
        }

        int index = url.lastIndexOf("/");

        if (index == -1) {
            return url;
        }
        
        return url.substring(0, index);
    }
    
    public static String getLastPart(String url) {
        
        if (url == null || url.length() == 0 || url.equals("/")) {
            return url;
        }
        
        int index; 

        if (url.endsWith("/") && url.length() > 1) {
            index = url.lastIndexOf("/", url.length() - 2);
        } else {
            index = url.lastIndexOf("/");
        }

        if (index == -1) {
            return url;
        }
        
        if (index < url.length() - 2) {
            return url.substring(index + 1);
        } else {
            return url;
        }
    }

    public static String normalize(URL url) {
        
        return normalize(url.toString());
    }

    public static String normalize(String url) {

        if (url == null || url.length() == 0 || url.equals("/")) {
            return url;
        }

        url = StringServices.chop(url, "/");
        url = url.toLowerCase();

        return url;
    }

    public static String normalizePath(String path) {

        if (path == null || path.length() == 0 || path.equals("/")) {
            return path;
        }

        path = StringServices.chop(path, "/");
        path = path.toLowerCase();

        return path;
    }
}
