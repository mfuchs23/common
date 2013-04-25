package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.format.Alignment;
import org.dbdoclet.jive.filter.BlurFilter;

public class TopPanel extends JPanel {

	private static final int BLUR_RADIUS = 9;

	private static Log logger = LogFactory.getLog(TopPanel.class);
	private static final long serialVersionUID = 1L;
	private static final int SHADOW_CANVAS_HEIGHT = 50;
	private static final int SHADOW_CANVAS_WIDTH = 4096;
	private static final int SHADOW_HEIGHT = 10;

	private Alignment alignment = Alignment.CENTER;
	private Color gradientColorFrom;
	private Color gradientColorTo;

	private int height = 60;
	private int width = 0;

	private BufferedImage leftImage;
	private BufferedImage rightImage;
	private Image shadowImage;
	private String title = "";
	private boolean titleHasChanged;
	private BufferedImage titleImage;
	private final Color eraseColor;

	public TopPanel() {
		setFont(new Font("Serif", Font.PLAIN, 24));
		eraseColor = getBackground();
	}

	public Alignment getAlignment() {
		return alignment;
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getPreferredSize() {

		Dimension dim = super.getPreferredSize();

		if (width == 0) {
			width = computeWidth(getTitle());
		}

		if (dim.height < height) {
			dim.height = height;
		}

		if (dim.width < width) {
			dim.width = width;
		}

		return dim;
	}

	public String getTitle() {

		if (title == null) {
			return "Title";
		}

		return title;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (leftImage != null && leftImage.getHeight() > height) {
			height = leftImage.getHeight();
		}

		if (rightImage != null && rightImage.getHeight() > height) {
			height = rightImage.getHeight();
		}

		if (shadowImage == null) {
			createShadow();
		}

		if (titleImage == null || titleHasChanged == true) {
			createTitleImage(getTitle());
			titleHasChanged = false;
		}

		if (titleImage != null && titleImage.getHeight() > height) {
			height = titleImage.getHeight() + SHADOW_HEIGHT;
		}

		// Zeichnen

		g2d.setPaint(eraseColor);
		g2d.fillRect(0, 0, getWidth(), height);

		if (gradientColorFrom != null && gradientColorTo != null) {
			g2d.setPaint(new GradientPaint(0, 0, gradientColorFrom, getWidth(),
					height / 2, gradientColorTo, false));
		} else {
			g2d.setPaint(getBackground());
		}

		g2d.fillRect(0, 0, getWidth(), height - SHADOW_HEIGHT);

		if (leftImage != null) {
			g2d.drawImage(leftImage, 0, 0, null);
		}

		if (rightImage != null) {
			g2d.drawImage(rightImage, getWidth() - rightImage.getWidth(), 0,
					null);
		}

		g2d.drawImage(shadowImage, 0, height - SHADOW_HEIGHT, null);
		g2d.drawImage(titleImage, 0, 0, null);

	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public void setGradient(Color gradientColorFrom, Color gradientColorTo) {
		this.gradientColorFrom = gradientColorFrom;
		this.gradientColorTo = gradientColorTo;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLeftImage(URL iconUrl) {

		try {

			if (iconUrl != null) {
				leftImage = readImage(iconUrl);
			}

		} catch (Throwable oops) {
			logger.fatal(oops);
		}
	}

	public void setRightImage(URL iconUrl) {

		try {

			if (iconUrl != null) {
				rightImage = readImage(iconUrl);
			}

		} catch (Throwable oops) {
			logger.fatal(oops);
		}
	}

	public void setTitle(String title) {
		this.title = title;
		titleHasChanged = true;
		repaint();
	}

	private void createShadow() {

		BufferedImage image = new BufferedImage(SHADOW_CANVAS_WIDTH,
				SHADOW_CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D shadow2D = (Graphics2D) image.getGraphics();
		shadow2D.setPaint(Color.DARK_GRAY);
		shadow2D.fillRect(0, SHADOW_CANVAS_HEIGHT / 2, SHADOW_CANVAS_WIDTH,
				BLUR_RADIUS);
		image = BlurFilter.boxBlur(image, BLUR_RADIUS);

		shadowImage = createImage(new FilteredImageSource(
				image.getSource(),
				new CropImageFilter(
						BLUR_RADIUS,
						(int) ((SHADOW_CANVAS_HEIGHT / 2) + (BLUR_RADIUS * 0.81)),
						SHADOW_CANVAS_WIDTH - BLUR_RADIUS, SHADOW_HEIGHT)));
	}

	private void createTitleImage(String title) {

		BufferedImage image;
		Graphics2D g2d;
		FontMetrics fontMetrics;

		width = computeWidth(title);

		image = new BufferedImage(width, height - SHADOW_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(getFont());
		fontMetrics = g2d.getFontMetrics();
		int length = fontMetrics.stringWidth(title);

		int offset = 1;
		int xpos = (width / 2) - (length / 2);

		if (alignment == Alignment.LEFT) {
			xpos = fontMetrics.getAscent();
		}

		int ypos = (height / 2)
				+ ((fontMetrics.getAscent() - fontMetrics.getDescent()) / 2);

		g2d.setPaint(Color.GRAY);
		g2d.drawString(title, xpos + offset, ypos + offset);

		BufferedImage blurImage = BlurFilter.gaussianBlur(image, 5);

		g2d = (Graphics2D) blurImage.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(getFont());
		g2d.setPaint(getForeground());
		g2d.drawString(title, xpos, ypos);

		titleImage = blurImage;
	}

	private int computeWidth(String title) {

		BufferedImage image = new BufferedImage(1024, 100,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setFont(getFont());

		FontMetrics fontMetrics = g2d.getFontMetrics();
		int width = fontMetrics.getAscent() * 2
				+ fontMetrics.stringWidth(title);

		if (getWidth() > width) {
			width = getWidth();
		}

		return width;
	}

	private BufferedImage readImage(URL iconUrl) {

		try {
			return ImageIO.read(iconUrl);
		} catch (IOException e) {
			return null;
		}
	}

}
