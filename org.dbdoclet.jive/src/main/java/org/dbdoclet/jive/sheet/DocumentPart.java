package org.dbdoclet.jive.sheet;

import java.awt.Graphics2D;

public class DocumentPart extends AbstractPart {

    private DocumentCursor cursor;

	public DocumentPart(Sheet sheet) {
        super(sheet);
        cursor = new DocumentCursor(sheet.getBodyLeft(), sheet.getBodyRight());
    }

    public Part deepCopy(Sheet sheet) {
        
        AbstractPart copy = new DocumentPart(sheet);
        
        for (Part child : getChildren()) {
            copy.appendChild(child.deepCopy(sheet));
        }
        
        return copy;
    }

	public DocumentCursor getCursor() {
		return cursor;
	}

	public void paintPart(Graphics2D g2d) {

        // paintLayoutInfo(g2d, Color.CYAN, "DOCUMENT");
        paintContainer(g2d);
    }

}
