/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Javadoc.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Javadoc implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _access.
     */
    private org.dbdoclet.antol.ant.types.Access _access;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.JavadocItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Javadoc() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.JavadocItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vJavadocItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavadocItem(
            final org.dbdoclet.antol.ant.JavadocItem vJavadocItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vJavadocItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vJavadocItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavadocItem(
            final int index,
            final org.dbdoclet.antol.ant.JavadocItem vJavadocItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vJavadocItem);
    }

    /**
     * Method enumerateJavadocItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.JavadocItem> enumerateJavadocItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'access'.
     * 
     * @return the value of field 'Access'.
     */
    public org.dbdoclet.antol.ant.types.Access getAccess(
    ) {
        return this._access;
    }

    /**
     * Method getJavadocItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.dbdoclet.antol.ant.JavadocItem
     * at the given index
     */
    public org.dbdoclet.antol.ant.JavadocItem getJavadocItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getJavadocItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.JavadocItem) _items.get(index);
    }

    /**
     * Method getJavadocItem.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.JavadocItem[] getJavadocItem(
    ) {
        org.dbdoclet.antol.ant.JavadocItem[] array = new org.dbdoclet.antol.ant.JavadocItem[0];
        return (org.dbdoclet.antol.ant.JavadocItem[]) this._items.toArray(array);
    }

    /**
     * Method getJavadocItemCount.
     * 
     * @return the size of this collection
     */
    public int getJavadocItemCount(
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
     * Method iterateJavadocItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.JavadocItem> iterateJavadocItem(
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
    public void removeAllJavadocItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeJavadocItem.
     * 
     * @param vJavadocItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeJavadocItem(
            final org.dbdoclet.antol.ant.JavadocItem vJavadocItem) {
        boolean removed = _items.remove(vJavadocItem);
        return removed;
    }

    /**
     * Method removeJavadocItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.JavadocItem removeJavadocItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.JavadocItem) obj;
    }

    /**
     * Sets the value of field 'access'.
     * 
     * @param access the value of field 'access'.
     */
    public void setAccess(
            final org.dbdoclet.antol.ant.types.Access access) {
        this._access = access;
    }

    /**
     * 
     * 
     * @param index
     * @param vJavadocItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setJavadocItem(
            final int index,
            final org.dbdoclet.antol.ant.JavadocItem vJavadocItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setJavadocItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vJavadocItem);
    }

    /**
     * 
     * 
     * @param vJavadocItemArray
     */
    public void setJavadocItem(
            final org.dbdoclet.antol.ant.JavadocItem[] vJavadocItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vJavadocItemArray.length; i++) {
                this._items.add(vJavadocItemArray[i]);
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
     * @return the unmarshaled org.dbdoclet.antol.ant.Javadoc
     */
    public static org.dbdoclet.antol.ant.Javadoc unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Javadoc) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Javadoc.class, reader);
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
