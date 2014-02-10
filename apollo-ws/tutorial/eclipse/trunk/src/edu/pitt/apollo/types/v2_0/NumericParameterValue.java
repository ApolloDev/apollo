
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NumericParameterValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumericParameterValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unitOfMeasure" type="{http://types.apollo.pitt.edu/v2_0/}UnitOfMeasure" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *           &lt;element name="unconditionalProbabilityDistribution" type="{http://types.apollo.pitt.edu/v2_0/}UnconditionalProbabilityDistribution"/>
 *           &lt;element name="conditionalProbabilityDistribution" type="{http://types.apollo.pitt.edu/v2_0/}ConditionalProbabilityDistribution"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumericParameterValue", propOrder = {
    "unitOfMeasure",
    "value",
    "unconditionalProbabilityDistribution",
    "conditionalProbabilityDistribution"
})
public class NumericParameterValue {

    protected UnitOfMeasure unitOfMeasure;
    protected Double value;
    protected UnconditionalProbabilityDistribution unconditionalProbabilityDistribution;
    protected ConditionalProbabilityDistribution conditionalProbabilityDistribution;

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasure }
     *     
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasure }
     *     
     */
    public void setUnitOfMeasure(UnitOfMeasure value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Gets the value of the unconditionalProbabilityDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link UnconditionalProbabilityDistribution }
     *     
     */
    public UnconditionalProbabilityDistribution getUnconditionalProbabilityDistribution() {
        return unconditionalProbabilityDistribution;
    }

    /**
     * Sets the value of the unconditionalProbabilityDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnconditionalProbabilityDistribution }
     *     
     */
    public void setUnconditionalProbabilityDistribution(UnconditionalProbabilityDistribution value) {
        this.unconditionalProbabilityDistribution = value;
    }

    /**
     * Gets the value of the conditionalProbabilityDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public ConditionalProbabilityDistribution getConditionalProbabilityDistribution() {
        return conditionalProbabilityDistribution;
    }

    /**
     * Sets the value of the conditionalProbabilityDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public void setConditionalProbabilityDistribution(ConditionalProbabilityDistribution value) {
        this.conditionalProbabilityDistribution = value;
    }

}
