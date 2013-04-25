package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Wertobjekt für XSL-FO Attribute. Bei einer Erweiterung um neue Attribute
 * müssen die Methoden {@link #clone} und {@link #copy} erweitert werden!
 * 
 * @author michael
 */
public class FoAttributeSet {

	public static final int FONT_SET = 0x01;
	public static final int FRAME_SET = 0x04;

	public static final String[] FRAME_STYLE_LIST = new String[] { "solid",
			"dashed", "dotted", "double", "inset", "outset", "groove", "ridge",
			"none" };
	public static final int LINE_SET = 0x04;
	public static final int SPACING_SET = 0x02;

	// private static final Border border = BorderFactory
	// .createCompoundBorder(BorderFactory.createLineBorder(Color.black),
	// BorderFactory.createEmptyBorder(4, 4, 4, 4));

	private static final Border border = BorderFactory.createEmptyBorder(4, 4,
			4, 4);

	private static final String defText = "\"Was glänzt, ist für den Augenblick geboren; // Das Echte bleibt der Nachwelt unverloren.\" - Johann Wolfgang von Goethe, Faust I, Vers 73 f. / Dichter";

	private Color backgroundColor = Color.white;;
	private boolean changed = false;
	private String endIndent;
	private Dimension fixedSize = null;
	private FoAttributeSetChooser foAttributeSetChooser;
	private boolean fontColorEnabled = true;
	private boolean fontEnabled = false;
	private boolean fontStyleEnabled = true;
	private Color foregroundColor = Color.black;
	private boolean frameBottom = false;
	private Color frameColor = Color.black;
	private boolean frameEnabled = false;
	private boolean frameLeft = false;
	private boolean frameRight = false;
	private String frameStyle = "solid";
	private boolean frameTop = false;
	private String frameWidth = "1pt";
	private boolean lineEnabled;
	private String lineHeight;
	private String padding;
	private String spaceAfterMaximum;
	private boolean spaceAfterMaximumVisible = true;
	private String spaceAfterMinimum;
	private boolean spaceAfterMinimumVisible = true;
	private String spaceAfterOptimum;
	private String spaceBeforeMaximum;
	private boolean spaceBeforeMaximumVisible = true;
	private String spaceBeforeMinimum;
	private boolean spaceBeforeMinimumVisible = true;
	private String spaceBeforeOptimum;
	private boolean spacingEnabled;
	private String startIndent;
	private String textAlign;
	private String textIndent;
	private int type = FONT_SET | SPACING_SET | FRAME_SET | LINE_SET;
	private String width;
	private String wrapOption;
	private Font font;
	private String text;
	private JLabel label;
	private boolean activated;

	public FoAttributeSet(Font font, Color color) {
		this(defText, font, color);
	}

	public FoAttributeSet(String text, Font font, Color color) {

		if (text == null) {
			text = defText;
		}

		if (color == null) {
			color = Color.black;
		}

		setText(text);
		setFont(font);
		setForeground(color);
		setBackground(Color.white);
	}

	public boolean changed() {
		return changed;
	}

	/**
	 * Erstellt eine exakte Kopie der Instanz eines Objektes.
	 */
	@Override
	public FoAttributeSet clone() {

		FoAttributeSet clone = new FoAttributeSet(getText(), getFont(),
				getForeground());
		clone.setType(getType());

		clone.setFontEnabled(isFontEnabled());
		clone.setFontColorEnabled(isFontColorEnabled());
		clone.setFontStyleEnabled(isFontStyleEnabled());
		clone.setFont(getFont());
		clone.setForeground(getForeground());
		clone.setBackground(getBackground());

		clone.setSpacingEnabled(isSpacingEnabled());
		clone.setSpaceBeforeMinimum(getSpaceBeforeMinimum());
		clone.setSpaceBeforeMinimumVisible(isSpaceBeforeMinimumVisible());
		clone.setSpaceBeforeOptimum(getSpaceBeforeOptimum());
		clone.setSpaceBeforeMaximum(getSpaceBeforeMaximum());
		clone.setSpaceBeforeMaximumVisible(isSpaceBeforeMaximumVisible());

		clone.setSpaceAfterMinimum(getSpaceAfterMinimum());
		clone.setSpaceAfterMinimumVisible(isSpaceAfterMinimumVisible());
		clone.setSpaceAfterOptimum(getSpaceAfterOptimum());
		clone.setSpaceAfterMaximum(getSpaceAfterMaximum());
		clone.setSpaceAfterMaximumVisible(isSpaceAfterMaximumVisible());

		clone.setPadding(getPadding());
		clone.setTextIndent(getTextIndent());
		clone.setStartIndent(getStartIndent());
		clone.setEndIndent(getEndIndent());

		clone.setFrameEnabled(isFrameEnabled());
		clone.setFrameTop(isFrameTop());
		clone.setFrameBottom(isFrameBottom());
		clone.setFrameRight(isFrameRight());
		clone.setFrameLeft(isFrameLeft());
		clone.setFrameWidth(getFrameWidth());
		clone.setFrameStyle(getFrameStyle());
		clone.setFrameColor(getFrameColor());

		clone.setLineEnabled(isLineEnabled());
		clone.setWrapOption(getWrapOption());
		clone.setTextAlign(getTextAlign());
		clone.setFoWidth(getFoWidth());
		clone.setLineHeight(getLineHeight());

		return clone;
	}

	/**
	 * Übernimmt die Werte einer anderen Instanz in das aktuelle Objekt.
	 * 
	 * @param other
	 */
	public void copy(FoAttributeSet other) {

		setText(other.getText());
		setType(getType());

		setFontEnabled(other.isFontEnabled());
		setFontColorEnabled(other.isFontColorEnabled());
		setFontStyleEnabled(other.isFontStyleEnabled());
		setFont(other.getFont());
		setForeground(other.getForeground());
		setBackground(other.getBackground());

		setSpacingEnabled(other.isSpacingEnabled());
		setSpaceBeforeMinimum(other.getSpaceBeforeMinimum());
		setSpaceBeforeOptimum(other.getSpaceBeforeOptimum());
		setSpaceBeforeMaximum(other.getSpaceBeforeMaximum());

		setSpaceAfterMinimum(other.getSpaceAfterMinimum());
		setSpaceAfterOptimum(other.getSpaceAfterOptimum());
		setSpaceAfterMaximum(other.getSpaceAfterMaximum());

		setPadding(other.getPadding());
		setTextIndent(other.getTextIndent());
		setStartIndent(other.getStartIndent());
		setEndIndent(other.getEndIndent());

		setFrameEnabled(other.isFrameEnabled());
		setFrameTop(other.isFrameTop());
		setFrameBottom(other.isFrameBottom());
		setFrameRight(other.isFrameRight());
		setFrameLeft(other.isFrameLeft());
		setFrameWidth(other.getFrameWidth());
		setFrameStyle(other.getFrameStyle());
		setFrameColor(other.getFrameColor());

		setLineEnabled(other.isLineEnabled());
		setWrapOption(other.getWrapOption());
		setTextAlign(other.getTextAlign());
		setFoWidth(other.getFoWidth());
		setLineHeight(other.getLineHeight());
	}

	public Color getBackground() {

		if (backgroundColor == null) {
			return Color.white;
		}

		return backgroundColor;
	}

	public String getEndIndent() {
		return endIndent;
	}

	public Font getFont() {
		return font;
	}

	public String getFontFamily() {
		return getFont().getFamily();
	}

	public int getFontSize() {
		return getFont().getSize();
	}

	public int getFontStyle() {
		return getFont().getStyle();
	}

	public Color getForeground() {

		if (foregroundColor == null) {
			foregroundColor = Color.black;
		}

		return foregroundColor;
	}

	public String getFoWidth() {
		return width;
	}

	public Color getFrameColor() {
		return frameColor;
	}

	public String getFrameStyle() {
		return frameStyle;
	}

	public String getFrameWidth() {
		return frameWidth;
	}

	public String getLineHeight() {
		return lineHeight;
	}

	public String getPadding() {
		return padding;
	}

	public String getSpaceAfterMaximum() {
		return spaceAfterMaximum;
	}

	public String getSpaceAfterMinimum() {
		return spaceAfterMinimum;
	}

	public String getSpaceAfterOptimum() {
		return spaceAfterOptimum;
	}

	public String getSpaceBeforeMaximum() {
		return spaceBeforeMaximum;
	}

	public String getSpaceBeforeMinimum() {
		return spaceBeforeMinimum;
	}

	public String getSpaceBeforeOptimum() {
		return spaceBeforeOptimum;
	}

	public String getStartIndent() {
		return startIndent;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public String getTextIndent() {
		return textIndent;
	}

	public int getType() {
		return type;
	}

	public String getWrapOption() {
		return wrapOption;
	}

	public boolean isActivated() {
		return activated;
	}

	public boolean isFontColorEnabled() {
		return fontColorEnabled;
	}

	public boolean isFontEnabled() {
		return fontEnabled;
	}

	public boolean isFontStyleEnabled() {
		return fontStyleEnabled;
	}

	public boolean isFontType() {
		return (type & FONT_SET) == FONT_SET;
	}

	public boolean isFrameBottom() {
		return frameBottom;
	}

	public boolean isFrameEnabled() {
		return frameEnabled;
	}

	public boolean isFrameLeft() {
		return frameLeft;
	}

	public boolean isFrameRight() {
		return frameRight;
	}

	public boolean isFrameTop() {
		return frameTop;
	}

	public boolean isFrameType() {
		return (type & FRAME_SET) == FRAME_SET;
	}

	public boolean isLineEnabled() {
		return lineEnabled;
	}

	public boolean isLineType() {
		return (type & LINE_SET) == LINE_SET;
	}

	public boolean isSpaceAfterMaximumVisible() {
		return spaceAfterMaximumVisible;
	}

	public boolean isSpaceAfterMinimumVisible() {
		return spaceAfterMinimumVisible;
	}

	public boolean isSpaceBeforeMaximumVisible() {
		return spaceBeforeMaximumVisible;
	}

	public boolean isSpaceBeforeMinimumVisible() {
		return spaceBeforeMinimumVisible;
	}

	public boolean isSpacingEnabled() {
		return spacingEnabled;
	}

	public boolean isSpacingType() {
		return (type & SPACING_SET) == SPACING_SET;
	}

	public void refresh() {

		if (foAttributeSetChooser != null) {
			foAttributeSetChooser.refreshAttributeSet(this);
		}
	}

	public void reset() {

		setActivated(false);
		setFontEnabled(false);
		setFrameEnabled(false);
		setSpacingEnabled(false);
		setLineEnabled(false);

		setFont(null);
		setChanged(false);
		setForeground(Color.black);
		setBackground(Color.white);
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setBackground(Color bg) {

		if (bg == null) {
			backgroundColor = Color.white;
		}

		backgroundColor = bg;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void setChooser(FoAttributeSetChooser foAttributeSetChooser) {
		this.foAttributeSetChooser = foAttributeSetChooser;
	}

	public void setEndIndent(String endIndent) {
		this.endIndent = endIndent;
	}

	public void setFixedSize(Dimension fixedSize) {

		this.fixedSize = fixedSize;
	}

	public void setFont(Font font) {

		if (font == null) {
			font = UIManager.getDefaults().getFont("Label.font");
		}

		this.font = font;
	}

	public void setFontColorEnabled(boolean fontColorEnabled) {
		this.fontColorEnabled = fontColorEnabled;
	}

	public void setFontEnabled(boolean fontEnabled) {
		this.fontEnabled = fontEnabled;
	}

	public void setFontStyleEnabled(boolean fontStyleEnabled) {
		this.fontStyleEnabled = fontStyleEnabled;
	}

	public void setForeground(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setFoWidth(String width) {
		this.width = width;
	}

	public void setFrameBottom(boolean frameBottom) {
		this.frameBottom = frameBottom;
	}

	public void setFrameColor(Color frameColor) {
		this.frameColor = frameColor;
	}

	public void setFrameEnabled(boolean frameEnabled) {
		this.frameEnabled = frameEnabled;
	}

	public void setFrameLeft(boolean frameLeft) {
		this.frameLeft = frameLeft;
	}

	public void setFrameRight(boolean frameRight) {
		this.frameRight = frameRight;
	}

	public void setFrameSideEnabled(String side) {

		if (side == null) {
			return;
		}

		if (side.equalsIgnoreCase("top")) {
			setFrameTop(true);
		}

		if (side.equalsIgnoreCase("bottom")) {
			setFrameBottom(true);
		}

		if (side.equalsIgnoreCase("right")) {
			setFrameRight(true);
		}

		if (side.equalsIgnoreCase("left")) {
			setFrameLeft(true);
		}
	}

	public void setFrameStyle(String frameStyle) {
		this.frameStyle = frameStyle;
	}

	public void setFrameTop(boolean frameTop) {
		this.frameTop = frameTop;
	}

	public void setFrameWidth(String frameWidth) {
		this.frameWidth = frameWidth;
	}

	public void setLineEnabled(boolean lineEnabled) {
		this.lineEnabled = lineEnabled;
	}

	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public void setSpaceAfterMaximum(String spaceAfterMaximum) {
		this.spaceAfterMaximum = spaceAfterMaximum;
	}

	public void setSpaceAfterMaximumVisible(boolean spaceAfterMaximumVisible) {
		this.spaceAfterMaximumVisible = spaceAfterMaximumVisible;
	}

	public void setSpaceAfterMinimum(String spaceAfterMinimum) {
		this.spaceAfterMinimum = spaceAfterMinimum;
	}

	public void setSpaceAfterMinimumVisible(boolean spaceAfterMinimumVisible) {
		this.spaceAfterMinimumVisible = spaceAfterMinimumVisible;
	}

	public void setSpaceAfterOptimum(String spaceAfterOptimum) {
		this.spaceAfterOptimum = spaceAfterOptimum;
	}

	public void setSpaceBeforeMaximum(String spaceBeforeMaximum) {
		this.spaceBeforeMaximum = spaceBeforeMaximum;
	}

	public void setSpaceBeforeMaximumVisible(boolean spaceBeforeMaximumVisible) {
		this.spaceBeforeMaximumVisible = spaceBeforeMaximumVisible;
	}

	public void setSpaceBeforeMinimum(String spaceBeforeMinimum) {
		this.spaceBeforeMinimum = spaceBeforeMinimum;
	}

	public void setSpaceBeforeMinimumVisible(boolean spaceBeforeMinimumVisible) {
		this.spaceBeforeMinimumVisible = spaceBeforeMinimumVisible;
	}

	public void setSpaceBeforeOptimum(String spaceBeforeOptimum) {
		this.spaceBeforeOptimum = spaceBeforeOptimum;
	}

	public void setSpacingEnabled(boolean spacingEnabled) {
		this.spacingEnabled = spacingEnabled;
	}

	public void setStartIndent(String startIndent) {
		this.startIndent = startIndent;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	public void setTextIndent(String textIndent) {
		this.textIndent = textIndent;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWrapOption(String wrapOption) {
		this.wrapOption = wrapOption;
	}

	public JLabel toJLabel() {

		if (label == null) {
			label = new JLabel(getText());
			label.setBorder(border);
			label.setOpaque(true);
			label.setHorizontalAlignment(SwingConstants.LEFT);
		}

		if (fixedSize != null) {
			label.setPreferredSize(fixedSize);
			label.setMaximumSize(fixedSize);
			label.setMinimumSize(fixedSize);
			label.setSize(fixedSize);
		} else {
			calculateDimension(label, font);
		}

		return label;
	}

	private void calculateDimension(JLabel label, Font font) {

		Graphics g = label.getGraphics();

		if (font == null) {
			return;
		}

		Dimension dimension = new Dimension(200, 48);

		if (g != null) {

			FontMetrics fm = g.getFontMetrics(font);
			dimension.height = fm.getHeight() + 6;

		} else {

			// dimension.height = font.getSize() + 8;
		}

		label.setPreferredSize(dimension);
		label.setMaximumSize(dimension);
		label.setMinimumSize(dimension);
		label.setSize(dimension);
	}

	private String getText() {
		return text;
	}

	private void setText(String text) {
		this.text = text;
	}

	public void autoEnable() {

		if (font != null || foregroundColor != null || backgroundColor != null) {
			setFontEnabled(true);
		} else {
			setFontEnabled(false);
		}

		if (spaceAfterMaximum != null || spaceAfterMinimum != null
				|| spaceAfterOptimum != null || spaceBeforeMaximum != null
				|| spaceBeforeMinimum != null || spaceBeforeOptimum != null
				|| padding != null || startIndent != null || textIndent != null
				|| endIndent != null) {
			setSpacingEnabled(true);
		} else {
			setSpacingEnabled(false);
		}

		if (frameBottom || frameLeft || frameRight || frameTop
				|| frameColor != null || frameStyle != null
				|| frameWidth != null) {
			setFrameEnabled(true);
		} else {
			setFrameEnabled(false);
		}

		if (lineHeight != null || textAlign != null || wrapOption != null
				|| width != null) {
			setLineEnabled(true);
		} else {
			setLineEnabled(false);
		}
	}
}
