/* 
 * ### Copyright (C) 2005-2009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.imagechooser;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.dialog.ImageChooser;
import org.dbdoclet.jive.widget.GridPanel;

public class AccessoryPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    public AccessoryPanel(ImageChooser chooser) {

        if (chooser == null) {
            throw new IllegalArgumentException("The argument chooser may not be null!");
        }

        incrRow();
        incrRow();

        addComponent(new ImagePreview(chooser), Colspan.CS_2, Rowspan.RS_1, Anchor.CENTER, Fill.BOTH);
    }
}
