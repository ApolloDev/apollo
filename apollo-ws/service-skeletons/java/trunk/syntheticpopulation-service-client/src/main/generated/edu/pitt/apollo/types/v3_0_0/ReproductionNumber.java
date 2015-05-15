
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReproductionNumber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReproductionNumber">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="exactValue" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *           &lt;element name="uncertainValue" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution"/>
 *         &lt;/choice>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReproductionNumber", propOrder = {
    "exactValue",
    "uncertainValue",
    "referenceId"
})
public class ReproductionNumber {

    protected Double exactValue;
    protected ProbabilityDistribution uncertainValue;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the exactValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getExactValue() {
        return exactValue;
    }

    /**
     * Sets the value of the exactValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setExactValue(Double value) {
        this.exactValue = value;
    }

    /**
     * Gets the value of the uncertainValue property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public ProbabilityDistribution getUncertainValue() {
        return uncertainValue;
    }

    /**
     * Sets the value of the uncertainValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public void setUncertainValue(ProbabilityDistribution value) {
        this.uncertainValue = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReferenceId(BigInteger value) {
        this.referenceId = value;
    }

}
