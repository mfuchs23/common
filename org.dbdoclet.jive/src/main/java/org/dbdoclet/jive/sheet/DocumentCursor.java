package org.dbdoclet.jive.sheet;


public class DocumentCursor {

	private double left;
	private double top;

	public DocumentCursor(double left, double top) {
		this.left = left;
		this.top = top;
	}

	public double getLeft() {
		return left;
	}

	public double getTop() {
		return top;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public void setTop(double top) {
		this.top = top;
	}

}
