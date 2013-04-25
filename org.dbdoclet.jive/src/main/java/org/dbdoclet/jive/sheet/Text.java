package org.dbdoclet.jive.sheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;

import org.dbdoclet.format.Alignment;
import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;
import org.dbdoclet.xiphias.XmlServices;
import org.w3c.dom.Element;

public class Text extends AbstractPart {

	private String content;
	private Color color;
	private ArrayList<String> lines;
	private FontRenderContext frc;

	public Text(Sheet sheet, String content) {
		this(sheet, content, new Font("Times New Roman", Font.PLAIN, 12),
				Color.black, Alignment.CENTER);
	}

	public Text(Sheet sheet, String content, Font font, Color color,
			Alignment alignment) {

		super(sheet);
		this.content = content;
		this.font = font;
		this.color = color;
		this.alignment = alignment;

		lines = new ArrayList<String>();
	}

	public Part deepCopy(Sheet sheet) {

		AbstractPart copy = new Text(sheet, content, font, color, alignment);
		fillCopy(copy);
		return copy;
	}

	public Color getColor() {
		return color;
	}

	public String getContent() {
		return content;
	}

	public double getNormalizedHeight() {

		double lineHeight = 0;

		if (frc == null) {
			lineHeight = new Length(font.getSize2D(), LengthUnit.POINT)
					.toMillimeter();
		} else {
			lineHeight = new Length(font.getLineMetrics("M", frc).getHeight(),
					LengthUnit.POINT).toMillimeter();
		}

		double normalizedHeight = (lineHeight * lines.size()) * 1.023;

		return margin.getTop().toMillimeter() + padding.getTop().toMillimeter()
				+ normalizedHeight + padding.getBottom().toMillimeter()
				+ margin.getBottom().toMillimeter();
	}

	public void paintPart(Graphics2D g2d) {

		frc = g2d.getFontRenderContext();

		double scaledFontSize = new Length(font.getSize()
				* sheet.getZoomFactor(), LengthUnit.POINT).toMillimeter();

		Font zoomedFont = font.deriveFont(new Double(scaledFontSize)
				.floatValue());

		g2d.setFont(zoomedFont);
		g2d.setPaint(color);

		PaperFormat paperFormat = sheet.getPaperFormat();

		double printableWidth = paperFormat.getPrintableWidth().toMillimeter();
		double zoomedPrintableWidth = zoom(printableWidth);
		int zoomedFontHeight = computeFontHeight(g2d, zoomedFont);

		Rectangle bb = sheet.rzoom(getBoundingBox());

		int ypos = bb.y + zoomedFontHeight;
		int xpos = bb.x;
		int xmax = 0;

		content = XmlServices.normalizeText(content);
		Point point = new Point();

		for (String line : layoutText(g2d, zoomedFont, zoomedPrintableWidth, 0,
				content)) {

			if (alignment == Alignment.RIGHT) {

				xpos = (bb.x + bb.width) - computeStringWidth(g2d, zoomedFont, line);

			} else if (alignment == Alignment.LEFT) {

				xpos = bb.x;

			} else {

				xpos = bb.x + (bb.width / 2);
				xpos = xpos - (computeStringWidth(g2d, zoomedFont, line) / 2);
			}

			g2d.drawString(line.trim(), xpos, ypos);
			point.y = ypos;
			point.x = xpos + computeStringWidth(g2d, zoomedFont, line.trim());

			if (point.x > xmax) {
				xmax = point.x;
			}

			ypos += zoomedFontHeight;
			bb.x = point.x;
		}

		boundingBox.height = getNormalizedHeight();
		updateBoxes();

		super.afterPaintPart(g2d);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void writeElement(Element element) {

		super.writeElement(element);

		element.setTextContent(content);
		element.setAttribute("font",
				font.getFamily() + "-" + getFontStyle(font.getStyle()) + "-"
						+ font.getSize());
		// element.setAttribute("color", color.toString());
		element.setAttribute("alignment", alignment.toString().toLowerCase());
	}

	private String getFontStyle(int style) {

		switch (style) {
		case Font.PLAIN:
			return "plain";
		case Font.BOLD:
			return "bold";
		case Font.ITALIC:
			return "italic";
		case Font.BOLD | Font.ITALIC:
			return "bold+italic";
		default:
			return "plain";
		}
	}

	private ArrayList<String> layoutText(Graphics2D g2d, Font zoomedFont,
			double zoomedPrintableWidth, int xpos, String text) {

		lines = new ArrayList<String>();

		int cursor = 0;
		int lineEnd = 0;

		for (int i = 0; i < text.length(); i++) {

			char c = text.charAt(i);

			if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {

				String line = text.substring(cursor, i);
				int lineWidth = computeStringWidth(g2d, zoomedFont, line.trim());

				if (xpos + lineWidth > zoomedPrintableWidth) {

					lines.add(text.substring(cursor, lineEnd).trim());
					cursor = lineEnd;
					xpos = 0;

				} else {

					lineEnd = i;
				}
			}
		}

		if (lines.size() == 0) {
			lines.add(text);
		} else {
			lines.add(text.substring(cursor));
		}

		return lines;
	}
}