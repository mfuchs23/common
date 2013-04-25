/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Packageset.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Packageset implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _dir.
     */
    private java.lang.String _dir;

    /**
     * Field _casesensitive.
     */
    private boolean _casesensitive;

    /**
     * keeps track of state for field: _casesensitive
     */
    private boolean _has_casesensitive;

    /**
     * Field _items.
     */
    private java.util.List<org.dbdoclet.antol.ant.PackagesetItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public Packageset() {
        super();
        this._items = new java.util.ArrayList<org.dbdoclet.antol.ant.PackagesetItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPackagesetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPackagesetItem(
            final org.dbdoclet.antol.ant.PackagesetItem vPackagesetItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(vPackagesetItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vPackagesetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPackagesetItem(
            final int index,
            final org.dbdoclet.antol.ant.PackagesetItem vPackagesetItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vPackagesetItem);
    }

    /**
     */
    public void deleteCasesensitive(
    ) {
        this._has_casesensitive= false;
    }

    /**
     * Method enumeratePackagesetItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.dbdoclet.antol.ant.PackagesetItem> enumeratePackagesetItem(
    ) {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Returns the value of field 'casesensitive'.
     * 
     * @return the value of field 'Casesensitive'.
     */
    public boolean getCasesensitive(
    ) {
        return this._casesensitive;
    }

    /**
     * Returns the value of field 'dir'.
     * 
     * @return the value of field 'Dir'.
     */
    public java.lang.String getDir(
    ) {
        return this._dir;
    }

    /**
     * Method getPackagesetItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * org.dbdoclet.antol.ant.PackagesetItem at the given index
     */
    public org.dbdoclet.antol.ant.PackagesetItem getPackagesetItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getPackagesetItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (org.dbdoclet.antol.ant.PackagesetItem) _items.get(index);
    }

    /**
     * Method getPackagesetItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.dbdoclet.antol.ant.PackagesetItem[] getPackagesetItem(
    ) {
        org.dbdoclet.antol.ant.PackagesetItem[] array = new org.dbdoclet.antol.ant.PackagesetItem[0];
        return (org.dbdoclet.antol.ant.PackagesetItem[]) this._items.toArray(array);
    }

    /**
     * Method getPackagesetItemCount.
     * 
     * @return the size of this collection
     */
    public int getPackagesetItemCount(
    ) {
        return this._items.size();
    }

    /**
     * Method hasCasesensitive.
     * 
     * @return true if at least one Casesensitive has been added
     */
    public boolean hasCasesensitive(
    ) {
        return this._has_casesensitive;
    }

    /**
     * Returns the value of field 'casesensitive'.
     * 
     * @return the value of field 'Casesensitive'.
     */
    public boolean isCasesensitive(
    ) {
        return this._casesensitive;
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
     * Method iteratePackagesetItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.dbdoclet.antol.ant.PackagesetItem> iteratePackagesetItem(
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
    public void removeAllPackagesetItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removePackagesetItem.
     * 
     * @param vPackagesetItem
     * @return true if the object was removed from the collection.
     */
    public boolean removePackagesetItem(
            final org.dbdoclet.antol.ant.PackagesetItem vPackagesetItem) {
        boolean removed = _items.remove(vPackagesetItem);
        return removed;
    }

    /**
     * Method removePackagesetItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.dbdoclet.antol.ant.PackagesetItem removePackagesetItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (org.dbdoclet.antol.ant.PackagesetItem) obj;
    }

    /**
     * Sets the value of field 'casesensitive'.
     * 
     * @param casesensitive the value of field 'casesensitive'.
     */
    public void setCasesensitive(
            final boolean casesensitive) {
        this._casesensitive = casesensitive;
        this._has_casesensitive = true;
    }

    /**
     * Sets the value of field 'dir'.
     * 
     * @param dir the value of field 'dir'.
     */
    public void setDir(
            final java.lang.String dir) {
        this._dir = dir;
    }

    /**
     * 
     * 
     * @param index
     * @param vPackagesetItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPackagesetItem(
            final int index,
            final org.dbdoclet.antol.ant.PackagesetItem vPackagesetItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setPackagesetItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vPackagesetItem);
    }

    /**
     * 
     * 
     * @param vPackagesetItemArray
     */
    public void setPackagesetItem(
            final org.dbdoclet.antol.ant.PackagesetItem[] vPackagesetItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vPackagesetItemArray.length; i++) {
                this._items.add(vPackagesetItemArray[i]);
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
     * @return the unmarshaled org.dbdoclet.antol.ant.Packageset
     */
    public static org.dbdoclet.antol.ant.Packageset unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Packageset) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Packageset.class, reader);
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
