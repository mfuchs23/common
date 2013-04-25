/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.DateChooser;

public class DateProperty extends AbstractProperty {

    private static final int LESS_OR_EQUAL_TODAY = 1;
    
    private int constraint;
    private DateChooser chooser;
    
    public DateProperty(String label, Date date) {
        super(label, date);
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }    

    public Date getDate() {
        return (Date) getValue();
    }

    public String getDateAsText(Locale locale) {

        Date date = (Date) getValue();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
        return df.format(date);
    }

    public void setConstraint(int constraint) {
        this.constraint = constraint;
    }
    
    @Override
    public void setValue(Object value) {

        if (value == null || value instanceof Date == false) {
            return;
        }

        Date date = (Date) value;
        Date now = new Date();
        
        if (constraint == LESS_OR_EQUAL_TODAY) {
            if (date.after(now)) {
                return;
            }
        }

        super.setValue(value);
    }
    
    @Override
    public Component getRenderer(Object value) {

	JiveFactory wm = JiveFactory.getInstance();
        ResourceBundle res = wm.getResourceBundle();
    
	JLabel label = new JLabel();
	label.setFont(getPlainFont());
	label.setText(getDateAsText(res.getLocale()));

	return label;
    }

    @Override
    public Component getEditor(Object value) {

        Date date = getDate();
        
        if (date == null) {
            date = new Date();
        }

        chooser = new DateChooser(date);
        return chooser;
    }

    @Override
    public Object getEditorValue() {

	if (chooser == null) {
	    throw new IllegalStateException("The field chooser must not be null!");
	}
	
        Date date = chooser.getDate();
        return date;
    }
}
