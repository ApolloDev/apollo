
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Distance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Distance">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ParameterValue">
 *       &lt;sequence>
 *         &lt;element name="unitOfDistance" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfDistanceEnum"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Distance", propOrder = {
    "unitOfDistance",
    "value"
})
public class Distance
    extends ParameterValue
{

    @XmlElement(required = true)
    protected UnitOfDistanceEnum unitOfDistance;
    protected double value;

    /**
     * Gets the value of the unitOfDistance property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfDistanceEnum }
     *     
     */
    public UnitOfDistanceEnum getUnitOfDistance() {
        return unitOfDistance;
    }

    /**
     * Sets the value of the unitOfDistance property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfDistanceEnum }
     *     
     */
    public void setUnitOfDistance(UnitOfDistanceEnum value) {
        this.unitOfDistance = value;
    }

    /**
     * Gets the value of the value property.
     * 
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(double value) {
        this.value = value;
    }

}
