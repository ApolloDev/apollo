
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClaraDensityDependentMortalityFunction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClaraDensityDependentMortalityFunction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}MortalityFunction">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="omega" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *         &lt;element name="sigma" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClaraDensityDependentMortalityFunction", propOrder = {
    "description",
    "omega",
    "sigma"
})
public class ClaraDensityDependentMortalityFunction
    extends MortalityFunction
{

    protected String description;
    protected double omega;
    protected double sigma;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the omega property.
     * 
     */
    public double getOmega() {
        return omega;
    }

    /**
     * Sets the value of the omega property.
     * 
     */
    public void setOmega(double value) {
        this.omega = value;
    }

    /**
     * Gets the value of the sigma property.
     * 
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Sets the value of the sigma property.
     * 
     */
    public void setSigma(double value) {
        this.sigma = value;
    }

}
