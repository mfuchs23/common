package org.dbdoclet.jive;

public class Colspan {

    public static final Colspan CS_1 = new Colspan(1);
    public static final Colspan CS_2 = new Colspan(2);
    public static final Colspan CS_3 = new Colspan(3);
    public static final Colspan CS_4 = new Colspan(4);
    public static final Colspan CS_5 = new Colspan(5);
    public static final Colspan CS_6 = new Colspan(6);
    
    private int value = 1;

    public Colspan() {
        this(1);
    }
    
    public Colspan(int value) {
        
        if (value < 1) {
            value = 1;
        }
        
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
