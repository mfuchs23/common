package org.dbdoclet.unit;

public enum LengthUnit {
    
    INCH("in"), MILLIMETER("mm"), POINT("pt"), PICA("pc"), PIXEL("px"), EM("em"), CENTIMETER("cm"), PERCENT("%");
    
    private final String abbrev;

    private LengthUnit(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getAbbreviation() {
        return abbrev;
    }
    
    public static LengthUnit valueOfAbbrev(String value) {
        
        if (value == null) {
            return null;
        }
        
        value = value.trim();
        
        if (value.equalsIgnoreCase("cm")) {
        	return CENTIMETER;
        }
        
        if (value.equalsIgnoreCase("em")) {
        	return EM;
        }
        
        if (value.equalsIgnoreCase("in")) {
            return INCH;
        }
        
        if (value.equalsIgnoreCase("mm")) {
            return MILLIMETER;
        }
        
        if (value.equalsIgnoreCase("pc")) {
        	return PICA;
        }
        
        if (value.equalsIgnoreCase("%")) {
        	return PERCENT;
        }

        if (value.equalsIgnoreCase("pt")) {
            return POINT;
        }
        
        return null;
    }
}
