/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.test;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.WindowConstants;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.PropertyDialog;
import org.dbdoclet.jive.dialog.property.BooleanProperty;
import org.dbdoclet.jive.dialog.property.ColorProperty;
import org.dbdoclet.jive.dialog.property.DateProperty;
import org.dbdoclet.jive.dialog.property.FileProperty;
import org.dbdoclet.jive.dialog.property.ImageProperty;
import org.dbdoclet.jive.dialog.property.ListProperty;
import org.dbdoclet.jive.dialog.property.SelectProperty;
import org.dbdoclet.jive.dialog.property.TextProperty;
import org.dbdoclet.jive.model.LabelItem;

public class PropertyDialogTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());
        
        PropertyDialog dlg = new PropertyDialog("Test");
        dlg.setShowAgainEnabled(false);
        dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        BooleanProperty optTrue = new BooleanProperty("Boolean true", true);
        dlg.addProperty(optTrue);
        
        TextProperty optText1 = new TextProperty("Text 1", "Adam");
        dlg.addProperty(optText1);
        
        TextProperty optText2 = new TextProperty("Text 2 mit langem Feldbezeichner", "Eva");
        dlg.addProperty(optText2);
        
        BooleanProperty optFalse = new BooleanProperty("Boolean false", false);
        dlg.addProperty(optFalse);
        
        ArrayList<LabelItem> itemList = new ArrayList<LabelItem>();
        itemList.add(new LabelItem("Rot", "red"));
        itemList.add(new LabelItem("Grün", "green"));
        itemList.add(new LabelItem("Blau", "blue"));

        SelectProperty optColours = new SelectProperty("Farben", itemList, "green");
        dlg.addProperty(optColours);

        ArrayList<LabelItem> values = new ArrayList<LabelItem>();
		values.add(new LabelItem("toc", false));
		values.add(new LabelItem("title", false));
		values.add(new LabelItem("nop", false));

        ListProperty optList = new ListProperty("TOC", values, "toc");
        dlg.addProperty(optList);
        
        File iconFile = new File("empty.gif");
        ImageProperty optImage = new ImageProperty("Bild", iconFile);
        optImage.setBaseDir(new File("/home/mfuchs/Bilder"));
        dlg.addProperty(optImage);
        
        File psFile = new File("/home/mfuchs/Bilder/Bild.ps");
        ImageProperty optPsImage = new ImageProperty("Postscript Bild", psFile);
        optPsImage.setBaseDir(new File("/home/mfuchs/Bilder"));
        dlg.addProperty(optPsImage);
        
        DateProperty optWhatsNew = new DateProperty("Neues", new Date());
        dlg.addProperty(optWhatsNew);
        
        ColorProperty optBackground = new ColorProperty("Hintergrund", Color.red);
        dlg.addProperty(optBackground);

        FileProperty optFile = new FileProperty("Datei", new File("./tmp/test"));
        dlg.addProperty(optFile);

        dlg.setVisible(true);
        
        if (dlg.isCanceled() == false) { 
            InfoBox.show("Info", "Text 1: " + optText1.getText()
                         + "\nText 2: " + optText2.getText()
                         + "\nTrue: " + optTrue.getBoolean()
                         + "\nFalse: " + optFalse.getBoolean()
                         + "\nComboBox: " + optColours.getValue()
                         + "\nFarbe: " + optBackground.getValue()
                         + "\nDatei: " + optFile.getFile()
                         + "\nPostscript Datei: " + optPsImage.getImageFile().getAbsolutePath()
                         + "\nBild: " + optImage.getImageFile().getAbsolutePath());
        }

        System.exit(0);
    }
}
