/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CreateSrcZip extends Task {

    private static Log logger = LogFactory.getLog(CreateSrcZip.class);

    public void execute()
        throws BuildException {

        try {

            logger.info("CreateSrcZip");
            
        } catch (Exception oops) {
            logger.fatal("Unexpected Exception!", oops);
        }
    }

}
