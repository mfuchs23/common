/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class FilesetItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class FilesetItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _exclude.
     */
    private org.dbdoclet.antol.ant.Exclude _exclude;

    /**
     * Field _include.
     */
    private org.dbdoclet.antol.ant.Include _include;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilesetItem() {
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
     * Returns the value of field 'exclude'.
     * 
     * @return the value of field 'Exclude'.
     */
    public org.dbdoclet.antol.ant.Exclude getExclude(
    ) {
        return this._exclude;
    }

    /**
     * Returns the value of field 'include'.
     * 
     * @return the value of field 'Include'.
     */
    public org.dbdoclet.antol.ant.Include getInclude(
    ) {
        return this._include;
    }

    /**
     * Sets the value of field 'exclude'.
     * 
     * @param exclude the value of field 'exclude'.
     */
    public void setExclude(
            final org.dbdoclet.antol.ant.Exclude exclude) {
        this._exclude = exclude;
        this._choiceValue = exclude;
    }

    /**
     * Sets the value of field 'include'.
     * 
     * @param include the value of field 'include'.
     */
    public void setInclude(
            final org.dbdoclet.antol.ant.Include include) {
        this._include = include;
        this._choiceValue = include;
    }

}
