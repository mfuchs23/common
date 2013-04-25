package org.dbdoclet.jive.test;

import java.util.Locale;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ProcessBox;

public class ProcessBoxTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());
        
        ProcessBox pbox = new ProcessBox(null, "Test ProcessBox");
        pbox.setVisible(true);

        for (int i = 0; i < 12; i++) {

            Thread.sleep(1000);
            pbox.info("Dies ist Nachricht Nummer " + (i + 1) + ".");
        }

        for (int i = 0; i < 12; i++) {

            Thread.sleep(1000);
            pbox.info("Kopiere Datei /usr/share/dbdoclet/docbook/xsl/highlighting/xslthl-config.xml nach /usr/share/dbdoclet/docbook/xsl/highlighting/xslthl-config.xml.");
        }

         Thread.sleep(10000);
        pbox.setVisible(false);
        
    }
}
