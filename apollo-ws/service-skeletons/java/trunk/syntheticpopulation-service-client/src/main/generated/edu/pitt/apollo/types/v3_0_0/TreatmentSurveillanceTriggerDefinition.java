
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TreatmentSurveillanceTriggerDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentSurveillanceTriggerDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TriggerDefinition">
 *       &lt;sequence>
 *         &lt;element name="reactiveControlStrategyTest" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="reactiveControlStrategyThreshold" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reactiveControlStrategyOperator" type="{http://types.apollo.pitt.edu/v3_0_0/}OperatorEnum"/>
 *         &lt;element name="unitOfMeasureForThreshold" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfMeasureEnum"/>
 *         &lt;element name="treatmentSurveillanceCapability" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentSurveillanceCapability"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentSurveillanceTriggerDefinition", propOrder = {
    "reactiveControlStrategyTest",
    "reactiveControlStrategyThreshold",
    "reactiveControlStrategyOperator",
    "unitOfMeasureForThreshold",
    "treatmentSurveillanceCapability"
})
public class TreatmentSurveillanceTriggerDefinition
    extends TriggerDefinition
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reactiveControlStrategyTest;
    protected int reactiveControlStrategyThreshold;
    @XmlElement(required = true)
    protected OperatorEnum reactiveControlStrategyOperator;
    @XmlElement(required = true)
    protected UnitOfMeasureEnum unitOfMeasureForThreshold;
    @XmlElement(required = true)
    protected TreatmentSurveillanceCapability treatmentSurveillanceCapability;

    /**
     * Gets the value of the reactiveControlStrategyTest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReactiveControlStrategyTest() {
        return reactiveControlStrategyTest;
    }

    /**
     * Sets the value of the reactiveControlStrategyTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReactiveControlStrategyTest(String value) {
        this.reactiveControlStrategyTest = value;
    }

    /**
     * Gets the value of the reactiveControlStrategyThreshold property.
     * 
     */
    public int getReactiveControlStrategyThreshold() {
        return reactiveControlStrategyThreshold;
    }

    /**
     * Sets the value of the reactiveControlStrategyThreshold property.
     * 
     */
    public void setReactiveControlStrategyThreshold(int value) {
        this.reactiveControlStrategyThreshold = value;
    }

    /**
     * Gets the value of the reactiveControlStrategyOperator property.
     * 
     * @return
     *     possible object is
     *     {@link OperatorEnum }
     *     
     */
    public OperatorEnum getReactiveControlStrategyOperator() {
        return reactiveControlStrategyOperator;
    }

    /**
     * Sets the value of the reactiveControlStrategyOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorEnum }
     *     
     */
    public void setReactiveControlStrategyOperator(OperatorEnum value) {
        this.reactiveControlStrategyOperator = value;
    }

    /**
     * Gets the value of the unitOfMeasureForThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public UnitOfMeasureEnum getUnitOfMeasureForThreshold() {
        return unitOfMeasureForThreshold;
    }

    /**
     * Sets the value of the unitOfMeasureForThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public void setUnitOfMeasureForThreshold(UnitOfMeasureEnum value) {
        this.unitOfMeasureForThreshold = value;
    }

    /**
     * Gets the value of the treatmentSurveillanceCapability property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentSurveillanceCapability }
     *     
     */
    public TreatmentSurveillanceCapability getTreatmentSurveillanceCapability() {
        return treatmentSurveillanceCapability;
    }

    /**
     * Sets the value of the treatmentSurveillanceCapability property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentSurveillanceCapability }
     *     
     */
    public void setTreatmentSurveillanceCapability(TreatmentSurveillanceCapability value) {
        this.treatmentSurveillanceCapability = value;
    }

}
