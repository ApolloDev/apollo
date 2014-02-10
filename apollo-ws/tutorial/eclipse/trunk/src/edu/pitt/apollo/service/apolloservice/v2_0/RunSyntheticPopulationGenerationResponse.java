
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.SyntheticPopulationGenerationResult;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="syntheticPopulationGenerationResult" type="{http://types.apollo.pitt.edu/v2_0/}SyntheticPopulationGenerationResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "syntheticPopulationGenerationResult"
})
@XmlRootElement(name = "runSyntheticPopulationGenerationResponse")
public class RunSyntheticPopulationGenerationResponse {

    @XmlElement(required = true)
    protected SyntheticPopulationGenerationResult syntheticPopulationGenerationResult;

    /**
     * Gets the value of the syntheticPopulationGenerationResult property.
     * 
     * @return
     *     possible object is
     *     {@link SyntheticPopulationGenerationResult }
     *     
     */
    public SyntheticPopulationGenerationResult getSyntheticPopulationGenerationResult() {
        return syntheticPopulationGenerationResult;
    }

    /**
     * Sets the value of the syntheticPopulationGenerationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyntheticPopulationGenerationResult }
     *     
     */
    public void setSyntheticPopulationGenerationResult(SyntheticPopulationGenerationResult value) {
        this.syntheticPopulationGenerationResult = value;
    }

}
