package org.dbdoclet.jive.sheet;

import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;

public class PaperSize {

    public static PaperSize A0 = new PaperSize(841, 1189, LengthUnit.MILLIMETER);
    public static PaperSize A1 = new PaperSize(594, 841, LengthUnit.MILLIMETER);
    public static PaperSize A2 = new PaperSize(420, 594, LengthUnit.MILLIMETER);
    public static PaperSize A3 = new PaperSize(297, 420, LengthUnit.MILLIMETER);
    public static PaperSize A4 = new PaperSize(210, 297, LengthUnit.MILLIMETER);
    public static PaperSize A5 = new PaperSize(148, 210, LengthUnit.MILLIMETER);
    public static PaperSize A6 = new PaperSize(105, 148, LengthUnit.MILLIMETER);
    public static PaperSize A7 = new PaperSize(74, 105, LengthUnit.MILLIMETER);
    public static PaperSize A8 = new PaperSize(52, 74, LengthUnit.MILLIMETER);
    public static PaperSize A9 = new PaperSize(37, 52, LengthUnit.MILLIMETER);
    public static PaperSize A10 = new PaperSize(26, 37, LengthUnit.MILLIMETER);
    public static PaperSize B0 = new PaperSize(1000, 1414, LengthUnit.MILLIMETER);
    public static PaperSize B1 = new PaperSize(707, 1000, LengthUnit.MILLIMETER);
    public static PaperSize B2 = new PaperSize(500, 707, LengthUnit.MILLIMETER);
    public static PaperSize B3 = new PaperSize(353, 500, LengthUnit.MILLIMETER);
    public static PaperSize B4 = new PaperSize(250, 353, LengthUnit.MILLIMETER);
    public static PaperSize B5 = new PaperSize(176, 250, LengthUnit.MILLIMETER);
    public static PaperSize B6 = new PaperSize(125, 176, LengthUnit.MILLIMETER);
    public static PaperSize B7 = new PaperSize(88, 125, LengthUnit.MILLIMETER);
    public static PaperSize B8 = new PaperSize(62, 88, LengthUnit.MILLIMETER);
    public static PaperSize B9 = new PaperSize(44, 62, LengthUnit.MILLIMETER);
    public static PaperSize B10 = new PaperSize(31, 44, LengthUnit.MILLIMETER);
    public static PaperSize C0 = new PaperSize(917, 1297, LengthUnit.MILLIMETER);
    public static PaperSize C1 = new PaperSize(648, 917, LengthUnit.MILLIMETER);
    public static PaperSize C2 = new PaperSize(458, 648, LengthUnit.MILLIMETER);
    public static PaperSize C3 = new PaperSize(324, 458, LengthUnit.MILLIMETER);
    public static PaperSize C4 = new PaperSize(229, 324, LengthUnit.MILLIMETER);
    public static PaperSize C5 = new PaperSize(162, 229, LengthUnit.MILLIMETER);
    public static PaperSize C6 = new PaperSize(114, 162, LengthUnit.MILLIMETER);
    public static PaperSize C7 = new PaperSize(81, 114, LengthUnit.MILLIMETER);
    public static PaperSize C8 = new PaperSize(57, 81, LengthUnit.MILLIMETER);
    public static PaperSize C9 = new PaperSize(40, 57, LengthUnit.MILLIMETER);
    public static PaperSize C10 = new PaperSize(28, 40, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur1 = new PaperSize(420, 680, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur2 = new PaperSize(300, 420, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur3 = new PaperSize(300, 400, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur4 = new PaperSize(285, 400, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur5 = new PaperSize(300, 390, LengthUnit.MILLIMETER);
    public static PaperSize Großpartitur6 = new PaperSize(290, 350, LengthUnit.MILLIMETER);
    public static PaperSize Quartformat = new PaperSize(270, 340, LengthUnit.MILLIMETER);
    public static PaperSize QuartformatUS = new PaperSize(215, 275, LengthUnit.MILLIMETER);
    public static PaperSize OrgelformatBach = new PaperSize(330, 260, LengthUnit.MILLIMETER);
    public static PaperSize OrgelformatPeters1 = new PaperSize(300, 230, LengthUnit.MILLIMETER);
    public static PaperSize OrgelformatPeters2 = new PaperSize(305, 220, LengthUnit.MILLIMETER);
    public static PaperSize OrgelformatSteingräber = new PaperSize(265, 220, LengthUnit.MILLIMETER);
    public static PaperSize Bachformat = new PaperSize(240, 325, LengthUnit.MILLIMETER);
    public static PaperSize Oktavformat = new PaperSize(170, 270, LengthUnit.MILLIMETER);
    public static PaperSize OktavformatUS = new PaperSize(171, 267, LengthUnit.MILLIMETER);
    public static PaperSize Studienpartitur = new PaperSize(170, 240, LengthUnit.MILLIMETER);
    public static PaperSize Salonorchester = new PaperSize(190, 290, LengthUnit.MILLIMETER);
    public static PaperSize Klavierauszug = new PaperSize(190, 270, LengthUnit.MILLIMETER);
    public static PaperSize Klavierformat = new PaperSize(235, 310, LengthUnit.MILLIMETER);
    public static PaperSize Großmarsch = new PaperSize(135, 190, LengthUnit.MILLIMETER);
    public static PaperSize Marschformat = new PaperSize(135, 170, LengthUnit.MILLIMETER);
    public static PaperSize USLetter = new PaperSize(8.5f, 11.0f, LengthUnit.INCH);
    public static PaperSize USLegal = new PaperSize(8.5f, 14.0f, LengthUnit.INCH);
    public static PaperSize USExecutive = new PaperSize(7.2f, 10.5f, LengthUnit.INCH);

    private Length width;
    private Length height;

    public PaperSize(double widthLength, double heightLength, LengthUnit unit) {

        this.width = new Length(widthLength, unit);
        this.height = new Length(heightLength, unit);
    }

    public PaperSize(Length width, Length height) {
        
        this.width = width;
        this.height = height;
    }

    public PaperSize deepCopy() {

        PaperSize copy = new PaperSize(width, height);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        PaperSize other = (PaperSize) obj;
        
        if (height == null && other.height != null) {
                return false;
        }
        
        if (height.equals(other.height) == false) {
            return false;
        }
        
        if (width == null && other.width != null) {
                return false;
        }
        
        if (width.equals(other.width) == false) {
            return false;
        }
        
        return true;
    }

    public Length getHeight() {
        return height;
    }

    public String getHeightAsText() {
        return String.format("%.2f %s", height.getLength(), height.getUnit().getAbbreviation());
    }

    public Length getWidth() {
        return width;
    }

    public String getWidthAsText() {
        return String.format("%.2f %s", width.getLength(), width.getUnit().getAbbreviation());
    }

    @Override
    public int hashCode() {
        
        final int prime = 31;
        int result = 1;
        
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        
        return result;
    }

    public void setHeight(Length height) {
        this.height = height;
    }

    public void setWidth(Length width) {
        this.width = width;
    }

    public void swap() {

        Length tmp = width;
        width = height;
        height = tmp;
    }

    @Override
    public String toString() {
        
        String unitAbbrev = width.getUnit().getAbbreviation();
        
        if (width.getUnit().equals(height.getUnit()) == false) {
            unitAbbrev = String.format("%s×%s", width.getUnit().getAbbreviation(), height.getUnit().getAbbreviation());
        }
        
        return String.format("%.2f×%.2f%s", width.getLength(), height.getLength(), unitAbbrev);
    }
}
