
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for RequesterIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequesterIdentification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requesterName" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="requesterCompany" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="requesterEmail" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequesterIdentification", propOrder = {
    "requesterName",
    "requesterCompany",
    "requesterEmail"
})
public class RequesterIdentification {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String requesterName;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String requesterCompany;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String requesterEmail;

    /**
     * Gets the value of the requesterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequesterName() {
        return requesterName;
    }

    /**
     * Sets the value of the requesterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequesterName(String value) {
        this.requesterName = value;
    }

    /**
     * Gets the value of the requesterCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequesterCompany() {
        return requesterCompany;
    }

    /**
     * Sets the value of the requesterCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequesterCompany(String value) {
        this.requesterCompany = value;
    }

    /**
     * Gets the value of the requesterEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequesterEmail() {
        return requesterEmail;
    }

    /**
     * Sets the value of the requesterEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequesterEmail(String value) {
        this.requesterEmail = value;
    }

}
