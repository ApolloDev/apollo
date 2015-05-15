
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Ecosystem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ecosystem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bioticEcosystem" type="{http://types.apollo.pitt.edu/v3_0_0/}BioticEcosystem"/>
 *         &lt;element name="abioticEcosystem" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystem"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ecosystem", propOrder = {
    "description",
    "bioticEcosystem",
    "abioticEcosystem"
})
public class Ecosystem {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected BioticEcosystem bioticEcosystem;
    @XmlElement(required = true)
    protected AbioticEcosystem abioticEcosystem;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the bioticEcosystem property.
     * 
     * @return
     *     possible object is
     *     {@link BioticEcosystem }
     *     
     */
    public BioticEcosystem getBioticEcosystem() {
        return bioticEcosystem;
    }

    /**
     * Sets the value of the bioticEcosystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link BioticEcosystem }
     *     
     */
    public void setBioticEcosystem(BioticEcosystem value) {
        this.bioticEcosystem = value;
    }

    /**
     * Gets the value of the abioticEcosystem property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystem }
     *     
     */
    public AbioticEcosystem getAbioticEcosystem() {
        return abioticEcosystem;
    }

    /**
     * Sets the value of the abioticEcosystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystem }
     *     
     */
    public void setAbioticEcosystem(AbioticEcosystem value) {
        this.abioticEcosystem = value;
    }

}
