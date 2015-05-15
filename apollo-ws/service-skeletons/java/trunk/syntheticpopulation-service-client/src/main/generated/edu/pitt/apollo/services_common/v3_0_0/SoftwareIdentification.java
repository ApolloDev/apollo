
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SoftwareIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoftwareIdentification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="softwareDeveloper" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="softwareName" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="softwareVersion" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="softwareType" type="{http://services-common.apollo.pitt.edu/v3_0_0/}ApolloSoftwareTypeEnum"/>
 *         &lt;element name="softwareLicenseIdentification" type="{http://services-common.apollo.pitt.edu/v3_0_0/}SoftwareLicenseIdentification" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoftwareIdentification", propOrder = {
    "softwareDeveloper",
    "softwareName",
    "softwareVersion",
    "softwareType",
    "softwareLicenseIdentification"
})
public class SoftwareIdentification {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String softwareDeveloper;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String softwareName;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String softwareVersion;
    @XmlElement(required = true)
    protected ApolloSoftwareTypeEnum softwareType;
    protected SoftwareLicenseIdentification softwareLicenseIdentification;

    /**
     * Gets the value of the softwareDeveloper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareDeveloper() {
        return softwareDeveloper;
    }

    /**
     * Sets the value of the softwareDeveloper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareDeveloper(String value) {
        this.softwareDeveloper = value;
    }

    /**
     * Gets the value of the softwareName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareName() {
        return softwareName;
    }

    /**
     * Sets the value of the softwareName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareName(String value) {
        this.softwareName = value;
    }

    /**
     * Gets the value of the softwareVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Sets the value of the softwareVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareVersion(String value) {
        this.softwareVersion = value;
    }

    /**
     * Gets the value of the softwareType property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloSoftwareTypeEnum }
     *     
     */
    public ApolloSoftwareTypeEnum getSoftwareType() {
        return softwareType;
    }

    /**
     * Sets the value of the softwareType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloSoftwareTypeEnum }
     *     
     */
    public void setSoftwareType(ApolloSoftwareTypeEnum value) {
        this.softwareType = value;
    }

    /**
     * Gets the value of the softwareLicenseIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareLicenseIdentification }
     *     
     */
    public SoftwareLicenseIdentification getSoftwareLicenseIdentification() {
        return softwareLicenseIdentification;
    }

    /**
     * Sets the value of the softwareLicenseIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareLicenseIdentification }
     *     
     */
    public void setSoftwareLicenseIdentification(SoftwareLicenseIdentification value) {
        this.softwareLicenseIdentification = value;
    }

}
