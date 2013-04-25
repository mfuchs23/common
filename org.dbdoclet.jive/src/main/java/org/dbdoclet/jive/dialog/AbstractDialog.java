/* 
 * ### Copyright (C) 2007,2012 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import org.dbdoclet.format.Alignment;
import org.dbdoclet.jive.JiveConstants;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.JiveServices;
import org.dbdoclet.jive.dialog.action.ActionCloseDialog;
import org.dbdoclet.jive.widget.TopPanel;

public class AbstractDialog extends JDialog {

	class DialogListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent event) {

			setPerformedAction(DialogAction.CANCEL);
			setVisible(false);
			dispose();
		}
	}

	private static final long serialVersionUID = 1L;
	private DialogAction performedAction = DialogAction.NONE;
	private final Window parent;

	protected static final int PREFERRED_HEIGHT = 800;
	protected static final int PREFERRED_WIDTH = 640;
	protected ResourceBundle res;
	private boolean initialized;

	public AbstractDialog() {
		this(null, "", true);
	}

	public AbstractDialog(String title) {
		this(null, title, true);
	}

	public AbstractDialog(Window parent, String title) {
		this(parent, title, true);
	}

	public AbstractDialog(Window parent, String title, boolean modal) {
		super(parent, title, modal ? DEFAULT_MODALITY_TYPE
				: ModalityType.MODELESS);
		this.parent = parent;
	}

	public void center() {
		center(null);
	}

	public void center(Window parent) {
		JiveServices.center(parent, this);
	}

	public Window getParentWindow() {
		return parent;
	}

	public DialogAction getPerformedAction() {
		return performedAction;
	}

	public void setPerformedAction(DialogAction action) {
		performedAction = action;
	}

	public void setTimeout(int delay) {

		Timer timer = new Timer(delay, new ActionCloseDialog(this,
				"close-dialog"));
		timer.start();
	}

	@Override
	public void setVisible(boolean visible) {

		if (visible == true && initialized == false) {
			init();
		}

		super.setVisible(visible);
	}

	protected void init() {

		initialized = true;

		JRootPane rootPane = this.getRootPane();

		ActionMap actionMap = rootPane.getActionMap();
		actionMap.put("cancel", new ActionCloseDialog(this, "cancel"));

		InputMap inputMap = rootPane
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");

		JiveFactory jive = JiveFactory.getInstance();
		res = jive.getResourceBundle();

		addWindowListener(new DialogListener());
	}

	protected TopPanel createTopPanel(String title) {

		TopPanel topPanel = new TopPanel();
		topPanel.setTitle(title);
		topPanel.setAlignment(Alignment.LEFT);
		topPanel.setBackground(getBackground());
		topPanel.setGradient(JiveConstants.COLOUR_BLUE_GRAY_1,
				JiveConstants.COLOUR_BLUE_GRAY_4);
		topPanel.setForeground(Color.white);
		topPanel.setTitle(title);
		return topPanel;
	}

}
