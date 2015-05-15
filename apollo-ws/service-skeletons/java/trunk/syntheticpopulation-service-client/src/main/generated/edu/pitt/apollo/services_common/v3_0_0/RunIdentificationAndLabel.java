
package edu.pitt.apollo.services_common.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunIdentificationAndLabel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunIdentificationAndLabel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runIdentification" type="{http://services-common.apollo.pitt.edu/v3_0_0/}RunIdentification"/>
 *         &lt;element name="runLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunIdentificationAndLabel", propOrder = {
    "runIdentification",
    "runLabel"
})
public class RunIdentificationAndLabel {

    @XmlElement(required = true)
    protected BigInteger runIdentification;
    @XmlElement(required = true)
    protected String runLabel;

    /**
     * Gets the value of the runIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRunIdentification() {
        return runIdentification;
    }

    /**
     * Sets the value of the runIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRunIdentification(BigInteger value) {
        this.runIdentification = value;
    }

    /**
     * Gets the value of the runLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunLabel() {
        return runLabel;
    }

    /**
     * Sets the value of the runLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunLabel(String value) {
        this.runLabel = value;
    }

}
