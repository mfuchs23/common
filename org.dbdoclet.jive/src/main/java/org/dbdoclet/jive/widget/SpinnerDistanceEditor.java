package org.dbdoclet.jive.widget;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatter;

public class SpinnerDistanceEditor extends JSpinner.DefaultEditor {

    private static final long serialVersionUID = 1L;

    public SpinnerDistanceEditor(JSpinner spinner) {
        super(spinner);

        JFormattedTextField textField = super.getTextField();
        
        AbstractFormatter formatter = textField.getFormatter();
        
        if (formatter instanceof DefaultFormatter) {
            
            DefaultFormatter defaultFormatter = (DefaultFormatter) formatter;
            defaultFormatter.setOverwriteMode(false);
        }
        
        textField.setEnabled(true);
        textField.setEditable(true);
    }

}