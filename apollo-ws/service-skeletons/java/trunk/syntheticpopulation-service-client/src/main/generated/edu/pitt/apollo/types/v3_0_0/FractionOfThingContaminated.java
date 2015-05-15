
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FractionOfThingContaminated complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FractionOfThingContaminated">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abioticEcosystemElement" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="fraction" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FractionOfThingContaminated", propOrder = {
    "abioticEcosystemElement",
    "fraction"
})
public class FractionOfThingContaminated {

    @XmlElement(required = true)
    protected AbioticEcosystemEnum abioticEcosystemElement;
    protected double fraction;

    /**
     * Gets the value of the abioticEcosystemElement property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getAbioticEcosystemElement() {
        return abioticEcosystemElement;
    }

    /**
     * Sets the value of the abioticEcosystemElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setAbioticEcosystemElement(AbioticEcosystemEnum value) {
        this.abioticEcosystemElement = value;
    }

    /**
     * Gets the value of the fraction property.
     * 
     */
    public double getFraction() {
        return fraction;
    }

    /**
     * Sets the value of the fraction property.
     * 
     */
    public void setFraction(double value) {
        this.fraction = value;
    }

}
