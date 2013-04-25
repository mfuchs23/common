/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Path.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Path implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id.
     */
    private java.lang.String _id;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.PathItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Path() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.PathItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPathItem(
            final org.dbdoclet.antol.ant.PathItem vPathItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vPathItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vPathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPathItem(
            final int index,
            final org.dbdoclet.antol.ant.PathItem vPathItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vPathItem);
    }

    /**
     * Method enumeratePathItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.PathItem> enumeratePathItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'id'.
     * 
     * @return the value of field 'Id'.
     */
    public java.lang.String getId(
    ) {
        return this._id;
    }

    /**
     * Method getPathItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.dbdoclet.antol.ant.PathItem at
     * the given index
     */
    public org.dbdoclet.antol.ant.PathItem getPathItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getPathItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.PathItem) _items.get(index);
    }

    /**
     * Method getPathItem.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.PathItem[] getPathItem(
    ) {
        org.dbdoclet.antol.ant.PathItem[] array = new org.dbdoclet.antol.ant.PathItem[0];
        return (org.dbdoclet.antol.ant.PathItem[]) this._items.toArray(array);
    }

    /**
     * Method getPathItemCount.
     * 
     * @return the size of this collection
     */
    public int getPathItemCount(
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
     * Method iteratePathItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.PathItem> iteratePathItem(
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
    public void removeAllPathItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removePathItem.
     * 
     * @param vPathItem
     * @return true if the object was removed from the collection.
     */
    public boolean removePathItem(
            final org.dbdoclet.antol.ant.PathItem vPathItem) {
        boolean removed = _items.remove(vPathItem);
        return removed;
    }

    /**
     * Method removePathItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.PathItem removePathItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.PathItem) obj;
    }

    /**
     * Sets the value of field 'id'.
     * 
     * @param id the value of field 'id'.
     */
    public void setId(
            final java.lang.String id) {
        this._id = id;
    }

    /**
     * 
     * 
     * @param index
     * @param vPathItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPathItem(
            final int index,
            final org.dbdoclet.antol.ant.PathItem vPathItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setPathItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vPathItem);
    }

    /**
     * 
     * 
     * @param vPathItemArray
     */
    public void setPathItem(
            final org.dbdoclet.antol.ant.PathItem[] vPathItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vPathItemArray.length; i++) {
                this._items.add(vPathItemArray[i]);
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
     * @return the unmarshaled org.dbdoclet.antol.ant.Path
     */
    public static org.dbdoclet.antol.ant.Path unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Path) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Path.class, reader);
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
