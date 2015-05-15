
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScenarioCartesianOrigin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScenarioCartesianOrigin">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cartesianReferenceLongitude" type="{http://types.apollo.pitt.edu/v3_0_0/}Longitude"/>
 *         &lt;element name="cartesianReferenceLatitude" type="{http://types.apollo.pitt.edu/v3_0_0/}Latitude"/>
 *         &lt;element name="cartesianReferenceAltitude" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScenarioCartesianOrigin", propOrder = {
    "cartesianReferenceLongitude",
    "cartesianReferenceLatitude",
    "cartesianReferenceAltitude"
})
public class ScenarioCartesianOrigin {

    protected double cartesianReferenceLongitude;
    protected double cartesianReferenceLatitude;
    @XmlElement(required = true)
    protected Distance cartesianReferenceAltitude;

    /**
     * Gets the value of the cartesianReferenceLongitude property.
     * 
     */
    public double getCartesianReferenceLongitude() {
        return cartesianReferenceLongitude;
    }

    /**
     * Sets the value of the cartesianReferenceLongitude property.
     * 
     */
    public void setCartesianReferenceLongitude(double value) {
        this.cartesianReferenceLongitude = value;
    }

    /**
     * Gets the value of the cartesianReferenceLatitude property.
     * 
     */
    public double getCartesianReferenceLatitude() {
        return cartesianReferenceLatitude;
    }

    /**
     * Sets the value of the cartesianReferenceLatitude property.
     * 
     */
    public void setCartesianReferenceLatitude(double value) {
        this.cartesianReferenceLatitude = value;
    }

    /**
     * Gets the value of the cartesianReferenceAltitude property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getCartesianReferenceAltitude() {
        return cartesianReferenceAltitude;
    }

    /**
     * Sets the value of the cartesianReferenceAltitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setCartesianReferenceAltitude(Distance value) {
        this.cartesianReferenceAltitude = value;
    }

}
