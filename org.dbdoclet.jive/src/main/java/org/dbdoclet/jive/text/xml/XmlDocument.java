package org.dbdoclet.jive.text.xml;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class XmlDocument extends DefaultStyledDocument {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void remove(int offset, int length)
        throws BadLocationException {

        super.remove(offset, length);
        highlight();
    }
 
    @Override
    public void insertString(int offset, String str, AttributeSet a) 
        throws BadLocationException {

        trace("insertString: str=" + str);
        super.insertString(offset, str, a);
        highlight();
    } 

    private void highlight() {

        int length = getLength();
        String text = "";

        try {
            text = getText(0, length);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        
        MutableAttributeSet keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, Color.blue);
        StyleConstants.setBold(keyword, true);
        
        MutableAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.black);
        StyleConstants.setBold(normal, true);
    
        setCharacterAttributes(0, length, normal, true);
        
        int start = 0;
        int end;
        
        for (int i = 0; i < length; i++) {
        
            if (text.charAt(i) == '<') {
                start = i;
            }

            if (text.charAt(i) == '>') {
                end = i;
                setCharacterAttributes(start, end - start, keyword, true);
            }
        }
    }

    private void trace(String str) {
        // System.out.println(str);
    }
}
