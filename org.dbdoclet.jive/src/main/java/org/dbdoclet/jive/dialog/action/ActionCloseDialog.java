package org.dbdoclet.jive.dialog.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.AbstractDialog;
import org.dbdoclet.jive.dialog.DialogAction;

public class ActionCloseDialog extends AbstractAction {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ActionCloseDialog.class);

	private final AbstractDialog dialog;
	private final String name;
	private Object mutex;

	public ActionCloseDialog(AbstractDialog dialog, String name) {

		super(name);

		if (dialog == null) {
			throw new IllegalArgumentException(
					"The argument dialog must not be null!");
		}

		this.dialog = dialog;
		this.name = name;
	}

	public void setMutex(Object mutex) {
		this.mutex = mutex;
	}

	public Object getMutex() {
		return mutex;
	}

	public void actionPerformed(ActionEvent event) {

		logger.debug("event=" + event);

		if (dialog != null) {

			if (name != null && name.equals("ok")) {
				dialog.setPerformedAction(DialogAction.OK);
			}

			if (name != null && name.equals("yes")) {
				dialog.setPerformedAction(DialogAction.YES);
			}

			if (name != null && name.equals("cancel")) {
				dialog.setPerformedAction(DialogAction.CANCEL);
			}

			if (name != null && name.equals("no")) {
				dialog.setPerformedAction(DialogAction.NO);
			}

			dialog.setVisible(false);
			dialog.dispose();

			if (mutex != null) {
				synchronized (mutex) {
					mutex.notify();
				}
			}
		}
	}
}
