package org.dbdoclet.jive.test;

import java.awt.Toolkit;
import java.net.URL;

import org.dbdoclet.jive.dialog.Splash;

public class SplashTest {

    public static void main(String[] args) 
        throws Exception {

        Splash splash = null;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL imageURL = ClassLoader.getSystemResource("Splash.gif");

        if (imageURL != null) {
            
            splash = Splash.getSplash();
            splash.splashImage(toolkit.createImage(imageURL),
                               "Splash Test 1.0");
        }

        Thread.sleep(2000);

        splash.setMessage("Die 1. Nachricht...");
        Thread.sleep(2000);

        splash.setMessage("Nachricht #2...");
        Thread.sleep(2000);

        splash.setMessage("Lorem ipsum dolor sit amet, consectetur adipisicing elit...");
        Thread.sleep(2000);

        splash.setMessage("Inge...");
        Thread.sleep(2000);

        if (splash != null) {
            splash.close();
        }
    }
}
