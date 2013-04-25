/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.dbdoclet.antol.ant;

/**
 * Class Fop.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Fop implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _format.
     */
    private java.lang.String _format;

    /**
     * Field _basedir.
     */
    private java.lang.String _basedir;

    /**
     * Field _fofile.
     */
    private java.lang.String _fofile;

    /**
     * Field _outfile.
     */
    private java.lang.String _outfile;


      //----------------/
     //- Constructors -/
    //----------------/

    public Fop() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'basedir'.
     * 
     * @return the value of field 'Basedir'.
     */
    public java.lang.String getBasedir(
    ) {
        return this._basedir;
    }

    /**
     * Returns the value of field 'fofile'.
     * 
     * @return the value of field 'Fofile'.
     */
    public java.lang.String getFofile(
    ) {
        return this._fofile;
    }

    /**
     * Returns the value of field 'format'.
     * 
     * @return the value of field 'Format'.
     */
    public java.lang.String getFormat(
    ) {
        return this._format;
    }

    /**
     * Returns the value of field 'outfile'.
     * 
     * @return the value of field 'Outfile'.
     */
    public java.lang.String getOutfile(
    ) {
        return this._outfile;
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
     * Sets the value of field 'basedir'.
     * 
     * @param basedir the value of field 'basedir'.
     */
    public void setBasedir(
            final java.lang.String basedir) {
        this._basedir = basedir;
    }

    /**
     * Sets the value of field 'fofile'.
     * 
     * @param fofile the value of field 'fofile'.
     */
    public void setFofile(
            final java.lang.String fofile) {
        this._fofile = fofile;
    }

    /**
     * Sets the value of field 'format'.
     * 
     * @param format the value of field 'format'.
     */
    public void setFormat(
            final java.lang.String format) {
        this._format = format;
    }

    /**
     * Sets the value of field 'outfile'.
     * 
     * @param outfile the value of field 'outfile'.
     */
    public void setOutfile(
            final java.lang.String outfile) {
        this._outfile = outfile;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.dbdoclet.antol.ant.Fop
     */
    public static org.dbdoclet.antol.ant.Fop unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.dbdoclet.antol.ant.Fop) org.exolab.castor.xml.Unmarshaller.unmarshal(org.dbdoclet.antol.ant.Fop.class, reader);
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
