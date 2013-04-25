package org.dbdoclet.jive.widget;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;

/** 
 * Texteditor
 * 
 * @author michael
 */
public class EditorPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private JTextPane textPane;
	private boolean isChanged;

	public EditorPanel() {
		
		JiveFactory jf = JiveFactory.getInstance();
		
		textPane = jf.createTextPane();
		textPane.setBackground(Color.white);
		textPane.setFont(Font.decode("Courier New-BOLD-16"));
		textPane.getDocument().addDocumentListener(new EditorDocumentListener());
		
		JScrollPane scrollPane = jf.createScrollPane(textPane);
		addComponent(scrollPane, Fill.BOTH);
	}

	public String getText() {
		return textPane.getText();
	}

	public void setText(String buffer) {
		textPane.setText(buffer);
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	class EditorDocumentListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			isChanged = true;
		}

		public void removeUpdate(DocumentEvent e) {
			isChanged = true;
		}

		public void changedUpdate(DocumentEvent e) {
			isChanged = true;
		}
	}
}
