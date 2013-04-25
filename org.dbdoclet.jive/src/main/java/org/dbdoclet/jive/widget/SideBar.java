/* 
 * ### Copyright (C) 2007-2008 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.InsetsUIResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.filter.BlurFilter;
import org.dbdoclet.jive.widget.sidebar.SideBarButton;
import org.dbdoclet.jive.widget.sidebar.SideBarGroup;

public class SideBar extends JPanel implements ActionListener, ChangeListener {

	private static Log logger = LogFactory.getLog(SideBar.class);

	private static final long serialVersionUID = 1L;
	public static final int BUTTON_WIDTH = 150;
	public static final int BUTTON_HEIGHT = 90;

	private boolean isVisible = true;
	private SideBarButton activeButton;

	/**
	 * Hash-Tabelle für die Schaltflächen.
	 */
	private final Hashtable<String, SideBarButton> buttonMap;
	private final ArrayList<SideBarGroup> groupList;
	private JTabbedPane tabbedPane;

	public SideBar() {

		buttonMap = new Hashtable<String, SideBarButton>();
		groupList = new ArrayList<SideBarGroup>();

		ActionMap actionMap = getActionMap();
		actionMap.put("toggleSideBar", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				toggleVisibility();
			}
		});

		InputMap inputMap = getInputMap();
		inputMap.put(KeyStroke.getKeyStroke("F9"), "toggleSideBar");

		setBackground(new Color(223, 223, 223));
	}

	public void actionPerformed(ActionEvent event) {

		try {

			Object obj = event.getSource();

			if (obj instanceof SideBarButton) {
				setSelected((SideBarButton) obj);
			}

		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	/**
	 * Fügt der Standardgruppe eine neue Schaltfläche hinzu,
	 * 
	 * @param button
	 */
	public void addButton(SideBarButton button) {

		addButton(SideBarGroup.DEFAULT_GROUP, button, null);
	}

	/**
	 * Fügt der Standardgruppe eine neue Schaltfläche hinzu. Die neue
	 * Schaltfläche wird unter dem angegebenen Namen registriert. Mit Hilfe des
	 * Namens, kann man später Zugriff auf das Objekt der Schaltfläche erhalten.
	 * 
	 * @param button
	 * @param name
	 */
	public void addButton(SideBarButton button, String name) {
		addButton(SideBarGroup.DEFAULT_GROUP, button, name);
	}

	/**
	 * Fügt der angegebenen Gruppe eine neue Schaltfläche mit dem angegebenen
	 * Namen hinzu. Gruppen werden in der Seitenleiste als Registerkarten
	 * angezeigt. Ist nur eine Gruppe vorhanden, so werden keine Registerkarten
	 * erzeugt. Mit Hilfe des Namens, kann man später Zugriff auf das Objekt der
	 * Schaltfläche erhalten.
	 * 
	 * @param group
	 * @param button
	 * @param name
	 */
	public void addButton(SideBarGroup group, SideBarButton button, String name) {

		if (group == null) {
			throw new IllegalArgumentException(
					"The argument groupName must not be null!");
		}

		if (groupList.contains(group) == false) {
			groupList.add(group);
		}

		button.addActionListener(this);
		button.setSideBar(this);
		group.addButton(button);

		if (name != null && name.length() > 0) {
			buttonMap.put(name, button);
		}
	}

	public void clear() {

		buttonMap.clear();
		groupList.clear();
	}

	public JButton findButton(String name) {
		return buttonMap.get(name);
	}

	public int getSelectedTab() {

		if (tabbedPane != null) {
			return tabbedPane.getSelectedIndex();
		}

		return 0;
	}

	/**
	 * Bereitet die Komponente auf die Anzeige vor. Diese Methode muß aufgerufen
	 * werden, nachdem alle Gruppen und Schaltflächen hinzugefügt wurden. AIn
	 * Abhängigkeit dieser Daten wird dann die Oberfläche erzeugt.
	 */
	public void prepare() {

		if (groupList.size() == 0) {
			return;
		}

		removeAll();

		if (groupList.size() == 1) {

			initGroup(this);
			fillGroup(this, groupList.get(0).getButtonList());
		}

		if (groupList.size() > 1) {
			createTabbedSideBar();
		}

		invalidate();
		repaint();
	}

	public boolean removeButton(String name) {

		JButton button = buttonMap.get(name);

		if (button == null) {
			return false;
		}

		buttonMap.remove(name);
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {

		logger.debug("setEnabled: " + enabled);

		int index = 0;

		while (index < 5) {

			try {

				for (JButton button : buttonMap.values()) {

					if (button.isEnabled() != enabled) {
						button.setEnabled(enabled);
					}
				}

				index = 5;

			} catch (ConcurrentModificationException oops) {
				oops.printStackTrace();
				index++;
			}
		}

		if (tabbedPane != null && tabbedPane.isEnabled() != enabled) {
			tabbedPane.setEnabled(enabled);
		}

		if (isEnabled() != enabled) {
			super.setEnabled(enabled);
		}
	}

	public void setSelected(SideBarButton button) {

		if (activeButton != null) {
			activeButton.isActive(false);
		}

		if (button != null) {
			activeButton = button;
			activeButton.isActive(true);
		} else {
			activeButton = null;
		}
	}

	public void setSelected(String name) {

		SideBarButton button = buttonMap.get(name);

		if (button != null) {
			setSelectedTab(button.getGroup().getTabIndex());
			setSelected(button);
		}
	}

	public void setSelectedTab(int index) {

		if (tabbedPane != null && index >= -1
				&& index < tabbedPane.getTabCount()) {
			tabbedPane.setSelectedIndex(index);
		}
	}

	/**
	 * Wird aufgerufen, wenn ein neues Register ausgewählt wurde.
	 */
	public void stateChanged(ChangeEvent event) {

		JTabbedPane tabbedPane = (JTabbedPane) event.getSource();
		int selectedIndex = tabbedPane.getSelectedIndex();

		if (selectedIndex > 0 && selectedIndex < groupList.size()) {

			SideBarGroup group = groupList.get(selectedIndex);

			if (group != null) {
				SideBarButton button = group.getButtonList().get(0);

				if (button != null && button.isSelected() == false) {
					button.doClick();
				}
			}
		}
	}

	public void toggleVisibility() {

		isVisible = !isVisible;
		setVisible(isVisible);
		repaint();
	}

	private synchronized void createTabbedSideBar() {

		setLayout(new GridBagLayout());

		Insets insets = (Insets) UIManager.get("TabbedPane.tabInsets");
		UIManager.put("TabbedPane.tabInsets", new InsetsUIResource(1, 1, 1, 1));

		tabbedPane = new JTabbedPane(SwingConstants.LEFT);
		tabbedPane.setName("tp.sidebar");
		tabbedPane.setBackground(new Color(223, 223, 223));
		tabbedPane.addChangeListener(this);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		add(tabbedPane, gbc);

		int index = 0;

		Collections.sort(groupList);

		for (SideBarGroup group : groupList) {

			JPanel groupPanel = new JPanel();
			initGroup(groupPanel);
			fillGroup(groupPanel, group.getButtonList());

			ImageIcon icon = createVerticalLabel(group.getName());
			tabbedPane.addTab(null, icon, groupPanel);

			group.setTabIndex(index++);
		}

		UIManager.put("TabbedPane.tabInsets", insets);
	}

	private ImageIcon createVerticalLabel(String text) {

		Font font = getFont().deriveFont(Font.BOLD);

		BufferedImage image = new BufferedImage(300, 300,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setFont(font);

		FontMetrics fontMetrics = g2d.getFontMetrics();

		int height = fontMetrics.getHeight();
		int width = fontMetrics.stringWidth(text) + 2 * fontMetrics.getAscent();
		int xpos = fontMetrics.getAscent();
		int ypos = height;
		int tx = 0;
		int ty = width;
		int offset = 1;
		int blur = 3;
		double theta = -90.0 * Math.PI / 180.0;

		image = image.getSubimage(0, 0, width, width);

		g2d = (Graphics2D) image.getGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);
		g2d.translate(tx, ty);
		g2d.rotate(theta);

		g2d.setPaint(Color.GRAY);
		g2d.drawString(text, xpos + offset, ypos + offset);

		image = BlurFilter.gaussianBlur(image, blur);

		g2d = (Graphics2D) image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.translate(tx, ty);
		g2d.rotate(theta);

		g2d.setPaint(Color.black);
		g2d.drawString(text, xpos, ypos);

		// g2d.setPaint(Color.BLACK);
		// g2d.drawRect(1, 1, width - 1, width - 1);
		//
		// System.out.println(image.getWidth() + ":" + image.getHeight());
		image = image.getSubimage(0, 0, height + offset + blur + 1,
				image.getHeight());

		return new ImageIcon(image);
	}

	/**
	 * Füllt das Panel einer Gruppe mit den dazugehörigen Schaltflächen.
	 * 
	 * @param groupPanel
	 * @param next
	 */
	private void fillGroup(JPanel groupPanel,
			ArrayList<SideBarButton> buttonList) {

		if (groupPanel == null) {
			throw new IllegalArgumentException(
					"The argument groupPanel must not be null!");
		}

		Collections.sort(buttonList);
		for (JButton button : buttonList) {
			groupPanel.add(button);
		}
	}

	private void initGroup(JPanel panel) {

		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);

		panel.setBackground(new Color(223, 223, 223));
		panel.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 5));
		panel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 5));

		// setBorder(BorderFactory.createCompoundBorder(BorderFactory.
		// createEmptyBorder(4, 4, 4, 4),
		// BorderFactory.createLoweredBevelBorder()));

		panel.setBorder(BorderFactory.createLoweredBevelBorder());
	}

}
