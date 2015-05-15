
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DrugTreatmentEfficacyForSimulatorConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DrugTreatmentEfficacyForSimulatorConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="averageDrugEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="drugEfficacyConditionedOnAgeRange" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityDistribution" minOccurs="0"/>
 *         &lt;element name="drugEfficaciesConditionedOnCurrentDiseaseOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityDistribution" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugTreatmentEfficacyForSimulatorConfiguration", propOrder = {
    "averageDrugEfficacy",
    "drugEfficacyConditionedOnAgeRange",
    "drugEfficaciesConditionedOnCurrentDiseaseOutcome"
})
public class DrugTreatmentEfficacyForSimulatorConfiguration
    extends TreatmentEfficacy
{

    protected double averageDrugEfficacy;
    protected ConditionalProbabilityDistribution drugEfficacyConditionedOnAgeRange;
    protected ConditionalProbabilityDistribution drugEfficaciesConditionedOnCurrentDiseaseOutcome;

    /**
     * Gets the value of the averageDrugEfficacy property.
     * 
     */
    public double getAverageDrugEfficacy() {
        return averageDrugEfficacy;
    }

    /**
     * Sets the value of the averageDrugEfficacy property.
     * 
     */
    public void setAverageDrugEfficacy(double value) {
        this.averageDrugEfficacy = value;
    }

    /**
     * Gets the value of the drugEfficacyConditionedOnAgeRange property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public ConditionalProbabilityDistribution getDrugEfficacyConditionedOnAgeRange() {
        return drugEfficacyConditionedOnAgeRange;
    }

    /**
     * Sets the value of the drugEfficacyConditionedOnAgeRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public void setDrugEfficacyConditionedOnAgeRange(ConditionalProbabilityDistribution value) {
        this.drugEfficacyConditionedOnAgeRange = value;
    }

    /**
     * Gets the value of the drugEfficaciesConditionedOnCurrentDiseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public ConditionalProbabilityDistribution getDrugEfficaciesConditionedOnCurrentDiseaseOutcome() {
        return drugEfficaciesConditionedOnCurrentDiseaseOutcome;
    }

    /**
     * Sets the value of the drugEfficaciesConditionedOnCurrentDiseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityDistribution }
     *     
     */
    public void setDrugEfficaciesConditionedOnCurrentDiseaseOutcome(ConditionalProbabilityDistribution value) {
        this.drugEfficaciesConditionedOnCurrentDiseaseOutcome = value;
    }

}
