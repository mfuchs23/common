package org.dbdoclet.jive.text.xml;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;

public class XmlEditor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextPane editor;
    private XmlEditorKit kit;

    public XmlEditor() {

	super("XML Editor");

	JiveFactory.getInstance(Locale.getDefault());

	editor = new JTextPane();
	kit = new XmlEditorKit();
	editor.setEditorKit(kit);

	JScrollPane scrollPane = new JScrollPane(editor);
	getContentPane().add(scrollPane, BorderLayout.CENTER);

	pack();
	setSize(600, 600);
    }

    private void open(String fileName) throws IOException {

	if (fileName == null) {
	    throw new IllegalArgumentException("The parameter fileName must not be null!");
	}

	try {

	    File file = new File(fileName);
	    editor.read(new FileReader(file), file);

	    /*
	     * XmlDocument doc = (XmlDocument) kit.createDefaultDocument();
	     * FileInputStream fis = new FileInputStream(fileName);
	     * kit.read(fis, doc, 0); editor.setDocument(doc); fis.close();
	     */

	} catch (Throwable oops) {

	    ExceptionBox ebox = new ExceptionBox(oops);
	    ebox.setVisible(true);
	    ebox.toFront();
	}
    }

    public static void main(String[] args) {

	try {

	    // JFrame.setDefaultLookAndFeelDecorated(true);
	    // JDialog.setDefaultLookAndFeelDecorated(true);

	    XmlEditor frame = new XmlEditor();
	    frame.open(args[0]);
	    frame.setVisible(true);

	} catch (Throwable oops) {

	    ExceptionBox ebox = new ExceptionBox(oops);
	    ebox.setVisible(true);
	    ebox.toFront();
	}
    }
}
