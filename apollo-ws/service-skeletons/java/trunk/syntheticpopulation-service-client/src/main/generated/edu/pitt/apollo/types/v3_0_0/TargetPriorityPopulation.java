
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TargetPriorityPopulation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TargetPriorityPopulation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;choice>
 *           &lt;element name="targetPopulationDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}TargetPopulationDefinition"/>
 *           &lt;element name="targetPopulationEnum" type="{http://types.apollo.pitt.edu/v3_0_0/}TargetPopulationEnum"/>
 *         &lt;/choice>
 *         &lt;element name="fractionOfTargetPopulationToPrioritize" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetPriorityPopulation", propOrder = {
    "description",
    "targetPopulationDefinition",
    "targetPopulationEnum",
    "fractionOfTargetPopulationToPrioritize",
    "priority"
})
public class TargetPriorityPopulation {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String description;
    protected TargetPopulationDefinition targetPopulationDefinition;
    protected TargetPopulationEnum targetPopulationEnum;
    protected double fractionOfTargetPopulationToPrioritize;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger priority;

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
     * Gets the value of the targetPopulationDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link TargetPopulationDefinition }
     *     
     */
    public TargetPopulationDefinition getTargetPopulationDefinition() {
        return targetPopulationDefinition;
    }

    /**
     * Sets the value of the targetPopulationDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetPopulationDefinition }
     *     
     */
    public void setTargetPopulationDefinition(TargetPopulationDefinition value) {
        this.targetPopulationDefinition = value;
    }

    /**
     * Gets the value of the targetPopulationEnum property.
     * 
     * @return
     *     possible object is
     *     {@link TargetPopulationEnum }
     *     
     */
    public TargetPopulationEnum getTargetPopulationEnum() {
        return targetPopulationEnum;
    }

    /**
     * Sets the value of the targetPopulationEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetPopulationEnum }
     *     
     */
    public void setTargetPopulationEnum(TargetPopulationEnum value) {
        this.targetPopulationEnum = value;
    }

    /**
     * Gets the value of the fractionOfTargetPopulationToPrioritize property.
     * 
     */
    public double getFractionOfTargetPopulationToPrioritize() {
        return fractionOfTargetPopulationToPrioritize;
    }

    /**
     * Sets the value of the fractionOfTargetPopulationToPrioritize property.
     * 
     */
    public void setFractionOfTargetPopulationToPrioritize(double value) {
        this.fractionOfTargetPopulationToPrioritize = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPriority(BigInteger value) {
        this.priority = value;
    }

}
