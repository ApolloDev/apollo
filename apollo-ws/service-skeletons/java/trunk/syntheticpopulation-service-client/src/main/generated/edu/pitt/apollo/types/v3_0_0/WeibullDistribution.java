
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeibullDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WeibullDistribution">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ContinuousParametricProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="k-shape" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="lambda-scale" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="shiftRight" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *         &lt;element name="cutTailAt" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeibullDistribution", propOrder = {
    "kShape",
    "lambdaScale",
    "shiftRight",
    "cutTailAt"
})
public class WeibullDistribution
    extends ContinuousParametricProbabilityDistribution
{

    @XmlElement(name = "k-shape")
    protected double kShape;
    @XmlElement(name = "lambda-scale")
    protected double lambdaScale;
    protected double shiftRight;
    protected double cutTailAt;

    /**
     * Gets the value of the kShape property.
     * 
     */
    public double getKShape() {
        return kShape;
    }

    /**
     * Sets the value of the kShape property.
     * 
     */
    public void setKShape(double value) {
        this.kShape = value;
    }

    /**
     * Gets the value of the lambdaScale property.
     * 
     */
    public double getLambdaScale() {
        return lambdaScale;
    }

    /**
     * Sets the value of the lambdaScale property.
     * 
     */
    public void setLambdaScale(double value) {
        this.lambdaScale = value;
    }

    /**
     * Gets the value of the shiftRight property.
     * 
     */
    public double getShiftRight() {
        return shiftRight;
    }

    /**
     * Sets the value of the shiftRight property.
     * 
     */
    public void setShiftRight(double value) {
        this.shiftRight = value;
    }

    /**
     * Gets the value of the cutTailAt property.
     * 
     */
    public double getCutTailAt() {
        return cutTailAt;
    }

    /**
     * Sets the value of the cutTailAt property.
     * 
     */
    public void setCutTailAt(double value) {
        this.cutTailAt = value;
    }

}
