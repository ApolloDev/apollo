
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Location complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="locationCode" type="{http://types.apollo.pitt.edu/v2_0/}ApolloLocationCode"/>
 *           &lt;element name="locationDefinition" type="{http://types.apollo.pitt.edu/v2_0/}LocationDefinition"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location", propOrder = {
    "locationCode",
    "locationDefinition"
})
public class Location {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String locationCode;
    protected LocationDefinition locationDefinition;

    /**
     * Gets the value of the locationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * Sets the value of the locationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationCode(String value) {
        this.locationCode = value;
    }

    /**
     * Gets the value of the locationDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link LocationDefinition }
     *     
     */
    public LocationDefinition getLocationDefinition() {
        return locationDefinition;
    }

    /**
     * Sets the value of the locationDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationDefinition }
     *     
     */
    public void setLocationDefinition(LocationDefinition value) {
        this.locationDefinition = value;
    }

}
