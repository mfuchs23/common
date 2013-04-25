package org.dbdoclet.jive.widget;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ContinueDialog;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.service.ResourceServices;

public class Editor extends JFrame {

	private static final long serialVersionUID = 1L;
	private AbstractAction closeAction;
	private EditorPanel editorPanel;
	private boolean initialized;
	private AbstractAction saveAction;
	private JMenuItem saveButton;

	public void createGui() {

		JiveFactory jf = JiveFactory.getInstance();
		jf.getResourceBundle();

		setTitle("Editor");
		setJMenuBar(createMenuBar(jf));

		editorPanel = new EditorPanel();
		getContentPane().add(editorPanel);
		pack();
		initialized = true;

		closeAction = new ActionClose(jf.getResourceBundle()
				.getString("C_SAVE"), this);
	}

	public String getText() {
		return editorPanel.getText();
	}

	public boolean isChanged() {
		return editorPanel.isChanged();
	}

	public void open(String buffer) {

		if (initialized == false) {
			createGui();
		}

		editorPanel.setText(buffer);
	}

	public void save() {
		saveButton.doClick();
		editorPanel.setChanged(false);
	}

	public void setChanged(boolean changed) {
		editorPanel.setChanged(changed);
	}

	public void setSaveAction(AbstractAction saveAction) {
		this.saveAction = saveAction;

	}

	private JMenuBar createMenuBar(JiveFactory jf) {

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = jf.createMenu(jf.getResourceBundle().getString(
				"C_FILE"));
		menuBar.add(fileMenu);

		saveButton = null;

		if (saveAction != null) {
			saveButton = jf.createMenuItem(new Identifier("editor.save"),
					saveAction);
		} else {
			saveButton = jf.createMenuItem(new Identifier("editor.save"), jf
					.getResourceBundle().getString("C_SAVE"));
		}

		fileMenu.add(saveButton);
		fileMenu.add(jf.createMenuItem(new Identifier("editor.close"),
				closeAction));

		return menuBar;
	}
}

class ActionClose extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private final Editor editor;

	public ActionClose(String name, Editor editor) {
		super(name);
		this.editor = editor;
	}

	public void actionPerformed(ActionEvent e) {

		try {

			JiveFactory jf = JiveFactory.getInstance();
			ResourceBundle res = jf.getResourceBundle();

			if (editor.isChanged()) {

				ContinueDialog dlg = new ContinueDialog(editor,
						ResourceServices.getString(res, "C_SAVE"),
						ResourceServices.getString(res, "C_SAVE_BEFORE_EXIT"));

				dlg.setCancelButtonText(ResourceServices.getString(res, "C_DONT_SAVE"));
				dlg.setContinueButtonText(ResourceServices.getString(res, "C_SAVE"));

				dlg.setVisible(true);
				
				if (dlg.doContinue()) {
					editor.save();
				}
				
			} else {
				editor.setVisible(false);
				editor.dispose();
			}
		
		} catch (Throwable oops) {

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
		}

	}
}