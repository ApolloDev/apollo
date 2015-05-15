
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TravelRestrictionControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TravelRestrictionControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="maximumTravelDistanceInKm" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *         &lt;element name="compliance" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelRestrictionControlStrategy", propOrder = {
    "maximumTravelDistanceInKm",
    "compliance"
})
public class TravelRestrictionControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    protected double maximumTravelDistanceInKm;
    @XmlElement(required = true)
    protected ProbabilisticParameter compliance;

    /**
     * Gets the value of the maximumTravelDistanceInKm property.
     * 
     */
    public double getMaximumTravelDistanceInKm() {
        return maximumTravelDistanceInKm;
    }

    /**
     * Sets the value of the maximumTravelDistanceInKm property.
     * 
     */
    public void setMaximumTravelDistanceInKm(double value) {
        this.maximumTravelDistanceInKm = value;
    }

    /**
     * Gets the value of the compliance property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getCompliance() {
        return compliance;
    }

    /**
     * Sets the value of the compliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setCompliance(ProbabilisticParameter value) {
        this.compliance = value;
    }

}
