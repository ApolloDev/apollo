
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualHumanBehavior complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualHumanBehavior">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}IndividualBehavior">
 *       &lt;sequence>
 *         &lt;element name="speedOfMovement" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate" minOccurs="0"/>
 *         &lt;element name="buildingAffinity" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualHumanBehavior", propOrder = {
    "speedOfMovement",
    "buildingAffinity"
})
public class IndividualHumanBehavior
    extends IndividualBehavior
{

    protected Rate speedOfMovement;
    protected Double buildingAffinity;

    /**
     * Gets the value of the speedOfMovement property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getSpeedOfMovement() {
        return speedOfMovement;
    }

    /**
     * Sets the value of the speedOfMovement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setSpeedOfMovement(Rate value) {
        this.speedOfMovement = value;
    }

    /**
     * Gets the value of the buildingAffinity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBuildingAffinity() {
        return buildingAffinity;
    }

    /**
     * Sets the value of the buildingAffinity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBuildingAffinity(Double value) {
        this.buildingAffinity = value;
    }

}
