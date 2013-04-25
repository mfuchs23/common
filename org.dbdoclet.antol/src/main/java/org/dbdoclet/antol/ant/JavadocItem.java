/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class JavadocItem.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class JavadocItem implements java.io.Serializable {


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

    /**
     * Field _dirset.
     */
    private org.dbdoclet.antol.ant.Dirset _dirset;

    /**
     * Field _doclet.
     */
    private org.dbdoclet.antol.ant.Doclet _doclet;

    /**
     * Field _fileset.
     */
    private org.dbdoclet.antol.ant.Fileset _fileset;

    /**
     * Field _package.
     */
    private org.dbdoclet.antol.ant.Package _package;

    /**
     * Field _packageset.
     */
    private org.dbdoclet.antol.ant.Packageset _packageset;

    /**
     * Field _sourcepath.
     */
    private org.dbdoclet.antol.ant.Sourcepath _sourcepath;


      //----------------/
     //- Constructors -/
    //----------------/

    public JavadocItem() {
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
     * Returns the value of field 'dirset'.
     * 
     * @return the value of field 'Dirset'.
     */
    public org.dbdoclet.antol.ant.Dirset getDirset(
    ) {
        return this._dirset;
    }

    /**
     * Returns the value of field 'doclet'.
     * 
     * @return the value of field 'Doclet'.
     */
    public org.dbdoclet.antol.ant.Doclet getDoclet(
    ) {
        return this._doclet;
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
     * Returns the value of field 'package'.
     * 
     * @return the value of field 'Package'.
     */
    public org.dbdoclet.antol.ant.Package getPackage(
    ) {
        return this._package;
    }

    /**
     * Returns the value of field 'packageset'.
     * 
     * @return the value of field 'Packageset'.
     */
    public org.dbdoclet.antol.ant.Packageset getPackageset(
    ) {
        return this._packageset;
    }

    /**
     * Returns the value of field 'sourcepath'.
     * 
     * @return the value of field 'Sourcepath'.
     */
    public org.dbdoclet.antol.ant.Sourcepath getSourcepath(
    ) {
        return this._sourcepath;
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
     * Sets the value of field 'doclet'.
     * 
     * @param doclet the value of field 'doclet'.
     */
    public void setDoclet(
            final org.dbdoclet.antol.ant.Doclet doclet) {
        this._doclet = doclet;
        this._choiceValue = doclet;
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
     * Sets the value of field 'package'.
     * 
     * @param _package
     * @param package the value of field 'package'.
     */
    public void setPackage(
            final org.dbdoclet.antol.ant.Package _package) {
        this._package = _package;
        this._choiceValue = _package;
    }

    /**
     * Sets the value of field 'packageset'.
     * 
     * @param packageset the value of field 'packageset'.
     */
    public void setPackageset(
            final org.dbdoclet.antol.ant.Packageset packageset) {
        this._packageset = packageset;
        this._choiceValue = packageset;
    }

    /**
     * Sets the value of field 'sourcepath'.
     * 
     * @param sourcepath the value of field 'sourcepath'.
     */
    public void setSourcepath(
            final org.dbdoclet.antol.ant.Sourcepath sourcepath) {
        this._sourcepath = sourcepath;
        this._choiceValue = sourcepath;
    }

}
