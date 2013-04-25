/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Target.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Target implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.TargetItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Target() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.TargetItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vTargetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTargetItem(
            final org.dbdoclet.antol.ant.TargetItem vTargetItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vTargetItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vTargetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTargetItem(
            final int index,
            final org.dbdoclet.antol.ant.TargetItem vTargetItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vTargetItem);
    }

    /**
     * Method enumerateTargetItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.TargetItem> enumerateTargetItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Method getTargetItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.dbdoclet.antol.ant.TargetItem
     * at the given index
     */
    public org.dbdoclet.antol.ant.TargetItem getTargetItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getTargetItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.TargetItem) _items.get(index);
    }

    /**
     * Method getTargetItem.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.TargetItem[] getTargetItem(
    ) {
        org.dbdoclet.antol.ant.TargetItem[] array = new org.dbdoclet.antol.ant.TargetItem[0];
        return (org.dbdoclet.antol.ant.TargetItem[]) this._items.toArray(array);
    }

    /**
     * Method getTargetItemCount.
     * 
     * @return the size of this collection
     */
    public int getTargetItemCount(
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
     * Method iterateTargetItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.TargetItem> iterateTargetItem(
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
    public void removeAllTargetItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeTargetItem.
     * 
     * @param vTargetItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeTargetItem(
            final org.dbdoclet.antol.ant.TargetItem vTargetItem) {
        boolean removed = _items.remove(vTargetItem);
        return removed;
    }

    /**
     * Method removeTargetItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.TargetItem removeTargetItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.TargetItem) obj;
    }

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * 
     * 
     * @param index
     * @param vTargetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTargetItem(
            final int index,
            final org.dbdoclet.antol.ant.TargetItem vTargetItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setTargetItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vTargetItem);
    }

    /**
     * 
     * 
     * @param vTargetItemArray
     */
    public void setTargetItem(
            final org.dbdoclet.antol.ant.TargetItem[] vTargetItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vTargetItemArray.length; i++) {
                this._items.add(vTargetItemArray[i]);
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
     * @return the unmarshaled org.dbdoclet.antol.ant.Target
     */
    public static org.dbdoclet.antol.ant.Target unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Target) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Target.class, reader);
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
