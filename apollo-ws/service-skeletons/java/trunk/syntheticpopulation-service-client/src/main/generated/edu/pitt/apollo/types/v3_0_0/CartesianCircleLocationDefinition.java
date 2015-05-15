
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CartesianCircleLocationDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CartesianCircleLocationDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eastWestOffsetFromCartesianCenter" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *         &lt;element name="northSouthOffsetFromCartesianCenter" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *         &lt;element name="altitudeRelativeToCartesianCenter" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *         &lt;element name="radius" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartesianCircleLocationDefinition", propOrder = {
    "eastWestOffsetFromCartesianCenter",
    "northSouthOffsetFromCartesianCenter",
    "altitudeRelativeToCartesianCenter",
    "radius"
})
public class CartesianCircleLocationDefinition {

    @XmlElement(required = true)
    protected Distance eastWestOffsetFromCartesianCenter;
    @XmlElement(required = true)
    protected Distance northSouthOffsetFromCartesianCenter;
    @XmlElement(required = true)
    protected Distance altitudeRelativeToCartesianCenter;
    @XmlElement(required = true)
    protected Distance radius;

    /**
     * Gets the value of the eastWestOffsetFromCartesianCenter property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getEastWestOffsetFromCartesianCenter() {
        return eastWestOffsetFromCartesianCenter;
    }

    /**
     * Sets the value of the eastWestOffsetFromCartesianCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setEastWestOffsetFromCartesianCenter(Distance value) {
        this.eastWestOffsetFromCartesianCenter = value;
    }

    /**
     * Gets the value of the northSouthOffsetFromCartesianCenter property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getNorthSouthOffsetFromCartesianCenter() {
        return northSouthOffsetFromCartesianCenter;
    }

    /**
     * Sets the value of the northSouthOffsetFromCartesianCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setNorthSouthOffsetFromCartesianCenter(Distance value) {
        this.northSouthOffsetFromCartesianCenter = value;
    }

    /**
     * Gets the value of the altitudeRelativeToCartesianCenter property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getAltitudeRelativeToCartesianCenter() {
        return altitudeRelativeToCartesianCenter;
    }

    /**
     * Sets the value of the altitudeRelativeToCartesianCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setAltitudeRelativeToCartesianCenter(Distance value) {
        this.altitudeRelativeToCartesianCenter = value;
    }

    /**
     * Gets the value of the radius property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getRadius() {
        return radius;
    }

    /**
     * Sets the value of the radius property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setRadius(Distance value) {
        this.radius = value;
    }

}
