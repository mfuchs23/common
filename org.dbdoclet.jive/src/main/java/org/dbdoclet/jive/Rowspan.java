package org.dbdoclet.jive;

public class Rowspan {

    public static final Rowspan RS_1 = new Rowspan(1);
    public static final Rowspan RS_2 = new Rowspan(2);
    public static final Rowspan RS_3 = new Rowspan(3);
    public static final Rowspan RS_4 = new Rowspan(4);
    public static final Rowspan RS_5 = new Rowspan(5);
    
    private int value = 1;

    public Rowspan() {
        this(1);
    }
    
    public Rowspan(int value) {
        
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
