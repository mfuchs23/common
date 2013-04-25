package org.dbdoclet.jive.sheet.undo;

import java.util.ArrayList;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.dbdoclet.jive.sheet.Part;

public class RemovePartEdit extends AbstractPartEdit {

    private static final long serialVersionUID = 1L;

    public RemovePartEdit() {
        this(null, null);
    }

    public RemovePartEdit(Part parent, Part part) {

        editList = new ArrayList<PartData>();

        if (parent != null && part != null) {
            editList.add(new PartData(parent, part));
        }
    }

    @Override
    public void undo() throws CannotUndoException {

        super.undo();
        addData();
    }

    @Override
    public void redo() throws CannotRedoException {

        super.redo();
        removeData();
    }
}
