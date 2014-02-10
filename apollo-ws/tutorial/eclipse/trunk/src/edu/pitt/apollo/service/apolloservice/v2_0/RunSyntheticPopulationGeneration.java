
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.RunSyntheticPopulationGenerationMessage;


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
 *         &lt;element name="runSyntheticPopulationGenerationMessage" type="{http://types.apollo.pitt.edu/v2_0/}RunSyntheticPopulationGenerationMessage"/>
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
    "runSyntheticPopulationGenerationMessage"
})
@XmlRootElement(name = "runSyntheticPopulationGeneration")
public class RunSyntheticPopulationGeneration {

    @XmlElement(required = true)
    protected RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage;

    /**
     * Gets the value of the runSyntheticPopulationGenerationMessage property.
     * 
     * @return
     *     possible object is
     *     {@link RunSyntheticPopulationGenerationMessage }
     *     
     */
    public RunSyntheticPopulationGenerationMessage getRunSyntheticPopulationGenerationMessage() {
        return runSyntheticPopulationGenerationMessage;
    }

    /**
     * Sets the value of the runSyntheticPopulationGenerationMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link RunSyntheticPopulationGenerationMessage }
     *     
     */
    public void setRunSyntheticPopulationGenerationMessage(RunSyntheticPopulationGenerationMessage value) {
        this.runSyntheticPopulationGenerationMessage = value;
    }

}
