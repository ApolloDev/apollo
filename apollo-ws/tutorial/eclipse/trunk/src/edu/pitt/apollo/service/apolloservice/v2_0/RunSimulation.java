
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;


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
 *         &lt;element name="runSimulationMessage" type="{http://types.apollo.pitt.edu/v2_0/}RunSimulationMessage"/>
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
    "runSimulationMessage"
})
@XmlRootElement(name = "runSimulation")
public class RunSimulation {

    @XmlElement(required = true)
    protected RunSimulationMessage runSimulationMessage;

    /**
     * Gets the value of the runSimulationMessage property.
     * 
     * @return
     *     possible object is
     *     {@link RunSimulationMessage }
     *     
     */
    public RunSimulationMessage getRunSimulationMessage() {
        return runSimulationMessage;
    }

    /**
     * Sets the value of the runSimulationMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link RunSimulationMessage }
     *     
     */
    public void setRunSimulationMessage(RunSimulationMessage value) {
        this.runSimulationMessage = value;
    }

}
