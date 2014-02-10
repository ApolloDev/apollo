
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AntiviralTreatmentEfficacy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntiviralTreatmentEfficacy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="antiviralTreatmentIdentifier" type="{http://www.w3.org/2001/XMLSchema}token"/>
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
    "antiviralTreatmentIdentifier"
})
@XmlSeeAlso({
    AntiviralEfficacyForSimulatorConfiguration.class
})
public class AntiviralTreatmentEfficacy
    extends TreatmentEfficacy
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String antiviralTreatmentIdentifier;

    /**
     * Gets the value of the antiviralTreatmentIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntiviralTreatmentIdentifier() {
        return antiviralTreatmentIdentifier;
    }

    /**
     * Sets the value of the antiviralTreatmentIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntiviralTreatmentIdentifier(String value) {
        this.antiviralTreatmentIdentifier = value;
    }

}
