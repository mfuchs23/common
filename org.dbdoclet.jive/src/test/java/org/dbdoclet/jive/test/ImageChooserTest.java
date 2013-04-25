package org.dbdoclet.jive.test;

import java.util.Locale;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ImageChooser;

public class ImageChooserTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());

        ImageChooser chooser = new ImageChooser();
        chooser.showOpenDialog(null);
    }
}
