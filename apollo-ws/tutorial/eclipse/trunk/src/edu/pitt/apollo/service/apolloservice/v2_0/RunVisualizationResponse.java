
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.VisualizerResult;


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
 *         &lt;element name="visualizationResult" type="{http://types.apollo.pitt.edu/v2_0/}VisualizerResult"/>
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
    "visualizationResult"
})
@XmlRootElement(name = "runVisualizationResponse")
public class RunVisualizationResponse {

    @XmlElement(required = true)
    protected VisualizerResult visualizationResult;

    /**
     * Gets the value of the visualizationResult property.
     * 
     * @return
     *     possible object is
     *     {@link VisualizerResult }
     *     
     */
    public VisualizerResult getVisualizationResult() {
        return visualizationResult;
    }

    /**
     * Sets the value of the visualizationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link VisualizerResult }
     *     
     */
    public void setVisualizationResult(VisualizerResult value) {
        this.visualizationResult = value;
    }

}
