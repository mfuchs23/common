package org.dbdoclet.jive.widget.fstree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileSystemTreeNode implements TreeNode {

	private File[] children;

	private File file = new File("null");
	private FileSystemTreeNode parent;

	public FileSystemTreeNode(File file, FileSystemTreeNode parent) {

		this.file = file;
		this.parent = parent;

		children = file.listFiles();

		if (children == null) {
			children = new File[0];
		} else {
			Arrays.sort(children);
		}
	}

	public FileSystemTreeNode(File[] children) {
		
		if (children == null) {
			children = new File[0];
		}
		
		this.children = children;
		Arrays.sort(children);

		parent = null;
		file = new File("");
	}

	
	public Enumeration<?> children() {

		return new ChildEnumeration(children);
	}

	
	public boolean getAllowsChildren() {
		return true;
	}

	
	public TreeNode getChildAt(int childIndex) {

		if (children == null) {
			return null;
		}

		if (childIndex < 0 || childIndex >= children.length) {
			return null;
		}

		return new FileSystemTreeNode(children[childIndex], this);
	}

	
	public int getChildCount() {

		if (children == null) {
			return 0;
		}

		return children.length;
	}

	public File getFile() {
		return file;
	}

	
	public int getIndex(TreeNode node) {

		FileSystemTreeNode child = (FileSystemTreeNode) node;

		for (int i = 0; i < children.length; i++) {
			if (child.file.equals(children[i])) {
				return i;
			}
		}

		return -1;
	}

	
	public TreeNode getParent() {
		return parent;
	}

	
	public boolean isLeaf() {

		if (children == null || children.length == 0) {
			return true;
		}

		return false;
	}

	
	public String toString() {

		return "[Parent=" + parent + ",file=" + file + "]";
	}

	public TreePath getTreePath() {

		ArrayList<FileSystemTreeNode> nodeList = new ArrayList<FileSystemTreeNode>();

		FileSystemTreeNode node = this;

		while (node != null) {

			nodeList.add(node);
			node = (FileSystemTreeNode) node.getParent();
		}

		Collections.reverse(nodeList);
		return new TreePath(nodeList.toArray());
	}
}

class ChildEnumeration implements Enumeration<File> {

	private File[] children;
	private int index = 0;

	public ChildEnumeration(File[] children) {
		this.children = children;
	}

	
	public boolean hasMoreElements() {

		if (children == null) {
			return false;
		}

		if (index < children.length) {
			return true;
		}

		return false;
	}

	
	public File nextElement() {

		if (children == null || index >= children.length) {
			throw new NoSuchElementException("Child Enumeration index=" + index);
		}

		return children[index++];
	}

}