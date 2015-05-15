
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationEfficacyForSimulatorConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacyForSimulatorConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="averageVaccinationEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="vaccinationEfficacyConditionedOnAgeRange" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityDistribution" minOccurs="0"/>
 *         &lt;element name="vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose" type="{http://types.apollo.pitt.edu/v3_0_0/}VaccinationEfficacyConditionedOnTimeSinceDose" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacyForSimulatorConfiguration", propOrder = {
    "averageVaccinationEfficacy",
    "vaccinationEfficacyConditionedOnAgeRange",
    "vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose"
})
public class VaccinationEfficacyForSimulatorConfiguration
    extends TreatmentEfficacy
{

    protected double averageVaccinationEfficacy;
    protected ConditionalProbabilityDistribution vaccinationEfficacyConditionedOnAgeRange;
    protected VaccinationEfficacyConditionedOnTimeSinceDose vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose;

    /**
     * Gets the value of the averageVaccinationEfficacy property.
     * 
     */
    public double getAverageVaccinationEfficacy() {
        return averageVaccinationEfficacy;
    }

    /**
     * Sets the value of the averageVaccinationEfficacy property.
     * 
     */
    public void setAverageVaccinationEfficacy(double value) {
        this.averageVaccinationEfficacy = value;
    }

    /**
     * Gets the value of the vaccinationEfficacyConditionedOnAgeRange property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public ConditionalProbabilityDistribution getVaccinationEfficacyConditionedOnAgeRange() {
        return vaccinationEfficacyConditionedOnAgeRange;
    }

    /**
     * Sets the value of the vaccinationEfficacyConditionedOnAgeRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public void setVaccinationEfficacyConditionedOnAgeRange(ConditionalProbabilityDistribution value) {
        this.vaccinationEfficacyConditionedOnAgeRange = value;
    }

    /**
     * Gets the value of the vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationEfficacyConditionedOnTimeSinceDose }
     *     
     */
    public VaccinationEfficacyConditionedOnTimeSinceDose getVaccinationEfficaciesConditionedOnTimeSinceMostRecentDose() {
        return vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose;
    }

    /**
     * Sets the value of the vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationEfficacyConditionedOnTimeSinceDose }
     *     
     */
    public void setVaccinationEfficaciesConditionedOnTimeSinceMostRecentDose(VaccinationEfficacyConditionedOnTimeSinceDose value) {
        this.vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose = value;
    }

}
