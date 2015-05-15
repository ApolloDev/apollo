
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationEfficacyMeasured complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacyMeasured">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="measuredEfficacyValue" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityTable"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacyMeasured", propOrder = {
    "measuredEfficacyValue"
})
public class VaccinationEfficacyMeasured
    extends TreatmentEfficacy
{

    @XmlElement(required = true)
    protected ConditionalProbabilityTable measuredEfficacyValue;

    /**
     * Gets the value of the measuredEfficacyValue property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionalProbabilityTable }
     *     
     */
    public ConditionalProbabilityTable getMeasuredEfficacyValue() {
        return measuredEfficacyValue;
    }

    /**
     * Sets the value of the measuredEfficacyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionalProbabilityTable }
     *     
     */
    public void setMeasuredEfficacyValue(ConditionalProbabilityTable value) {
        this.measuredEfficacyValue = value;
    }

}
