
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeanWithConfidenceInterval complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeanWithConfidenceInterval">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="mean" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lowerBound" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="upperBound" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="confidence" type="{http://types.apollo.pitt.edu/v3_0_0/}Percent"/>
 *         &lt;element name="sampleSize" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeanWithConfidenceInterval", propOrder = {
    "mean",
    "lowerBound",
    "upperBound",
    "confidence",
    "sampleSize"
})
public class MeanWithConfidenceInterval
    extends ProbabilityDistribution
{

    protected double mean;
    protected double lowerBound;
    protected double upperBound;
    @XmlElement(required = true)
    protected BigInteger confidence;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sampleSize;

    /**
     * Gets the value of the mean property.
     * 
     */
    public double getMean() {
        return mean;
    }

    /**
     * Sets the value of the mean property.
     * 
     */
    public void setMean(double value) {
        this.mean = value;
    }

    /**
     * Gets the value of the lowerBound property.
     * 
     */
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     * Sets the value of the lowerBound property.
     * 
     */
    public void setLowerBound(double value) {
        this.lowerBound = value;
    }

    /**
     * Gets the value of the upperBound property.
     * 
     */
    public double getUpperBound() {
        return upperBound;
    }

    /**
     * Sets the value of the upperBound property.
     * 
     */
    public void setUpperBound(double value) {
        this.upperBound = value;
    }

    /**
     * Gets the value of the confidence property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the confidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setConfidence(BigInteger value) {
        this.confidence = value;
    }

    /**
     * Gets the value of the sampleSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSampleSize() {
        return sampleSize;
    }

    /**
     * Sets the value of the sampleSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSampleSize(BigInteger value) {
        this.sampleSize = value;
    }

}
