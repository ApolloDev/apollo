
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationInfectionAndImmunityCensusDataCell complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationInfectionAndImmunityCensusDataCell">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v2_0/}AgeRange" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://types.apollo.pitt.edu/v2_0/}Gender" minOccurs="0"/>
 *         &lt;element name="infectionState" type="{http://types.apollo.pitt.edu/v2_0/}InfectionState"/>
 *         &lt;element name="fractionInInfectionState" type="{http://types.apollo.pitt.edu/v2_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfectionAndImmunityCensusDataCell", propOrder = {
    "ageRange",
    "gender",
    "infectionState",
    "fractionInInfectionState"
})
public class PopulationInfectionAndImmunityCensusDataCell {

    protected AgeRange ageRange;
    protected Gender gender;
    @XmlElement(required = true)
    protected InfectionState infectionState;
    protected double fractionInInfectionState;

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
     * Gets the value of the infectionState property.
     * 
     * @return
     *     possible object is
     *     {@link InfectionState }
     *     
     */
    public InfectionState getInfectionState() {
        return infectionState;
    }

    /**
     * Sets the value of the infectionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfectionState }
     *     
     */
    public void setInfectionState(InfectionState value) {
        this.infectionState = value;
    }

    /**
     * Gets the value of the fractionInInfectionState property.
     * 
     */
    public double getFractionInInfectionState() {
        return fractionInInfectionState;
    }

    /**
     * Sets the value of the fractionInInfectionState property.
     * 
     */
    public void setFractionInInfectionState(double value) {
        this.fractionInInfectionState = value;
    }

}
