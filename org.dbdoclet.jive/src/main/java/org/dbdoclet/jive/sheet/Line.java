package org.dbdoclet.jive.sheet;

import java.awt.geom.Point2D;

public class Line {

    private final Point2D.Double p1;
    private final Point2D.Double p2;
    
    public Line(Point2D.Double p1, Point2D.Double p2) {

        if (p1 == null) {
            throw new IllegalArgumentException("The argument p1 must not be null!");
        }
        
        if (p2 == null) {
            throw new IllegalArgumentException("The argument p2 must not be null!");
        }
        
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public Point2D.Double getP1() {
        return p1;
    }
    
    public Point2D.Double getP2() {
        return p2;
    }
    
    public double getWidth() {
        return p2.x - p1.x;
    }

    public double getX1() {
        return p1.x;
    }

    public double getX2() {
        return p2.x;
    }

    public double getY1() {
        return p1.y;
    }

    public double getY2() {
        return p2.y;
    }

    public void setY1(double y) {
       p1.y = y;
    }

    public void setY2(double y) {
       p2.y = y;
    }
}
