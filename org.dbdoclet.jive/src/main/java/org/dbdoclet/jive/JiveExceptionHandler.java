package org.dbdoclet.jive;

import org.dbdoclet.jive.dialog.ExceptionBox;

public class JiveExceptionHandler {

	public static void handle(Throwable oops) {
		
		ExceptionBox ebox = new ExceptionBox(oops);
		ebox.center();
		ebox.toFront();
		ebox.setVisible(true);
	}
}
