package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.dbdoclet.jive.filter.BlurFilter;

public class RichLabel extends JLabel {

	private static final double FACTOR = 1.3;
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;

	public RichLabel(String text) {
		super(text);
	}

	@Override
	public Dimension getPreferredSize() {
		
		if (width > 0 && height > 0) {
			return new Dimension(width, height);
		}
		
		Dimension dim = super.getPreferredSize();
		dim = new Dimension((int) (dim.width * FACTOR), (int) (dim.height * FACTOR));
		return dim;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Font font = getFont();
		int offset = font.getSize() / 15;

		FontMetrics fontMetrics = g.getFontMetrics();

		width = (int) (fontMetrics.stringWidth(getText()) * FACTOR);
		height = (int) (fontMetrics.getHeight() * FACTOR);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);
		fontMetrics = g2d.getFontMetrics();
		int length = fontMetrics.stringWidth(getText());
		
		int xpos = (width / 2) - (length / 2);

		xpos = 0;

		int ypos = (height / 2)
				+ ((fontMetrics.getAscent() - fontMetrics.getDescent()) / 2);

		g2d.setPaint(Color.GRAY);
		g2d.drawString(getText(), xpos + offset, ypos + offset);

		BufferedImage blurImage = BlurFilter.gaussianBlur(image, 7);

		g2d = (Graphics2D) blurImage.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(getFont());
		g2d.setPaint(getForeground());
		g2d.drawString(getText(), 0, ypos);

		g.drawImage(blurImage, 0, 0, null);
	}
}
