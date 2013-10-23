package org.dbdoclet.jive.fo;

public class SpacingAttributes {

	private String endIndent;
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
	private String textIndent;

	public String getEndIndent() {
		return endIndent;
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
	
	public String getTextIndent() {
		return textIndent;
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
	
	public void setEndIndent(String endIndent) {
		this.endIndent = endIndent;
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
		if (spacingEnabled == false) {
			reset();
		}
	}
	
	private void reset() {
		setSpaceAfterMaximum(null);
		setSpaceAfterMinimum(null);
		setSpaceAfterOptimum(null);
		setSpaceBeforeMaximum(null);
		setSpaceBeforeMinimum(null);
		setSpaceBeforeOptimum(null);
		setPadding(null);
		setStartIndent(null);
		setEndIndent(null);
		setTextIndent(null);
	}

	public void setStartIndent(String startIndent) {
		this.startIndent = startIndent;
	}
	
	public void setTextIndent(String textIndent) {
		this.textIndent = textIndent;
	}

	public void autoEnable() {
				
		if (spaceAfterMaximum != null || spaceAfterMinimum != null
				|| spaceAfterOptimum != null || spaceBeforeMaximum != null
				|| spaceBeforeMinimum != null || spaceBeforeOptimum != null
				|| padding != null || startIndent != null || textIndent != null
				|| endIndent != null) {
			setSpacingEnabled(true);
		} else {
			setSpacingEnabled(false);
		}
	}
}
