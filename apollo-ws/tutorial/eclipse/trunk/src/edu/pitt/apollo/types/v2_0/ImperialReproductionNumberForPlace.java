
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImperialReproductionNumberForPlace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImperialReproductionNumberForPlace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="placeType" type="{http://types.apollo.pitt.edu/v2_0/}FacilityEnum"/>
 *         &lt;element name="averageImperialReproductionNumber" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="withinGroupTransmissionProbability" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="probSymptomaticIndividualAbsent" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="symptomaticAbsenteeMultiplier" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="probSeverelyInfectedIndividualAbsent" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="severelyInfectedAbsenteeMultiplier" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="spatialKernelFunctionParameters" type="{http://types.apollo.pitt.edu/v2_0/}SpatialKernelFunctionParameters"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImperialReproductionNumberForPlace", propOrder = {
    "placeType",
    "averageImperialReproductionNumber",
    "withinGroupTransmissionProbability",
    "probSymptomaticIndividualAbsent",
    "symptomaticAbsenteeMultiplier",
    "probSeverelyInfectedIndividualAbsent",
    "severelyInfectedAbsenteeMultiplier",
    "spatialKernelFunctionParameters"
})
public class ImperialReproductionNumberForPlace {

    @XmlElement(required = true)
    protected FacilityEnum placeType;
    protected double averageImperialReproductionNumber;
    protected double withinGroupTransmissionProbability;
    protected double probSymptomaticIndividualAbsent;
    protected double symptomaticAbsenteeMultiplier;
    protected double probSeverelyInfectedIndividualAbsent;
    protected double severelyInfectedAbsenteeMultiplier;
    @XmlElement(required = true)
    protected SpatialKernelFunctionParameters spatialKernelFunctionParameters;

    /**
     * Gets the value of the placeType property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityEnum }
     *     
     */
    public FacilityEnum getPlaceType() {
        return placeType;
    }

    /**
     * Sets the value of the placeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityEnum }
     *     
     */
    public void setPlaceType(FacilityEnum value) {
        this.placeType = value;
    }

    /**
     * Gets the value of the averageImperialReproductionNumber property.
     * 
     */
    public double getAverageImperialReproductionNumber() {
        return averageImperialReproductionNumber;
    }

    /**
     * Sets the value of the averageImperialReproductionNumber property.
     * 
     */
    public void setAverageImperialReproductionNumber(double value) {
        this.averageImperialReproductionNumber = value;
    }

    /**
     * Gets the value of the withinGroupTransmissionProbability property.
     * 
     */
    public double getWithinGroupTransmissionProbability() {
        return withinGroupTransmissionProbability;
    }

    /**
     * Sets the value of the withinGroupTransmissionProbability property.
     * 
     */
    public void setWithinGroupTransmissionProbability(double value) {
        this.withinGroupTransmissionProbability = value;
    }

    /**
     * Gets the value of the probSymptomaticIndividualAbsent property.
     * 
     */
    public double getProbSymptomaticIndividualAbsent() {
        return probSymptomaticIndividualAbsent;
    }

    /**
     * Sets the value of the probSymptomaticIndividualAbsent property.
     * 
     */
    public void setProbSymptomaticIndividualAbsent(double value) {
        this.probSymptomaticIndividualAbsent = value;
    }

    /**
     * Gets the value of the symptomaticAbsenteeMultiplier property.
     * 
     */
    public double getSymptomaticAbsenteeMultiplier() {
        return symptomaticAbsenteeMultiplier;
    }

    /**
     * Sets the value of the symptomaticAbsenteeMultiplier property.
     * 
     */
    public void setSymptomaticAbsenteeMultiplier(double value) {
        this.symptomaticAbsenteeMultiplier = value;
    }

    /**
     * Gets the value of the probSeverelyInfectedIndividualAbsent property.
     * 
     */
    public double getProbSeverelyInfectedIndividualAbsent() {
        return probSeverelyInfectedIndividualAbsent;
    }

    /**
     * Sets the value of the probSeverelyInfectedIndividualAbsent property.
     * 
     */
    public void setProbSeverelyInfectedIndividualAbsent(double value) {
        this.probSeverelyInfectedIndividualAbsent = value;
    }

    /**
     * Gets the value of the severelyInfectedAbsenteeMultiplier property.
     * 
     */
    public double getSeverelyInfectedAbsenteeMultiplier() {
        return severelyInfectedAbsenteeMultiplier;
    }

    /**
     * Sets the value of the severelyInfectedAbsenteeMultiplier property.
     * 
     */
    public void setSeverelyInfectedAbsenteeMultiplier(double value) {
        this.severelyInfectedAbsenteeMultiplier = value;
    }

    /**
     * Gets the value of the spatialKernelFunctionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link SpatialKernelFunctionParameters }
     *     
     */
    public SpatialKernelFunctionParameters getSpatialKernelFunctionParameters() {
        return spatialKernelFunctionParameters;
    }

    /**
     * Sets the value of the spatialKernelFunctionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpatialKernelFunctionParameters }
     *     
     */
    public void setSpatialKernelFunctionParameters(SpatialKernelFunctionParameters value) {
        this.spatialKernelFunctionParameters = value;
    }

}
