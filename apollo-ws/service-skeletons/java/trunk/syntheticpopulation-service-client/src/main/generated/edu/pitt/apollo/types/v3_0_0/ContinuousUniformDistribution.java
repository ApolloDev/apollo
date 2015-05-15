
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContinuousUniformDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContinuousUniformDistribution">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ContinuousParametricProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="minimumValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="maximumValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContinuousUniformDistribution", propOrder = {
    "minimumValue",
    "maximumValue"
})
public class ContinuousUniformDistribution
    extends ContinuousParametricProbabilityDistribution
{

    protected double minimumValue;
    protected double maximumValue;

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

}
