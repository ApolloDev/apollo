
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetPopulationAndEnvironmentCensusResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPopulationAndEnvironmentCensusResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="methodCallStatus" type="{http://types.apollo.pitt.edu/v2_0/}MethodCallStatus"/>
 *         &lt;element name="populationAndEnvironmentCensus" type="{http://types.apollo.pitt.edu/v2_0/}PopulationAndEnvironmentCensus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPopulationAndEnvironmentCensusResult", propOrder = {
    "methodCallStatus",
    "populationAndEnvironmentCensus"
})
public class GetPopulationAndEnvironmentCensusResult {

    @XmlElement(required = true)
    protected MethodCallStatus methodCallStatus;
    @XmlElement(required = true)
    protected PopulationAndEnvironmentCensus populationAndEnvironmentCensus;

    /**
     * Gets the value of the methodCallStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MethodCallStatus }
     *     
     */
    public MethodCallStatus getMethodCallStatus() {
        return methodCallStatus;
    }

    /**
     * Sets the value of the methodCallStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodCallStatus }
     *     
     */
    public void setMethodCallStatus(MethodCallStatus value) {
        this.methodCallStatus = value;
    }

    /**
     * Gets the value of the populationAndEnvironmentCensus property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationAndEnvironmentCensus }
     *     
     */
    public PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensus() {
        return populationAndEnvironmentCensus;
    }

    /**
     * Sets the value of the populationAndEnvironmentCensus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationAndEnvironmentCensus }
     *     
     */
    public void setPopulationAndEnvironmentCensus(PopulationAndEnvironmentCensus value) {
        this.populationAndEnvironmentCensus = value;
    }

}
