package org.dbdoclet.jive.sheet;

import org.dbdoclet.unit.Length;

public class Padding extends Bound {

	public Padding() {
		super();
	}

	public Padding(Length top, Length right, Length bottom, Length left) {
		super(top, right, bottom, left);
	}

	public Padding deepCopy() {
	
		Padding copy = new Padding();
		copy.setTop(getTop().deepCopy());
		copy.setRight(getRight().deepCopy());
		copy.setBottom(getBottom().deepCopy());
		copy.setLeft(getLeft().deepCopy());
		return copy;
	}
	
	@Override
	public String toString() {
		return "Padding [top=" + getTop() + ", right=" + getRight() + ", bottom=" + getBottom()
				+ ", left=" + getLeft() + "]";
	}

}
