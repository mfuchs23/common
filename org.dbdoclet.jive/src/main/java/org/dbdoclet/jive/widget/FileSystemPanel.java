package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.widget.fstree.FileSystemTreeNode;

public class FileSystemPanel extends GridPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton upButton;
	private JLabel titleLabel;
	private FileSystemTree fileSystemTree;

	public FileSystemPanel() {

		super(new Insets(0, 0, 0, 0));

		File[] roots = File.listRoots();
		FileSystemTreeNode root = new FileSystemTreeNode(roots);
		init(root);
	}

	public FileSystemPanel(File dir) {

		super(new Insets(0, 0, 0, 0));

		FileSystemTreeNode root = new FileSystemTreeNode(new File[] { dir });
		init(root);
	}

	public String getTitle() {
		return titleLabel.getText();
	}

	public void setTitle(String title) {

		titleLabel.setText(title);
		titleLabel.setVisible(true);

	}

	private void init(FileSystemTreeNode root) {

		JiveFactory jf = JiveFactory.getInstance();

		titleLabel = jf.createLabel(null, "");
		Font titleFont = titleLabel.getFont();
		titleFont = titleFont.deriveFont(titleFont.getSize() * 1.2f);
		
		titleLabel.setBackground(Color.WHITE);
		titleLabel.setFont(titleFont);
		
		int borderSize = getFont().getSize();
		titleLabel.setBorder(BorderFactory.createEmptyBorder(borderSize,
				borderSize, borderSize, borderSize));
		titleLabel.setOpaque(true);
		titleLabel.setVisible(false);

		addComponent(titleLabel, Anchor.CENTER, Fill.HORIZONTAL);
		incrRow();

		startSubPanel();
		upButton = jf.createIconButton(null, JiveServices.getJlfgrIcon("navigation",
				"Up24.gif"));
		upButton.setActionCommand("up");
		upButton.addActionListener(this);
		addComponent(upButton);
		
		leaveSubPanel();
		incrRow();

		fileSystemTree = new FileSystemTree(root);
		JScrollPane scrollPane = new JScrollPane(fileSystemTree);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		addComponent(scrollPane, Colspan.CS_1, Rowspan.RS_1, Anchor.NORTHWEST,
				Fill.BOTH);
	}

	public File getSelectedFile() {

		TreePath treePath = fileSystemTree.getSelectionPath();
		
		if (treePath != null) {
			return ((FileSystemTreeNode) treePath.getLastPathComponent()).getFile();
		}
		
		return null;
	}

	
	public void actionPerformed(ActionEvent event) {
		
		String cmd = event.getActionCommand();
		
		if (cmd == null) {
			return;
		}
		
		if (cmd.equals("up")) {
			
			DefaultTreeModel model = (DefaultTreeModel) fileSystemTree.getModel();
			FileSystemTreeNode root = (FileSystemTreeNode) model.getRoot();
			root = (FileSystemTreeNode) root.getChildAt(0);
			File rootFile = root.getFile();
			File parentFile = rootFile.getParentFile();
			
			if (rootFile.isDirectory() == false && parentFile != null) {
				parentFile = parentFile.getParentFile();
			}
			
			if (parentFile != null) {
				
				FileSystemTreeNode node = new FileSystemTreeNode(new File[] { parentFile });
				
				model.setRoot(node);
				model.nodeStructureChanged(node);
				fileSystemTree.expandFirstChild();
			}
		}
	}
}
