/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.jive.model;


public final class ImageFormatItem extends LabelItem {

    public ImageFormatItem(String value) {

        super();

        if (value == null) {
            throw new IllegalArgumentException("Parameter value is null!");
        }

        String label = "???";

        if (value.equalsIgnoreCase("eps")) {

            label = "EPS (Postscript)";

        } else if (value.equalsIgnoreCase("gif")) {

            label = "GIF (PDF)";

        } else if (value.equalsIgnoreCase("jpg")) {

            label = "JPEG (PDF)";

        } else if (value.equalsIgnoreCase("png")) {

            label = "PNG (PDF. Needs Jimi Library)";

        } else if (value.equalsIgnoreCase("svg")) {

            label = "SVG (PDF)";
        }

        setLabel(label);
        setValue(value);
    }
}
/*
 * $Log$
 */
