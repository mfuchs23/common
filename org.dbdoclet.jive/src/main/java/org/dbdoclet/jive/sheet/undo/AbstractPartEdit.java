package org.dbdoclet.jive.sheet.undo;

import java.util.ArrayList;

import javax.swing.undo.AbstractUndoableEdit;

import org.dbdoclet.jive.sheet.Part;

public abstract class AbstractPartEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;

    protected ArrayList<PartData> editList;

    public AbstractPartEdit() {
        super();
    }

    public void addEditData(Part parent, Part child) {

        if (parent != null && child != null) {
            editList.add(new PartData(parent, child));
        }
    }
    
    @Override
    public String getPresentationName() {
        return getClass().getSimpleName() + "(" + hashCode() + "): EditList[" + editList + "]";
    }

    protected void addData() {

        for (PartData data : editList) {

            if (data.getIndex() == -1) {
                data.getParent().appendChild(data.getChild());
            } else {
                data.getParent().insertChild(data.getIndex(), data.getChild());
            }

            data.getParent().getSheet().updateView();
        }
    }

    /**
     * 
     */
    protected void removeData() {

        for (PartData data : editList) {
            data.getParent().removeChild(data.getChild());
            data.getParent().getSheet().updateView();
        }
    }

}

class PartData {

    private final Part parent;
    private final Part child;
    private final int index;
    
    public PartData(Part parent, Part child) {

        this.parent = parent;
        this.child = child;
        index = child.getIndex();
    }

    public int getIndex() {
        return index;
    }

    public Part getChild() {
        return child;
    }

    public Part getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "PartData [child=" + child + ", parent=" + parent + "]";
    }
}
