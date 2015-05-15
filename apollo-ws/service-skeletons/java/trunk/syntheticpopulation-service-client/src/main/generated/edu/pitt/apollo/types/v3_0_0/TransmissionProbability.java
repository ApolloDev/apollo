
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TransmissionProbability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmissionProbability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contactDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}ContactDefinitionEnum"/>
 *         &lt;element name="contactDefinitionText" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="probability" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransmissionProbability", propOrder = {
    "contactDefinition",
    "contactDefinitionText",
    "probability"
})
public class TransmissionProbability {

    @XmlElement(required = true)
    protected ContactDefinitionEnum contactDefinition;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String contactDefinitionText;
    protected double probability;

    /**
     * Gets the value of the contactDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link ContactDefinitionEnum }
     *     
     */
    public ContactDefinitionEnum getContactDefinition() {
        return contactDefinition;
    }

    /**
     * Sets the value of the contactDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactDefinitionEnum }
     *     
     */
    public void setContactDefinition(ContactDefinitionEnum value) {
        this.contactDefinition = value;
    }

    /**
     * Gets the value of the contactDefinitionText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactDefinitionText() {
        return contactDefinitionText;
    }

    /**
     * Sets the value of the contactDefinitionText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactDefinitionText(String value) {
        this.contactDefinitionText = value;
    }

    /**
     * Gets the value of the probability property.
     * 
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     * 
     */
    public void setProbability(double value) {
        this.probability = value;
    }

}
