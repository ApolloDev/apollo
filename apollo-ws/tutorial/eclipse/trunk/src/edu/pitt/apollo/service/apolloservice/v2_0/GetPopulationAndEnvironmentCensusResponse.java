
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.GetPopulationAndEnvironmentCensusResult;


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
 *         &lt;element name="getPopulationAndEnvironmentCensusResult" type="{http://types.apollo.pitt.edu/v2_0/}GetPopulationAndEnvironmentCensusResult"/>
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
    "getPopulationAndEnvironmentCensusResult"
})
@XmlRootElement(name = "getPopulationAndEnvironmentCensusResponse")
public class GetPopulationAndEnvironmentCensusResponse {

    @XmlElement(required = true)
    protected GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensusResult;

    /**
     * Gets the value of the getPopulationAndEnvironmentCensusResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetPopulationAndEnvironmentCensusResult }
     *     
     */
    public GetPopulationAndEnvironmentCensusResult getGetPopulationAndEnvironmentCensusResult() {
        return getPopulationAndEnvironmentCensusResult;
    }

    /**
     * Sets the value of the getPopulationAndEnvironmentCensusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPopulationAndEnvironmentCensusResult }
     *     
     */
    public void setGetPopulationAndEnvironmentCensusResult(GetPopulationAndEnvironmentCensusResult value) {
        this.getPopulationAndEnvironmentCensusResult = value;
    }

}
