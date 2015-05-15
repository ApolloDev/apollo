
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactModelForCommunity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactModelForCommunity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ContactModelForSetting">
 *       &lt;sequence>
 *         &lt;element name="r_community" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate"/>
 *         &lt;element name="c_community" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="q_community" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactModelForCommunity", propOrder = {
    "rCommunity",
    "cCommunity",
    "qCommunity"
})
public class ContactModelForCommunity
    extends ContactModelForSetting
{

    @XmlElement(name = "r_community", required = true)
    protected Rate rCommunity;
    @XmlElement(name = "c_community")
    protected double cCommunity;
    @XmlElement(name = "q_community")
    protected double qCommunity;

    /**
     * Gets the value of the rCommunity property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getRCommunity() {
        return rCommunity;
    }

    /**
     * Sets the value of the rCommunity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setRCommunity(Rate value) {
        this.rCommunity = value;
    }

    /**
     * Gets the value of the cCommunity property.
     * 
     */
    public double getCCommunity() {
        return cCommunity;
    }

    /**
     * Sets the value of the cCommunity property.
     * 
     */
    public void setCCommunity(double value) {
        this.cCommunity = value;
    }

    /**
     * Gets the value of the qCommunity property.
     * 
     */
    public double getQCommunity() {
        return qCommunity;
    }

    /**
     * Sets the value of the qCommunity property.
     * 
     */
    public void setQCommunity(double value) {
        this.qCommunity = value;
    }

}
