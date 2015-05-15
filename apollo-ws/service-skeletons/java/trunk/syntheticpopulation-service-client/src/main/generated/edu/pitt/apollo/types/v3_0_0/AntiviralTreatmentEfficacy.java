
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AntiviralTreatmentEfficacy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntiviralTreatmentEfficacy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="efficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AntiviralTreatmentEfficacy", propOrder = {
    "efficacy"
})
public class AntiviralTreatmentEfficacy
    extends TreatmentEfficacy
{

    protected double efficacy;

    /**
     * Gets the value of the efficacy property.
     * 
     */
    public double getEfficacy() {
        return efficacy;
    }

    /**
     * Sets the value of the efficacy property.
     * 
     */
    public void setEfficacy(double value) {
        this.efficacy = value;
    }

}
