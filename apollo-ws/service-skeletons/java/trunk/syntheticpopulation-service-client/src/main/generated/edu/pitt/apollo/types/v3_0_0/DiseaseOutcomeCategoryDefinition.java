
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseOutcomeCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiseaseOutcomeCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="diseaseOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseOutcomeCategoryDefinition", propOrder = {
    "diseaseOutcome"
})
public class DiseaseOutcomeCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected DiseaseOutcomeEnum diseaseOutcome;

    /**
     * Gets the value of the diseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getDiseaseOutcome() {
        return diseaseOutcome;
    }

    /**
     * Sets the value of the diseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setDiseaseOutcome(DiseaseOutcomeEnum value) {
        this.diseaseOutcome = value;
    }

}
