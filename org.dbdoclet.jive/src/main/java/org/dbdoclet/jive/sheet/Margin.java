package org.dbdoclet.jive.sheet;

import org.dbdoclet.unit.Length;

public class Margin extends Bound {

	public Margin() {
		super();
	}

	public Margin(Length top, Length right, Length bottom, Length left) {
		super(top, right, bottom, left);
	}

	public Margin deepCopy() {
	
		Margin copy = new Margin();
		copy.setTop(getTop().deepCopy());
		copy.setRight(getRight().deepCopy());
		copy.setBottom(getBottom().deepCopy());
		copy.setLeft(getLeft().deepCopy());
		return copy;
	}
	
	@Override
	public String toString() {
		return "Margin [top=" + getTop() + ", right=" + getRight() + ", bottom=" + getBottom()
				+ ", left=" + getLeft() + "]";
	}

	public static Margin valueOf(String buffer) {
		
		Margin margin = new Margin();
		
		Length[] list = parse(buffer);
		
		for (int i = 0; i < list.length; i++) {
		
			switch (i) {
			case 0: margin.setTop(list[i]);
				break;
			case 1: margin.setRight(list[i]);
				break;
			case 2: margin.setBottom(list[i]);
				break;
			case 3: margin.setLeft(list[i]);
				break;
			}
		}
		
		return margin;
	}
}
