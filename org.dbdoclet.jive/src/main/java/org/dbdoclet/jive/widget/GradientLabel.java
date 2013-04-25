package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JLabel;

public class GradientLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	private Color start;
	private Color end;

	private boolean gradientEnabled;

	public GradientLabel() {

		super("");
		start = Color.LIGHT_GRAY;
		end = getBackground();
	}

	public GradientLabel(String text) {

		super(text);
		start = Color.LIGHT_GRAY;
		end = getBackground();
	}

	public GradientLabel(String text, Color start, Color end) {

		super(text);
		this.start = start;
		this.end = end;
	}

	public void paint(Graphics g) {

		int width = getWidth();
		int height = getHeight();

		if (gradientEnabled == true) {
	
			GradientPaint paint = new GradientPaint(0, 0, start, width, height,
					end, true);

			Graphics2D g2d = (Graphics2D) g;
			Paint oldPaint = g2d.getPaint();
			g2d.setPaint(paint);
			g2d.fillRect(0, 0, width, height);
			g2d.setPaint(oldPaint);
		}

		super.paint(g);
	}

	public void setGradient(Color start, Color end) {
		this.start = start;
		this.end = end;
	}

	public void setGradientEnabled(boolean gradientEnabled) {
		this.gradientEnabled = gradientEnabled;
	}
}
