
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeratorUnitOfMeasure" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfMeasureEnum"/>
 *         &lt;element name="denominatorUnitOfMeasure" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfMeasureEnum"/>
 *         &lt;choice>
 *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *           &lt;element name="probabilityDistribution" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution"/>
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
@XmlType(name = "Rate", propOrder = {
    "numeratorUnitOfMeasure",
    "denominatorUnitOfMeasure",
    "value",
    "probabilityDistribution"
})
public class Rate {

    @XmlElement(required = true)
    protected UnitOfMeasureEnum numeratorUnitOfMeasure;
    @XmlElement(required = true)
    protected UnitOfMeasureEnum denominatorUnitOfMeasure;
    protected Double value;
    protected ProbabilityDistribution probabilityDistribution;

    /**
     * Gets the value of the numeratorUnitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public UnitOfMeasureEnum getNumeratorUnitOfMeasure() {
        return numeratorUnitOfMeasure;
    }

    /**
     * Sets the value of the numeratorUnitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public void setNumeratorUnitOfMeasure(UnitOfMeasureEnum value) {
        this.numeratorUnitOfMeasure = value;
    }

    /**
     * Gets the value of the denominatorUnitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public UnitOfMeasureEnum getDenominatorUnitOfMeasure() {
        return denominatorUnitOfMeasure;
    }

    /**
     * Sets the value of the denominatorUnitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public void setDenominatorUnitOfMeasure(UnitOfMeasureEnum value) {
        this.denominatorUnitOfMeasure = value;
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
     * Gets the value of the probabilityDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public ProbabilityDistribution getProbabilityDistribution() {
        return probabilityDistribution;
    }

    /**
     * Sets the value of the probabilityDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public void setProbabilityDistribution(ProbabilityDistribution value) {
        this.probabilityDistribution = value;
    }

}
