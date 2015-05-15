
package edu.pitt.apollo.synthetic_population_service_types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;


/**
 * <p>Java class for GetConfigurationFileForSimulationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetConfigurationFileForSimulationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="methodCallStatus" type="{http://services-common.apollo.pitt.edu/v3_0_0/}MethodCallStatus"/>
 *         &lt;element name="configurationFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="configurationFileInHtmlFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetConfigurationFileForSimulationResult", propOrder = {
    "methodCallStatus",
    "configurationFile",
    "configurationFileInHtmlFormat"
})
public class GetConfigurationFileForSimulationResult {

    @XmlElement(required = true)
    protected MethodCallStatus methodCallStatus;
    protected String configurationFile;
    protected String configurationFileInHtmlFormat;

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
     * Gets the value of the configurationFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationFile() {
        return configurationFile;
    }

    /**
     * Sets the value of the configurationFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationFile(String value) {
        this.configurationFile = value;
    }

    /**
     * Gets the value of the configurationFileInHtmlFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationFileInHtmlFormat() {
        return configurationFileInHtmlFormat;
    }

    /**
     * Sets the value of the configurationFileInHtmlFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationFileInHtmlFormat(String value) {
        this.configurationFileInHtmlFormat = value;
    }

}
