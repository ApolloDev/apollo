
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SoftwareLicenseIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoftwareLicenseIdentification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licenseLocation" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="licenseVersion" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="licenseName" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="attributionNotice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoftwareLicenseIdentification", propOrder = {
    "licenseLocation",
    "licenseVersion",
    "licenseName",
    "attributionNotice"
})
public class SoftwareLicenseIdentification {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String licenseLocation;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String licenseVersion;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String licenseName;
    @XmlElement(required = true)
    protected String attributionNotice;

    /**
     * Gets the value of the licenseLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseLocation() {
        return licenseLocation;
    }

    /**
     * Sets the value of the licenseLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseLocation(String value) {
        this.licenseLocation = value;
    }

    /**
     * Gets the value of the licenseVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseVersion() {
        return licenseVersion;
    }

    /**
     * Sets the value of the licenseVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseVersion(String value) {
        this.licenseVersion = value;
    }

    /**
     * Gets the value of the licenseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseName() {
        return licenseName;
    }

    /**
     * Sets the value of the licenseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseName(String value) {
        this.licenseName = value;
    }

    /**
     * Gets the value of the attributionNotice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributionNotice() {
        return attributionNotice;
    }

    /**
     * Sets the value of the attributionNotice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributionNotice(String value) {
        this.attributionNotice = value;
    }

}
