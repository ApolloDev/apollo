
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.SoftwareIdentification;


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
 *         &lt;element name="simulatorIdentification" type="{http://types.apollo.pitt.edu/v2_0/}SoftwareIdentification"/>
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
    "simulatorIdentification"
})
@XmlRootElement(name = "getScenarioLocationCodesSupportedBySimulator")
public class GetScenarioLocationCodesSupportedBySimulator {

    @XmlElement(required = true)
    protected SoftwareIdentification simulatorIdentification;

    /**
     * Gets the value of the simulatorIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareIdentification }
     *     
     */
    public SoftwareIdentification getSimulatorIdentification() {
        return simulatorIdentification;
    }

    /**
     * Sets the value of the simulatorIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareIdentification }
     *     
     */
    public void setSimulatorIdentification(SoftwareIdentification value) {
        this.simulatorIdentification = value;
    }

}
