package org.dbdoclet.jive.sheet;

import org.dbdoclet.unit.Length;

public class Bound {

	private Length top;
	private Length right;
	private Length bottom;
	private Length left;

	public Bound() {
		this(new Length(0), new Length(0), new Length(0), new Length(0));
	}
	
	public Bound(Length top, Length right, Length bottom, Length left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;		
	}
	
	public Length getTop() {
		
		if (top == null) {
			top = new Length(0);
		}
		
		return top;
	}

	public void setTop(Length top) {
		this.top = top;
	}

	public Length getRight() {

		if (right == null) {
			right = new Length(0);
		}
		
		return right;
	}

	public void setRight(Length right) {
		this.right = right;
	}

	public Length getBottom() {

		if (bottom == null) {
			bottom = new Length(0);
		}
		
		return bottom;
	}

	public void setBottom(Length bottom) {
		this.bottom = bottom;
	}

	public Length getLeft() {

		if (left == null) {
			left = new Length(0);
		}
		
		return left;
	}

	public void setLeft(Length left) {
		this.left = left;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((top == null) ? 0 : top.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (getClass() != other.getClass()) {
			return false;
		}

		Bound otherBound = (Bound) other;

		if (top == null && otherBound.top != null) {
			return false;
		}

		if (top.equals(otherBound.top) == false) {
			return false;
		}

		if (right == null && otherBound.right != null) {
			return false;
		}

		if (right.equals(otherBound.right) == false) {
			return false;
		}

		if (bottom == null && otherBound.bottom != null) {
			return false;
		}

		if (bottom.equals(otherBound.bottom) == false) {
			return false;
		}

		if (left == null && otherBound.left != null) {
			return false;
		}

		if (left.equals(otherBound.left) == false) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Bound [top=" + top + ", right=" + right + ", bottom=" + bottom
				+ ", left=" + left + "]";
	}

	protected static Length[] parse(String buffer) {
	
		if (buffer == null) {
			return new Length[0];
		}
		
		String[] tokens = buffer.split("\\s+");
		
		if (tokens == null) {
			return new Length[0];
		}
		
		Length[] list = new Length[tokens.length];
		
		int index = 0;
		for (String token : tokens) {
			list[index++] = Length.valueOf(token);
		}
		
		return list;
	}
	
	public String toNormalizedString() {

		StringBuilder buffer = new StringBuilder();
		
		buffer.append(getTop().toNormalizedString());
		buffer.append(' ');
		buffer.append(getRight().toNormalizedString());
		buffer.append(' ');
		buffer.append(getBottom().toNormalizedString());
		buffer.append(' ');
		buffer.append(getLeft().toNormalizedString());
		
		return buffer.toString();
	}

}
