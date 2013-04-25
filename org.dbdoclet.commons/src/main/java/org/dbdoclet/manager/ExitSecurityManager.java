package org.dbdoclet.manager;

public class ExitSecurityManager extends SecurityManager {

    @Override
    public void checkExit(int status)
 	throws SecurityException {
	throw new SecurityException(String.valueOf(status));
    }
}
