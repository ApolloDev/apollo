
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContainerReductionControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContainerReductionControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}VectorControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="coverRadius" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *         &lt;element name="fractionReductionOfEggs" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *         &lt;element name="fractionReductionOfLarvae" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContainerReductionControlStrategy", propOrder = {
    "coverRadius",
    "fractionReductionOfEggs",
    "fractionReductionOfLarvae"
})
public class ContainerReductionControlStrategy
    extends VectorControlStrategy
{

    @XmlElement(required = true)
    protected Distance coverRadius;
    protected double fractionReductionOfEggs;
    protected double fractionReductionOfLarvae;

    /**
     * Gets the value of the coverRadius property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getCoverRadius() {
        return coverRadius;
    }

    /**
     * Sets the value of the coverRadius property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setCoverRadius(Distance value) {
        this.coverRadius = value;
    }

    /**
     * Gets the value of the fractionReductionOfEggs property.
     * 
     */
    public double getFractionReductionOfEggs() {
        return fractionReductionOfEggs;
    }

    /**
     * Sets the value of the fractionReductionOfEggs property.
     * 
     */
    public void setFractionReductionOfEggs(double value) {
        this.fractionReductionOfEggs = value;
    }

    /**
     * Gets the value of the fractionReductionOfLarvae property.
     * 
     */
    public double getFractionReductionOfLarvae() {
        return fractionReductionOfLarvae;
    }

    /**
     * Sets the value of the fractionReductionOfLarvae property.
     * 
     */
    public void setFractionReductionOfLarvae(double value) {
        this.fractionReductionOfLarvae = value;
    }

}
