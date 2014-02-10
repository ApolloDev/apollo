
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityCategory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityCategory">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="facility" type="{http://types.apollo.pitt.edu/v2_0/}FacilityEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityCategory", propOrder = {
    "facility"
})
public class FacilityCategory
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected FacilityEnum facility;

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityEnum }
     *     
     */
    public FacilityEnum getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityEnum }
     *     
     */
    public void setFacility(FacilityEnum value) {
        this.facility = value;
    }

}
