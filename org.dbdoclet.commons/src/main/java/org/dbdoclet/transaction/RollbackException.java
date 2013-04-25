package org.dbdoclet.transaction;

import java.util.HashMap;

public class RollbackException extends Exception {

    private static final long serialVersionUID = 1L;

    public RollbackException(HashMap<Throwable, String> oopsMap) {
        super();
    }
}
