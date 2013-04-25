/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant.types;

/**
 * Enumeration Access.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public enum Access implements java.io.Serializable {


      //------------------/
     //- Enum Constants -/
    //------------------/

    /**
     * Constant PUBLIC
     */
    PUBLIC("public"),
    /**
     * Constant PACKAGE
     */
    PACKAGE("package"),
    /**
     * Constant PROTECTED
     */
    PROTECTED("protected"),
    /**
     * Constant PRIVATE
     */
    PRIVATE("private");

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field value.
     */
    private final java.lang.String value;


      //----------------/
     //- Constructors -/
    //----------------/

    private Access(final java.lang.String value) {
        this.value = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method fromValue.
     * 
     * @param value
     * @return the constant for this value
     */
    public static org.dbdoclet.antol.ant.types.Access fromValue(
            final java.lang.String value) {
        for (Access c: Access.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * 
     * 
     * @param value
     */
    public void setValue(
            final java.lang.String value) {
    }

    /**
     * Method toString.
     * 
     * @return the value of this constant
     */
    public java.lang.String toString(
    ) {
        return this.value;
    }

    /**
     * Method value.
     * 
     * @return the value of this constant
     */
    public java.lang.String value(
    ) {
        return this.value;
    }

}
