
package edu.pitt.apollo.synthetic_population_service_types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;


/**
 * <p>Java class for SyntheticPopulationRunStatusMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SyntheticPopulationRunStatusMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runStatus" type="{http://services-common.apollo.pitt.edu/v3_0_0/}MethodCallStatusEnum"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="completedRunUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyntheticPopulationRunStatusMessage", propOrder = {
    "runStatus",
    "errorMessage",
    "completedRunUrl"
})
public class SyntheticPopulationRunStatusMessage {

    @XmlElement(required = true)
    protected MethodCallStatusEnum runStatus;
    protected String errorMessage;
    protected String completedRunUrl;

    /**
     * Gets the value of the runStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MethodCallStatusEnum }
     *     
     */
    public MethodCallStatusEnum getRunStatus() {
        return runStatus;
    }

    /**
     * Sets the value of the runStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodCallStatusEnum }
     *     
     */
    public void setRunStatus(MethodCallStatusEnum value) {
        this.runStatus = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the completedRunUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompletedRunUrl() {
        return completedRunUrl;
    }

    /**
     * Sets the value of the completedRunUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompletedRunUrl(String value) {
        this.completedRunUrl = value;
    }

}
