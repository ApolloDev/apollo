
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AntiviralTreatment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntiviralTreatment">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}Treatment">
 *       &lt;sequence>
 *         &lt;element name="antiviral" type="{http://types.apollo.pitt.edu/v2_0/}Antiviral"/>
 *         &lt;element name="antiviralTreatmentEfficacy" type="{http://types.apollo.pitt.edu/v2_0/}AntiviralTreatmentEfficacy"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AntiviralTreatment", propOrder = {
    "antiviral",
    "antiviralTreatmentEfficacy"
})
public class AntiviralTreatment
    extends Treatment
{

    @XmlElement(required = true)
    protected Antiviral antiviral;
    @XmlElement(required = true)
    protected AntiviralTreatmentEfficacy antiviralTreatmentEfficacy;

    /**
     * Gets the value of the antiviral property.
     * 
     * @return
     *     possible object is
     *     {@link Antiviral }
     *     
     */
    public Antiviral getAntiviral() {
        return antiviral;
    }

    /**
     * Sets the value of the antiviral property.
     * 
     * @param value
     *     allowed object is
     *     {@link Antiviral }
     *     
     */
    public void setAntiviral(Antiviral value) {
        this.antiviral = value;
    }

    /**
     * Gets the value of the antiviralTreatmentEfficacy property.
     * 
     * @return
     *     possible object is
     *     {@link AntiviralTreatmentEfficacy }
     *     
     */
    public AntiviralTreatmentEfficacy getAntiviralTreatmentEfficacy() {
        return antiviralTreatmentEfficacy;
    }

    /**
     * Sets the value of the antiviralTreatmentEfficacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link AntiviralTreatmentEfficacy }
     *     
     */
    public void setAntiviralTreatmentEfficacy(AntiviralTreatmentEfficacy value) {
        this.antiviralTreatmentEfficacy = value;
    }

}
