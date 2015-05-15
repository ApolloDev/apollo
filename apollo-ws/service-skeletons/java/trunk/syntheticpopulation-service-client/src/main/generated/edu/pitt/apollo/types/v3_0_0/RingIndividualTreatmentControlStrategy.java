
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RingIndividualTreatmentControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RingIndividualTreatmentControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}IndividualTreatmentControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="ringDiameterInMeters" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RingIndividualTreatmentControlStrategy", propOrder = {
    "ringDiameterInMeters"
})
public class RingIndividualTreatmentControlStrategy
    extends IndividualTreatmentControlStrategy
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger ringDiameterInMeters;

    /**
     * Gets the value of the ringDiameterInMeters property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRingDiameterInMeters() {
        return ringDiameterInMeters;
    }

    /**
     * Sets the value of the ringDiameterInMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRingDiameterInMeters(BigInteger value) {
        this.ringDiameterInMeters = value;
    }

}
