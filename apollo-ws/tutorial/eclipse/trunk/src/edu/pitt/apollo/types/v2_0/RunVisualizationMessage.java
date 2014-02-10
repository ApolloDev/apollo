
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunVisualizationMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunVisualizationMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="visualizerIdentification" type="{http://types.apollo.pitt.edu/v2_0/}SoftwareIdentification"/>
 *         &lt;element name="authentication" type="{http://types.apollo.pitt.edu/v2_0/}Authentication"/>
 *         &lt;element name="visualizationOptions" type="{http://types.apollo.pitt.edu/v2_0/}VisualizationOptions"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunVisualizationMessage", propOrder = {
    "visualizerIdentification",
    "authentication",
    "visualizationOptions"
})
public class RunVisualizationMessage {

    @XmlElement(required = true)
    protected SoftwareIdentification visualizerIdentification;
    @XmlElement(required = true)
    protected Authentication authentication;
    @XmlElement(required = true)
    protected VisualizationOptions visualizationOptions;

    /**
     * Gets the value of the visualizerIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareIdentification }
     *     
     */
    public SoftwareIdentification getVisualizerIdentification() {
        return visualizerIdentification;
    }

    /**
     * Sets the value of the visualizerIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareIdentification }
     *     
     */
    public void setVisualizerIdentification(SoftwareIdentification value) {
        this.visualizerIdentification = value;
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
     * Gets the value of the visualizationOptions property.
     * 
     * @return
     *     possible object is
     *     {@link VisualizationOptions }
     *     
     */
    public VisualizationOptions getVisualizationOptions() {
        return visualizationOptions;
    }

    /**
     * Sets the value of the visualizationOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link VisualizationOptions }
     *     
     */
    public void setVisualizationOptions(VisualizationOptions value) {
        this.visualizationOptions = value;
    }

}
