package org.dbdoclet.jive.test;

import java.util.Locale;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.widget.Editor;
import org.dbdoclet.service.ResourceServices;

public class EditorTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());

        Editor editor = new Editor();
        editor.open(ResourceServices.getResourceAsString("/xsl/pdf.xsl"));

        editor.setSize(800, 600);
        JiveServices.center(editor);
        editor.setVisible(true);
    }
}
