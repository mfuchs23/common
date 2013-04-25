/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.dbdoclet.io.MimeType;

/**
 * Eine ComboBox mit einer Liste von oft verwendeten MimeTypes. Im Modell der
 * ComboBox werden Objekte des Types {@link org.dbdoclet.io.MimeType}
 * gespeichert.
 * 
 * @author mfuchs
 */
public class MimeTypeListBox extends JComboBox {

    private static final long serialVersionUID = 1L;
    private DefaultComboBoxModel model;
    
    public static final int ALL = 0x0001;
    public static final int PICTURES = 0x0002;
    
    public MimeTypeListBox() {

	model = new DefaultComboBoxModel();
	addAllMimeTypes();
	setModel(model);
    }

    public MimeType getSelectedMimeType() {
	return (MimeType) getSelectedItem();
    }

    public void setSelectedMimeType(String mimeType) {

	MimeType mimeTypeItem = MimeType.valueOf(mimeType);
	setSelectedItem(mimeTypeItem);
    }
    
    public void setMimeTypeGroup(int groupMode) {
	
	if (groupMode == PICTURES) {
	
	    model.removeAllElements();
	    model.addElement(MimeType.GIF);
	    model.addElement(MimeType.JPEG);
	    model.addElement(MimeType.PNG);
	}
	
	if (groupMode == ALL) {
	    addAllMimeTypes();
	}
    }

    private void addAllMimeTypes() {
	
	model.removeAllElements();
	model.addElement(MimeType.CSV);
	model.addElement(MimeType.DOC);
	model.addElement(MimeType.DOCX);
	model.addElement(MimeType.GIF);
	model.addElement(MimeType.HTML);
	model.addElement(MimeType.JPEG);
	model.addElement(MimeType.MIDI);
	model.addElement(MimeType.MP3);
	model.addElement(MimeType.MPEG);
	model.addElement(MimeType.ODP);
	model.addElement(MimeType.ODS);
	model.addElement(MimeType.ODT);
	model.addElement(MimeType.PDF);
	model.addElement(MimeType.PNG);
	model.addElement(MimeType.POD);
	model.addElement(MimeType.PPT);
	model.addElement(MimeType.PPTX);
	model.addElement(MimeType.PPTM);
	model.addElement(MimeType.POTX);
	model.addElement(MimeType.WAV);
	model.addElement(MimeType.WMV);
	model.addElement(MimeType.XLS);
	model.addElement(MimeType.XLSX);
    }
}
