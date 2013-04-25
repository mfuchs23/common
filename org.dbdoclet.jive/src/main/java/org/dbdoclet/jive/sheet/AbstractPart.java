package org.dbdoclet.jive.sheet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.dbdoclet.format.Alignment;
import org.dbdoclet.format.Display;
import org.dbdoclet.format.FontWeight;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.unit.Length;
import org.w3c.dom.Element;

public abstract class AbstractPart implements Part {

	protected Rectangle2D.Double boundingBox;
	protected Rectangle2D.Double paddingBox;
	protected Rectangle2D.Double contentBox;

	private ArrayList<Part> children;
	private Color foreground;
	private Display display;

	private Part parent;

	protected Alignment alignment;
	protected Font font;
	protected String id;
	protected int index = -1;
	protected Margin margin = new Margin();
	protected Padding padding = new Padding();
	protected ResourceBundle res;
	protected boolean selected;
	protected final Sheet sheet;
	public AbstractPart(Sheet sheet) {

		if (sheet == null) {
			throw new IllegalArgumentException(
					"The argument sheet must not be null!");
		}

		this.sheet = sheet;
		this.res = sheet.getResourceBundle();

		children = new ArrayList<Part>();
	}

	public void appendChild(Part part) {

		if (part != null) {
			children.add(part);
			part.setParent(this);
		}

		updateChildren();
	}

	public int computeFontHeight(Graphics2D g2d, Font font) {

		FontMetrics fm = g2d.getFontMetrics(font);
		return fm.getHeight();
	}

