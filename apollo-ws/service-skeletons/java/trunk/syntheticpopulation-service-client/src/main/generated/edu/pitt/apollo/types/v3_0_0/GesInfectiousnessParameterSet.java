
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GesInfectiousnessParameterSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GesInfectiousnessParameterSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infectiousnessProfile" type="{http://types.apollo.pitt.edu/v3_0_0/}ContinuousParametricProbabilityDistribution" minOccurs="0"/>
 *         &lt;element name="treat_inf" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *         &lt;element name="prophylaxis_inf" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *         &lt;element name="vacc_inf" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *         &lt;element name="relative_inf_symptomatic_multiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GesInfectiousnessParameterSet", propOrder = {
    "infectiousnessProfile",
    "treatInf",
    "prophylaxisInf",
    "vaccInf",
    "relativeInfSymptomaticMultiplier"
})
public class GesInfectiousnessParameterSet {

    protected ContinuousParametricProbabilityDistribution infectiousnessProfile;
    @XmlElement(name = "treat_inf", required = true)
    protected ProbabilisticParameter treatInf;
    @XmlElement(name = "prophylaxis_inf", required = true)
    protected ProbabilisticParameter prophylaxisInf;
    @XmlElement(name = "vacc_inf", required = true)
    protected ProbabilisticParameter vaccInf;
    @XmlElement(name = "relative_inf_symptomatic_multiplier")
    protected double relativeInfSymptomaticMultiplier;

    /**
     * Gets the value of the infectiousnessProfile property.
     * 
     * @return
     *     possible object is
     *     {@link ContinuousParametricProbabilityDistribution }
     *     
     */
    public ContinuousParametricProbabilityDistribution getInfectiousnessProfile() {
        return infectiousnessProfile;
    }

    /**
     * Sets the value of the infectiousnessProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousParametricProbabilityDistribution }
     *     
     */
    public void setInfectiousnessProfile(ContinuousParametricProbabilityDistribution value) {
        this.infectiousnessProfile = value;
    }

    /**
     * Gets the value of the treatInf property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getTreatInf() {
        return treatInf;
    }

    /**
     * Sets the value of the treatInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setTreatInf(ProbabilisticParameter value) {
        this.treatInf = value;
    }

    /**
     * Gets the value of the prophylaxisInf property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getProphylaxisInf() {
        return prophylaxisInf;
    }

    /**
     * Sets the value of the prophylaxisInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setProphylaxisInf(ProbabilisticParameter value) {
        this.prophylaxisInf = value;
    }

    /**
     * Gets the value of the vaccInf property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getVaccInf() {
        return vaccInf;
    }

    /**
     * Sets the value of the vaccInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setVaccInf(ProbabilisticParameter value) {
        this.vaccInf = value;
    }

    /**
     * Gets the value of the relativeInfSymptomaticMultiplier property.
     * 
     */
    public double getRelativeInfSymptomaticMultiplier() {
        return relativeInfSymptomaticMultiplier;
    }

    /**
     * Sets the value of the relativeInfSymptomaticMultiplier property.
     * 
     */
    public void setRelativeInfSymptomaticMultiplier(double value) {
        this.relativeInfSymptomaticMultiplier = value;
    }

}
