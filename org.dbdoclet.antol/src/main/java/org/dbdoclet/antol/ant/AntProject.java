/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class AntProject.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class AntProject implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id.
     */
    private java.lang.String _id;

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.AntProjectItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public AntProject() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.AntProjectItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAntProjectItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAntProjectItem(
            final org.dbdoclet.antol.ant.AntProjectItem vAntProjectItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vAntProjectItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vAntProjectItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAntProjectItem(
            final int index,
            final org.dbdoclet.antol.ant.AntProjectItem vAntProjectItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vAntProjectItem);
    }

    /**
     * Method enumerateAntProjectItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.AntProjectItem> enumerateAntProjectItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getAntProjectItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * org.dbdoclet.antol.ant.AntProjectItem at the given index
     */
    public org.dbdoclet.antol.ant.AntProjectItem getAntProjectItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getAntProjectItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.AntProjectItem) _items.get(index);
    }

    /**
     * Method getAntProjectItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.AntProjectItem[] getAntProjectItem(
    ) {
        org.dbdoclet.antol.ant.AntProjectItem[] array = new org.dbdoclet.antol.ant.AntProjectItem[0];
        return (org.dbdoclet.antol.ant.AntProjectItem[]) this._items.toArray(array);
    }

    /**
     * Method getAntProjectItemCount.
     * 
     * @return the size of this collection
     */
    public int getAntProjectItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
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
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
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
     * Method iterateAntProjectItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.AntProjectItem> iterateAntProjectItem(
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
    public void removeAllAntProjectItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeAntProjectItem.
     * 
     * @param vAntProjectItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeAntProjectItem(
            final org.dbdoclet.antol.ant.AntProjectItem vAntProjectItem) {
        boolean removed = _items.remove(vAntProjectItem);
        return removed;
    }

    /**
     * Method removeAntProjectItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.AntProjectItem removeAntProjectItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.AntProjectItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vAntProjectItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setAntProjectItem(
            final int index,
            final org.dbdoclet.antol.ant.AntProjectItem vAntProjectItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setAntProjectItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vAntProjectItem);
    }

    /**
     * 
     * 
     * @param vAntProjectItemArray
     */
    public void setAntProjectItem(
            final org.dbdoclet.antol.ant.AntProjectItem[] vAntProjectItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vAntProjectItemArray.length; i++) {
                this._items.add(vAntProjectItemArray[i]);
        }
    }

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
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
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.AntProject
     */
    public static org.dbdoclet.antol.ant.AntProject unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.AntProject) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.AntProject.class, reader);
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
