
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationTreatmentCensusDataCell complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationTreatmentCensusDataCell">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v3_0_0/}AgeRangeCategoryDefinition" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://types.apollo.pitt.edu/v3_0_0/}GenderEnum" minOccurs="0"/>
 *         &lt;element name="treatmentState" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentStateEnum"/>
 *         &lt;element name="fractionInTreatmentState" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationTreatmentCensusDataCell", propOrder = {
    "ageRange",
    "gender",
    "treatmentState",
    "fractionInTreatmentState"
})
public class PopulationTreatmentCensusDataCell {

    protected AgeRangeCategoryDefinition ageRange;
    protected GenderEnum gender;
    @XmlElement(required = true)
    protected TreatmentStateEnum treatmentState;
    protected double fractionInTreatmentState;

    /**
     * Gets the value of the ageRange property.
     * 
     * @return
     *     possible object is
     *     {@link AgeRangeCategoryDefinition }
     *     
     */
    public AgeRangeCategoryDefinition getAgeRange() {
        return ageRange;
    }

    /**
     * Sets the value of the ageRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgeRangeCategoryDefinition }
     *     
     */
    public void setAgeRange(AgeRangeCategoryDefinition value) {
        this.ageRange = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link GenderEnum }
     *     
     */
    public GenderEnum getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenderEnum }
     *     
     */
    public void setGender(GenderEnum value) {
        this.gender = value;
    }

    /**
     * Gets the value of the treatmentState property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentStateEnum }
     *     
     */
    public TreatmentStateEnum getTreatmentState() {
        return treatmentState;
    }

    /**
     * Sets the value of the treatmentState property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentStateEnum }
     *     
     */
    public void setTreatmentState(TreatmentStateEnum value) {
        this.treatmentState = value;
    }

    /**
     * Gets the value of the fractionInTreatmentState property.
     * 
     */
    public double getFractionInTreatmentState() {
        return fractionInTreatmentState;
    }

    /**
     * Sets the value of the fractionInTreatmentState property.
     * 
     */
    public void setFractionInTreatmentState(double value) {
        this.fractionInTreatmentState = value;
    }

}
