package org.dbdoclet.jive.widget;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import org.dbdoclet.jive.widget.fstree.FileSystemTreeCellRenderer;
import org.dbdoclet.jive.widget.fstree.FileSystemTreeNode;

public class FileSystemTree extends JTree implements TreeExpansionListener {

	private static final long serialVersionUID = 1L;

	public FileSystemTree(FileSystemTreeNode root) {

		super(root);

		// setPreferredSize(new Dimension(200, 600));
		setCellRenderer(new FileSystemTreeCellRenderer());
		setRootVisible(false);

		addTreeExpansionListener(this);

		expandFirstChild();
	}

	public void expandFirstChild() {
	
		FileSystemTreeNode root = (FileSystemTreeNode) getModel().getRoot();
		FileSystemTreeNode firstChild = (FileSystemTreeNode) root.getChildAt(0);

		if (firstChild != null) {
			expandPath(firstChild.getTreePath());
		}
	}

	
	public void treeExpanded(TreeExpansionEvent event) {
		validate();
	}

	
	public void treeCollapsed(TreeExpansionEvent event) {
		//
	}
}
