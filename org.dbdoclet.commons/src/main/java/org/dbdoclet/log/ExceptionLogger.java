package org.dbdoclet.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Die Klasse <code>ExceptionLogger</code> dient der Protokollierung und Analyse
 * von Exceptions.
 * 
 * @author michael
 * 
 */
public class ExceptionLogger {

	private static Log logger = LogFactory.getLog("exceptions");
	
	public Throwable getRootCause(Throwable oops) {
		
		if (oops == null) {
			return null;
		}
		
		Throwable cause = oops;
		
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		
		return cause;
	}
	
	public void handleException(Throwable oops) {
		
		if (oops == null) {
			return;
		}

		//Throwable cause = getRootCause(oops);
		
		logger.fatal(oops);
	}
}
