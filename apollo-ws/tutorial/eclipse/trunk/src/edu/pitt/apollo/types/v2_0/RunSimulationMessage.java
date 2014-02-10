
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunSimulationMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunSimulationMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simulatorIdentification" type="{http://types.apollo.pitt.edu/v2_0/}SoftwareIdentification"/>
 *         &lt;element name="authentication" type="{http://types.apollo.pitt.edu/v2_0/}Authentication"/>
 *         &lt;element name="simulatorTimeSpecification" type="{http://types.apollo.pitt.edu/v2_0/}SimulatorTimeSpecification"/>
 *         &lt;element name="infectiousDiseaseScenario" type="{http://types.apollo.pitt.edu/v2_0/}InfectiousDiseaseScenario"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunSimulationMessage", propOrder = {
    "simulatorIdentification",
    "authentication",
    "simulatorTimeSpecification",
    "infectiousDiseaseScenario"
})
public class RunSimulationMessage {

    @XmlElement(required = true)
    protected SoftwareIdentification simulatorIdentification;
    @XmlElement(required = true)
    protected Authentication authentication;
    @XmlElement(required = true)
    protected SimulatorTimeSpecification simulatorTimeSpecification;
    @XmlElement(required = true)
    protected InfectiousDiseaseScenario infectiousDiseaseScenario;

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

    /**
     * Gets the value of the authentication property.
     * 
     * @return
     *     possible object is
     *     {@link Authentication }
     *     
     */
    public Authentication getAuthentication() {
        return authentication;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Authentication }
     *     
     */
    public void setAuthentication(Authentication value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the simulatorTimeSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link SimulatorTimeSpecification }
     *     
     */
    public SimulatorTimeSpecification getSimulatorTimeSpecification() {
        return simulatorTimeSpecification;
    }

    /**
     * Sets the value of the simulatorTimeSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimulatorTimeSpecification }
     *     
     */
    public void setSimulatorTimeSpecification(SimulatorTimeSpecification value) {
        this.simulatorTimeSpecification = value;
    }

    /**
     * Gets the value of the infectiousDiseaseScenario property.
     * 
     * @return
     *     possible object is
     *     {@link InfectiousDiseaseScenario }
     *     
     */
    public InfectiousDiseaseScenario getInfectiousDiseaseScenario() {
        return infectiousDiseaseScenario;
    }

    /**
     * Sets the value of the infectiousDiseaseScenario property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfectiousDiseaseScenario }
     *     
     */
    public void setInfectiousDiseaseScenario(InfectiousDiseaseScenario value) {
        this.infectiousDiseaseScenario = value;
    }

}
