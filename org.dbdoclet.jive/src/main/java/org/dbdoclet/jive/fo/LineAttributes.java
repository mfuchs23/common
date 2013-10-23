package org.dbdoclet.jive.fo;

public class LineAttributes {

	private boolean lineEnabled;
	private String lineHeight;
	private String textAlign;
	private String width;
	private String wrapOption;
	
	public String getLineHeight() {
		return lineHeight;
	}
	
	public String getTextAlign() {
		return textAlign;
	}
	
	public String getLineWidth() {
		return width;
	}
	
	public String getWrapOption() {
		return wrapOption;
	}
	
	public boolean isLineEnabled() {
		return lineEnabled;
	}
	
	public void setLineEnabled(boolean lineEnabled) {
		this.lineEnabled = lineEnabled;
		
		if (lineEnabled == false) {
			setLineHeight(null);
			setLineWidth(null);
			setTextAlign(null);
			setWrapOption(null);
		}
	}
	
	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}
	
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
	
	public void setLineWidth(String width) {
		this.width = width;
	}
	
	public void setWrapOption(String wrapOption) {
		this.wrapOption = wrapOption;
	}

	public void autoEnable() {

		if (lineHeight != null || textAlign != null || wrapOption != null
				|| width != null) {
			setLineEnabled(true);
		} else {
			setLineEnabled(false);
		}
	}
}
