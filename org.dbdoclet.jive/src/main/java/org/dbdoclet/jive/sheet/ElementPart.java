package org.dbdoclet.jive.sheet;

import java.awt.Graphics2D;

import org.w3c.dom.Element;

public class ElementPart extends AbstractPart {

    private final Element element;

    public ElementPart(Sheet sheet, Element element) {
        
        super(sheet);
        this.element = element;
    }

    public Part deepCopy(Sheet sheet) {
        
        ElementPart copy = new ElementPart(sheet, element);
        
        for (Part child : getChildren()) {
            copy.appendChild(child.deepCopy(sheet));
        }
        
        return copy;
    }

    public void paintPart(Graphics2D g2d) {

        paintContainer(g2d);
        // paintLayoutInfo(g2d, Color.red, element.getTagName());

    }

    @Override
    public String toString() {
        return "ElementPart [element=" + element + "]";
    }

}
