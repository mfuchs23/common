package org.dbdoclet.jive.sheet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class PaperFormatComboBox extends JComboBox {

    private static final long serialVersionUID = 1L;
    
    public PaperFormatComboBox() {
        setModel(new DefaultComboBoxModel(PaperFormat.FORMATS));
    }

    public boolean contains(PaperFormat paperFormat) {

        PaperFormat found = PaperFormat.valueOf(paperFormat.getSize());

        if (found.isUserDefined() == false) {
            return true;
        } else {
            return false;
        }
    }

    public void setUserDefinedLabel(String label) {

        PaperFormat.USER_DEFINED.setName(label);
    }
}
