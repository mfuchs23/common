/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class PathItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PathItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _dirset.
     */
    private org.dbdoclet.antol.ant.Dirset _dirset;

    /**
     * Field _fileset.
     */
    private org.dbdoclet.antol.ant.Fileset _fileset;

    /**
     * Field _pathelement.
     */
    private org.dbdoclet.antol.ant.Pathelement _pathelement;


      //----------------/
     //- Constructors -/
    //----------------/

    public PathItem() {
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
     * Returns the value of field 'dirset'.
     * 
     * @return the value of field 'Dirset'.
     */
    public org.dbdoclet.antol.ant.Dirset getDirset(
    ) {
        return this._dirset;
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
     * Returns the value of field 'pathelement'.
     * 
     * @return the value of field 'Pathelement'.
     */
    public org.dbdoclet.antol.ant.Pathelement getPathelement(
    ) {
        return this._pathelement;
    }

    /**
     * Sets the value of field 'dirset'.
     * 
     * @param dirset the value of field 'dirset'.
     */
    public void setDirset(
            final org.dbdoclet.antol.ant.Dirset dirset) {
        this._dirset = dirset;
        this._choiceValue = dirset;
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

    /**
     * Sets the value of field 'pathelement'.
     * 
     * @param pathelement the value of field 'pathelement'.
     */
    public void setPathelement(
            final org.dbdoclet.antol.ant.Pathelement pathelement) {
        this._pathelement = pathelement;
        this._choiceValue = pathelement;
    }

}
