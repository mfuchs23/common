package org.dbdoclet.jive.test;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.PathList;

/**
 * PathList.java
 * 
 * 
 * Created: Sun Sep 12 15:21:32 2004
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */

public class TestPathList extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public TestPathList() {

		super("Test PathList");
		setSize(800, 300);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowOpened(WindowEvent e) { /* */
			}
		});

		PathList pathlist = new PathList(JiveFactory.getInstance(Locale
				.getDefault()));
		pathlist.setWorkingDir(new File("."));

		getContentPane().add(new JScrollPane(pathlist));

		pathlist.addEntry("/home/michael");
		pathlist.addEntry("/home/michael/Werkbank/dodo-services/build.xml");
		pathlist.addEntry("/Existiert/nicht");
		pathlist.addEntry("build.xml");
		pathlist.addEntry("../build.xml");
		pathlist.addEntry("./src/main/java");
	}

	protected JMenuBar createMenu() {

		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");

		menu.add(new AbstractAction("Exit") {
			/**
	     * 
	     */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mb.add(menu);
		return mb;
	}

	public static void main(String[] args) {

		TestPathList f = new TestPathList();
		f.setVisible(true);
	}

}
