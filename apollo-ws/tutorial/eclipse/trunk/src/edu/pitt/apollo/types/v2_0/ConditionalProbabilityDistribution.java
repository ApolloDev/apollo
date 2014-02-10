
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditionalProbabilityDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionalProbabilityDistribution">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstConditioningVariable" type="{http://types.apollo.pitt.edu/v2_0/}ConditioningVariable"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConditionalProbabilityDistribution", propOrder = {
    "firstConditioningVariable"
})
public class ConditionalProbabilityDistribution {

    @XmlElement(required = true)
    protected ConditioningVariable firstConditioningVariable;

    /**
     * Gets the value of the firstConditioningVariable property.
     * 
     * @return
     *     possible object is
     *     {@link ConditioningVariable }
     *     
     */
    public ConditioningVariable getFirstConditioningVariable() {
        return firstConditioningVariable;
    }

    /**
     * Sets the value of the firstConditioningVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditioningVariable }
     *     
     */
    public void setFirstConditioningVariable(ConditioningVariable value) {
        this.firstConditioningVariable = value;
    }

}
