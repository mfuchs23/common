/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.settings.SettingsPanel;
import org.dbdoclet.jive.listener.SettingsListener;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.service.ResourceServices;

public class SettingsDialog extends AbstractDialog implements ActionListener,
		TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(SettingsDialog.class);

	private final JiveFactory wm;
	private final ResourceBundle res;
	private JTree settingsTree;
	private final HashMap<String, DefaultMutableTreeNode> groupMap;
	private final ArrayList<SettingsPanel> panelList;
	private JPanel optionPanel;
	private Settings settings;
	private final ArrayList<SettingsListener> listenerList;

	public SettingsDialog(Frame parent, String title) {

		super(parent, title);

		wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();

		JPanel rootPanel = wm.createPanel(new Identifier("settings.root"),
				new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.3;
		gbc.weighty = 0.3;

		rootPanel.add(createTreePanel(), gbc);

		gbc.weightx = 0.7;
		gbc.weighty = 0.7;

		rootPanel.add(createOptionPanel(), gbc);

		getContentPane().add(rootPanel, BorderLayout.CENTER);

		pack();
		center(parent);

		groupMap = new HashMap<String, DefaultMutableTreeNode>();
		panelList = new ArrayList<SettingsPanel>();
		settings = new Settings(new File("settings.properties"));
		listenerList = new ArrayList<SettingsListener>();
	}

	public void addSettingsListener(SettingsListener listener) {

		if (listener != null) {
			listenerList.add(listener);
		}
	}

	public void setSettings(Settings settings) {

		if (settings == null) {
			throw new IllegalArgumentException(
					"The argument settings must not be null!");
		}

		this.settings = settings;

		SettingsPanel panel;

		for (Iterator<SettingsPanel> iterator = panelList.iterator(); iterator
				.hasNext();) {

			panel = iterator.next();

			logger.debug("panel=" + panel.getName());
			panel.setProperties(settings);
		}
	}

	public void setSelected(TreePath treePath) {

		settingsTree.setSelectionPath(treePath);
		settingsTree.scrollPathToVisible(treePath);
	}

	public TreePath addGroup(String label) {

		if (label == null) {
			throw new IllegalArgumentException(
					"The argument label must not be null!");
		}

		DefaultTreeModel model = (DefaultTreeModel) settingsTree.getModel();
		DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				new SettingNode(label, null));
		top.add(node);
		model.reload();

		groupMap.put(label, node);

		TreePath treePath = new TreePath(model.getPathToRoot(node));

		return treePath;
	}

	public TreePath addPanel(String groupName, String name, SettingsPanel panel) {

		if (groupName == null) {
			throw new IllegalArgumentException(
					"The argument groupName must not be null!");
		}

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name must not be null!");
		}

		if (panel == null) {
			throw new IllegalArgumentException(
					"The argument panel must not be null!");
		}

		DefaultMutableTreeNode group = groupMap.get(groupName);

		if (group != null) {

			DefaultTreeModel model = (DefaultTreeModel) settingsTree.getModel();
			panel.setProperties(settings);
			panelList.add(panel);

			DefaultMutableTreeNode node = new DefaultMutableTreeNode(
					new SettingNode(name, panel));
			group.add(node);
			model.reload();

			TreePath treePath = new TreePath(model.getPathToRoot(node));
			return treePath;
		}

		return null;
	}

	private JPanel createTreePanel() {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		JPanel panel = wm.createPanel(new Identifier("settings.tree"),
				new GridBagLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(
				new SettingNode(ResourceServices.getString(res, "C_SETTINGS"),
						null));
		DefaultTreeModel model = new DefaultTreeModel(top);

		settingsTree = wm.createTree(new Identifier("settings.tree"), model);
		settingsTree.setPreferredSize(new Dimension(200, 450));

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

		URL iconUrl;
		ImageIcon icon;

		ClassLoader loader = this.getClass().getClassLoader();

		iconUrl = loader.getResource("images/settingsGroupClosed.gif");
		icon = new ImageIcon(iconUrl, "Folder is closed.");
		renderer.setClosedIcon(icon);

		iconUrl = loader.getResource("images/settingsGroupOpen.gif");
		icon = new ImageIcon(iconUrl, "Folder is open.");
		renderer.setOpenIcon(icon);

		renderer.setLeafIcon(null);

		settingsTree.addTreeSelectionListener(this);
		settingsTree.setCellRenderer(renderer);
		settingsTree.setRootVisible(false);

		JScrollPane scrollPane = wm.createScrollPane(null, settingsTree);

		panel.add(scrollPane, gbc);

		return panel;
	}

	private JPanel createOptionPanel() {

		String rbstr;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;

		JPanel panel = wm.createPanel(new Identifier("settings.option"),
				new GridBagLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		// optionPanel.setBorder(BorderFactory.createLineBorder(Color.green));

		optionPanel.setPreferredSize(new Dimension(400, 400));

		JScrollPane scrollPane = wm.createScrollPane(null, optionPanel);
		panel.add(scrollPane, gbc);

		JPanel buttonPanel = wm.createPanel(new Identifier("settings.buttons"),
				new GridBagLayout());
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;
		panel.add(buttonPanel, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(4, 4, 4, 4);

		JButton okButton = wm.createButton(new Identifier("settings.ok"),
				ResourceServices.getString(res, "C_OK"));
		okButton.setActionCommand("ok");
		okButton.addActionListener(this);
		okButton.setMnemonic('o');
		buttonPanel.add(okButton, gbc);

		rbstr = ResourceServices.getString(res, "C_CANCEL");
		JButton closeButton = wm.createButton(
				new Identifier("settings.cancel"), rbstr);
		closeButton.setActionCommand("cancel");
		closeButton.addActionListener(this);
		closeButton.setMnemonic(rbstr.charAt(0));
		gbc.gridx = 1;
		buttonPanel.add(closeButton, gbc);

		return panel;
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();

		if (cmd != null && cmd.equals("cancel")) {
			setVisible(false);
			return;
		}

		if (cmd != null && cmd.equals("ok")) {

			if (settings == null) {
				throw new IllegalStateException(
						"The field settings must not be null!");
			}

			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			SettingsPanel panel;
			Properties properties;

			for (Iterator<SettingsPanel> iterator = panelList.iterator(); iterator
					.hasNext();) {

				panel = iterator.next();

				String namespace = panel.getNamespace();

				if (namespace != null && namespace.length() > 0) {

					String name;

					for (Enumeration<?> e = settings.propertyNames(); e
							.hasMoreElements();) {

						name = (String) e.nextElement();

						if (name.startsWith(namespace)) {
							settings.remove(name);
						}
					}
				}

				properties = panel.getProperties();
				settings.putAll(properties);
			}

			try {

				settings.store();

				SettingsListener listener;

				for (int i = 0; i < listenerList.size(); i++) {

					listener = listenerList.get(i);
					listener.settingsChanged(settings);
				}

				setVisible(false);

			} catch (IOException oops) {

				ExceptionBox ebox = new ExceptionBox(oops);
				ebox.setVisible(true);
				ebox.toFront();
			}

			setCursor(Cursor.getDefaultCursor());

			return;
		}
	}

	public void valueChanged(TreeSelectionEvent event) {

		TreePath path = event.getPath();
		logger.debug("path=" + path);

		if (path == null) {
			return;
		}

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		logger.debug("node=" + node);

		if (node == null) {
			return;
		}

		SettingNode setting = (SettingNode) node.getUserObject();
		logger.debug("setting=" + setting);

		if (setting == null) {
			return;
		}

		setting.getLabel();
		SettingsPanel panel = setting.getPanel();

		if (panel == null) {
			return;
		}

		logger.debug("panel=" + panel.getName());

		optionPanel.removeAll();
		optionPanel.invalidate();

		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.add(panel.getPanel());
		optionPanel.add(Box.createVerticalGlue());

		optionPanel.validate();
		optionPanel.repaint();
	}
}

class SettingNode {

	private final String label;
	private final SettingsPanel panel;

	public SettingNode(String label, SettingsPanel panel) {

		if (label == null) {
			throw new IllegalArgumentException(
					"The argument label must not be null!");
		}

		this.label = label;
		this.panel = panel;
	}

	public String getLabel() {
		return label;
	}

	public SettingsPanel getPanel() {
		return panel;
	}

	@Override
	public String toString() {
		return label;
	}
}
