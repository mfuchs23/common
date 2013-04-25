/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class JavaItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class JavaItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _arg.
     */
    private org.dbdoclet.antol.ant.Arg _arg;

    /**
     * Field _bootclasspath.
     */
    private org.dbdoclet.antol.ant.Bootclasspath _bootclasspath;

    /**
     * Field _classpath.
     */
    private org.dbdoclet.antol.ant.Classpath _classpath;

    /**
     * Field _jvmarg.
     */
    private org.dbdoclet.antol.ant.Jvmarg _jvmarg;


      //----------------/
     //- Constructors -/
    //----------------/

    public JavaItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'arg'.
     * 
     * @return the value of field 'Arg'.
     */
    public org.dbdoclet.antol.ant.Arg getArg(
    ) {
        return this._arg;
    }

    /**
     * Returns the value of field 'bootclasspath'.
     * 
     * @return the value of field 'Bootclasspath'.
     */
    public org.dbdoclet.antol.ant.Bootclasspath getBootclasspath(
    ) {
        return this._bootclasspath;
    }

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     * 
     * @return the value of field 'ChoiceValue'.
     */
    public java.lang.Object getChoiceValue(
    ) {
        return this._choiceValue;
    }

    /**
     * Returns the value of field 'classpath'.
     * 
     * @return the value of field 'Classpath'.
     */
    public org.dbdoclet.antol.ant.Classpath getClasspath(
    ) {
        return this._classpath;
    }

    /**
     * Returns the value of field 'jvmarg'.
     * 
     * @return the value of field 'Jvmarg'.
     */
    public org.dbdoclet.antol.ant.Jvmarg getJvmarg(
    ) {
        return this._jvmarg;
    }

    /**
     * Sets the value of field 'arg'.
     * 
     * @param arg the value of field 'arg'.
     */
    public void setArg(
            final org.dbdoclet.antol.ant.Arg arg) {
        this._arg = arg;
        this._choiceValue = arg;
    }

    /**
     * Sets the value of field 'bootclasspath'.
     * 
     * @param bootclasspath the value of field 'bootclasspath'.
     */
    public void setBootclasspath(
            final org.dbdoclet.antol.ant.Bootclasspath bootclasspath) {
        this._bootclasspath = bootclasspath;
        this._choiceValue = bootclasspath;
    }

    /**
     * Sets the value of field 'classpath'.
     * 
     * @param classpath the value of field 'classpath'.
     */
    public void setClasspath(
            final org.dbdoclet.antol.ant.Classpath classpath) {
        this._classpath = classpath;
        this._choiceValue = classpath;
    }

    /**
     * Sets the value of field 'jvmarg'.
     * 
     * @param jvmarg the value of field 'jvmarg'.
     */
    public void setJvmarg(
            final org.dbdoclet.antol.ant.Jvmarg jvmarg) {
        this._jvmarg = jvmarg;
        this._choiceValue = jvmarg;
    }

}
