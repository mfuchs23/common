/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.antol.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dbdoclet.antol.AntRunner;
import org.dbdoclet.jive.text.Screen;
import org.dbdoclet.jive.text.Terminal;

/**
 * AntolTest.java
 *
 *
 * Created: Thu Oct 16 12:42:19 2003
 *
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */

public class AntolTest extends JFrame
    implements ActionListener {

    /**
     * <div lang="de">
     * Der Ausgabeschirm auf dem sämtliche Ausgaben erscheinen.
     * </div>
     *
     * <div lang="en">
     * The screen for the output.
     * </div>
     */
    private Screen screen;
    
    /**
     * Creates a new <code>AntolTest</code> instance.
     */
    public AntolTest() {

        super("AntolTest");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        Terminal term = new Terminal();
        contentPane.add(term, BorderLayout.CENTER);
  
        screen = term.getScreen();

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        JButton exitButton = new JButton("Beenden");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
    }
    
    
    // Implementation of java.awt.event.ActionListener
    
    /**
     * Describe <code>actionPerformed</code> method here.
     *
     * @param actionEvent an <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent actionEvent) {
        
        String cmd = actionEvent.getActionCommand();
        
        screen.clear();
        screen.println("Die Aktion " + cmd + " wurde gestartet.");

        if (cmd.equals("exit")) {
            System.exit(0);
        } // end of if ()

        if (cmd.equals("start")) {

            String fsep = System.getProperty("file.separator");
            String fname = System.getProperty("user.home")
                + fsep + "products" 
                + fsep + "ode" 
                + fsep + "antol"
                + fsep + "test" 
                + fsep + "TestBuild.xml";

            screen.section("Aufruf eines Ant-Zieles.");
            AntRunner runner = new AntRunner(screen,
                                             new File(fname),
                                             "test");

            runner.addProperty("logs.dir", "/var/logs");

            runner.start();

        } // end of if ()
        
    }
    
    /**
     * The application starts here.
     *
     * @param args a <code>String[]</code> value
     */
    public static void main(String[] args) {
        
        AntolTest app = new AntolTest();
        app.pack();
        app.setVisible(true);
    }
} // AntolTest
/*
 * $Log$
 */
