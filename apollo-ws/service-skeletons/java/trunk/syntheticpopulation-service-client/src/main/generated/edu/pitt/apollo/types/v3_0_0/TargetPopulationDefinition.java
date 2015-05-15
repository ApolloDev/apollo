
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TargetPopulationDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TargetPopulationDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v3_0_0/}AgeRangeCategoryDefinition" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://types.apollo.pitt.edu/v3_0_0/}GenderEnum" minOccurs="0"/>
 *         &lt;element name="diseaseOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum" minOccurs="0"/>
 *         &lt;element name="otherStratification" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationStratificationEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetPopulationDefinition", propOrder = {
    "ageRange",
    "gender",
    "diseaseOutcome",
    "otherStratification"
})
public class TargetPopulationDefinition {

    protected AgeRangeCategoryDefinition ageRange;
    protected GenderEnum gender;
    protected DiseaseOutcomeEnum diseaseOutcome;
    protected PopulationStratificationEnum otherStratification;

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
     * Gets the value of the diseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getDiseaseOutcome() {
        return diseaseOutcome;
    }

    /**
     * Sets the value of the diseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setDiseaseOutcome(DiseaseOutcomeEnum value) {
        this.diseaseOutcome = value;
    }

    /**
     * Gets the value of the otherStratification property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationStratificationEnum }
     *     
     */
    public PopulationStratificationEnum getOtherStratification() {
        return otherStratification;
    }

    /**
     * Sets the value of the otherStratification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationStratificationEnum }
     *     
     */
    public void setOtherStratification(PopulationStratificationEnum value) {
        this.otherStratification = value;
    }

}
