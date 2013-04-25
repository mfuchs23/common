package org.dbdoclet.jive.fo;

import javax.swing.JSpinner;

import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.unit.Length;

public class FoAttributePanel extends GridPanel {

	private static final long serialVersionUID = 1L;

    public static String getDistance(JSpinner spinner) {

        if (spinner == null) {
            return "";
        }

        Length distance = (Length) spinner.getValue();
        return distance.toNormalizedString();
    }
}
