/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class TaskdefItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class TaskdefItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _classpath.
     */
    private org.dbdoclet.antol.ant.Classpath _classpath;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaskdefItem() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Sets the value of field 'classpath'.
     * 
     * @param classpath the value of field 'classpath'.
     */
    public void setClasspath(
            final org.dbdoclet.antol.ant.Classpath classpath) {
        this._classpath = classpath;
        this._choiceValue = classpath;
    }

}
