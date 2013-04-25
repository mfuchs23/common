/* 
 *
 * ### Copyright (C) 2005,2011 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.text;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class TextConsole extends JTextArea implements IConsole {

	private static final String FONT_FAMILY = "Courier New";
	private static final long serialVersionUID = 1L;
    
    private int cols = 40;
    private Font font;
    private ResourceBundle res;

	private int rows = 25;
    
    public TextConsole(int cols, int rows) {

        super(cols, rows);
		this.font = new Font(FONT_FAMILY, Font.PLAIN, 12);

        JiveFactory widgetMap = JiveFactory.getInstance();
        res = widgetMap.getResourceBundle();

        setFont(font);

        if ((cols < 1) || (cols > 500)) {
            cols = 40;
        }

        if (rows < 1) {
            rows = 25;
        }

        this.cols = cols;
        this.rows = rows;

        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public void clear() {

    	try {
    	
    		Document doc = getDocument();

    		if (doc != null) {
    			doc.remove(0, doc.getLength());
    		}
		
    	} catch (BadLocationException e) {
			e.printStackTrace();
		}

    }

    public void error(String text) {

        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
        error(ResourceServices.getString(res,"C_ERROR"), text);
    }

    public void error(String label, String text) {

        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
        append("[" + label + "] ");
        append(text);
        seeEnd();
    }

    public void exception(Throwable oops) {
        exception(oops, null);
    }

    public void exception(Throwable oops, String buffer) {

        String text = "[Exception] Class: " + oops.getClass().getName();

        String msg = oops.getMessage();

        if ((msg != null) && (msg.length() > 0)) {

            text += (" Message: " + msg);
        }

        if (buffer != null) {

            text += buffer;
        }

        StringWriter stack = new StringWriter();
        oops.printStackTrace(new PrintWriter(stack));
        text += ("\n" + "===================\n" + stack.toString());

        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
        append(text);
        seeEnd();
    }

    @Override
    public Dimension getPreferredSize() {

        Dimension dim = super.getPreferredSize();

        Graphics graphics = getGraphics();
        FontMetrics metrics = graphics.getFontMetrics(font);

        double minWidth = metrics.charWidth('m') * cols;
        double minHeight = metrics.getHeight() * rows;

        double width = dim.getWidth();
        double height = dim.getHeight();

        if (width < minWidth) {
            width = minWidth;
        }

        if (height < minHeight) {
            height = minHeight;
        }

        dim.setSize(width, height);
        return dim;
    }

    public void info(String text) {
        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
    	append(text);        
    	seeEnd();
    }

    public void message(String text) {
        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
    	append(text);
    	seeEnd();
    }

    public void println(String text) {
        message(text);
    }

    public void section(String text) {
        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
        append(text);
        seeEnd();
    }

    public void seeEnd() {
        setCaretPosition(getText().length() - 1);
    }


    public void warning(String text) {
        
    	if (text.endsWith("\n") == false) {
    		text += "\n";
    	}
    	
        append(text);
        seeEnd();
    }

}
