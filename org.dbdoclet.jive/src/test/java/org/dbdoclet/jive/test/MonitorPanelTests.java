package org.dbdoclet.jive.test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.monitor.MonitorPanel;

/**
 * PathList.java
 * 
 * 
 * Created: Sun Sep 12 15:21:32 2004
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */

public class MonitorPanelTests extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorPanelTests() throws IOException {

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

		setJMenuBar(createMenu());
		MonitorPanel monitorPanel = new MonitorPanel();
		monitorPanel.addFile(new File("/var/log/syslog"));
		monitorPanel.addFile(new File("/tmp/test.log"));
		monitorPanel.addFile(new File("/home/michael/Dokument.pdf"));
		
		getContentPane().add(new JScrollPane(monitorPanel));
	}

	protected JMenuBar createMenu() {

		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");

		menu.add(new AbstractAction("Exit") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mb.add(menu);
		return mb;
	}

	public static void main(String[] args) throws IOException {

		MonitorPanelTests f = new MonitorPanelTests();
		f.pack();
		f.setSize(new Dimension(400, 200));
		f.setVisible(true);
		JiveServices.center(f);
		
	}

}
