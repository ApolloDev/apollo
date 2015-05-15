
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlaceCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlaceCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="place" type="{http://types.apollo.pitt.edu/v3_0_0/}PlaceEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlaceCategoryDefinition", propOrder = {
    "place"
})
public class PlaceCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected PlaceEnum place;

    /**
     * Gets the value of the place property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceEnum }
     *     
     */
    public PlaceEnum getPlace() {
        return place;
    }

    /**
     * Sets the value of the place property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceEnum }
     *     
     */
    public void setPlace(PlaceEnum value) {
        this.place = value;
    }

}
