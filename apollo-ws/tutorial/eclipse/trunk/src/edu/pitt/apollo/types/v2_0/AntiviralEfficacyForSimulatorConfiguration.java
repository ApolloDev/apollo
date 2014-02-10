
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AntiviralEfficacyForSimulatorConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntiviralEfficacyForSimulatorConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}AntiviralTreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="averageAntiviralTreatmentEfficacy" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AntiviralEfficacyForSimulatorConfiguration", propOrder = {
    "averageAntiviralTreatmentEfficacy"
})
public class AntiviralEfficacyForSimulatorConfiguration
    extends AntiviralTreatmentEfficacy
{

    protected double averageAntiviralTreatmentEfficacy;

    /**
     * Gets the value of the averageAntiviralTreatmentEfficacy property.
     * 
     */
    public double getAverageAntiviralTreatmentEfficacy() {
        return averageAntiviralTreatmentEfficacy;
    }

    /**
     * Sets the value of the averageAntiviralTreatmentEfficacy property.
     * 
     */
    public void setAverageAntiviralTreatmentEfficacy(double value) {
        this.averageAntiviralTreatmentEfficacy = value;
    }

}
