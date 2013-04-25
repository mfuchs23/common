package org.dbdoclet.jive.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.TopPanel;

public class DataDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	private GridPanel gridPanel;
	protected JLabel errorLabel;
	protected ImageIcon hintIcon;

	public DataDialog(Frame parent, String title) {

		super(parent, title, true);
		init(title);
	}

	public DataDialog(Frame parent, String title, boolean isModal) {

		super(parent, title, isModal);
		init(title);
	}

	public DataDialog(Dialog parent, String title, boolean isModal) {

		super(parent, title, isModal);
		init(title);
	}

	protected GridPanel getGridPanel() {
		return gridPanel;
	}

	protected void setError(String message) {

		errorLabel.setText(message);

		if (hintIcon != null) {
			errorLabel.setIcon(hintIcon);
		}
	}

	protected void unsetError() {

		errorLabel.setIcon(null);
		errorLabel.setText(" ");
	}

	private void init(String title) {

		GridPanel rootPanel = new GridPanel(new Insets(0, 0, 0, 0));
		rootPanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(rootPanel, BorderLayout.CENTER);

		TopPanel topPanel = createTopPanel(title);

		errorLabel = new JLabel();
		errorLabel.setText(" ");
		errorLabel.setFont(errorLabel.getFont().deriveFont(Font.BOLD));
		errorLabel.setForeground(JiveConstants.COLOUR_FIREBRICK_4);

		rootPanel.addComponent(topPanel, Anchor.NORTHWEST, Fill.HORIZONTAL);
		rootPanel.incrRow();

		rootPanel.addComponent(errorLabel, Anchor.NORTHWEST, Fill.HORIZONTAL);
		rootPanel.incrRow();

		gridPanel = new GridPanel();
		// gridPanel.setBorder(new TopEtchedBorder());
		rootPanel.addComponent(gridPanel, Anchor.NORTHWEST, Fill.BOTH);

	}

	public boolean isCanceled() {
		return getPerformedAction() == DialogAction.CANCEL ? true : false;
	}
}
