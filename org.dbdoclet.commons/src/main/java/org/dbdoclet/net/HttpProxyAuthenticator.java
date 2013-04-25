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
package org.dbdoclet.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class HttpProxyAuthenticator extends Authenticator {

    private String user;
    private String password;

    public HttpProxyAuthenticator(String user, String password) {

        if (user == null) {
            user = "";
        }

        if (password == null) {
            password = "";
        }

        this.user = user;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}
