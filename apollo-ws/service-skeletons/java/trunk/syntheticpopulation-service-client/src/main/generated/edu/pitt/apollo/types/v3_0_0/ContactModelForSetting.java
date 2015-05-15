
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactModelForSetting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactModelForSetting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="spatialKernelParametersForSetting" type="{http://types.apollo.pitt.edu/v3_0_0/}SpatialKernelFunctionParameters"/>
 *         &lt;element name="probSymptomaticInfectionClinicallyDetected" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="symptomaticInfectionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="probSevereInfectionClinicallyDetected" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactModelForSetting", propOrder = {
    "spatialKernelParametersForSetting",
    "probSymptomaticInfectionClinicallyDetected",
    "symptomaticInfectionMultiplier",
    "probSevereInfectionClinicallyDetected"
})
@XmlSeeAlso({
    ContactModelForPlace.class,
    ContactModelForCommunity.class,
    ContactModelForHousehold.class
})
public class ContactModelForSetting {

    @XmlElement(required = true)
    protected SpatialKernelFunctionParameters spatialKernelParametersForSetting;
    protected double probSymptomaticInfectionClinicallyDetected;
    protected double symptomaticInfectionMultiplier;
    protected double probSevereInfectionClinicallyDetected;

    /**
     * Gets the value of the spatialKernelParametersForSetting property.
     * 
     * @return
     *     possible object is
     *     {@link SpatialKernelFunctionParameters }
     *     
     */
    public SpatialKernelFunctionParameters getSpatialKernelParametersForSetting() {
        return spatialKernelParametersForSetting;
    }

    /**
     * Sets the value of the spatialKernelParametersForSetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpatialKernelFunctionParameters }
     *     
     */
    public void setSpatialKernelParametersForSetting(SpatialKernelFunctionParameters value) {
        this.spatialKernelParametersForSetting = value;
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

}
