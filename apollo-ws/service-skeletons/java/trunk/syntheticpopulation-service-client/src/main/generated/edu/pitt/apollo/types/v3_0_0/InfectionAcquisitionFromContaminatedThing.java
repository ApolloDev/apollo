
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfectionAcquisitionFromContaminatedThing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectionAcquisitionFromContaminatedThing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abioticEcosystemType" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="transmissionProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectionAcquisitionFromContaminatedThing", propOrder = {
    "abioticEcosystemType",
    "transmissionProbability"
})
public class InfectionAcquisitionFromContaminatedThing {

    @XmlElement(required = true)
    protected AbioticEcosystemEnum abioticEcosystemType;
    @XmlElement(required = true)
    protected ProbabilisticParameter transmissionProbability;

    /**
     * Gets the value of the abioticEcosystemType property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getAbioticEcosystemType() {
        return abioticEcosystemType;
    }

    /**
     * Sets the value of the abioticEcosystemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setAbioticEcosystemType(AbioticEcosystemEnum value) {
        this.abioticEcosystemType = value;
    }

    /**
     * Gets the value of the transmissionProbability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getTransmissionProbability() {
        return transmissionProbability;
    }

    /**
     * Sets the value of the transmissionProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setTransmissionProbability(ProbabilisticParameter value) {
        this.transmissionProbability = value;
    }

}
