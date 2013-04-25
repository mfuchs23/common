/* 
 * $Id: TemplateTransformException.java,v 1.1.1.1 2004/12/21 14:06:41 mfuchs Exp $
 *
 * ### Copyright (C) 2004 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 *
 * Unico Media GmbH, Görresstr. 12, 80798 München, Germany
 * http://www.unico-group.com
 *
 * RCS Information
 * Author..........: $Author: mfuchs $
 * Date............: $Date: 2004/12/21 14:06:41 $
 * Revision........: $Revision: 1.1.1.1 $
 * State...........: $State: Exp $
 */
package org.dbdoclet.template;
 
public class TemplateTransformException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TemplateTransformException(String msg) {
        super(msg);
    }

    public TemplateTransformException(String msg, Throwable oops) {
        super(msg, oops);
    }
}

