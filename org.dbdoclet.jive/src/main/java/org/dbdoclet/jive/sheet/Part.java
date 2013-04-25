package org.dbdoclet.jive.sheet;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.dbdoclet.format.FormattedObject;
import org.w3c.dom.Element;

public interface Part extends FormattedObject {

    public void appendChild(Part newChild);
    public int computeFontHeight(Graphics2D g2d, Font font);
    public void copyProperties(Part toPart);
    public Part deepCopy(Sheet sheet);
    public ArrayList<Part> getChildren();
    public String getId();
    public Rectangle2D.Double getMarginBox();
    public int getIndex();
    public double getNormalizedHeight();
    public Part getParent();
    public Sheet getSheet();
    public Margin getMargin();
    public Padding getPadding();
    public void setMargin(Margin margin);
    public void setPadding(Padding padding);
    public String getTagName();
    public void insertChild(int index, Part part);
    public boolean isSelected();
    public void paintPart(Graphics2D g2d);
    public boolean removeChild(Part part);
    public int rzoom(double value);
    public void setBoundingBox(Rectangle2D.Double bb);
    public void setIndex(int index);
    public void setParent(Part part);
    public void setSelected(boolean b);
    public void writeElement(Element element);
    public double zoom(double value);
	public double getSpaceBefore();
	public double getSpaceAfter();
	public Rectangle2D.Double getCopyOfContentBox();

}
