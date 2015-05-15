
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactModelForHousehold complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactModelForHousehold">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ContactModelForSetting">
 *       &lt;sequence>
 *         &lt;element name="r_household" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate"/>
 *         &lt;element name="c_household" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="q_household" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactModelForHousehold", propOrder = {
    "rHousehold",
    "cHousehold",
    "qHousehold"
})
public class ContactModelForHousehold
    extends ContactModelForSetting
{

    @XmlElement(name = "r_household", required = true)
    protected Rate rHousehold;
    @XmlElement(name = "c_household")
    protected double cHousehold;
    @XmlElement(name = "q_household")
    protected double qHousehold;

    /**
     * Gets the value of the rHousehold property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getRHousehold() {
        return rHousehold;
    }

    /**
     * Sets the value of the rHousehold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setRHousehold(Rate value) {
        this.rHousehold = value;
    }

    /**
     * Gets the value of the cHousehold property.
     * 
     */
    public double getCHousehold() {
        return cHousehold;
    }

    /**
     * Sets the value of the cHousehold property.
     * 
     */
    public void setCHousehold(double value) {
        this.cHousehold = value;
    }

    /**
     * Gets the value of the qHousehold property.
     * 
     */
    public double getQHousehold() {
        return qHousehold;
    }

    /**
     * Sets the value of the qHousehold property.
     * 
     */
    public void setQHousehold(double value) {
        this.qHousehold = value;
    }

}
