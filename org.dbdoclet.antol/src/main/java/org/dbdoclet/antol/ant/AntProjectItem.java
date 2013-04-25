/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class AntProjectItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class AntProjectItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _path.
     */
    private org.dbdoclet.antol.ant.Path _path;

    /**
     * Field _property.
     */
    private org.dbdoclet.antol.ant.Property _property;

    /**
     * Field _taskdef.
     */
    private org.dbdoclet.antol.ant.Taskdef _taskdef;

    /**
     * Field _target.
     */
    private org.dbdoclet.antol.ant.Target _target;


      //----------------/
     //- Constructors -/
    //----------------/

    public AntProjectItem() {
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
     * Returns the value of field 'path'.
     * 
     * @return the value of field 'Path'.
     */
    public org.dbdoclet.antol.ant.Path getPath(
    ) {
        return this._path;
    }

    /**
     * Returns the value of field 'property'.
     * 
     * @return the value of field 'Property'.
     */
    public org.dbdoclet.antol.ant.Property getProperty(
    ) {
        return this._property;
    }

    /**
     * Returns the value of field 'target'.
     * 
     * @return the value of field 'Target'.
     */
    public org.dbdoclet.antol.ant.Target getTarget(
    ) {
        return this._target;
    }

    /**
     * Returns the value of field 'taskdef'.
     * 
     * @return the value of field 'Taskdef'.
     */
    public org.dbdoclet.antol.ant.Taskdef getTaskdef(
    ) {
        return this._taskdef;
    }

    /**
     * Sets the value of field 'path'.
     * 
     * @param path the value of field 'path'.
     */
    public void setPath(
            final org.dbdoclet.antol.ant.Path path) {
        this._path = path;
        this._choiceValue = path;
    }

    /**
     * Sets the value of field 'property'.
     * 
     * @param property the value of field 'property'.
     */
    public void setProperty(
            final org.dbdoclet.antol.ant.Property property) {
        this._property = property;
        this._choiceValue = property;
    }

    /**
     * Sets the value of field 'target'.
     * 
     * @param target the value of field 'target'.
     */
    public void setTarget(
            final org.dbdoclet.antol.ant.Target target) {
        this._target = target;
        this._choiceValue = target;
    }

    /**
     * Sets the value of field 'taskdef'.
     * 
     * @param taskdef the value of field 'taskdef'.
     */
    public void setTaskdef(
            final org.dbdoclet.antol.ant.Taskdef taskdef) {
        this._taskdef = taskdef;
        this._choiceValue = taskdef;
    }

}
