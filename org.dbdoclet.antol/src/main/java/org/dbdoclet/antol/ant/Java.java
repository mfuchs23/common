/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Java.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Java implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _classname.
     */
    private java.lang.String _classname;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.JavaItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Java() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.JavaItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vJavaItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavaItem(
            final org.dbdoclet.antol.ant.JavaItem vJavaItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vJavaItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vJavaItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavaItem(
            final int index,
            final org.dbdoclet.antol.ant.JavaItem vJavaItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vJavaItem);
    }

    /**
     * Method enumerateJavaItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.JavaItem> enumerateJavaItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'classname'.
     * 
     * @return the value of field 'Classname'.
     */
    public java.lang.String getClassname(
    ) {
        return this._classname;
    }

    /**
     * Method getJavaItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.dbdoclet.antol.ant.JavaItem at
     * the given index
     */
    public org.dbdoclet.antol.ant.JavaItem getJavaItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getJavaItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.JavaItem) _items.get(index);
    }

    /**
     * Method getJavaItem.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.JavaItem[] getJavaItem(
    ) {
        org.dbdoclet.antol.ant.JavaItem[] array = new org.dbdoclet.antol.ant.JavaItem[0];
        return (org.dbdoclet.antol.ant.JavaItem[]) this._items.toArray(array);
    }

    /**
     * Method getJavaItemCount.
     * 
     * @return the size of this collection
     */
    public int getJavaItemCount(
    ) {
        return this._items.size();
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
     * Method iterateJavaItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.JavaItem> iterateJavaItem(
    ) {
        return this._items.iterator();
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
    public void removeAllJavaItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeJavaItem.
     * 
     * @param vJavaItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeJavaItem(
            final org.dbdoclet.antol.ant.JavaItem vJavaItem) {
        boolean removed = _items.remove(vJavaItem);
        return removed;
    }

    /**
     * Method removeJavaItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.JavaItem removeJavaItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.JavaItem) obj;
    }

    /**
     * Sets the value of field 'classname'.
     * 
     * @param classname the value of field 'classname'.
     */
    public void setClassname(
            final java.lang.String classname) {
        this._classname = classname;
    }

    /**
     * 
     * 
     * @param index
     * @param vJavaItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setJavaItem(
            final int index,
            final org.dbdoclet.antol.ant.JavaItem vJavaItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setJavaItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vJavaItem);
    }

    /**
     * 
     * 
     * @param vJavaItemArray
     */
    public void setJavaItem(
            final org.dbdoclet.antol.ant.JavaItem[] vJavaItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vJavaItemArray.length; i++) {
                this._items.add(vJavaItemArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.Java
     */
    public static org.dbdoclet.antol.ant.Java unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Java) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Java.class, reader);
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
