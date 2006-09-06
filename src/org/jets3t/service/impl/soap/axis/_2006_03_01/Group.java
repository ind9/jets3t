/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9.1</a>, using an XML
 * Schema.
 * $Id$
 */

package org.jets3t.service.impl.soap.axis._2006_03_01;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class Group.
 * 
 * @version $Revision$ $Date$
 */
public class Group extends org.jets3t.service.impl.soap.axis._2006_03_01.Grantee 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _URI
     */
    private java.lang.String _URI;


      //----------------/
     //- Constructors -/
    //----------------/

    public Group() 
     {
        super();
    } //-- org.jets3t.service.soap._2006_03_01.Group()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'URI'.
     * 
     * @return String
     * @return the value of field 'URI'.
     */
    public java.lang.String getURI()
    {
        return this._URI;
    } //-- java.lang.String getURI() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'URI'.
     * 
     * @param URI the value of field 'URI'.
     */
    public void setURI(java.lang.String URI)
    {
        this._URI = URI;
    } //-- void setURI(java.lang.String) 

    /**
     * Method unmarshalGroup
     * 
     * 
     * 
     * @param reader
     * @return Grantee
     */
    public static org.jets3t.service.impl.soap.axis._2006_03_01.Grantee unmarshalGroup(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.jets3t.service.impl.soap.axis._2006_03_01.Grantee) Unmarshaller.unmarshal(org.jets3t.service.impl.soap.axis._2006_03_01.Group.class, reader);
    } //-- org.jets3t.service.soap._2006_03_01.Grantee unmarshalGroup(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}