	public void copyProperties(Part to) {

		to.setMargin(getMargin().deepCopy());
		to.setPadding(getPadding().deepCopy());
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public Rectangle2D.Double getBoundingBox() {
		return getMarginBox();
	}

	public Part getChild(int index) {

		updateChildren();

		if (index >= 0 && index < children.size()) {
			return children.get(index);
		}

		return null;
	}

	/**
	 * Durchsucht die Liste der Kindelemente und liefert das Kind mit gesuchten
	 * Id zurück, falls vorhanden. Andernfalls wird der Wert null
	 * zurückgeliefert.
	 * 
	 * @param id
	 * @return Part
	 */
	public Part getChildById(String id) {

		if (children == null || children.size() == 0) {
			return null;
		}

		for (Part child : children) {

			if (child != null && child.getId() != null
					&& child.getId().equals(id)) {
				return child;
			}
		}

		return null;
	}

	public ArrayList<Part> getChildren() {

		updateChildren();
		return children;
	}

	public Rectangle2D.Double getContentBox() {

		if (contentBox == null) {
			contentBox = new Rectangle2D.Double();
		}

		return contentBox;
	}

	public Rectangle2D.Double getCopyOfContentBox() {

		Rectangle2D.Double copy = new Rectangle2D.Double();

		if (contentBox == null) {
			updateBoxes();
		}

		copy.x = contentBox.x;
		copy.y = contentBox.y;
		copy.width = contentBox.width;
		copy.height = contentBox.height;

		return copy;
	}

	public Display getDisplay() {

		if (display == null) {
			return Display.INLINE;
		}

		return display;
	}

	public Part getFirstChild() {

		if (children == null || children.size() == 0) {
			return null;
		}

		Part part = children.get(0);
		part.setIndex(0);

		return part;
	}

	public Font getFont() {
		return font;
	}

	public Color getForeground() {
		return foreground;
	}

	public String getId() {
		return id;
	}

	/**
	 * Liefert den aktuellen Index des Elements in der Liste Geschwisterknoten
	 * zurück.
	 */
	public int getIndex() {
		return index;
	}

	public Part getLastChild() {

		if (children == null || children.size() == 0) {
			return null;
		}

		Part part = children.get(children.size() - 1);
		part.setIndex(children.size() - 1);

		return part;
	}

	public Margin getMargin() {
		return margin.deepCopy();
	}

	public Rectangle2D.Double getMarginBox() {

		if (boundingBox == null) {
			boundingBox = new Rectangle2D.Double();
		}

		return boundingBox;
	}

	public double getNormalizedHeight() {

		double height = getMargin().getTop().toMillimeter();
		height += getPadding().getTop().toMillimeter();

		for (Part staff : children) {
			height += staff.getNormalizedHeight();
		}

		height += getPadding().getBottom().toMillimeter();
		height += getMargin().getBottom().toMillimeter();

		return height;
	}

	public Padding getPadding() {
		return padding.deepCopy();
	}

	public Rectangle2D.Double getPaddingBox() {

		if (paddingBox == null) {
			paddingBox = new Rectangle2D.Double();
		}

		return paddingBox;
	}

	public Part getParent() {
		return parent;
	}

	public ResourceBundle getResourceBundle() {
		return res;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public double getSpaceAfter() {
		return margin.getBottom().toMillimeter()
				+ padding.getBottom().toMillimeter();
	}

	public double getSpaceBefore() {
		return margin.getTop().toMillimeter() + padding.getTop().toMillimeter();
	}

	public String getTagName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	/*
	 * Fügt einen untergeordneten Abschnitt in den aktuellen Bereich an der
	 * angegebenen Position ein.
	 * 
	 * @see org.dbdoclet.jive.sheet.Part#insertChild(int,
	 * org.dbdoclet.jive.sheet.Part)
	 */
	public void insertChild(int index, Part part) {

		if (part == null) {
			throw new IllegalArgumentException(
					"Argument part must not be null!");
		}

		if (index >= 0 && index < children.size()) {

			children.add(index, part);
			part.setParent(this);

		} else {

			children.add(part);
			part.setParent(this);
		}

		updateChildren();
	}

	public boolean isSelected() {
		return selected;
	}

	public void removeAll() {
		children.clear();
	}

	public Part removeChild(int index) {

		Part child = children.remove(index);
		updateChildren();

		return child;
	}

	public boolean removeChild(Part part) {

		boolean rc = children.remove(part);
		updateChildren();

		return rc;
	}

	public int rzoom(double value) {
		return sheet.rzoom(value);
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public void setBoundingBox(Rectangle2D.Double boundingBox) {

		this.boundingBox = new Rectangle2D.Double();
		this.boundingBox.x = boundingBox.x;
		this.boundingBox.y = boundingBox.y;
		this.boundingBox.width = boundingBox.width;
		this.boundingBox.height = boundingBox.height;

		updateBoxes();
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setFontSize(Length fontSize) {

		if (font != null) {
			font = font.deriveFont((float) fontSize.toPoint());
		}
	}

	public void setFontWeight(FontWeight fontWeight) {

		if (font != null) {

			if (font.isBold() == false && fontWeight == FontWeight.BOLD) {
				font = font.deriveFont(Font.BOLD);
			}
		}
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setMargin(Margin margin) {
		this.margin = margin;
		updateBoxes();
	}

	public void setMarginBottom(Length marginBottom) {
		margin.setBottom(marginBottom);
		updateBoxes();
	}

	public void setMarginLeft(Length marginLeft) {
		margin.setLeft(marginLeft);
		updateBoxes();
	}

	public void setMarginRight(Length marginRight) {
		margin.setRight(marginRight);
		updateBoxes();
	}

	public void setMarginTop(Length marginTop) {
		margin.setTop(marginTop);
		updateBoxes();
	}

	public void setPadding(Padding padding) {
		this.padding = padding;
		updateBoxes();
	}

	public void setParent(Part parent) {
		this.parent = parent;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + getIndex() + ")";
	}

	public int unzoom(double value) {
		return sheet.unzoom(value);
	}

	public void updateBoxes() {

		if (boundingBox == null) {
			boundingBox = new Rectangle2D.Double();
		}

		if (paddingBox == null) {
			paddingBox = new Rectangle2D.Double();
		}

		if (contentBox == null) {
			contentBox = new Rectangle2D.Double();
		}

		Margin margin = getMargin();

		paddingBox.x = boundingBox.x + margin.getLeft().toMillimeter();
		paddingBox.y = boundingBox.y + margin.getTop().toMillimeter();
		paddingBox.width = boundingBox.width
				- (margin.getLeft().toMillimeter() + margin.getRight()
						.toMillimeter());
		paddingBox.height = boundingBox.height
				- (margin.getTop().toMillimeter() + margin.getBottom()
						.toMillimeter());

		Padding padding = getPadding();

		contentBox.x = paddingBox.x + padding.getLeft().toMillimeter();
		contentBox.y = paddingBox.y + padding.getTop().toMillimeter();
		contentBox.width = paddingBox.width
				- (padding.getLeft().toMillimeter() + padding.getRight()
						.toMillimeter());
		contentBox.height = paddingBox.height
				- (padding.getTop().toMillimeter() + padding.getBottom()
						.toMillimeter());
	}

	public void writeElement(Element element) {

		if (element == null) {
			throw new IllegalArgumentException(
					"The argument element must not be null!");
		}

		element.setAttribute("margin", getMargin().toNormalizedString());
		element.setAttribute("padding", getPadding().toNormalizedString());

		String id = getId();

		if (id != null && id.trim().length() > 0) {
			element.setAttribute("id", id.trim());
		}
	}

	public double zoom(double value) {
		return sheet.zoom(value);
	}

	public Lines zoom(Lines lines) {

		Lines zoomed = new Lines();

		for (Line line : lines.getList()) {
			zoomed.add(zoom(line));
		}

		return zoomed;
	}

	private void updateChildren() {

		int index = 0;

		for (Part part : children) {
			part.setIndex(index++);
		}
	}

	private Line zoom(Line line) {

		Line zoomed = new Line(zoom(line.getP1()), zoom(line.getP2()));
		return zoomed;
	}

	private Point2D.Double zoom(Point2D.Double p) {

		Point2D.Double zoomed = new Point2D.Double(rzoom(p.getX()),
				rzoom(p.getY()));
		return zoomed;
	}

	/**
	 * @param g2d
	 */
	protected void afterPaintPart(Graphics2D g2d) {

		if (sheet.isVisibleSpaceEnabled() == true) {

			Rectangle2D.Double bb = getMarginBox();
			g2d.setPaint(Color.red);
			g2d.setStroke(new BasicStroke(1.0f));
			Rectangle zbb = sheet.rzoom(bb);
			g2d.drawRect(zbb.x, zbb.y, zbb.width, zbb.height);

			bb = getPaddingBox();
			g2d.setPaint(Color.green);
			g2d.setStroke(new BasicStroke(1.0f));
			zbb = sheet.rzoom(bb);
			g2d.drawRect(zbb.x, zbb.y, zbb.width, zbb.height);

			bb = getContentBox();
			g2d.setPaint(JiveConstants.COLOUR_BLUE_GRAY_1);
			g2d.setStroke(new BasicStroke(1.0f));
			zbb = sheet.rzoom(bb);
			g2d.drawRect(zbb.x, zbb.y, zbb.width, zbb.height);
		}
	}

	protected void beforePaintPart(Graphics2D g2d) {

		if (isSelected() == true) {

			Rectangle bb = sheet.rzoom(getMarginBox());
			g2d.setPaint(JiveConstants.COLOUR_BEIGE);
			g2d.fillRect(bb.x, bb.y, bb.width, bb.height);
		}
	}

	protected int computeStringWidth(Graphics2D g2d, Font font, String content) {

		FontMetrics fm = g2d.getFontMetrics(font);
		return fm.stringWidth(content);
	}

	protected void fillCopy(AbstractPart copy) {

		copy.setMargin(getMargin().deepCopy());
		copy.setPadding(getPadding().deepCopy());
		copy.setId(getId());
		copy.setIndex(getIndex());

		for (Part child : getChildren()) {
			copy.appendChild(child.deepCopy(copy.getSheet()));
		}
	}

	protected Rectangle2D.Double getCopyOfBoundingBox() {

		Rectangle2D.Double copy = new Rectangle2D.Double();

		copy.x = boundingBox.x;
		copy.y = boundingBox.y;
		copy.width = boundingBox.width;
		copy.height = boundingBox.height;

		return copy;
	}

	protected int getNumOfChildren() {
		return children.size();
	}

	/**
	 * @param g2d
	 */
	protected void paintContainer(Graphics2D g2d) {

		double y = 0;

		Rectangle2D.Double pbb = getBoundingBox();
		pbb.height = 0;
		pbb.height = getMargin().getTop().toMillimeter()
				+ getPadding().getTop().toMillimeter();

		for (Part child : getChildren()) {

			Rectangle2D.Double bb = getCopyOfContentBox();
			bb.height = 0;
			bb.y += y;

			child.setBoundingBox(bb);
			child.paintPart(g2d);

			y += child.getNormalizedHeight();
			pbb.height += y;
		}

		pbb.height += getPadding().getBottom().toMillimeter()
				+ getMargin().getBottom().toMillimeter();
		updateBoxes();
	}

	protected void paintLayoutInfo(Graphics2D g2d, Rectangle bb, Color color,
			String text) {

		g2d.setPaint(color);
		g2d.drawRect(bb.x, bb.y, bb.width, bb.height);
		g2d.drawString(text, bb.x, bb.y - 2);
	}

	protected Point zoom(Point p) {

		Point zoomed = new Point(rzoom(p.getX()), rzoom(p.getY()));
		return zoomed;
	}

	public void setFontFamily(String value) {
		// TODO Auto-generated method stub
		
	}
}
