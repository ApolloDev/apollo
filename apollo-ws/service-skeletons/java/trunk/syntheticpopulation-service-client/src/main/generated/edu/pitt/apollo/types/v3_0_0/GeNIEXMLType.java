
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeNIE_XMLType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeNIE_XMLType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="theXML" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeNIE_XMLType", propOrder = {
    "theXML"
})
public class GeNIEXMLType {

    @XmlElement(required = true)
    protected String theXML;

    /**
     * Gets the value of the theXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTheXML() {
        return theXML;
    }

    /**
     * Sets the value of the theXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTheXML(String value) {
        this.theXML = value;
    }

}
