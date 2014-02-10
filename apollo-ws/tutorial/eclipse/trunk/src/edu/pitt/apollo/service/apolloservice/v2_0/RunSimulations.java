
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.RunSimulationsMessage;


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
 *         &lt;element name="runSimulationsMessage" type="{http://types.apollo.pitt.edu/v2_0/}RunSimulationsMessage"/>
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
    "runSimulationsMessage"
})
@XmlRootElement(name = "runSimulations")
public class RunSimulations {

    @XmlElement(required = true)
    protected RunSimulationsMessage runSimulationsMessage;

    /**
     * Gets the value of the runSimulationsMessage property.
     * 
     * @return
     *     possible object is
     *     {@link RunSimulationsMessage }
     *     
     */
    public RunSimulationsMessage getRunSimulationsMessage() {
        return runSimulationsMessage;
    }

    /**
     * Sets the value of the runSimulationsMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link RunSimulationsMessage }
     *     
     */
    public void setRunSimulationsMessage(RunSimulationsMessage value) {
        this.runSimulationsMessage = value;
    }

}
