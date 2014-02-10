
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.GetScenarioLocationCodesSupportedBySimulatorResult;


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
 *         &lt;element name="getLocationsSupportedBySimulatorResult" type="{http://types.apollo.pitt.edu/v2_0/}GetScenarioLocationCodesSupportedBySimulatorResult"/>
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
    "getLocationsSupportedBySimulatorResult"
})
@XmlRootElement(name = "getScenarioLocationCodesSupportedBySimulatorResponse")
public class GetScenarioLocationCodesSupportedBySimulatorResponse {

    @XmlElement(required = true)
    protected GetScenarioLocationCodesSupportedBySimulatorResult getLocationsSupportedBySimulatorResult;

    /**
     * Gets the value of the getLocationsSupportedBySimulatorResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetScenarioLocationCodesSupportedBySimulatorResult }
     *     
     */
    public GetScenarioLocationCodesSupportedBySimulatorResult getGetLocationsSupportedBySimulatorResult() {
        return getLocationsSupportedBySimulatorResult;
    }

    /**
     * Sets the value of the getLocationsSupportedBySimulatorResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetScenarioLocationCodesSupportedBySimulatorResult }
     *     
     */
    public void setGetLocationsSupportedBySimulatorResult(GetScenarioLocationCodesSupportedBySimulatorResult value) {
        this.getLocationsSupportedBySimulatorResult = value;
    }

}
