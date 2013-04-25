package org.dbdoclet.jive.sheet;

import java.awt.Graphics2D;

import org.w3c.dom.Element;

public class Info extends AbstractPart {

	public Info(Sheet sheet) {

		super(sheet);
	}

	public void addText(Part text) {
		appendChild(text);
	}

	public Part deepCopy(Sheet sheet) {

		AbstractPart copy = new Info(sheet);
		fillCopy(copy);
		return copy;
	}

	public void paintPart(Graphics2D g2d) {

		paintContainer(g2d);
		// super.afterPaintPart(g2d);
	}

	@Override
	public void writeElement(Element element) {
		super.writeElement(element);

	}

	
}