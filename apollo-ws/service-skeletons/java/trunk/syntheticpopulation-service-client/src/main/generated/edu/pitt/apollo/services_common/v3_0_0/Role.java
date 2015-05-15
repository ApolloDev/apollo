
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Role complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Role">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="softwareIdentification" type="{http://services-common.apollo.pitt.edu/v3_0_0/}SoftwareIdentification"/>
 *         &lt;element name="can_run" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="can_view_cached_results" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Role", propOrder = {
    "softwareIdentification",
    "canRun",
    "canViewCachedResults"
})
public class Role {

    @XmlElement(required = true)
    protected SoftwareIdentification softwareIdentification;
    @XmlElement(name = "can_run")
    protected boolean canRun;
    @XmlElement(name = "can_view_cached_results")
    protected boolean canViewCachedResults;

    /**
     * Gets the value of the softwareIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareIdentification }
     *     
     */
    public SoftwareIdentification getSoftwareIdentification() {
        return softwareIdentification;
    }

    /**
     * Sets the value of the softwareIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareIdentification }
     *     
     */
    public void setSoftwareIdentification(SoftwareIdentification value) {
        this.softwareIdentification = value;
    }

    /**
     * Gets the value of the canRun property.
     * 
     */
    public boolean isCanRun() {
        return canRun;
    }

    /**
     * Sets the value of the canRun property.
     * 
     */
    public void setCanRun(boolean value) {
        this.canRun = value;
    }

    /**
     * Gets the value of the canViewCachedResults property.
     * 
     */
    public boolean isCanViewCachedResults() {
        return canViewCachedResults;
    }

    /**
     * Sets the value of the canViewCachedResults property.
     * 
     */
    public void setCanViewCachedResults(boolean value) {
        this.canViewCachedResults = value;
    }

}
