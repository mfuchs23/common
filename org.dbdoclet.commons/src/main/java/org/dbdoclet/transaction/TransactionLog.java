/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.transaction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.dbdoclet.service.FileServices;

public class TransactionLog {

    private HashMap<Object, String> actionMap = new HashMap<Object, String>();
    private File transactionDir = null;
    private File shadowedDir = null;
    private File shadowCopyDir = null;

    public TransactionLog() {
        super();
    }

    public void setTransactionDirectory(File transactionDir)
        throws IOException {

        if (transactionDir == null) {
            throw new IllegalArgumentException("The argument transactionDir must not be null!");
        }

        String shadowCopyPath = FileServices.appendPath(transactionDir, "__tlog.01");
        shadowCopyDir = new File(shadowCopyPath);

        FileServices.createPath(shadowCopyDir);

        this.transactionDir = transactionDir;
    }
    
    public void createShadowCopy(File dir)
        throws IOException {

        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }

        if (transactionDir == null) {
            throw new IllegalStateException("The field transactionDir must not be null!");
        }

        FileServices.copyDir(dir, shadowCopyDir);

        shadowedDir = dir;
    }

    public void restoreShadowCopy()
        throws IOException {

        FileServices.setWritable(shadowedDir);
        FileServices.setWritable(shadowCopyDir);

        FileServices.delete(shadowedDir);
        FileServices.createPath(shadowedDir);
        FileServices.copyDir(shadowCopyDir, shadowedDir);
    }

    public void addDelete(File file) {
        actionMap.put(file, "delete");
    }

    public Iterator<Object> iterator() {
        return actionMap.keySet().iterator();
    }

    public void rollback()
        throws RollbackException {

        rollback(null);
    }

    public void rollback(Throwable cause)
        throws RollbackException {
        
        if (transactionDir == null || transactionDir.length() == 0) {
            throw new IllegalStateException("TransactionLog.rollback - The transaction directory must not be null! Stopping rollback.");
        }

        File file;
        String action;
        Object obj;
        
        HashMap<Throwable, String> oopsMap = new HashMap<Throwable, String>();

        if (cause != null) {
            oopsMap.put(cause, "Cause");
        }

        try {
			FileServices.setWritable(transactionDir);
		} catch (IOException e) {
			throw new RollbackException(e);
		}

        String lockFileName = FileServices.appendFileName(transactionDir, "TRANSACTION_LOCKED");
        File lockFile = new File(lockFileName);
        
        try {
            FileServices.touch(lockFile);
        } catch (IOException oops) {
            oopsMap.put(oops, lockFile.getAbsolutePath());
        }

        Iterator<Object> iterator = actionMap.keySet().iterator();
        
        while (iterator.hasNext()) {
            
            obj = iterator.next();

            if (obj != null && obj instanceof File) {

                file = (File) obj;
                action = actionMap.get(obj);

                if (action != null 
                    && action.equals("delete") 
                    && file.exists() == true) {

                    try {

                        FileServices.setWritable(file);
                        FileServices.delete(file);

                    } catch (IOException oops) {
                        oopsMap.put(oops, file.getAbsolutePath());
                    }
                }
            }
        }

        if (shadowedDir != null) {

            try {

                restoreShadowCopy();

            } catch (IOException oops) {
                oopsMap.put(oops, shadowedDir.getAbsolutePath());
            }
        }

        if (oopsMap.size() > 0) {

            RollbackException oops = new RollbackException(oopsMap);
            throw oops;
        }
    }
}
