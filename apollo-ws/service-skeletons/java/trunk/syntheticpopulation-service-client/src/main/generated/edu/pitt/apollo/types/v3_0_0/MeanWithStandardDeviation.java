
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeanWithStandardDeviation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeanWithStandardDeviation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="mean" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="standardDeviation" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
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
@XmlType(name = "MeanWithStandardDeviation", propOrder = {
    "mean",
    "standardDeviation",
    "sampleSize"
})
public class MeanWithStandardDeviation
    extends ProbabilityDistribution
{

    protected double mean;
    protected double standardDeviation;
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
     * Gets the value of the standardDeviation property.
     * 
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }

    /**
     * Sets the value of the standardDeviation property.
     * 
     */
    public void setStandardDeviation(double value) {
        this.standardDeviation = value;
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
