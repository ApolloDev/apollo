
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfectiousDiseaseControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDiseaseControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="controlStrategyStartTime" type="{http://types.apollo.pitt.edu/v2_0/}ControlStrategyStartTime"/>
 *         &lt;element name="controlStrategyResponseDelay" type="{http://types.apollo.pitt.edu/v2_0/}NumericParameterValue"/>
 *         &lt;element name="controlStrategyCompliance" type="{http://types.apollo.pitt.edu/v2_0/}ProbabilisticParameterValue"/>
 *         &lt;element name="controlStrategyReactiveEndPointFraction" type="{http://types.apollo.pitt.edu/v2_0/}Fraction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectiousDiseaseControlStrategy", propOrder = {
    "description",
    "controlStrategyStartTime",
    "controlStrategyResponseDelay",
    "controlStrategyCompliance",
    "controlStrategyReactiveEndPointFraction"
})
@XmlSeeAlso({
    SchoolClosureControlStrategy.class,
    IndividualTreatmentControlStrategy.class
})
public class InfectiousDiseaseControlStrategy
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected ControlStrategyStartTime controlStrategyStartTime;
    @XmlElement(required = true)
    protected NumericParameterValue controlStrategyResponseDelay;
    @XmlElement(required = true)
    protected ProbabilisticParameterValue controlStrategyCompliance;
    protected Double controlStrategyReactiveEndPointFraction;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the controlStrategyStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link ControlStrategyStartTime }
     *     
     */
    public ControlStrategyStartTime getControlStrategyStartTime() {
        return controlStrategyStartTime;
    }

    /**
     * Sets the value of the controlStrategyStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlStrategyStartTime }
     *     
     */
    public void setControlStrategyStartTime(ControlStrategyStartTime value) {
        this.controlStrategyStartTime = value;
    }

    /**
     * Gets the value of the controlStrategyResponseDelay property.
     * 
     * @return
     *     possible object is
     *     {@link NumericParameterValue }
     *     
     */
    public NumericParameterValue getControlStrategyResponseDelay() {
        return controlStrategyResponseDelay;
    }

    /**
     * Sets the value of the controlStrategyResponseDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericParameterValue }
     *     
     */
    public void setControlStrategyResponseDelay(NumericParameterValue value) {
        this.controlStrategyResponseDelay = value;
    }

    /**
     * Gets the value of the controlStrategyCompliance property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public ProbabilisticParameterValue getControlStrategyCompliance() {
        return controlStrategyCompliance;
    }

    /**
     * Sets the value of the controlStrategyCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public void setControlStrategyCompliance(ProbabilisticParameterValue value) {
        this.controlStrategyCompliance = value;
    }

    /**
     * Gets the value of the controlStrategyReactiveEndPointFraction property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getControlStrategyReactiveEndPointFraction() {
        return controlStrategyReactiveEndPointFraction;
    }

    /**
     * Sets the value of the controlStrategyReactiveEndPointFraction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setControlStrategyReactiveEndPointFraction(Double value) {
        this.controlStrategyReactiveEndPointFraction = value;
    }

}
