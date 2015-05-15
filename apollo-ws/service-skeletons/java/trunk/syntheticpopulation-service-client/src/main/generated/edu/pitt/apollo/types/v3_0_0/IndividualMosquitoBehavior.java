
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualMosquitoBehavior complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualMosquitoBehavior">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}IndividualBehavior">
 *       &lt;sequence>
 *         &lt;element name="biteProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter" minOccurs="0"/>
 *         &lt;element name="biteRadius" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance" minOccurs="0"/>
 *         &lt;element name="timeBetweenBites" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration" minOccurs="0"/>
 *         &lt;element name="migrationSpeed" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate" minOccurs="0"/>
 *         &lt;element name="shadeAffinity" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *         &lt;element name="maleMatingRadius" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance" minOccurs="0"/>
 *         &lt;element name="matingProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability" minOccurs="0"/>
 *         &lt;element name="straightTravelAfterOviposit" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualMosquitoBehavior", propOrder = {
    "biteProbability",
    "biteRadius",
    "timeBetweenBites",
    "migrationSpeed",
    "shadeAffinity",
    "maleMatingRadius",
    "matingProbability",
    "straightTravelAfterOviposit"
})
public class IndividualMosquitoBehavior
    extends IndividualBehavior
{

    protected ProbabilisticParameter biteProbability;
    protected Distance biteRadius;
    protected Duration timeBetweenBites;
    protected Rate migrationSpeed;
    protected Double shadeAffinity;
    protected Distance maleMatingRadius;
    protected Double matingProbability;
    protected Distance straightTravelAfterOviposit;

    /**
     * Gets the value of the biteProbability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getBiteProbability() {
        return biteProbability;
    }

    /**
     * Sets the value of the biteProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setBiteProbability(ProbabilisticParameter value) {
        this.biteProbability = value;
    }

    /**
     * Gets the value of the biteRadius property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getBiteRadius() {
        return biteRadius;
    }

    /**
     * Sets the value of the biteRadius property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setBiteRadius(Distance value) {
        this.biteRadius = value;
    }

    /**
     * Gets the value of the timeBetweenBites property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getTimeBetweenBites() {
        return timeBetweenBites;
    }

    /**
     * Sets the value of the timeBetweenBites property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setTimeBetweenBites(Duration value) {
        this.timeBetweenBites = value;
    }

    /**
     * Gets the value of the migrationSpeed property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getMigrationSpeed() {
        return migrationSpeed;
    }

    /**
     * Sets the value of the migrationSpeed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setMigrationSpeed(Rate value) {
        this.migrationSpeed = value;
    }

    /**
     * Gets the value of the shadeAffinity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getShadeAffinity() {
        return shadeAffinity;
    }

    /**
     * Sets the value of the shadeAffinity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setShadeAffinity(Double value) {
        this.shadeAffinity = value;
    }

    /**
     * Gets the value of the maleMatingRadius property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getMaleMatingRadius() {
        return maleMatingRadius;
    }

    /**
     * Sets the value of the maleMatingRadius property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setMaleMatingRadius(Distance value) {
        this.maleMatingRadius = value;
    }

    /**
     * Gets the value of the matingProbability property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMatingProbability() {
        return matingProbability;
    }

    /**
     * Sets the value of the matingProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMatingProbability(Double value) {
        this.matingProbability = value;
    }

    /**
     * Gets the value of the straightTravelAfterOviposit property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getStraightTravelAfterOviposit() {
        return straightTravelAfterOviposit;
    }

    /**
     * Sets the value of the straightTravelAfterOviposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setStraightTravelAfterOviposit(Distance value) {
        this.straightTravelAfterOviposit = value;
    }

}
