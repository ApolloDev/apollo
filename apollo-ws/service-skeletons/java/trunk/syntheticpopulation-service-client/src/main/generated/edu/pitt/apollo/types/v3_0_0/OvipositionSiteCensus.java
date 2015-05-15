
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OvipositionSiteCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OvipositionSiteCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemCensus">
 *       &lt;sequence>
 *         &lt;element name="breteauIndex" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="carryingCapacityPerHouse" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble" minOccurs="0"/>
 *         &lt;element name="heterogenousCarryingCapacity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ratioOfOutdoorToIndoorOvisites" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble" minOccurs="0"/>
 *         &lt;element name="maximumRatioOfOutdoorCarryingCapacityToIndoor" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OvipositionSiteCensus", propOrder = {
    "breteauIndex",
    "carryingCapacityPerHouse",
    "heterogenousCarryingCapacity",
    "ratioOfOutdoorToIndoorOvisites",
    "maximumRatioOfOutdoorCarryingCapacityToIndoor"
})
public class OvipositionSiteCensus
    extends AbioticEcosystemCensus
{

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger breteauIndex;
    protected Double carryingCapacityPerHouse;
    protected Boolean heterogenousCarryingCapacity;
    protected Double ratioOfOutdoorToIndoorOvisites;
    protected Double maximumRatioOfOutdoorCarryingCapacityToIndoor;

    /**
     * Gets the value of the breteauIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBreteauIndex() {
        return breteauIndex;
    }

    /**
     * Sets the value of the breteauIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBreteauIndex(BigInteger value) {
        this.breteauIndex = value;
    }

    /**
     * Gets the value of the carryingCapacityPerHouse property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCarryingCapacityPerHouse() {
        return carryingCapacityPerHouse;
    }

    /**
     * Sets the value of the carryingCapacityPerHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCarryingCapacityPerHouse(Double value) {
        this.carryingCapacityPerHouse = value;
    }

    /**
     * Gets the value of the heterogenousCarryingCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHeterogenousCarryingCapacity() {
        return heterogenousCarryingCapacity;
    }

    /**
     * Sets the value of the heterogenousCarryingCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHeterogenousCarryingCapacity(Boolean value) {
        this.heterogenousCarryingCapacity = value;
    }

    /**
     * Gets the value of the ratioOfOutdoorToIndoorOvisites property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatioOfOutdoorToIndoorOvisites() {
        return ratioOfOutdoorToIndoorOvisites;
    }

    /**
     * Sets the value of the ratioOfOutdoorToIndoorOvisites property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatioOfOutdoorToIndoorOvisites(Double value) {
        this.ratioOfOutdoorToIndoorOvisites = value;
    }

    /**
     * Gets the value of the maximumRatioOfOutdoorCarryingCapacityToIndoor property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumRatioOfOutdoorCarryingCapacityToIndoor() {
        return maximumRatioOfOutdoorCarryingCapacityToIndoor;
    }

    /**
     * Sets the value of the maximumRatioOfOutdoorCarryingCapacityToIndoor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumRatioOfOutdoorCarryingCapacityToIndoor(Double value) {
        this.maximumRatioOfOutdoorCarryingCapacityToIndoor = value;
    }

}
