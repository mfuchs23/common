package org.dbdoclet.jive.test;

import java.util.Locale;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.InfoBox;

public class ErrorBoxTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());
        
        ErrorBox.show("Fehlermeldung", "<html>Lorem ipsum dolor <b>sit</b> amet, <code>consectetuer</code> adipiscing elit. Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
        InfoBox.show("Information", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
    }
}

