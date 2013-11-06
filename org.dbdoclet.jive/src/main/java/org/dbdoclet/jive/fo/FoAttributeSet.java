package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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
	public static final int SPACING_SET = 0x02;

	// private static final Border border = BorderFactory
	// .createCompoundBorder(BorderFactory.createLineBorder(Color.black),
	// BorderFactory.createEmptyBorder(4, 4, 4, 4));

	private static final Border border = BorderFactory.createEmptyBorder(4, 4,
			4, 4);

	public static final int LINE_SET = 0x04;
	private static final String defText = "\"Was glänzt, ist für den Augenblick geboren; // Das Echte bleibt der Nachwelt unverloren.\" - Johann Wolfgang von Goethe, Faust I, Vers 73 f. / Dichter";

	private boolean changed = false;
	private Dimension fixedSize = null;
	private FontAttributes fontAttributes;
	private FrameAttributes frameAttributes;
	private SpacingAttributes spacingAttributes;
	private LineAttributes lineAttributes;
	
	private int type = FONT_SET | SPACING_SET | FRAME_SET | LINE_SET;
	private String text;
	private JLabel label;

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

		spacingAttributes = new SpacingAttributes();
		fontAttributes = new FontAttributes();
		frameAttributes = new FrameAttributes();
		lineAttributes = new LineAttributes();
		
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
		clone.setLineWidth(getLineWidth());
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
		setLineWidth(other.getLineWidth());
		setLineHeight(other.getLineHeight());
	}

	public Color getBackground() {
		return fontAttributes.getBackgroundColor();
	}

	public String getEndIndent() {
		return spacingAttributes.getEndIndent();
	}

	public Font getFont() {
		return fontAttributes.getFont();
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
		return fontAttributes.getForegroundColor();
	}

	public String getLineWidth() {
		return lineAttributes.getLineWidth();
	}

	public Color getFrameColor() {
		return frameAttributes.getFrameColor();
	}

	public String getFrameStyle() {
		return frameAttributes.getFrameStyle();
	}

	public String getFrameWidth() {
		return frameAttributes.getFrameWidth();
	}

	public String getLineHeight() {
		return lineAttributes.getLineHeight();
	}

	public String getPadding() {
		return spacingAttributes.getPadding();
	}

	public String getSpaceAfterMaximum() {
		return spacingAttributes.getSpaceAfterMaximum();
	}

	public String getSpaceAfterMinimum() {
		return spacingAttributes.getSpaceAfterMinimum();
	}

	public String getSpaceAfterOptimum() {
		return spacingAttributes.getSpaceAfterOptimum();
	}

	public String getSpaceBeforeMaximum() {
		return spacingAttributes.getSpaceBeforeMaximum();
	}

	public String getSpaceBeforeMinimum() {
		return spacingAttributes.getSpaceBeforeMinimum();
	}

	public String getSpaceBeforeOptimum() {
		return spacingAttributes.getSpaceBeforeOptimum();
	}

	public String getStartIndent() {
		return spacingAttributes.getStartIndent();
	}

	public String getTextAlign() {
		return lineAttributes.getTextAlign();
	}

	public String getTextIndent() {
		return spacingAttributes.getTextIndent();
	}

	public int getType() {
		return type;
	}

	public String getWrapOption() {
		return lineAttributes.getWrapOption();
	}

	public boolean isActivated() {
		return isLineEnabled() || isFrameEnabled() || isSpacingEnabled() || isFontEnabled();
	}

	public boolean isFontColorEnabled() {
		return fontAttributes.isFontColorEnabled();
	}

	public boolean isFontEnabled() {
		return fontAttributes.isFontEnabled();
	}

	public boolean isFontStyleEnabled() {
		return fontAttributes.isFontStyleEnabled();
	}

	public boolean isFontType() {
		return (type & FONT_SET) == FONT_SET;
	}

	public boolean isFrameBottom() {
		return frameAttributes.isFrameBottom();
	}

	public boolean isFrameEnabled() {
		return frameAttributes.isFrameEnabled();
	}

	public boolean isFrameLeft() {
		return frameAttributes.isFrameLeft();
	}

	public boolean isFrameRight() {
		return frameAttributes.isFrameRight();
	}

	public boolean isFrameTop() {
		return frameAttributes.isFrameTop();
	}

	public boolean isFrameType() {
		return (type & FRAME_SET) == FRAME_SET;
	}

	public boolean isLineEnabled() {
		return lineAttributes.isLineEnabled();
	}

	public boolean isLineType() {
		return (type & LINE_SET) == LINE_SET;
	}

	public boolean isSpaceAfterMaximumVisible() {
		return spacingAttributes.isSpaceAfterMaximumVisible();
	}

	public boolean isSpaceAfterMinimumVisible() {
		return spacingAttributes.isSpaceAfterMinimumVisible();
	}

	public boolean isSpaceBeforeMaximumVisible() {
		return spacingAttributes.isSpaceBeforeMaximumVisible();
	}

	public boolean isSpaceBeforeMinimumVisible() {
		return spacingAttributes.isSpaceBeforeMinimumVisible();
	}

	public boolean isSpacingEnabled() {
		return spacingAttributes.isSpacingEnabled();
	}

	public boolean isSpacingType() {
		return (type & SPACING_SET) == SPACING_SET;
	}

	public void reset() {

		setFontEnabled(false);
		setFrameEnabled(false);
		setSpacingEnabled(false);
		setLineEnabled(false);

		setFont(null);
		setChanged(false);
		setForeground(Color.black);
		setBackground(Color.white);
	}

	public void setBackground(Color bg) {
		fontAttributes.setBackgroundColor(bg);
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void setChooser(FoAttributeSetChooser foAttributeSetChooser) {
	}

	public void setEndIndent(String endIndent) {
		spacingAttributes.setEndIndent(endIndent);
	}

	public void setFixedSize(Dimension fixedSize) {

		this.fixedSize = fixedSize;
	}

	public void setFont(Font font) {
		fontAttributes.setFont(font);
	}

	public void setFontColorEnabled(boolean fontColorEnabled) {
		fontAttributes.setFontColorEnabled(fontColorEnabled);
	}

	public void setFontEnabled(boolean fontEnabled) {
		fontAttributes.setFontEnabled(fontEnabled);
	}

	public void setFontStyleEnabled(boolean fontStyleEnabled) {
		fontAttributes.setFontStyleEnabled(fontStyleEnabled);
	}

	public void setForeground(Color foregroundColor) {
		fontAttributes.setForegroundColor(foregroundColor);
	}

	public void setLineWidth(String width) {
		lineAttributes.setLineWidth(width);
	}

	public void setFrameBottom(boolean frameBottom) {
		frameAttributes.setFrameBottom(frameBottom);
	}

	public void setFrameColor(Color frameColor) {
		frameAttributes.setFrameColor(frameColor);
	}

	public void setFrameEnabled(boolean frameEnabled) {
		frameAttributes.setFrameEnabled(frameEnabled);
	}

	public void setFrameLeft(boolean frameLeft) {
		frameAttributes.setFrameLeft(frameLeft);
	}

	public void setFrameRight(boolean frameRight) {
		frameAttributes.setFrameRight(frameRight);
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
		frameAttributes.setFrameStyle(frameStyle);
	}

	public void setFrameTop(boolean frameTop) {
		frameAttributes.setFrameTop(frameTop);
	}

	public void setFrameWidth(String frameWidth) {
		frameAttributes.setFrameWidth(frameWidth);
	}

	public void setLineEnabled(boolean lineEnabled) {
		lineAttributes.setLineEnabled(lineEnabled);
	}

	public void setLineHeight(String lineHeight) {
		lineAttributes.setLineHeight(lineHeight);
	}

	public void setPadding(String padding) {
		spacingAttributes.setPadding(padding);		setFrameColor(null);

	}

	public void setSpaceAfterMaximum(String spaceAfterMaximum) {
		spacingAttributes.setSpaceAfterMaximum(spaceAfterMaximum);
	}

	public void setSpaceAfterMaximumVisible(boolean spaceAfterMaximumVisible) {
		spacingAttributes.setSpaceAfterMaximumVisible(spaceAfterMaximumVisible);
	}

	public void setSpaceAfterMinimum(String spaceAfterMinimum) {
		spacingAttributes.setSpaceAfterMinimum(spaceAfterMinimum);
	}

	public void setSpaceAfterMinimumVisible(boolean spaceAfterMinimumVisible) {
		spacingAttributes.setSpaceAfterMinimumVisible(spaceAfterMinimumVisible);
	}

	public void setSpaceAfterOptimum(String spaceAfterOptimum) {
		spacingAttributes.setSpaceAfterOptimum(spaceAfterOptimum);
	}

	public void setSpaceBeforeMaximum(String spaceBeforeMaximum) {
		spacingAttributes.setSpaceBeforeMaximum(spaceBeforeMaximum);
	}

	public void setSpaceBeforeMaximumVisible(boolean spaceBeforeMaximumVisible) {
		spacingAttributes.setSpaceBeforeMaximumVisible(spaceBeforeMaximumVisible);
	}

	public void setSpaceBeforeMinimum(String spaceBeforeMinimum) {
		spacingAttributes.setSpaceBeforeMinimum(spaceBeforeMinimum);
	}

	public void setSpaceBeforeMinimumVisible(boolean spaceBeforeMinimumVisible) {
		spacingAttributes.setSpaceBeforeMinimumVisible(spaceBeforeMinimumVisible);
	}

	public void setSpaceBeforeOptimum(String spaceBeforeOptimum) {
		spacingAttributes.setSpaceBeforeOptimum(spaceBeforeOptimum);
	}

	public void setSpacingEnabled(boolean spacingEnabled) {
		spacingAttributes.setSpacingEnabled(spacingEnabled);
	}

	public void setStartIndent(String startIndent) {
		spacingAttributes.setStartIndent(startIndent);
	}

	public void setTextAlign(String textAlign) {
		lineAttributes.setTextAlign(textAlign);
	}

	public void setTextIndent(String textIndent) {
		spacingAttributes.setTextIndent(textIndent);
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWrapOption(String wrapOption) {
		lineAttributes.setWrapOption(wrapOption);
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
			calculateDimension(label, fontAttributes.getFont());
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

	public String getText() {
		return text;
	}

	private void setText(String text) {
		this.text = text;
	}

	public void autoEnable() {

		fontAttributes.autoEnable();
		spacingAttributes.autoEnable();
		frameAttributes.autoEnable();
		lineAttributes.autoEnable();
	}
}
