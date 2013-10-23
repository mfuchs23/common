package org.dbdoclet.jive.fo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public class FontAttributes {

	private Color backgroundColor = Color.white;
	private Font font;
	private boolean fontColorEnabled = true;
	private boolean fontEnabled = false;
	private boolean fontStyleEnabled = true;
	private Color foregroundColor = Color.black;

	public Color getBackgroundColor() {		

		if (backgroundColor == null) {
			return Color.white;
		}

		return backgroundColor;
	}
	
	public Font getFont() {
				
		if (font == null) {
			return UIManager.getDefaults().getFont("Label.font");
		}

		return font;
	}
	
	public Color getForegroundColor() {
				
		if (foregroundColor == null) {
			return Color.black;
		}		

		return foregroundColor;
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
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFontColorEnabled(boolean fontColorEnabled) {
		this.fontColorEnabled = fontColorEnabled;
	}
	
	public void setFontEnabled(boolean fontEnabled) {
		this.fontEnabled = fontEnabled;
		if (fontEnabled == false) {
			reset();
		}
	}
	
	private void reset() {
		setFont(null);
		setForegroundColor(null);
		setBackgroundColor(null);
	}

	public void setFontStyleEnabled(boolean fontStyleEnabled) {
		this.fontStyleEnabled = fontStyleEnabled;
	}
	
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void autoEnable() {
		
		if (font != null || foregroundColor != null || backgroundColor != null) {
			setFontEnabled(true);
		} else {
			setFontEnabled(false);
		}
	};

}
