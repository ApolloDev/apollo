
package edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="serviceResult" type="{http://services-common.apollo.pitt.edu/v3_0_0/}RunIdentification"/>
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
    "serviceResult"
})
@XmlRootElement(name = "runSyntheticPopulationGenerationResponse")
public class RunSyntheticPopulationGenerationResponse {

    @XmlElement(required = true)
    protected BigInteger serviceResult;

    /**
     * Gets the value of the serviceResult property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getServiceResult() {
        return serviceResult;
    }

    /**
     * Sets the value of the serviceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setServiceResult(BigInteger value) {
        this.serviceResult = value;
    }

}
