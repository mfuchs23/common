/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class ClasspathItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ClasspathItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _fileset.
     */
    private org.dbdoclet.antol.ant.Fileset _fileset;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClasspathItem() {
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
     * Returns the value of field 'fileset'.
     * 
     * @return the value of field 'Fileset'.
     */
    public org.dbdoclet.antol.ant.Fileset getFileset(
    ) {
        return this._fileset;
    }

    /**
     * Sets the value of field 'fileset'.
     * 
     * @param fileset the value of field 'fileset'.
     */
    public void setFileset(
            final org.dbdoclet.antol.ant.Fileset fileset) {
        this._fileset = fileset;
        this._choiceValue = fileset;
    }

}
