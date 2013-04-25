package org.dbdoclet.jive.widget;

public class GridCell {

    private int row = 0;
    private int col = 0;
    
    public GridCell() {
        this(0, 0);
    }
    
    public GridCell(int row, int col) {
        
        this.row = row;
        this.col = col;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
    public int getCol() {
        return col;
    }

    public int incrRow() {
        row++;
        return row - 1;
    }
    
    public int nextRow() {
        col = 0;
        return incrRow();
    }
    
    public int incrCol(int incr) {
        col += incr;
        return col - incr;
    }

    public int incrCol() {
        return incrCol(1);
    }

    public void resetCol() {
        col = 0;
    }
}
