/**
 * 
 */
package org.dbdoclet.jive;

import java.awt.GridBagConstraints;

public enum Anchor {
    
    CENTER, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;
    
    public int getGbc() {

        if (this == Anchor.NORTH) {
            return GridBagConstraints.NORTH;
        }
        
        if (this == Anchor.NORTHEAST) {
            return GridBagConstraints.NORTHEAST;
        }
        
        if (this == Anchor.EAST) {
            return GridBagConstraints.EAST;
        }
        
        if (this == Anchor.SOUTHEAST) {
            return GridBagConstraints.SOUTHEAST;
        }
        
        if (this == Anchor.SOUTH) {
            return GridBagConstraints.SOUTH;
        }
        
        if (this == Anchor.SOUTHWEST) {
            return GridBagConstraints.SOUTHWEST;
        }
        
        if (this == Anchor.WEST) {
            return GridBagConstraints.WEST;
        }
        
        if (this == Anchor.NORTHWEST) {
            return GridBagConstraints.NORTHWEST;
        }
        
        return GridBagConstraints.WEST;
    }

}