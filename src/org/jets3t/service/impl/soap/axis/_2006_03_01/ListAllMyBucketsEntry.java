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
import java.util.Date;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class ListAllMyBucketsEntry.
 * 
 * @version $Revision$ $Date$
 */
public class ListAllMyBucketsEntry implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _creationDate
     */
    private java.util.Date _creationDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public ListAllMyBucketsEntry() 
     {
        super();
    } //-- org.jets3t.service.soap._2006_03_01.ListAllMyBucketsEntry()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'creationDate'.
     * 
     * @return Date
     * @return the value of field 'creationDate'.
     */
    public java.util.Date getCreationDate()
    {
        return this._creationDate;
    } //-- java.util.Date getCreationDate() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

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
     * Sets the value of field 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.util.Date creationDate)
    {
        this._creationDate = creationDate;
    } //-- void setCreationDate(java.util.Date) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Method unmarshalListAllMyBucketsEntry
     * 
     * 
     * 
     * @param reader
     * @return ListAllMyBucketsEntry
     */
    public static org.jets3t.service.impl.soap.axis._2006_03_01.ListAllMyBucketsEntry unmarshalListAllMyBucketsEntry(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.jets3t.service.impl.soap.axis._2006_03_01.ListAllMyBucketsEntry) Unmarshaller.unmarshal(org.jets3t.service.impl.soap.axis._2006_03_01.ListAllMyBucketsEntry.class, reader);
    } //-- org.jets3t.service.soap._2006_03_01.ListAllMyBucketsEntry unmarshalListAllMyBucketsEntry(java.io.Reader) 

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