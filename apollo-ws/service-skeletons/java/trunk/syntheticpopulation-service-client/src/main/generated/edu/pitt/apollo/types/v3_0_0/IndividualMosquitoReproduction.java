
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualMosquitoReproduction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualMosquitoReproduction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gonotrophicPeriodDuration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="eggsPerBrood" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="minimumEggsPerOviposition" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="ageDependentFecundityReduction" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualMosquitoReproduction", propOrder = {
    "gonotrophicPeriodDuration",
    "eggsPerBrood",
    "minimumEggsPerOviposition",
    "ageDependentFecundityReduction"
})
public class IndividualMosquitoReproduction {

    @XmlElement(required = true)
    protected Duration gonotrophicPeriodDuration;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger eggsPerBrood;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger minimumEggsPerOviposition;
    protected Rate ageDependentFecundityReduction;

    /**
     * Gets the value of the gonotrophicPeriodDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getGonotrophicPeriodDuration() {
        return gonotrophicPeriodDuration;
    }

    /**
     * Sets the value of the gonotrophicPeriodDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setGonotrophicPeriodDuration(Duration value) {
        this.gonotrophicPeriodDuration = value;
    }

    /**
     * Gets the value of the eggsPerBrood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEggsPerBrood() {
        return eggsPerBrood;
    }

    /**
     * Sets the value of the eggsPerBrood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEggsPerBrood(BigInteger value) {
        this.eggsPerBrood = value;
    }

    /**
     * Gets the value of the minimumEggsPerOviposition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinimumEggsPerOviposition() {
        return minimumEggsPerOviposition;
    }

    /**
     * Sets the value of the minimumEggsPerOviposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinimumEggsPerOviposition(BigInteger value) {
        this.minimumEggsPerOviposition = value;
    }

    /**
     * Gets the value of the ageDependentFecundityReduction property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getAgeDependentFecundityReduction() {
        return ageDependentFecundityReduction;
    }

    /**
     * Sets the value of the ageDependentFecundityReduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setAgeDependentFecundityReduction(Rate value) {
        this.ageDependentFecundityReduction = value;
    }

}
