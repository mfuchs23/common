package org.dbdoclet.jive.sheet;

import java.util.HashMap;

import org.dbdoclet.jive.RegionFrame;
import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;

public class PaperFormat implements Comparable<PaperFormat> {

    public enum Orientation {
        PORTRAIT, LANDSCAPE
    }

    public static PaperFormat USER_DEFINED = new PaperFormat("---", new PaperSize(0, 0, LengthUnit.MILLIMETER));
    public static PaperFormat A0 = new PaperFormat("A0", PaperSize.A0);
    public static PaperFormat A1 = new PaperFormat("A1", PaperSize.A1);
    public static PaperFormat A2 = new PaperFormat("A2", PaperSize.A2);
    public static PaperFormat A3 = new PaperFormat("A3", PaperSize.A3);
    public static PaperFormat A4 = new PaperFormat("A4", PaperSize.A4);
    public static PaperFormat A5 = new PaperFormat("A5", PaperSize.A5);
    public static PaperFormat A6 = new PaperFormat("A6", PaperSize.A6);
    public static PaperFormat A7 = new PaperFormat("A7", PaperSize.A7);
    public static PaperFormat A8 = new PaperFormat("A8", PaperSize.A8);
    public static PaperFormat A9 = new PaperFormat("A9", PaperSize.A9);
    public static PaperFormat A10 = new PaperFormat("A10", PaperSize.A10);
    public static PaperFormat B0 = new PaperFormat("B0", PaperSize.B0);
    public static PaperFormat B1 = new PaperFormat("B1", PaperSize.B1);
    public static PaperFormat B2 = new PaperFormat("B2", PaperSize.B2);
    public static PaperFormat B3 = new PaperFormat("B3", PaperSize.B3);
    public static PaperFormat B4 = new PaperFormat("B4", PaperSize.B4);
    public static PaperFormat B5 = new PaperFormat("B5", PaperSize.B5);
    public static PaperFormat B6 = new PaperFormat("B6", PaperSize.B6);
    public static PaperFormat B7 = new PaperFormat("B7", PaperSize.B7);
    public static PaperFormat B8 = new PaperFormat("B8", PaperSize.B8);
    public static PaperFormat B9 = new PaperFormat("B9", PaperSize.B9);
    public static PaperFormat B10 = new PaperFormat("B10", PaperSize.B10);
    public static PaperFormat C0 = new PaperFormat("C0", PaperSize.C0);
    public static PaperFormat C1 = new PaperFormat("C1", PaperSize.C1);
    public static PaperFormat C2 = new PaperFormat("C2", PaperSize.C2);
    public static PaperFormat C3 = new PaperFormat("C3", PaperSize.C3);
    public static PaperFormat C4 = new PaperFormat("C4", PaperSize.C4);
    public static PaperFormat C5 = new PaperFormat("C5", PaperSize.C5);
    public static PaperFormat C6 = new PaperFormat("C6", PaperSize.C6);
    public static PaperFormat C7 = new PaperFormat("C7", PaperSize.C7);
    public static PaperFormat C8 = new PaperFormat("C8", PaperSize.C8);
    public static PaperFormat C9 = new PaperFormat("C9", PaperSize.C9);
    public static PaperFormat C10 = new PaperFormat("C10", PaperSize.C10);
    public static PaperFormat Großpartitur1 = new PaperFormat("Großpartitur 1", PaperSize.Großpartitur1);
    public static PaperFormat Großpartitur2 = new PaperFormat("Großpartitur 2", PaperSize.Großpartitur2);
    public static PaperFormat Großpartitur3 = new PaperFormat("Großpartitur 3", PaperSize.Großpartitur3);
    public static PaperFormat Großpartitur4 = new PaperFormat("Großpartitur 4", PaperSize.Großpartitur4);
    public static PaperFormat Großpartitur5 = new PaperFormat("Großpartitur 5", PaperSize.Großpartitur5);
    public static PaperFormat Großpartitur6 = new PaperFormat("Großpartitur 6", PaperSize.Großpartitur6);
    public static PaperFormat Quartformat = new PaperFormat("Quartformat", PaperSize.Quartformat);
    public static PaperFormat QuartformatUS = new PaperFormat("Quartformat (US)", PaperSize.QuartformatUS);
    public static PaperFormat OrgelformatBach = new PaperFormat("Orgelformat (Bach)", PaperSize.OrgelformatBach);
    public static PaperFormat OrgelformatPeters1 = new PaperFormat("Orgelformat (Peters) 1",
            PaperSize.OrgelformatPeters1);
    public static PaperFormat OrgelformatPeters2 = new PaperFormat("Orgelformat (Peters) 2",
            PaperSize.OrgelformatPeters2);
    public static PaperFormat OrgelformatSteingräber = new PaperFormat("Orgelformat (Steingräber)",
            PaperSize.OrgelformatSteingräber);
    public static PaperFormat Bachformat = new PaperFormat("Bachformat", PaperSize.Bachformat);
    public static PaperFormat Oktavformat = new PaperFormat("Oktavformat", PaperSize.Oktavformat);
    public static PaperFormat OktavformatUS = new PaperFormat("Oktavformat (US)", PaperSize.OktavformatUS);
    public static PaperFormat Studienpartitur = new PaperFormat("Studienpartitur", PaperSize.Studienpartitur);
    public static PaperFormat Salonorchester = new PaperFormat("Salonorchester", PaperSize.Salonorchester);
    public static PaperFormat Klavierauszug = new PaperFormat("Klavierauszug", PaperSize.Klavierauszug);
    public static PaperFormat Klavierformat = new PaperFormat("Klavierformat", PaperSize.Klavierformat);
    public static PaperFormat Großmarsch = new PaperFormat("Großmarsch", PaperSize.Großmarsch);
    public static PaperFormat Marschformat = new PaperFormat("Marschformat", PaperSize.Marschformat);
    public static PaperFormat PaperFormatUSLetter = new PaperFormat("US Letter", PaperSize.USLetter);
    public static PaperFormat PaperFormatUSLegal = new PaperFormat("US Legal", PaperSize.USLegal);
    public static PaperFormat PaperFormatUSExecutive = new PaperFormat("US Executive", PaperSize.USExecutive);

