
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for VisualizerResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VisualizerResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runId" type="{http://types.apollo.pitt.edu/v2_0/}RunIdentification"/>
 *         &lt;element name="visualizerOutputResource" type="{http://types.apollo.pitt.edu/v2_0/}UrlOutputResource" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VisualizerResult", propOrder = {
    "runId",
    "visualizerOutputResource"
})
public class VisualizerResult {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String runId;
    @XmlElement(required = true)
    protected List<UrlOutputResource> visualizerOutputResource;

    /**
     * Gets the value of the runId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunId() {
        return runId;
    }

    /**
     * Sets the value of the runId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunId(String value) {
        this.runId = value;
    }

    /**
     * Gets the value of the visualizerOutputResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the visualizerOutputResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVisualizerOutputResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UrlOutputResource }
     * 
     * 
     */
    public List<UrlOutputResource> getVisualizerOutputResource() {
        if (visualizerOutputResource == null) {
            visualizerOutputResource = new ArrayList<UrlOutputResource>();
        }
        return this.visualizerOutputResource;
    }

}
