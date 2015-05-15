
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseCountArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseCountArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nDimensions" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="firstArrayAxis" type="{http://types.apollo.pitt.edu/v3_0_0/}ArrayAxis"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseCountArray", propOrder = {
    "nDimensions",
    "firstArrayAxis"
})
public class CaseCountArray {

    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nDimensions;
    @XmlElement(required = true)
    protected ArrayAxis firstArrayAxis;

    /**
     * Gets the value of the nDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNDimensions() {
        return nDimensions;
    }

    /**
     * Sets the value of the nDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNDimensions(BigInteger value) {
        this.nDimensions = value;
    }

    /**
     * Gets the value of the firstArrayAxis property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayAxis }
     *     
     */
    public ArrayAxis getFirstArrayAxis() {
        return firstArrayAxis;
    }

    /**
     * Sets the value of the firstArrayAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayAxis }
     *     
     */
    public void setFirstArrayAxis(ArrayAxis value) {
        this.firstArrayAxis = value;
    }

}
