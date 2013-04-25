/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Move.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Move implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _file.
     */
    private java.lang.String _file;

    /**
     * Field _todir.
     */
    private java.lang.String _todir;

    /**
     * Field _tofile.
     */
    private java.lang.String _tofile;


      //----------------/
     //- Constructors -/
    //----------------/

    public Move() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'file'.
     * 
     * @return the value of field 'File'.
     */
    public java.lang.String getFile(
    ) {
        return this._file;
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
     * Returns the value of field 'tofile'.
     * 
     * @return the value of field 'Tofile'.
     */
    public java.lang.String getTofile(
    ) {
        return this._tofile;
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
     * Sets the value of field 'file'.
     * 
     * @param file the value of field 'file'.
     */
    public void setFile(
            final java.lang.String file) {
        this._file = file;
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
     * Sets the value of field 'tofile'.
     * 
     * @param tofile the value of field 'tofile'.
     */
    public void setTofile(
            final java.lang.String tofile) {
        this._tofile = tofile;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.Move
     */
    public static org.dbdoclet.antol.ant.Move unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Move) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Move.class, reader);
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
