
package edu.pitt.apollo.types.v3_0_0;

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
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="firstConditioningVariable" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditioningVariable"/>
 *       &lt;/sequence>
 *     &lt;/extension>
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
public class ConditionalProbabilityDistribution
    extends ProbabilityDistribution
{

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
