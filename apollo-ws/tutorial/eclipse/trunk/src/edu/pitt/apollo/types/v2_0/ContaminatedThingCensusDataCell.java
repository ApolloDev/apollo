
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContaminatedThingCensusDataCell complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContaminatedThingCensusDataCell">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v2_0/}AgeRange" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://types.apollo.pitt.edu/v2_0/}Gender" minOccurs="0"/>
 *         &lt;element name="fractionContaminated" type="{http://types.apollo.pitt.edu/v2_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContaminatedThingCensusDataCell", propOrder = {
    "ageRange",
    "gender",
    "fractionContaminated"
})
public class ContaminatedThingCensusDataCell {

    protected AgeRange ageRange;
    protected Gender gender;
    protected double fractionContaminated;

    /**
     * Gets the value of the ageRange property.
     * 
     * @return
     *     possible object is
     *     {@link AgeRange }
     *     
     */
    public AgeRange getAgeRange() {
        return ageRange;
    }

    /**
     * Sets the value of the ageRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgeRange }
     *     
     */
    public void setAgeRange(AgeRange value) {
        this.ageRange = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link Gender }
     *     
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gender }
     *     
     */
    public void setGender(Gender value) {
        this.gender = value;
    }

    /**
     * Gets the value of the fractionContaminated property.
     * 
     */
    public double getFractionContaminated() {
        return fractionContaminated;
    }

    /**
     * Sets the value of the fractionContaminated property.
     * 
     */
    public void setFractionContaminated(double value) {
        this.fractionContaminated = value;
    }

}