    public static final PaperFormat[] FORMATS = { USER_DEFINED, A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B0, B1,
            B2, B3, B4, B5, B6, B7, B8, B9, B10, C0, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, Großpartitur1,
            Großpartitur2, Großpartitur3, Großpartitur4, Großpartitur5, Großpartitur6, Quartformat, QuartformatUS,
            OrgelformatBach, OrgelformatPeters1, OrgelformatPeters2, OrgelformatSteingräber, Bachformat, Oktavformat,
            OktavformatUS, Studienpartitur, Salonorchester, Klavierauszug, Klavierformat, Großmarsch, Marschformat,
            PaperFormatUSLetter, PaperFormatUSLegal, PaperFormatUSExecutive };

    public static final HashMap<PaperSize, PaperFormat> SIZE_MAP = new HashMap<PaperSize, PaperFormat>();

    static {

        for (PaperFormat pf : FORMATS) {
            SIZE_MAP.put(pf.getSize(), pf);
        }
    }

    private PaperSize size;
    private String name;
    private RegionFrame margins = new RegionFrame(10, 15, 10, 15);
    private RegionFrame regions = new RegionFrame(10, 0, 10, 0);
    private Orientation orientation = Orientation.PORTRAIT;

    public static PaperFormat valueOf(PaperSize size) {

        if (size == null) {
            return null;
        }

        PaperSize ps = size.deepCopy();

        PaperFormat pf = SIZE_MAP.get(ps);

        if (pf == null) {

            ps.swap();
            pf = SIZE_MAP.get(ps);
        }

        if (pf != null) {
            return new PaperFormat(pf);
        }

        return new PaperFormat("", size);
    }

    public PaperFormat(String name, PaperSize size) {

        if (size == null) {
            throw new IllegalArgumentException("The argument size must not be null!");
        }

        this.name = name;
        this.size = size;
    }

    public PaperFormat(PaperFormat template) {

        this.name = template.getName();
        this.size = template.getSize();
        this.margins = template.getMargins();
        this.regions = template.getRegions();
        this.orientation = template.getOrientation();
    }

    public int compareTo(PaperFormat other) {
        return name.compareTo(other.getName());
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

        PaperFormat other = (PaperFormat) obj;

        if (name == null && other.name != null) {
            return false;
        }

        if (name.equals(other.name) == false) {
            return false;
        }

        return true;
    }

    public Length getHeight() {
        return size.getHeight();
    }

    public String getHeightAsText() {
        return size.getHeightAsText();
    }

    public RegionFrame getMargins() {

        if (margins == null) {
            margins = new RegionFrame(0, 0, 0, 0);
        }

        return margins.deepCopy();
    }

    public String getName() {
        return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public RegionFrame getRegions() {
        return regions.deepCopy();
    }

    public PaperSize getSize() {
        return size.deepCopy();
    }

    public Length getWidth() {
        return size.getWidth();
    }

    public String getWidthAsText() {
        return size.getWidthAsText();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean isUserDefined() {

        PaperFormat paperFormat = SIZE_MAP.get(this.getSize());

        if (paperFormat == null) {
            return true;
        } else {
            return false;
        }
    }

    public void setMargins(RegionFrame margins) {
        this.margins = margins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setRegions(RegionFrame regions) {
        this.regions = regions;
    }

    public void setSize(PaperSize size) {
        this.size = size;
    }

    @Override
    public String toString() {

        if (this == USER_DEFINED) {
            return getName();
        }

        return String.format("%s (%s)", getName(), size.toString());
    }

    public PaperFormat deepCopy() {

        PaperFormat copy = new PaperFormat(this);
        return copy;
    }

    public Length getPrintableWidth() {
        return size.getWidth().minus(
                margins.getLeft().plus(margins.getRight()).plus(regions.getLeft()).plus(regions.getRight()));
    }

    public Length getPrintableHeight() {
        return size.getHeight().minus(
                margins.getTop().plus(margins.getBottom()).plus(regions.getTop()).plus(regions.getBottom()));
    }
}
