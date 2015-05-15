
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentSurveillanceCapability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentSurveillanceCapability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location"/>
 *         &lt;element name="treatment" type="{http://types.apollo.pitt.edu/v3_0_0/}IndividualTreatmentEnum"/>
 *         &lt;element name="sensitivityOfTreatmentDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="specificityOfTreatmentDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="timeDelayOfTreatmentDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentSurveillanceCapability", propOrder = {
    "location",
    "treatment",
    "sensitivityOfTreatmentDetection",
    "specificityOfTreatmentDetection",
    "timeDelayOfTreatmentDetection"
})
public class TreatmentSurveillanceCapability {

    @XmlElement(required = true)
    protected Location location;
    @XmlElement(required = true)
    protected IndividualTreatmentEnum treatment;
    protected double sensitivityOfTreatmentDetection;
    protected double specificityOfTreatmentDetection;
    @XmlElement(required = true)
    protected Duration timeDelayOfTreatmentDetection;

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the treatment property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualTreatmentEnum }
     *     
     */
    public IndividualTreatmentEnum getTreatment() {
        return treatment;
    }

    /**
     * Sets the value of the treatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualTreatmentEnum }
     *     
     */
    public void setTreatment(IndividualTreatmentEnum value) {
        this.treatment = value;
    }

    /**
     * Gets the value of the sensitivityOfTreatmentDetection property.
     * 
     */
    public double getSensitivityOfTreatmentDetection() {
        return sensitivityOfTreatmentDetection;
    }

    /**
     * Sets the value of the sensitivityOfTreatmentDetection property.
     * 
     */
    public void setSensitivityOfTreatmentDetection(double value) {
        this.sensitivityOfTreatmentDetection = value;
    }

    /**
     * Gets the value of the specificityOfTreatmentDetection property.
     * 
     */
    public double getSpecificityOfTreatmentDetection() {
        return specificityOfTreatmentDetection;
    }

    /**
     * Sets the value of the specificityOfTreatmentDetection property.
     * 
     */
    public void setSpecificityOfTreatmentDetection(double value) {
        this.specificityOfTreatmentDetection = value;
    }

    /**
     * Gets the value of the timeDelayOfTreatmentDetection property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getTimeDelayOfTreatmentDetection() {
        return timeDelayOfTreatmentDetection;
    }

    /**
     * Sets the value of the timeDelayOfTreatmentDetection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setTimeDelayOfTreatmentDetection(Duration value) {
        this.timeDelayOfTreatmentDetection = value;
    }

}
