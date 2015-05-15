
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v3_0_0/}AgeRangeCategoryDefinition" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://types.apollo.pitt.edu/v3_0_0/}GenderEnum" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="infectionState" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectionStateEnum"/>
 *           &lt;element name="diseaseState" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum"/>
 *         &lt;/choice>
 *         &lt;element name="fractionInState" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
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
    "diseaseState",
    "fractionInState"
})
public class PopulationInfectionAndImmunityCensusDataCell {

    protected AgeRangeCategoryDefinition ageRange;
    protected GenderEnum gender;
    protected InfectionStateEnum infectionState;
    protected DiseaseOutcomeEnum diseaseState;
    protected double fractionInState;

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
     * Gets the value of the infectionState property.
     * 
     * @return
     *     possible object is
     *     {@link InfectionStateEnum }
     *     
     */
    public InfectionStateEnum getInfectionState() {
        return infectionState;
    }

    /**
     * Sets the value of the infectionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfectionStateEnum }
     *     
     */
    public void setInfectionState(InfectionStateEnum value) {
        this.infectionState = value;
    }

    /**
     * Gets the value of the diseaseState property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getDiseaseState() {
        return diseaseState;
    }

    /**
     * Sets the value of the diseaseState property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setDiseaseState(DiseaseOutcomeEnum value) {
        this.diseaseState = value;
    }

    /**
     * Gets the value of the fractionInState property.
     * 
     */
    public double getFractionInState() {
        return fractionInState;
    }

    /**
     * Sets the value of the fractionInState property.
     * 
     */
    public void setFractionInState(double value) {
        this.fractionInState = value;
    }

}
