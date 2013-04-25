/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.service;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateServices {

    public static Date calendarToDate(XMLGregorianCalendar xmlCalendar) {

        if (xmlCalendar == null) {
            return null;
        }

        GregorianCalendar calendar = xmlCalendar.toGregorianCalendar();
        return calendar.getTime();
    }
}
