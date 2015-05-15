
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WolbachiaControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WolbachiaControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}VectorControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="wolbachiaSitesEveryNth" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="wolbachiaSeedAdultsPerHouse" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="clearWolbachiaOnMigration" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="wolbachiaReleaseInterval" type="{http://types.apollo.pitt.edu/v3_0_0/}FixedDuration" minOccurs="0"/>
 *         &lt;element name="wolbachiaReleaseSites" type="{http://types.apollo.pitt.edu/v3_0_0/}WolbachiaReleaseSiteEnum" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnEggMortalityRate" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnAdultMortalityRate" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnLarvalMortalityRate" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnFecundity" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnMatingProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnLeakageRate" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaEffectOnVectorialCapacity" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaPreReleaseAdultSuppressionEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="wolbachiaPreReleaseLarvalSuppressionEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WolbachiaControlStrategy", propOrder = {
    "wolbachiaSitesEveryNth",
    "wolbachiaSeedAdultsPerHouse",
    "clearWolbachiaOnMigration",
    "wolbachiaReleaseInterval",
    "wolbachiaReleaseSites",
    "wolbachiaEffectOnEggMortalityRate",
    "wolbachiaEffectOnAdultMortalityRate",
    "wolbachiaEffectOnLarvalMortalityRate",
    "wolbachiaEffectOnFecundity",
    "wolbachiaEffectOnMatingProbability",
    "wolbachiaEffectOnLeakageRate",
    "wolbachiaEffectOnVectorialCapacity",
    "wolbachiaPreReleaseAdultSuppressionEfficacy",
    "wolbachiaPreReleaseLarvalSuppressionEfficacy"
})
public class WolbachiaControlStrategy
    extends VectorControlStrategy
{

    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger wolbachiaSitesEveryNth;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger wolbachiaSeedAdultsPerHouse;
    protected Boolean clearWolbachiaOnMigration;
    protected FixedDuration wolbachiaReleaseInterval;
    protected WolbachiaReleaseSiteEnum wolbachiaReleaseSites;
    protected Double wolbachiaEffectOnEggMortalityRate;
    protected Double wolbachiaEffectOnAdultMortalityRate;
    protected Double wolbachiaEffectOnLarvalMortalityRate;
    protected Double wolbachiaEffectOnFecundity;
    protected Double wolbachiaEffectOnMatingProbability;
    protected Double wolbachiaEffectOnLeakageRate;
    protected Double wolbachiaEffectOnVectorialCapacity;
    protected Double wolbachiaPreReleaseAdultSuppressionEfficacy;
    protected Double wolbachiaPreReleaseLarvalSuppressionEfficacy;

    /**
     * Gets the value of the wolbachiaSitesEveryNth property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWolbachiaSitesEveryNth() {
        return wolbachiaSitesEveryNth;
    }

    /**
     * Sets the value of the wolbachiaSitesEveryNth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWolbachiaSitesEveryNth(BigInteger value) {
        this.wolbachiaSitesEveryNth = value;
    }

    /**
     * Gets the value of the wolbachiaSeedAdultsPerHouse property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWolbachiaSeedAdultsPerHouse() {
        return wolbachiaSeedAdultsPerHouse;
    }

    /**
     * Sets the value of the wolbachiaSeedAdultsPerHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWolbachiaSeedAdultsPerHouse(BigInteger value) {
        this.wolbachiaSeedAdultsPerHouse = value;
    }

    /**
     * Gets the value of the clearWolbachiaOnMigration property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClearWolbachiaOnMigration() {
        return clearWolbachiaOnMigration;
    }

    /**
     * Sets the value of the clearWolbachiaOnMigration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClearWolbachiaOnMigration(Boolean value) {
        this.clearWolbachiaOnMigration = value;
    }

    /**
     * Gets the value of the wolbachiaReleaseInterval property.
     * 
     * @return
     *     possible object is
     *     {@link FixedDuration }
     *     
     */
    public FixedDuration getWolbachiaReleaseInterval() {
        return wolbachiaReleaseInterval;
    }

    /**
     * Sets the value of the wolbachiaReleaseInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixedDuration }
     *     
     */
    public void setWolbachiaReleaseInterval(FixedDuration value) {
        this.wolbachiaReleaseInterval = value;
    }

    /**
     * Gets the value of the wolbachiaReleaseSites property.
     * 
     * @return
     *     possible object is
     *     {@link WolbachiaReleaseSiteEnum }
     *     
     */
    public WolbachiaReleaseSiteEnum getWolbachiaReleaseSites() {
        return wolbachiaReleaseSites;
    }

    /**
     * Sets the value of the wolbachiaReleaseSites property.
     * 
     * @param value
     *     allowed object is
     *     {@link WolbachiaReleaseSiteEnum }
     *     
     */
    public void setWolbachiaReleaseSites(WolbachiaReleaseSiteEnum value) {
        this.wolbachiaReleaseSites = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnEggMortalityRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnEggMortalityRate() {
        return wolbachiaEffectOnEggMortalityRate;
    }

    /**
     * Sets the value of the wolbachiaEffectOnEggMortalityRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnEggMortalityRate(Double value) {
        this.wolbachiaEffectOnEggMortalityRate = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnAdultMortalityRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnAdultMortalityRate() {
        return wolbachiaEffectOnAdultMortalityRate;
    }

    /**
     * Sets the value of the wolbachiaEffectOnAdultMortalityRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnAdultMortalityRate(Double value) {
        this.wolbachiaEffectOnAdultMortalityRate = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnLarvalMortalityRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnLarvalMortalityRate() {
        return wolbachiaEffectOnLarvalMortalityRate;
    }

    /**
     * Sets the value of the wolbachiaEffectOnLarvalMortalityRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnLarvalMortalityRate(Double value) {
        this.wolbachiaEffectOnLarvalMortalityRate = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnFecundity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnFecundity() {
        return wolbachiaEffectOnFecundity;
    }

    /**
     * Sets the value of the wolbachiaEffectOnFecundity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnFecundity(Double value) {
        this.wolbachiaEffectOnFecundity = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnMatingProbability property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnMatingProbability() {
        return wolbachiaEffectOnMatingProbability;
    }

    /**
     * Sets the value of the wolbachiaEffectOnMatingProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnMatingProbability(Double value) {
        this.wolbachiaEffectOnMatingProbability = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnLeakageRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnLeakageRate() {
        return wolbachiaEffectOnLeakageRate;
    }

    /**
     * Sets the value of the wolbachiaEffectOnLeakageRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnLeakageRate(Double value) {
        this.wolbachiaEffectOnLeakageRate = value;
    }

    /**
     * Gets the value of the wolbachiaEffectOnVectorialCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaEffectOnVectorialCapacity() {
        return wolbachiaEffectOnVectorialCapacity;
    }

    /**
     * Sets the value of the wolbachiaEffectOnVectorialCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaEffectOnVectorialCapacity(Double value) {
        this.wolbachiaEffectOnVectorialCapacity = value;
    }

    /**
     * Gets the value of the wolbachiaPreReleaseAdultSuppressionEfficacy property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaPreReleaseAdultSuppressionEfficacy() {
        return wolbachiaPreReleaseAdultSuppressionEfficacy;
    }

    /**
     * Sets the value of the wolbachiaPreReleaseAdultSuppressionEfficacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaPreReleaseAdultSuppressionEfficacy(Double value) {
        this.wolbachiaPreReleaseAdultSuppressionEfficacy = value;
    }

    /**
     * Gets the value of the wolbachiaPreReleaseLarvalSuppressionEfficacy property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWolbachiaPreReleaseLarvalSuppressionEfficacy() {
        return wolbachiaPreReleaseLarvalSuppressionEfficacy;
    }

    /**
     * Sets the value of the wolbachiaPreReleaseLarvalSuppressionEfficacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWolbachiaPreReleaseLarvalSuppressionEfficacy(Double value) {
        this.wolbachiaPreReleaseLarvalSuppressionEfficacy = value;
    }

}
