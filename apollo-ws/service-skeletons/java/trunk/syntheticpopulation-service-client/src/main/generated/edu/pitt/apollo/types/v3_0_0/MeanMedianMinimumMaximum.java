
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeanMedianMinimumMaximum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeanMedianMinimumMaximum">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="mean" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="median" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="minimumValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="maximumValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "MeanMedianMinimumMaximum", propOrder = {
    "mean",
    "median",
    "minimumValue",
    "maximumValue",
    "sampleSize"
})
public class MeanMedianMinimumMaximum
    extends ProbabilityDistribution
{

    protected Double mean;
    protected Double median;
    protected double minimumValue;
    protected double maximumValue;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sampleSize;

    /**
     * Gets the value of the mean property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMean() {
        return mean;
    }

    /**
     * Sets the value of the mean property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMean(Double value) {
        this.mean = value;
    }

    /**
     * Gets the value of the median property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMedian() {
        return median;
    }

    /**
     * Sets the value of the median property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMedian(Double value) {
        this.median = value;
    }

    /**
     * Gets the value of the minimumValue property.
     * 
     */
    public double getMinimumValue() {
        return minimumValue;
    }

    /**
     * Sets the value of the minimumValue property.
     * 
     */
    public void setMinimumValue(double value) {
        this.minimumValue = value;
    }

    /**
     * Gets the value of the maximumValue property.
     * 
     */
    public double getMaximumValue() {
        return maximumValue;
    }

    /**
     * Sets the value of the maximumValue property.
     * 
     */
    public void setMaximumValue(double value) {
        this.maximumValue = value;
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
