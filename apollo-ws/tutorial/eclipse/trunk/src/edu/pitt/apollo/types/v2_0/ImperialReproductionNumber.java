
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImperialReproductionNumber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImperialReproductionNumber">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="averageCommunityImperialReproductionNumber" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="averageHouseholdImperialReproductionNumber" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="imperialReproductionNumberForPlace" type="{http://types.apollo.pitt.edu/v2_0/}ImperialReproductionNumberForPlace" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="seasonalityFunctionParameters" type="{http://types.apollo.pitt.edu/v2_0/}SeasonalityFunctionParameters"/>
 *         &lt;element name="probSymptomaticInfectionClinicallyDetected" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="symptomaticInfectionMultiplier" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="probSevereInfectionClinicallyDetected" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="severeInfectionMultiplier" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImperialReproductionNumber", propOrder = {
    "averageCommunityImperialReproductionNumber",
    "averageHouseholdImperialReproductionNumber",
    "imperialReproductionNumberForPlace",
    "seasonalityFunctionParameters",
    "probSymptomaticInfectionClinicallyDetected",
    "symptomaticInfectionMultiplier",
    "probSevereInfectionClinicallyDetected",
    "severeInfectionMultiplier"
})
public class ImperialReproductionNumber {

    protected double averageCommunityImperialReproductionNumber;
    protected double averageHouseholdImperialReproductionNumber;
    protected List<ImperialReproductionNumberForPlace> imperialReproductionNumberForPlace;
    @XmlElement(required = true)
    protected SeasonalityFunctionParameters seasonalityFunctionParameters;
    protected double probSymptomaticInfectionClinicallyDetected;
    protected double symptomaticInfectionMultiplier;
    protected double probSevereInfectionClinicallyDetected;
    protected double severeInfectionMultiplier;

    /**
     * Gets the value of the averageCommunityImperialReproductionNumber property.
     * 
     */
    public double getAverageCommunityImperialReproductionNumber() {
        return averageCommunityImperialReproductionNumber;
    }

    /**
     * Sets the value of the averageCommunityImperialReproductionNumber property.
     * 
     */
    public void setAverageCommunityImperialReproductionNumber(double value) {
        this.averageCommunityImperialReproductionNumber = value;
    }

    /**
     * Gets the value of the averageHouseholdImperialReproductionNumber property.
     * 
     */
    public double getAverageHouseholdImperialReproductionNumber() {
        return averageHouseholdImperialReproductionNumber;
    }

    /**
     * Sets the value of the averageHouseholdImperialReproductionNumber property.
     * 
     */
    public void setAverageHouseholdImperialReproductionNumber(double value) {
        this.averageHouseholdImperialReproductionNumber = value;
    }

    /**
     * Gets the value of the imperialReproductionNumberForPlace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imperialReproductionNumberForPlace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImperialReproductionNumberForPlace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImperialReproductionNumberForPlace }
     * 
     * 
     */
    public List<ImperialReproductionNumberForPlace> getImperialReproductionNumberForPlace() {
        if (imperialReproductionNumberForPlace == null) {
            imperialReproductionNumberForPlace = new ArrayList<ImperialReproductionNumberForPlace>();
        }
        return this.imperialReproductionNumberForPlace;
    }

    /**
     * Gets the value of the seasonalityFunctionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link SeasonalityFunctionParameters }
     *     
     */
    public SeasonalityFunctionParameters getSeasonalityFunctionParameters() {
        return seasonalityFunctionParameters;
    }

    /**
     * Sets the value of the seasonalityFunctionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeasonalityFunctionParameters }
     *     
     */
    public void setSeasonalityFunctionParameters(SeasonalityFunctionParameters value) {
        this.seasonalityFunctionParameters = value;
    }

    /**
     * Gets the value of the probSymptomaticInfectionClinicallyDetected property.
     * 
     */
    public double getProbSymptomaticInfectionClinicallyDetected() {
        return probSymptomaticInfectionClinicallyDetected;
    }

    /**
     * Sets the value of the probSymptomaticInfectionClinicallyDetected property.
     * 
     */
    public void setProbSymptomaticInfectionClinicallyDetected(double value) {
        this.probSymptomaticInfectionClinicallyDetected = value;
    }

    /**
     * Gets the value of the symptomaticInfectionMultiplier property.
     * 
     */
    public double getSymptomaticInfectionMultiplier() {
        return symptomaticInfectionMultiplier;
    }

    /**
     * Sets the value of the symptomaticInfectionMultiplier property.
     * 
     */
    public void setSymptomaticInfectionMultiplier(double value) {
        this.symptomaticInfectionMultiplier = value;
    }

    /**
     * Gets the value of the probSevereInfectionClinicallyDetected property.
     * 
     */
    public double getProbSevereInfectionClinicallyDetected() {
        return probSevereInfectionClinicallyDetected;
    }

    /**
     * Sets the value of the probSevereInfectionClinicallyDetected property.
     * 
     */
    public void setProbSevereInfectionClinicallyDetected(double value) {
        this.probSevereInfectionClinicallyDetected = value;
    }

    /**
     * Gets the value of the severeInfectionMultiplier property.
     * 
     */
    public double getSevereInfectionMultiplier() {
        return severeInfectionMultiplier;
    }

    /**
     * Sets the value of the severeInfectionMultiplier property.
     * 
     */
    public void setSevereInfectionMultiplier(double value) {
        this.severeInfectionMultiplier = value;
    }

}
