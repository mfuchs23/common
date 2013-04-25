/**
 * 
 */
package org.dbdoclet.jive;

import java.awt.GridBagConstraints;

public enum Fill {
    BOTH, HORIZONTAL, NONE, VERTICAL;

    public int getGbc() {

        if (this == Fill.BOTH) {
            return GridBagConstraints.BOTH;
        }
        
        if (this == Fill.HORIZONTAL) {
            return GridBagConstraints.HORIZONTAL;
        }
        
        if (this == Fill.VERTICAL) {
            return GridBagConstraints.VERTICAL;
        }
        
        return GridBagConstraints.NONE;
    }
}