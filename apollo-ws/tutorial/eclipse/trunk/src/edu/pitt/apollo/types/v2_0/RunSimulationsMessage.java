
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunSimulationsMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunSimulationsMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="batchConfigurationFile" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="acceptCachedResults" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="softwareIdentification" type="{http://types.apollo.pitt.edu/v2_0/}SoftwareIdentification"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunSimulationsMessage", propOrder = {
    "batchConfigurationFile",
    "acceptCachedResults",
    "softwareIdentification"
})
public class RunSimulationsMessage {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String batchConfigurationFile;
    protected boolean acceptCachedResults;
    @XmlElement(required = true)
    protected SoftwareIdentification softwareIdentification;

    /**
     * Gets the value of the batchConfigurationFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchConfigurationFile() {
        return batchConfigurationFile;
    }

    /**
     * Sets the value of the batchConfigurationFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchConfigurationFile(String value) {
        this.batchConfigurationFile = value;
    }

    /**
     * Gets the value of the acceptCachedResults property.
     * 
     */
    public boolean isAcceptCachedResults() {
        return acceptCachedResults;
    }

    /**
     * Sets the value of the acceptCachedResults property.
     * 
     */
    public void setAcceptCachedResults(boolean value) {
        this.acceptCachedResults = value;
    }

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

}
