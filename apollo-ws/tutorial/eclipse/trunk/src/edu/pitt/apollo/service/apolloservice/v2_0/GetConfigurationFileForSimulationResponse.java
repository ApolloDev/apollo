
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.GetConfigurationFileForSimulationResult;


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
 *         &lt;element name="getConfigurationFileForSimulationResult" type="{http://types.apollo.pitt.edu/v2_0/}GetConfigurationFileForSimulationResult"/>
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
    "getConfigurationFileForSimulationResult"
})
@XmlRootElement(name = "getConfigurationFileForSimulationResponse")
public class GetConfigurationFileForSimulationResponse {

    @XmlElement(required = true)
    protected GetConfigurationFileForSimulationResult getConfigurationFileForSimulationResult;

    /**
     * Gets the value of the getConfigurationFileForSimulationResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetConfigurationFileForSimulationResult }
     *     
     */
    public GetConfigurationFileForSimulationResult getGetConfigurationFileForSimulationResult() {
        return getConfigurationFileForSimulationResult;
    }

    /**
     * Sets the value of the getConfigurationFileForSimulationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetConfigurationFileForSimulationResult }
     *     
     */
    public void setGetConfigurationFileForSimulationResult(GetConfigurationFileForSimulationResult value) {
        this.getConfigurationFileForSimulationResult = value;
    }

}
