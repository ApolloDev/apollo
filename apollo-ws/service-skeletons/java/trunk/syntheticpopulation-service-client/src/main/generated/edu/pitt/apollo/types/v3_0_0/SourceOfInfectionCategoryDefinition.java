
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SourceOfInfectionCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SourceOfInfectionCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="sourceOfInfection" type="{http://types.apollo.pitt.edu/v3_0_0/}SourceOfInfectionEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SourceOfInfectionCategoryDefinition", propOrder = {
    "sourceOfInfection"
})
public class SourceOfInfectionCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected SourceOfInfectionEnum sourceOfInfection;

    /**
     * Gets the value of the sourceOfInfection property.
     * 
     * @return
     *     possible object is
     *     {@link SourceOfInfectionEnum }
     *     
     */
    public SourceOfInfectionEnum getSourceOfInfection() {
        return sourceOfInfection;
    }

    /**
     * Sets the value of the sourceOfInfection property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceOfInfectionEnum }
     *     
     */
    public void setSourceOfInfection(SourceOfInfectionEnum value) {
        this.sourceOfInfection = value;
    }

}
