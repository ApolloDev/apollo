//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.05 at 06:45:13 PM EST 
//


package edu.pitt.rods.research.apollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseModelParamObjType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseModelParamObjType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TemporalGranularity" type="{http://research.rods.pitt.edu/apollo/}TemporalGranularityEnum"/>
 *         &lt;element name="TemporalIncrement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RunLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseModelParamObjType", propOrder = {
    "temporalGranularity",
    "temporalIncrement",
    "runLength",
    "username",
    "password"
})
@XmlSeeAlso({
    edu.pitt.rods.research.apollo.CompartmentModelRunRequestObjType.Parameters.Recognized.class,
    edu.pitt.rods.research.apollo.SEIRModelRunRequestObjType.Parameters.Recognized.class,
    edu.pitt.rods.research.apollo.AgentBasedModelRunRequestObjType.Parameters.Recognized.class
})
public class BaseModelParamObjType {

    @XmlElement(name = "TemporalGranularity", required = true)
    protected TemporalGranularityEnum temporalGranularity;
    @XmlElement(name = "TemporalIncrement")
    protected int temporalIncrement;
    @XmlElement(name = "RunLength")
    protected int runLength;
    @XmlElement(name = "Username", required = true)
    protected String username;
    @XmlElement(name = "Password", required = true)
    protected String password;

    /**
     * Gets the value of the temporalGranularity property.
     * 
     * @return
     *     possible object is
     *     {@link TemporalGranularityEnum }
     *     
     */
    public TemporalGranularityEnum getTemporalGranularity() {
        return temporalGranularity;
    }

    /**
     * Sets the value of the temporalGranularity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TemporalGranularityEnum }
     *     
     */
    public void setTemporalGranularity(TemporalGranularityEnum value) {
        this.temporalGranularity = value;
    }

    /**
     * Gets the value of the temporalIncrement property.
     * 
     */
    public int getTemporalIncrement() {
        return temporalIncrement;
    }

    /**
     * Sets the value of the temporalIncrement property.
     * 
     */
    public void setTemporalIncrement(int value) {
        this.temporalIncrement = value;
    }

    /**
     * Gets the value of the runLength property.
     * 
     */
    public int getRunLength() {
        return runLength;
    }

    /**
     * Sets the value of the runLength property.
     * 
     */
    public void setRunLength(int value) {
        this.runLength = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
