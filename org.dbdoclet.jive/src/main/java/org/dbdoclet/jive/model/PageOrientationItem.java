/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: PageOrientationItem.java,v 1.1.1.1 2004/12/21 13:56:57 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 13:56:57 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.jive.model;

import java.util.ResourceBundle;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;


public final class PageOrientationItem extends LabelItem {

    public PageOrientationItem(String value) {

        super();

        if (value == null) {
            throw new IllegalArgumentException("Parameter value is null!");
        }

        JiveFactory widgetMap = JiveFactory.getInstance();
        ResourceBundle res = widgetMap.getResourceBundle();

        String label = "???";

        if (value.equalsIgnoreCase("portrait")) {
            label = ResourceServices.getString(res,"C_PORTRAIT");
        } else if (value.equalsIgnoreCase("landscape")) {
            label = ResourceServices.getString(res,"C_LANDSCAPE");
        }

        setLabel(label);
        setValue(value);
    }
}

/*
 * $Log: PageOrientationItem.java,v $
 * Revision 1.1.1.1  2004/12/21 13:56:57  mfuchs
 * Reimport
 *
 * Revision 1.3  2004/10/14 20:21:30  mfuchs
 * Windows-Unterstützung
 *
 * Revision 1.2  2004/10/05 13:09:49  mfuchs
 * PathList
 *
 * Revision 1.1.1.1  2004/02/17 22:34:42  mfuchs
 * jive
 *
 * Revision 1.3  2004/01/16 08:02:50  mfuchs
 * Hilfetexte
 *
 * Revision 1.2  2004/01/13 20:44:06  mfuchs
 * Weiterentwicklung
 *
 * Revision 1.1.1.1  2003/10/21 19:24:13  cvs
 * A Swing library.
 *
 * Revision 1.1.1.1  2003/08/01 13:19:49  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
 * DocBook Doclet 0.40
 *
 * Revision 1.1.1.1  2003/03/17 20:51:54  cvs
 * dbdoclet
 *
 */
