package org.dbdoclet.jive.dialog;

import org.dbdoclet.jive.widget.ButtonPanel;

public enum DialogAction {
	CANCEL, NO, NONE, OK, YES;

	public static int getFlag(DialogAction action) {
		switch (action) {
		case CANCEL:
			return ButtonPanel.OK;
		case NO:
			return ButtonPanel.NO;
		case OK:
			return ButtonPanel.OK;
		case YES:
			return ButtonPanel.YES;
		default:
			return -1;
		}
	}
}
