package org.dbdoclet.jive;

import org.dbdoclet.unit.Length;

public class RegionFrame {

    private Length top;
    private Length right;
    private Length bottom;
    private Length left;
    
    public RegionFrame(Length top, Length right, Length bottom, Length left) {
        super();
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public RegionFrame(int top, int right, int bottom, int left) {
        
        this.top = new Length(top);
        this.right = new Length(right);
        this.bottom = new Length(bottom);
        this.left = new Length(left);
    }

    public Length getTop() {
        return top;
    }

    public void setTop(Length top) {
        this.top = top;
    }

    public Length getRight() {
        return right;
    }

    public void setRight(Length right) {
        this.right = right;
    }

    public Length getBottom() {
        return bottom;
    }

    public void setBottom(Length bottom) {
        this.bottom = bottom;
    }

    public Length getLeft() {
        return left;
    }

    public void setLeft(Length left) {
        this.left = left;
    }

    public void scale(double ratio) {
        
        top.setLength(top.getLength() * ratio);
        bottom.setLength(bottom.getLength() * ratio);
        left.setLength(left.getLength() * ratio);
        right.setLength(right.getLength() * ratio);
    }

    public RegionFrame deepCopy() {

        RegionFrame copy = new RegionFrame(top.deepCopy(), right.deepCopy(), bottom.deepCopy(), left.deepCopy());
        return copy;
    }
}
