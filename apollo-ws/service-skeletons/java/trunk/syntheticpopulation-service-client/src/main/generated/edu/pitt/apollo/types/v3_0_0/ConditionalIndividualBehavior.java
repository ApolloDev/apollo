
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditionalIndividualBehavior complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionalIndividualBehavior">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="behavior" type="{http://types.apollo.pitt.edu/v3_0_0/}BehaviorEnum"/>
 *         &lt;element name="conditionalProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityDistribution"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConditionalIndividualBehavior", propOrder = {
    "behavior",
    "conditionalProbability"
})
public class ConditionalIndividualBehavior {

    @XmlElement(required = true)
    protected BehaviorEnum behavior;
    @XmlElement(required = true)
    protected ConditionalProbabilityDistribution conditionalProbability;

    /**
     * Gets the value of the behavior property.
     * 
     * @return
     *     possible object is
     *     {@link BehaviorEnum }
     *     
     */
    public BehaviorEnum getBehavior() {
        return behavior;
    }

    /**
     * Sets the value of the behavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link BehaviorEnum }
     *     
     */
    public void setBehavior(BehaviorEnum value) {
        this.behavior = value;
    }

    /**
     * Gets the value of the conditionalProbability property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public ConditionalProbabilityDistribution getConditionalProbability() {
        return conditionalProbability;
    }

    /**
     * Sets the value of the conditionalProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public void setConditionalProbability(ConditionalProbabilityDistribution value) {
        this.conditionalProbability = value;
    }

}
