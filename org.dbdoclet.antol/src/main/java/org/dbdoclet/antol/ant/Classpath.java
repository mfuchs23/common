/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Classpath.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Classpath implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _refid.
     */
    private java.lang.String _refid;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.ClasspathItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Classpath() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.ClasspathItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vClasspathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addClasspathItem(
            final org.dbdoclet.antol.ant.ClasspathItem vClasspathItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vClasspathItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vClasspathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addClasspathItem(
            final int index,
            final org.dbdoclet.antol.ant.ClasspathItem vClasspathItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vClasspathItem);
    }

    /**
     * Method enumerateClasspathItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.ClasspathItem> enumerateClasspathItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getClasspathItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * org.dbdoclet.antol.ant.ClasspathItem at the given index
     */
    public org.dbdoclet.antol.ant.ClasspathItem getClasspathItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getClasspathItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.ClasspathItem) _items.get(index);
    }

    /**
     * Method getClasspathItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.ClasspathItem[] getClasspathItem(
    ) {
        org.dbdoclet.antol.ant.ClasspathItem[] array = new org.dbdoclet.antol.ant.ClasspathItem[0];
        return (org.dbdoclet.antol.ant.ClasspathItem[]) this._items.toArray(array);
    }

    /**
     * Method getClasspathItemCount.
     * 
     * @return the size of this collection
     */
    public int getClasspathItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'refid'.
     * 
     * @return the value of field 'Refid'.
     */
    public java.lang.String getRefid(
    ) {
        return this._refid;
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
     * Method iterateClasspathItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.ClasspathItem> iterateClasspathItem(
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
    public void removeAllClasspathItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeClasspathItem.
     * 
     * @param vClasspathItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeClasspathItem(
            final org.dbdoclet.antol.ant.ClasspathItem vClasspathItem) {
        boolean removed = _items.remove(vClasspathItem);
        return removed;
    }

    /**
     * Method removeClasspathItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.ClasspathItem removeClasspathItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.ClasspathItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vClasspathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setClasspathItem(
            final int index,
            final org.dbdoclet.antol.ant.ClasspathItem vClasspathItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setClasspathItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vClasspathItem);
    }

    /**
     * 
     * 
     * @param vClasspathItemArray
     */
    public void setClasspathItem(
            final org.dbdoclet.antol.ant.ClasspathItem[] vClasspathItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vClasspathItemArray.length; i++) {
                this._items.add(vClasspathItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'refid'.
     * 
     * @param refid the value of field 'refid'.
     */
    public void setRefid(
            final java.lang.String refid) {
        this._refid = refid;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.Classpath
     */
    public static org.dbdoclet.antol.ant.Classpath unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Classpath) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Classpath.class, reader);
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
