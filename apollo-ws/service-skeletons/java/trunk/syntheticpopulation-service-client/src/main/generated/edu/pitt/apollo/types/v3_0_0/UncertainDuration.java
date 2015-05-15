
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UncertainDuration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UncertainDuration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Duration">
 *       &lt;sequence>
 *         &lt;element name="probabilityDistribution" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UncertainDuration", propOrder = {
    "probabilityDistribution"
})
public class UncertainDuration
    extends Duration
{

    @XmlElement(required = true)
    protected ProbabilityDistribution probabilityDistribution;

    /**
     * Gets the value of the probabilityDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public ProbabilityDistribution getProbabilityDistribution() {
        return probabilityDistribution;
    }

    /**
     * Sets the value of the probabilityDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilityDistribution }
     *     
     */
    public void setProbabilityDistribution(ProbabilityDistribution value) {
        this.probabilityDistribution = value;
    }

}
