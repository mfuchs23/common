package org.dbdoclet.jive.sheet.undo;

import java.util.ArrayList;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.dbdoclet.jive.sheet.Part;

public class ChangePartEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;
    private ArrayList<ChangePartData> editList;

    public ChangePartEdit() {
        editList = new ArrayList<ChangePartData>();
    }

    public void addEditData(Part oldPart, Part part) {
        editList.add(new ChangePartData(oldPart, part.deepCopy(part.getSheet()), part));
    }

    @Override
    public String getPresentationName() {
        return getClass().getSimpleName() + ": EditList[" + editList + "]";
    }

    @Override
    public void redo() throws CannotRedoException {

        super.redo();

        for (ChangePartData data : editList) {
            copyProperties(data.getNewPart(), data.getRefPart());
            data.getRefPart().getSheet().updateView();
        }
    }

    @Override
    public void undo() throws CannotUndoException {

        super.undo();

        for (ChangePartData data : editList) {
            copyProperties(data.getOldPart(), data.getRefPart());
            data.getRefPart().getSheet().updateView();
        }
    }

    /**
     * 
     */
    private void copyProperties(Part fromPart, Part toPart) {
        fromPart.copyProperties(toPart);
    }

}

class ChangePartData {

    private Part oldPart;
    private Part newPart;
    private Part refPart;

    public ChangePartData(Part oldPart, Part newPart, Part refPart) {
        super();
        this.oldPart = oldPart;
        this.newPart = newPart;
        this.refPart = refPart;
    }
    public Part getNewPart() {
        return newPart;
    }

    public Part getOldPart() {
        return oldPart;
    }

    public Part getRefPart() {
        return refPart;
    }

    public void setNewPart(Part newPart) {
        this.newPart = newPart;
    }

    public void setOldPart(Part oldPart) {
        this.oldPart = oldPart;
    }

    public void setRefPart(Part refPart) {
        this.refPart = refPart;
    }

    @Override
    public String toString() {
        return "ChangePartData [newPart=" + newPart + ", oldPart=" + oldPart + ", refPart=" + refPart + "]";
    }
}