/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Copy.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Copy implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _todir.
     */
    private java.lang.String _todir;

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _filesetList.
     */
    private java.util.List<org.dbdoclet.antol.ant.Fileset> _filesetList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Copy() {
        super();
        this._filesetList = new java.util.ArrayList<org.dbdoclet.antol.ant.Fileset>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vFileset
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFileset(
            final org.dbdoclet.antol.ant.Fileset vFileset)
    throws java.lang.IndexOutOfBoundsException {
        this._filesetList.add(vFileset);
    }

    /**
     * 
     * 
     * @param index
     * @param vFileset
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFileset(
            final int index,
            final org.dbdoclet.antol.ant.Fileset vFileset)
    throws java.lang.IndexOutOfBoundsException {
        this._filesetList.add(index, vFileset);
    }

    /**
     * Method enumerateFileset.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.Fileset> enumerateFileset(
    ) {
        return java.util.Collections.enumeration(this._filesetList);
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
     * Method getFileset.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.dbdoclet.antol.ant.Fileset at
     * the given index
     */
    public org.dbdoclet.antol.ant.Fileset getFileset(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._filesetList.size()) {
            throw new IndexOutOfBoundsException("getFileset: Index value '" + index + "' not in range [0.." + (this._filesetList.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.Fileset) _filesetList.get(index);
    }

    /**
     * Method getFileset.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.Fileset[] getFileset(
    ) {
        org.dbdoclet.antol.ant.Fileset[] array = new org.dbdoclet.antol.ant.Fileset[0];
        return (org.dbdoclet.antol.ant.Fileset[]) this._filesetList.toArray(array);
    }

    /**
     * Method getFilesetCount.
     * 
     * @return the size of this collection
     */
    public int getFilesetCount(
    ) {
        return this._filesetList.size();
    }

    /**
     * Returns the value of field 'todir'.
     * 
     * @return the value of field 'Todir'.
     */
    public java.lang.String getTodir(
    ) {
        return this._todir;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Method iterateFileset.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.Fileset> iterateFileset(
    ) {
        return this._filesetList.iterator();
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     */
    public void removeAllFileset(
    ) {
        this._filesetList.clear();
    }

    /**
     * Method removeFileset.
     * 
     * @param vFileset
     * @return true if the object was removed from the collection.
     */
    public boolean removeFileset(
            final org.dbdoclet.antol.ant.Fileset vFileset) {
        boolean removed = _filesetList.remove(vFileset);
        return removed;
    }

    /**
     * Method removeFilesetAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.Fileset removeFilesetAt(
            final int index) {
        java.lang.Object obj = this._filesetList.remove(index);
        return (org.dbdoclet.antol.ant.Fileset) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vFileset
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setFileset(
            final int index,
            final org.dbdoclet.antol.ant.Fileset vFileset)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._filesetList.size()) {
            throw new IndexOutOfBoundsException("setFileset: Index value '" + index + "' not in range [0.." + (this._filesetList.size() - 1) + "]");
        }

        this._filesetList.set(index, vFileset);
    }

    /**
     * 
     * 
     * @param vFilesetArray
     */
    public void setFileset(
            final org.dbdoclet.antol.ant.Fileset[] vFilesetArray) {
        //-- copy array
        _filesetList.clear();

        for (int i = 0; i < vFilesetArray.length; i++) {
                this._filesetList.add(vFilesetArray[i]);
        }
    }

    /**
     * Sets the value of field 'todir'.
     * 
     * @param todir the value of field 'todir'.
     */
    public void setTodir(
            final java.lang.String todir) {
        this._todir = todir;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.Copy
     */
    public static org.dbdoclet.antol.ant.Copy unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Copy) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Copy.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
