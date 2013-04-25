package org.dbdoclet.jive.sheet;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.dbdoclet.format.Alignment;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.RegionFrame;
import org.dbdoclet.unit.LengthUnit;
import org.w3c.dom.Element;

/**
 * Ein virtuelles, beschreibares Blatt, bestehend aus Schreibtischunterlage,
 * Rand, Seitenregionen und Textkörper.
 * 
 * @author michael
 * 
 */
public class Sheet extends JPanel {

	private static final long serialVersionUID = 1L;

	private int alignment;

	private boolean autoZoom = false;

	private Color backgroundColor = Color.lightGray;

	private BasicStroke basicStroke = new BasicStroke(1.0f);

	private BasicStroke boldStroke = new BasicStroke(2.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

	private BasicStroke dashedStroke = new BasicStroke(1.0f,
			BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, new float[] {
					5.0f, 5.0f }, 5.0f);

	private boolean desktopEnabled = true;
	private int desktopHeight = 320;
	private float desktopMargin = 10.0f;
	private int desktopWidth = 320;

	private RegionFrame displayMargins;
	private RegionFrame displayRegions;
	private boolean endlessPaperMode = false;

	private boolean fitToDesktopSize = false;

	private Color paperColor = Color.white;
	private double paperDisplayHeight;
	private double paperDisplayWidth;

	private PaperFormat paperFormat = PaperFormat.A4;
	private Rectangle selectionRectangle;

	private Color shadowColor = Color.darkGray;
	private boolean shadowEnabled = true;
	private int shadowLeftOffset = 1;
	private int shadowTopOffset = 1;
	private Font textFont;
	private boolean visibleMargins = false;
	private boolean visibleSpaceEnabled = false;
	private double zoomFactor = 1.0f;
	// private float yPosOfNextPart = 0.0f;

	protected SheetContainer container;
	protected SheetModel model;
	protected ResourceBundle res;

	public Sheet(SheetContainer container, ResourceBundle res) {

		this.container = container;
		this.res = res;

		setPaperFormat(new PaperFormat(PaperFormat.A4));

		textFont = new Font("Serif", Font.PLAIN, 12);
		model = new SheetModel(this);

		setFocusable(true);
	}

	public boolean contains(Part part) {

		Rectangle boundingBox = rzoom(part.getMarginBox());

		if (boundingBox.getMaxY() > zoom(getBodyBottom())) {

			// System.out.println("part.MaxY: " + boundingBox.getMaxY() +
			// ", bodyBottom: " + zoom(getBodyBottom()));
			return false;
		}

		return true;
	}

	public Sheet deepCopy() {

		Sheet copy = new Sheet(container, res);
		deepCopy(copy);

		return copy;
	}

	public void drawSelectionRectangle(Rectangle newSelectionRectangle) {

		Rectangle refreshRectangle;

		if (newSelectionRectangle == null) {

			refreshRectangle = selectionRectangle;

		} else {

			refreshRectangle = (Rectangle) newSelectionRectangle.clone();

			if (selectionRectangle != null) {

				if (selectionRectangle.x < refreshRectangle.x) {
					refreshRectangle.x = selectionRectangle.x;
				}

				if (selectionRectangle.y < refreshRectangle.y) {
					refreshRectangle.y = selectionRectangle.y;
				}

				if (selectionRectangle.width > refreshRectangle.width) {
					refreshRectangle.width = selectionRectangle.width;
				}

				if (selectionRectangle.height > refreshRectangle.height) {
					refreshRectangle.height = selectionRectangle.height;
				}
			}

			if (newSelectionRectangle.width < 0) {
				newSelectionRectangle.width *= -1;
				newSelectionRectangle.x -= newSelectionRectangle.width;
			}

			if (newSelectionRectangle.height < 0) {
				newSelectionRectangle.height *= -1;
				newSelectionRectangle.y -= newSelectionRectangle.height;
			}
		}

		this.selectionRectangle = newSelectionRectangle;

		if (refreshRectangle != null) {

			refreshRectangle.x -= 3;
			refreshRectangle.y -= 3;
			refreshRectangle.width += 6;
			refreshRectangle.height += 6;
			repaint(refreshRectangle);
		}
	}

	public boolean endOfSheet(Part nextPart) {

		int cursorPos = 0;

		for (Part part : model.getPartList()) {
			cursorPos += part.getSpaceBefore() + part.getNormalizedHeight()
					+ part.getSpaceAfter();
		}

		if ((cursorPos + (nextPart.getNormalizedHeight() - nextPart
				.getSpaceAfter())) > paperFormat.getPrintableHeight()
				.toMillimeter()) {
			return true;
		}

		return false;
	}

	public void erase() {
		model.getPartList().clear();
	}

	public void fillElement(Element element) {

		element.setAttribute("paper-width", paperFormat.getSize().getWidth()
				.toNormalizedString());
		element.setAttribute("paper-height", paperFormat.getSize().getHeight()
				.toNormalizedString());
		element.setAttribute("paper-format", paperFormat.getName());
		element.setAttribute("orientation", paperFormat.getOrientation()
				.toString().toLowerCase());
		element.setAttribute("margin-top", paperFormat.getMargins().getTop()
				.toNormalizedString());
		element.setAttribute("margin-bottom", paperFormat.getMargins()
				.getBottom().toNormalizedString());
		element.setAttribute("margin-left", paperFormat.getMargins().getLeft()
				.toNormalizedString());
		element.setAttribute("margin-right", paperFormat.getMargins()
				.getRight().toNormalizedString());
		element.setAttribute("region-top", paperFormat.getRegions().getTop()
				.toNormalizedString());
		element.setAttribute("region-bottom", paperFormat.getRegions()
				.getBottom().toNormalizedString());
		element.setAttribute("region-left", paperFormat.getRegions().getLeft()
				.toNormalizedString());
		element.setAttribute("region-right", paperFormat.getRegions()
				.getRight().toNormalizedString());
	}

	public Part findPart(Point point) {

		for (Part part : model.getPartList()) {

			Rectangle bb = rzoom(part.getMarginBox());

			if (bb.contains(point)) {
				return part;
			}
		}

		return null;
	}

	public Text findText(Point point) {

		for (Text part : model.getTextList()) {

			Rectangle bb = rzoom(part.getMarginBox());

			if (bb.contains(point)) {
				return part;
			}
		}

		return null;
	}

	public Text findTextById(String id) {

		if (id == null) {
			return null;
		}

		for (Part part : model.getPartList()) {

			String otherId = part.getId();

			if (otherId != null && id.equalsIgnoreCase(part.getId())
					&& part instanceof Text) {
				return (Text) part;
			}

			for (Part child : part.getChildren()) {

				otherId = child.getId();

				if (otherId != null && id.equalsIgnoreCase(child.getId())
						&& child instanceof Text) {
					return (Text) child;
				}

			}
		}

		return null;
	}

	public String findTextContentById(String id) {

		Text text = findTextById(id);

		if (text != null) {
			return text.getContent();
		}

		return null;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public double getBodyBottom() {
		return getDesktopTop() + paperDisplayHeight
				- displayMargins.getBottom().toMillimeter()
				- displayRegions.getBottom().toMillimeter();
	}

	/**
	 * Liefert die Position des linken Randes des Textkörpers in Milimeter. Der
	 * Wert muß noch an die jeweilige Auflösung angepaßt werden.
	 * 
	 * @return double
	 */
	public double getBodyLeft() {
		return getDesktopLeft() + displayMargins.getLeft().toMillimeter()
				+ displayRegions.getLeft().toMillimeter();
	}

	/**
	 * Liefert die Position des rechten Randes des Textkörpers in Milimeter.
	 * 
	 * @return double
	 */
	public double getBodyRight() {
		return getDesktopLeft() + paperDisplayWidth
				- displayMargins.getRight().toMillimeter()
				- displayRegions.getRight().toMillimeter();
	}

	public double getBodyTop() {
		return getDesktopTop() + displayMargins.getTop().toMillimeter()
				+ displayRegions.getTop().toMillimeter();
	}

	public Stroke getBoldStroke() {
		return boldStroke;
	}

	public BasicStroke getDashedStroke() {
		return dashedStroke;
	}

	public int getDesktopHeight() {
		return desktopHeight;
	}

	public int getDesktopWidth() {
		return desktopWidth;
	}

	public Part getDocumentPart() {
		return model.getDocumentPart();
	}

	@Override
	public int getHeight() {

		if (desktopEnabled == false) {
			return rzoom(paperDisplayHeight);
		}

		return rzoom(desktopHeight);
	}

	public String getId() {
		return String.valueOf(hashCode());
	}

	public RegionFrame getMargins() {
		return paperFormat.getMargins();
	}

	public SheetModel getModel() {
		return model;
	}

	public Color getPaperColor() {
		return paperColor;
	}

	public PaperFormat getPaperFormat() {

		PaperFormat copy = PaperFormat.valueOf(paperFormat.getSize());
		copy.setMargins(paperFormat.getMargins());
		copy.setRegions(paperFormat.getRegions());
		copy.setOrientation(paperFormat.getOrientation());

		return copy;
	}

	@Override
	public Dimension getPreferredSize() {

		if (autoZoom == true) {

			Container container = getParent();

			int parentWidth = container.getWidth();
			double ratio = parentWidth
					/ (getDesktopLeft() + paperDisplayWidth + getDesktopRight());

			if (ratio > 1.0 && Math.abs(zoomFactor - ratio) > 0.1) {
				zoomFactor = ratio;
			}
		}

		Dimension dim = new Dimension(getWidth(), getHeight());

		// System.out.println("PreferredSize: " + dim + ", parentWidth = " +
		// parentWidth + ", ratio = " + ratio);
		return dim;
	}

	public ResourceBundle getResourceBundle() {
		return res;
	}

	public ArrayList<Part> getSelectedPartList() {

		ArrayList<Part> selectedPartList = new ArrayList<Part>();

		for (Part part : model.getPartList()) {
			selectedPartList.addAll(getSelectedPartList(part));
		}

		return selectedPartList;
	}

	public ArrayList<Part> getSelectedPartList(Part part) {

		ArrayList<Part> selectedPartList = new ArrayList<Part>();

		if (part.isSelected()) {
			selectedPartList.add(part);
		}

		for (Part child : part.getChildren()) {
			selectedPartList.addAll(getSelectedPartList(child));
		}

		return selectedPartList;
	}

	public Font getTextFont() {
		return textFont;
	}

	@Override
	public int getWidth() {

		if (desktopEnabled == false) {
			return rzoom(paperDisplayWidth);
		}

		return rzoom(desktopWidth);
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void insertPart(Part text, int index) {
		model.insertPart(text, index);
	}

	public boolean isAutoZoom() {
		return autoZoom;
	}

	public boolean isEndlessPaperMode() {
		return endlessPaperMode;
	}

	public boolean isFitToDesktopSize() {
		return fitToDesktopSize;
	}

	public boolean isVisibleSpaceEnabled() {
		return visibleSpaceEnabled;
	}

	/**
	 * Zeichnen des aktuellen Blatts.
	 */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		if (desktopEnabled == true) {
			g2d.setPaint(backgroundColor);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}

		if (shadowEnabled == true) {
			g2d.setPaint(shadowColor);
			g2d.fillRect(rzoom(getDesktopLeft() + getShadowLeftOffset()),
					rzoom(getDesktopTop() + getShadowTopOffset()),
					rzoom(getPaperDisplayWidth()),
					rzoom(getPaperDisplayHeight()));
		}

		g2d.setPaint(paperColor);
		g2d.fillRect(rzoom(getDesktopLeft()), rzoom(getDesktopTop()),
				rzoom(getPaperDisplayWidth()), rzoom(getPaperDisplayHeight()));

		if (visibleMargins == true) {

			g2d.setStroke(dashedStroke);
			g2d.setPaint(Color.darkGray);
			g2d.drawRect(rzoom(getDesktopLeft()
					+ displayMargins.getLeft().toMillimeter()),
					rzoom(getDesktopTop()
							+ displayMargins.getTop().toMillimeter()),
					rzoom(getPaperDisplayWidth()
							- displayMargins.getRight().toMillimeter()
							- displayMargins.getLeft().toMillimeter()),
					rzoom(getPaperDisplayHeight()
							- displayMargins.getBottom().toMillimeter()
							- displayMargins.getTop().toMillimeter()));

			g2d.setPaint(Color.green);
			g2d.drawRect(rzoom(getDesktopLeft()
					+ displayMargins.getLeft().toMillimeter()),
					rzoom(getDesktopTop()
							+ displayMargins.getTop().toMillimeter()),
					rzoom(getPaperDisplayWidth()
							- displayMargins.getRight().toMillimeter()
							- displayMargins.getLeft().toMillimeter()),
					rzoom(displayRegions.getTop().toMillimeter()));
		}

		DocumentPart documentPart = model.getDocumentPart();
		documentPart.setBoundingBox(new Rectangle2D.Double(getBodyLeft(),
				getBodyTop(), getBodyRight() - getBodyLeft(), getBodyBottom()
						- getBodyTop()));
		documentPart.paintPart(g2d);

		paintHeader(g2d);
		paintPageNumber(g2d);
		paintSelectionRectangle(g2d);

		if (hasFocus() == true) {

			g2d.setPaint(Color.black);
			g2d.setStroke(boldStroke);
			g2d.drawRect(0, 0, getWidth(), getHeight());
		}

		int lastYPos = +(int) Math.round(documentPart.getCursor().getTop()
				+ (2 * desktopMargin)
				+ paperFormat.getMargins().getBottom().toMillimeter()
				+ paperFormat.getRegions().getBottom().toMillimeter());

		if (endlessPaperMode == true && this.desktopHeight != lastYPos
				&& lastYPos > paperFormat.getHeight().toMillimeter()) {
			setDesktopSize(desktopWidth, lastYPos);
		}
	}

	public void paintSelectionMarker(Graphics2D g2d, Part part) {

		Color selectionColor = new Color(0, 80, 0);
		g2d.setPaint(selectionColor);
		g2d.setStroke(boldStroke);

		Rectangle bb = rzoom(part.getMarginBox());

		int length = 5;
		int lineWidth = Math.round(boldStroke.getLineWidth() - 1);

		// g2d.fillRect(bb.x, bb.y, bb.width, bb.height);
		// g2d.drawRect(bb.x, bb.y, bb.width, bb.height);

		g2d.drawLine(bb.x, bb.y, bb.x, bb.y + rzoom(length));
		g2d.drawLine(bb.x - lineWidth, bb.y, bb.x + rzoom(length), bb.y);

		g2d.drawLine(bb.x + bb.width - rzoom(length), bb.y + bb.height, bb.x
				+ bb.width, bb.y + bb.height);
		g2d.drawLine(bb.x + bb.width, bb.y + bb.height + lineWidth, bb.x
				+ bb.width, bb.y + bb.height - rzoom(length));
	}

	public void readSheet(Element element) {
		//
	}

	public int rzoom(double value) {
		return (int) Math.round(zoomFactor * value);
	}

	public Rectangle rzoom(Rectangle2D.Double rect2D) {
		return rzoom(rect2D, 1.0);
	}

	public Rectangle rzoom(Rectangle2D.Double rect2D, double factor) {

		Rectangle rect = new Rectangle();

		if (factor != 1.0) {
			double owidth = rect2D.width;
			double oheight = rect2D.height;
			double nwidth = rect2D.width * factor;
			double nheight = rect2D.height * factor;

			double xdiff = (nwidth - owidth) / 2;
			double ydiff = (nheight - oheight) / 2;

			rect.x = rzoom(rect2D.x - xdiff);
			rect.y = rzoom(rect2D.y - ydiff);
			rect.width = rzoom(nwidth);
			rect.height = rzoom(nheight);

		} else {

			rect.x = rzoom(rect2D.x);
			rect.y = rzoom(rect2D.y);
			rect.width = rzoom(rect2D.width);
			rect.height = rzoom(rect2D.height);
		}

		return rect;
	}

	public void setAlign(int alignment) {
		this.alignment = alignment;
	}

	public void setAutoZoom(boolean autoZoom) {
		this.autoZoom = autoZoom;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setDesktopEnabled(boolean desktopEnabled) {
		this.desktopEnabled = desktopEnabled;
	}

	public void setDesktopMargin(float desktopMargin) {
		this.desktopMargin = desktopMargin;
	}

	public void setDesktopSize(int desktopWidth, int desktopHeight) {

		if (desktopWidth < 0) {
			throw new IllegalArgumentException("Invalid desktop width of "
					+ desktopWidth + "!");
		}

		if (desktopHeight < 0) {
			throw new IllegalArgumentException("Invalid desktop height of "
					+ desktopHeight + "!");
		}

		this.desktopHeight = desktopHeight;
		this.desktopWidth = desktopWidth;

		updateView();
	}

	public void setEndlessPaperMode(boolean endlessPaperMode) {
		this.endlessPaperMode = endlessPaperMode;
	}

	public void setFitToDesktopSize(boolean fitToDesktopSize) {

		this.fitToDesktopSize = fitToDesktopSize;

		if (fitToDesktopSize == true) {
			fitToDesktopSize();
		}
	}

	public void setMargins(RegionFrame margins) {

		paperFormat.setMargins(margins);
		updateView();
	}

	public void setModel(SheetModel model) {
		this.model = model;
	}

	public void setPaperColor(Color paperColor) {
		this.paperColor = paperColor;
	}

	public void setPaperFormat(PaperFormat paperFormat) {

		this.paperFormat = paperFormat;
		updateView();
	}

	public void setPaperSize(PaperSize paperSize) {

		paperFormat.setSize(paperSize);
		updateView();
	}

	public void setScalePaperToPoint(double imageableWidth,
			double imageableHeight) {

		double ratio1 = imageableWidth
				/ paperFormat.getPrintableWidth().toMillimeter();
		double ratio2 = imageableHeight
				/ paperFormat.getPrintableHeight().toMillimeter();

		double ratio = ratio1 > ratio2 ? ratio2 : ratio1;
		setZoomFactor((float) ratio);
		setPaperSize(new PaperSize(pointToMm(imageableWidth),
				pointToMm(imageableHeight), LengthUnit.MILLIMETER));

		recalculate();
	}

	public void setSelection(Point point, boolean isShiftDown, boolean isControlDown) {

		drawSelectionRectangle(null);

		for (Part part : model.getPartList()) {
			setSelection(point, part, isShiftDown, isControlDown);
		}
	}

	public boolean setSelection(Point point, Part part, boolean isShiftDown, boolean isControlDown) {

		if (point == null) {

			part.setSelected(false);

		} else {

			for (Part child : part.getChildren()) {
				if (setSelection(point, child, isShiftDown, isControlDown)) {
					return true;
				}
			}

			if (rzoom(part.getMarginBox()).contains(point)) {

				if (part.isSelected() == false) {

					part.setSelected(true);
					repaint(rzoom(part.getMarginBox()));
					return true;
				}

			} else {

				if (part.isSelected() && isControlDown == false) {
					part.setSelected(false);
					repaint(rzoom(part.getMarginBox()));
				}

			}
		}

		return false;
	}

	public void setSelection(Rectangle rect) {

		drawSelectionRectangle(null);

		for (Part part : model.getPartList()) {
			setSelection(rect, part);
		}
	}

	public void setSelection(Rectangle rect, Part part) {

		if (rect == null) {

			part.setSelected(false);

		} else {

			if (rect.contains(rzoom(part.getMarginBox()))) {

				if (part.isSelected() == false) {
					part.setSelected(true);
					repaint(rzoom(part.getMarginBox(), 2.0));
				}

			} else {

				if (part.isSelected()) {
					part.setSelected(false);
					repaint(rzoom(part.getMarginBox(), 2.0));
				}

			}

			for (Part child : part.getChildren()) {
				setSelection(rect, child);
			}
		}
	}

	public void setShadowEnabled(boolean shadowEnabled) {
		this.shadowEnabled = shadowEnabled;
	}

	public void setVisibleMargins(boolean visibleMargins) {
		this.visibleMargins = visibleMargins;
	}

	public void setVisibleSpaceEnabled(boolean visibleSpaceEnabled) {
		this.visibleSpaceEnabled = visibleSpaceEnabled;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	@Override
	public String toString() {
		return "Sheet(" + hashCode() + ") zoomFactor=" + getZoomFactor();
	}

	public int unzoom(double value) {
		return (int) Math.round(value / zoomFactor);
	}

	public void updateView() {

		recalculate();
		validateAndRepaint();
	}

	public void validateAndRepaint() {

		validate();
		repaint();
	}

	public double zoom(double value) {
		return zoomFactor * value;
	}

	private void fitToDesktopSize() {

		double paperWidth = paperFormat.getWidth().toMillimeter();
		double paperHeight = paperFormat.getHeight().toMillimeter();

		if (endlessPaperMode == true) {
			paperHeight = getDesktopHeight();
		}

		double widthRatio = (desktopWidth - (2 * desktopMargin)) / paperWidth;
		double heightRatio = (desktopHeight - (2 * desktopMargin))
				/ paperHeight;

		double ratio = widthRatio;

		if (heightRatio < widthRatio) {
			ratio = heightRatio;
		}

		paperDisplayWidth = Math.round(ratio * paperWidth);
		paperDisplayHeight = Math.round(ratio * paperHeight);

		displayMargins.scale(ratio);
		displayRegions.scale(ratio);

	}

	private double getDesktopLeft() {

		if (desktopEnabled == false) {
			return 0.0f;
		}

		return (desktopWidth - paperDisplayWidth) / 2;
	}

	private double getDesktopRight() {

		if (desktopEnabled == false) {
			return 0.0f;
		}

		return (desktopWidth - paperDisplayWidth) / 2;
	}

	private double getDesktopTop() {

		if (desktopEnabled == false) {
			return 0.0f;
		}

		double topX = (desktopHeight - paperDisplayHeight) / 2;

		if (alignment == SwingConstants.TOP) {

			if (topX > desktopMargin) {
				topX = desktopMargin;
			}
		}

		// System.out.println("desktopTop=" + topX + ", desktopHeight=" +
		// desktopHeight + ", paperDisplayHeight="
		// + paperDisplayHeight);
		return topX;
	}

	private double getPaperDisplayHeight() {
		return paperDisplayHeight;
	}

	private double getPaperDisplayWidth() {
		return paperDisplayWidth;
	}

	private int getShadowLeftOffset() {
		return shadowLeftOffset;
	}

	private int getShadowTopOffset() {
		return shadowTopOffset;
	}

	private void paintHeader(Graphics2D g2d) {

		String header = String.valueOf(model.getHeader());

		if (header == null || header.trim().length() == 0) {
			return;
		}

		Font regionFont = textFont.deriveFont(12.0f);
		FontMetrics fm = g2d.getFontMetrics(regionFont);
		g2d.setFont(regionFont);
		g2d.setPaint(Color.black);

		double printableWidth = paperFormat.getPrintableWidth().toMillimeter();
		long center = Math.round(paperFormat.getMargins().getLeft()
				.toMillimeter()
				+ (printableWidth / 2));

		long ypos = Math.round(getDesktopTop()
				+ displayMargins.getTop().toMillimeter()
				+ displayRegions.getTop().toMillimeter());

		// Vertikal mittige Ausrichtung
		int yMiddle = rzoom(displayRegions.getTop().toMillimeter())
				- fm.getHeight() / 2;

		g2d.drawString(header, rzoom(center) - (fm.stringWidth(header) / 2),
				rzoom(ypos) - yMiddle);
	}

	/**
	 * @param g2d
	 * @param fm
	 */
	private void paintPageNumber(Graphics2D g2d) {

		Font regionFont = textFont.deriveFont(8.0f);
		FontMetrics fm = g2d.getFontMetrics(regionFont);
		g2d.setFont(regionFont);
		g2d.setPaint(Color.black);

		String pageNumberText = String.valueOf(model.getPageNumber());
		g2d.drawString(pageNumberText, rzoom(getDesktopLeft()
				+ getPaperDisplayWidth()
				- displayMargins.getRight().toMillimeter())
				- fm.stringWidth(pageNumberText), rzoom(getPaperDisplayHeight()
				- displayMargins.getBottom().toMillimeter()));
	}

	/**
	 * Zeichnet das Rechteck, das während des Ziehens mit der Maus die aktuell
	 * markierte Fläche zeigt. 
	 * 
	 * @param g2d
	 */
	private void paintSelectionRectangle(Graphics2D g2d) {

		if (selectionRectangle != null) {

			g2d.setPaint(JiveConstants.COLOUR_SAND);
			
			Composite originalComposite = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.33f));
			//g2d.setComposite(AlphaComposite.SrcOver.derive(0.33f));
			g2d.fillRect(selectionRectangle.x, selectionRectangle.y,
					selectionRectangle.width, selectionRectangle.height);
			g2d.setComposite(originalComposite);

			g2d.setStroke(basicStroke);
			g2d.drawRect(selectionRectangle.x, selectionRectangle.y,
					selectionRectangle.width, selectionRectangle.height);
		}
	}

	private float pointToMm(double imageableWidth) {
		return (float) (imageableWidth / 72) * 25.4f;
	}

	private void recalculate() {

		paperDisplayWidth = paperFormat.getWidth().toMillimeter();
		paperDisplayHeight = paperFormat.getHeight().toMillimeter();
		displayMargins = paperFormat.getMargins();
		displayRegions = paperFormat.getRegions();

		if (fitToDesktopSize == true) {
			fitToDesktopSize();
		}
	}

	/**
	 * @param copy
	 */
	protected void deepCopy(Sheet copy) {

		copy.setPaperFormat(getPaperFormat());
		copy.setZoomFactor(getZoomFactor());
		copy.setAutoZoom(isAutoZoom());
		copy.setFitToDesktopSize(isFitToDesktopSize());
		copy.setAlign(alignment);
		copy.setDesktopSize(desktopWidth, desktopHeight);
		copy.setDesktopMargin(desktopMargin);
		copy.setModel(model.deepCopy(copy));

		for (MouseListener mouseListener : getMouseListeners()) {
			copy.addMouseListener(mouseListener);
		}
	}

	/**
	 * @param child
	 * @param part
	 */
	protected void readCommonAttributes(Element child, AbstractPart part) {

		String value;

		value = child.getAttribute("id");
		if (value != null && value.trim().length() > 0) {
			part.setId(value);
		}

		value = child.getAttribute("margin");
		if (value != null) {
			part.setMargin(Margin.valueOf(value));
		}
	}

	protected Part readText(Element child) {

		String value;

		AbstractPart text = new Text(this, child.getTextContent());

		readCommonAttributes(child, text);

		value = child.getAttribute("font");

		if (value != null) {
			Font font = Font.decode(value);
			text.setFont(font);
		}

		try {

			value = child.getAttribute("alignment");

			if (value != null) {
				text.setAlignment(Alignment.valueOf(value.toUpperCase()));
			}

			value = child.getAttribute("margin");

			if (value != null) {
				text.setMargin(Margin.valueOf(value.toUpperCase()));
			}

		} catch (Exception oops) {
			// Keine Textausrichtung
		}

		return text;
	}
}
