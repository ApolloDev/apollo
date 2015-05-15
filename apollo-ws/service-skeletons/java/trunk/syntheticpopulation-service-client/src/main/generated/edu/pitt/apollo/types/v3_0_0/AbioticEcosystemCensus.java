
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbioticEcosystemCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbioticEcosystemCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Census">
 *       &lt;sequence>
 *         &lt;element name="abioticEcosystemElement" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbioticEcosystemCensus", propOrder = {
    "abioticEcosystemElement"
})
@XmlSeeAlso({
    OvipositionSiteCensus.class
})
public class AbioticEcosystemCensus
    extends Census
{

    @XmlElement(required = true)
    protected AbioticEcosystemEnum abioticEcosystemElement;

    /**
     * Gets the value of the abioticEcosystemElement property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getAbioticEcosystemElement() {
        return abioticEcosystemElement;
    }

    /**
     * Sets the value of the abioticEcosystemElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setAbioticEcosystemElement(AbioticEcosystemEnum value) {
        this.abioticEcosystemElement = value;
    }

}
