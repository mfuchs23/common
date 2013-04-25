package org.dbdoclet.jive.test;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.dbdoclet.io.Screen;
import org.dbdoclet.jive.text.Terminal;

/**
 * TerminalTest.java
 *
 *
 * Created: Wed Oct 15 14:14:02 2003
 *
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */

public class TerminalTest {

    public void execute() {
        
        JFrame frame = new JFrame("Konsole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Terminal term = new Terminal(true,new Dimension(380,340));
        
        Screen screen = term.getScreen();

        screen.clear();
        screen.println("Hello World!");
        screen.message("This is a message.");
        screen.command("This is a command.");
        screen.error("Fehler","This an error.");
        screen.warning("This a warning.");
        screen.section("This is a section.");
        screen.println("Long text. The first thing that you need to know " 
                       + "is how to move around from place to "
                       + "place in the text.  You already know "
                       + "how to move forward one screen, with C-v. "
                       + "To move backwards one screen, type M-v "
                       + "(hold down the META key and type v, or "
                       + "type <ESC>v if you do not have a META, " 
                       + "EDIT, or ALT key).");
        
        
        frame.getContentPane().add(term);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        TerminalTest app = new TerminalTest();
        app.execute();
    }
} // TerminalTest
