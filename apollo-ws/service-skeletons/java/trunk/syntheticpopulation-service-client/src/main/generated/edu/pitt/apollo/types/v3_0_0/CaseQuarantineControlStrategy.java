
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseQuarantineControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseQuarantineControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="quarantinePeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="compliance" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *         &lt;element name="householdTransmissionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *         &lt;element name="schoolTransmissionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *         &lt;element name="workplaceTransmissionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseQuarantineControlStrategy", propOrder = {
    "quarantinePeriod",
    "compliance",
    "householdTransmissionMultiplier",
    "schoolTransmissionMultiplier",
    "workplaceTransmissionMultiplier"
})
public class CaseQuarantineControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlElement(required = true)
    protected Duration quarantinePeriod;
    @XmlElement(required = true)
    protected ProbabilisticParameter compliance;
    protected double householdTransmissionMultiplier;
    protected double schoolTransmissionMultiplier;
    protected double workplaceTransmissionMultiplier;

    /**
     * Gets the value of the quarantinePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getQuarantinePeriod() {
        return quarantinePeriod;
    }

    /**
     * Sets the value of the quarantinePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setQuarantinePeriod(Duration value) {
        this.quarantinePeriod = value;
    }

    /**
     * Gets the value of the compliance property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getCompliance() {
        return compliance;
    }

    /**
     * Sets the value of the compliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setCompliance(ProbabilisticParameter value) {
        this.compliance = value;
    }

    /**
     * Gets the value of the householdTransmissionMultiplier property.
     * 
     */
    public double getHouseholdTransmissionMultiplier() {
        return householdTransmissionMultiplier;
    }

    /**
     * Sets the value of the householdTransmissionMultiplier property.
     * 
     */
    public void setHouseholdTransmissionMultiplier(double value) {
        this.householdTransmissionMultiplier = value;
    }

    /**
     * Gets the value of the schoolTransmissionMultiplier property.
     * 
     */
    public double getSchoolTransmissionMultiplier() {
        return schoolTransmissionMultiplier;
    }

    /**
     * Sets the value of the schoolTransmissionMultiplier property.
     * 
     */
    public void setSchoolTransmissionMultiplier(double value) {
        this.schoolTransmissionMultiplier = value;
    }

    /**
     * Gets the value of the workplaceTransmissionMultiplier property.
     * 
     */
    public double getWorkplaceTransmissionMultiplier() {
        return workplaceTransmissionMultiplier;
    }

    /**
     * Sets the value of the workplaceTransmissionMultiplier property.
     * 
     */
    public void setWorkplaceTransmissionMultiplier(double value) {
        this.workplaceTransmissionMultiplier = value;
    }

}
