
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for RunSimulationsResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunSimulationsResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="completedFile" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="runId" type="{http://types.apollo.pitt.edu/v2_0/}RunIdentification"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunSimulationsResult", propOrder = {
    "completedFile",
    "runId"
})
public class RunSimulationsResult {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String completedFile;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String runId;

    /**
     * Gets the value of the completedFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompletedFile() {
        return completedFile;
    }

    /**
     * Sets the value of the completedFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompletedFile(String value) {
        this.completedFile = value;
    }

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

}
