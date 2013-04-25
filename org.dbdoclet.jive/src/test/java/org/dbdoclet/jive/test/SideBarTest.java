/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.test;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.SideBar;
import org.dbdoclet.jive.widget.sidebar.SideBarButton;
import org.dbdoclet.jive.widget.sidebar.SideBarGroup;
import org.dbdoclet.service.ResourceServices;

public class SideBarTest extends JFrame {

	private static final long serialVersionUID = 1L;

	public void execute() throws InterruptedException {

		setTitle("SideBarTest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridPanel panel = new GridPanel();
		SideBar sideBar = new SideBar();

		SideBarGroup contentsGroup = new SideBarGroup("Contents", 1);
		SideBarGroup othersGroup = new SideBarGroup("Others", 0);

		sideBar.addButton(
				contentsGroup,
				new SideBarButton(new TestAction("Projekte", ResourceServices
						.getIcon("/images/sidebartest.png"))), "projects");

		sideBar.addButton(
				contentsGroup,
				new SideBarButton(new TestAction("Strukturen", ResourceServices
						.getIcon("/images/sidebartest.png"))), "structures");

		sideBar.addButton(
				othersGroup,
				new SideBarButton(new TestAction("Module", ResourceServices
						.getIcon("/images/sidebartest.png"))), "modules");

		sideBar.addButton(othersGroup,
				new SideBarButton(new TestAction("Übersetzungen",
						ResourceServices.getIcon("/images/sidebartest.png"))),
				"translations");

		JLabel label = new JLabel("SideBar Test");

		panel.addComponent(sideBar, Anchor.NORTHWEST, Fill.VERTICAL);
		panel.addComponent(label, Anchor.CENTER, Fill.BOTH);

		getContentPane().add(panel);

		sideBar.setSelected("modules");

		sideBar.addButton(
				contentsGroup,
				new SideBarButton(new TestAction("Projekte 2", ResourceServices
						.getIcon("/images/projects.png"))), "projects-2");
		sideBar.prepare();

		Thread.sleep(5000);
	}

	public static void main(String[] args) throws Exception {

		SideBarTest app = new SideBarTest();
		app.execute();
		app.setSize(800, 600);
		app.pack();
		app.setVisible(true);
		JiveServices.center(app);
		Thread.sleep(10000);
	}
}

class TestAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public TestAction(String name, Icon icon) {
		super(name, icon);
	}

	public void actionPerformed(ActionEvent event) {
		//
	}
}
