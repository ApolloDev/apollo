
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.RunAndSoftwareIdentification;


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
 *         &lt;element name="runAndSoftwareIdentification" type="{http://types.apollo.pitt.edu/v2_0/}RunAndSoftwareIdentification"/>
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
    "runAndSoftwareIdentification"
})
@XmlRootElement(name = "getConfigurationFileForSimulation")
public class GetConfigurationFileForSimulation {

    @XmlElement(required = true)
    protected RunAndSoftwareIdentification runAndSoftwareIdentification;

    /**
     * Gets the value of the runAndSoftwareIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link RunAndSoftwareIdentification }
     *     
     */
    public RunAndSoftwareIdentification getRunAndSoftwareIdentification() {
        return runAndSoftwareIdentification;
    }

    /**
     * Sets the value of the runAndSoftwareIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link RunAndSoftwareIdentification }
     *     
     */
    public void setRunAndSoftwareIdentification(RunAndSoftwareIdentification value) {
        this.runAndSoftwareIdentification = value;
    }

}
