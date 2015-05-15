
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlaceClosureControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlaceClosureControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="placeClass" type="{http://types.apollo.pitt.edu/v3_0_0/}PlaceEnum"/>
 *         &lt;element name="closeIndividualPlacesIndependently" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="closurePeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="householdTransmissionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble" minOccurs="0"/>
 *         &lt;element name="communityTransmissionMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlaceClosureControlStrategy", propOrder = {
    "placeClass",
    "closeIndividualPlacesIndependently",
    "closurePeriod",
    "householdTransmissionMultiplier",
    "communityTransmissionMultiplier"
})
public class PlaceClosureControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlElement(required = true)
    protected PlaceEnum placeClass;
    protected boolean closeIndividualPlacesIndependently;
    @XmlElement(required = true)
    protected Duration closurePeriod;
    protected Double householdTransmissionMultiplier;
    protected Double communityTransmissionMultiplier;

    /**
     * Gets the value of the placeClass property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceEnum }
     *     
     */
    public PlaceEnum getPlaceClass() {
        return placeClass;
    }

    /**
     * Sets the value of the placeClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceEnum }
     *     
     */
    public void setPlaceClass(PlaceEnum value) {
        this.placeClass = value;
    }

    /**
     * Gets the value of the closeIndividualPlacesIndependently property.
     * 
     */
    public boolean isCloseIndividualPlacesIndependently() {
        return closeIndividualPlacesIndependently;
    }

    /**
     * Sets the value of the closeIndividualPlacesIndependently property.
     * 
     */
    public void setCloseIndividualPlacesIndependently(boolean value) {
        this.closeIndividualPlacesIndependently = value;
    }

    /**
     * Gets the value of the closurePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getClosurePeriod() {
        return closurePeriod;
    }

    /**
     * Sets the value of the closurePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setClosurePeriod(Duration value) {
        this.closurePeriod = value;
    }

    /**
     * Gets the value of the householdTransmissionMultiplier property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHouseholdTransmissionMultiplier() {
        return householdTransmissionMultiplier;
    }

    /**
     * Sets the value of the householdTransmissionMultiplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHouseholdTransmissionMultiplier(Double value) {
        this.householdTransmissionMultiplier = value;
    }

    /**
     * Gets the value of the communityTransmissionMultiplier property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCommunityTransmissionMultiplier() {
        return communityTransmissionMultiplier;
    }

    /**
     * Sets the value of the communityTransmissionMultiplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCommunityTransmissionMultiplier(Double value) {
        this.communityTransmissionMultiplier = value;
    }

}
