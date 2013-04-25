package org.dbdoclet.jive.sheet.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.dbdoclet.jive.fo.FoAttributeSet;
import org.dbdoclet.jive.sheet.AbstractPart;
import org.dbdoclet.unit.Length;

public class ChangeTextEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;
    private final FoAttributeSet oldAttributeSet;
    private final FoAttributeSet newAttributeSet;
    private final AbstractPart text;

    public ChangeTextEdit(AbstractPart text, FoAttributeSet oldAttributeSet, FoAttributeSet newAttributeSet) {
        this.text = text;
        this.oldAttributeSet = oldAttributeSet;
        this.newAttributeSet = newAttributeSet;
    }

    @Override
    public String getPresentationName() {
        return getClass().getSimpleName() + ": Alte Schriftart=" + oldAttributeSet.getFontFamily()
                + ", neue Schriftart=" + newAttributeSet.getFontFamily();
    }

    @Override
    public void redo() throws CannotRedoException {

        super.redo();
        setFontAttributes(newAttributeSet);
    }

    @Override
    public void undo() throws CannotUndoException {

        super.undo();
        setFontAttributes(oldAttributeSet);
    }

    private void setFontAttributes(FoAttributeSet attributeSet) {

        text.setFont(attributeSet.getFont());
        text.setMarginTop(Length.valueOf(attributeSet.getSpaceBeforeOptimum()));
        text.setMarginBottom(Length.valueOf(attributeSet.getSpaceAfterOptimum()));
        text.getSheet().updateView();
    }
}
