
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Interval complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Interval">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startBoundaryDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}IntervalBoundaryDefinitionEnum"/>
 *         &lt;element name="endBoundaryDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}IntervalBoundaryDefinitionEnum"/>
 *         &lt;element name="duration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="readableTitle" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Interval", propOrder = {
    "startBoundaryDefinition",
    "endBoundaryDefinition",
    "duration",
    "readableTitle",
    "referenceId"
})
public class Interval {

    @XmlElement(required = true)
    protected IntervalBoundaryDefinitionEnum startBoundaryDefinition;
    @XmlElement(required = true)
    protected IntervalBoundaryDefinitionEnum endBoundaryDefinition;
    @XmlElement(required = true)
    protected Duration duration;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String readableTitle;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the startBoundaryDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link IntervalBoundaryDefinitionEnum }
     *     
     */
    public IntervalBoundaryDefinitionEnum getStartBoundaryDefinition() {
        return startBoundaryDefinition;
    }

    /**
     * Sets the value of the startBoundaryDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntervalBoundaryDefinitionEnum }
     *     
     */
    public void setStartBoundaryDefinition(IntervalBoundaryDefinitionEnum value) {
        this.startBoundaryDefinition = value;
    }

    /**
     * Gets the value of the endBoundaryDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link IntervalBoundaryDefinitionEnum }
     *     
     */
    public IntervalBoundaryDefinitionEnum getEndBoundaryDefinition() {
        return endBoundaryDefinition;
    }

    /**
     * Sets the value of the endBoundaryDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntervalBoundaryDefinitionEnum }
     *     
     */
    public void setEndBoundaryDefinition(IntervalBoundaryDefinitionEnum value) {
        this.endBoundaryDefinition = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the readableTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadableTitle() {
        return readableTitle;
    }

    /**
     * Sets the value of the readableTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadableTitle(String value) {
        this.readableTitle = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReferenceId(BigInteger value) {
        this.referenceId = value;
    }

}
