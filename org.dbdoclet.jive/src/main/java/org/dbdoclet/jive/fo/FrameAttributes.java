package org.dbdoclet.jive.fo;

import java.awt.Color;

public class FrameAttributes {

	private boolean frameBottom = false;
	private Color frameColor = Color.black;
	private boolean frameEnabled = false;
	private boolean frameLeft = false;
	private boolean frameRight = false;
	private String frameStyle = "solid";
	private boolean frameTop = false;
	private String frameWidth = "1pt";
	
	public Color getFrameColor() {
		return frameColor;
	}
	
	public String getFrameStyle() {
		return frameStyle;
	}
	
	public String getFrameWidth() {
		return frameWidth;
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
	
	public void setFrameBottom(boolean frameBottom) {
		this.frameBottom = frameBottom;
	}
	
	public void setFrameColor(Color frameColor) {
		this.frameColor = frameColor;
	}
	
	public void setFrameEnabled(boolean frameEnabled) {
		this.frameEnabled = frameEnabled;
		if (frameEnabled == false) {
			reset();
		}
	}
	
	private void reset() {
		setFrameBottom(false);
		setFrameTop(false);
		setFrameLeft(false);
		setFrameRight(false);
		setFrameColor(null);
		setFrameStyle(null);
		setFrameWidth(null);
	}

	public void setFrameLeft(boolean frameLeft) {
		this.frameLeft = frameLeft;
	}
	
	public void setFrameRight(boolean frameRight) {
		this.frameRight = frameRight;
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

	public void autoEnable() {

		if (frameBottom || frameLeft || frameRight || frameTop
				|| frameColor != null || frameStyle != null
				|| frameWidth != null) {
			setFrameEnabled(true);
		} else {
			setFrameEnabled(false);
		}
	}
}
