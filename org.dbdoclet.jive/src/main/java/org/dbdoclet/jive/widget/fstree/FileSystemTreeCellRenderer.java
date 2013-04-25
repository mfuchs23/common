package org.dbdoclet.jive.widget.fstree;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FileSystemTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		FileSystemTreeNode ftn = (FileSystemTreeNode) value;

		File file = ftn.getFile();
		String fileName = "?";

		if (file != null) {
			
			fileName = file.getName();
			
			if (fileName.length() == 0) {
				fileName = file.getAbsolutePath();
			}
		}

		JLabel result = (JLabel) super.getTreeCellRendererComponent(tree,
				fileName, sel, expanded, leaf, row, hasFocus);
		
		return result;
	}
}