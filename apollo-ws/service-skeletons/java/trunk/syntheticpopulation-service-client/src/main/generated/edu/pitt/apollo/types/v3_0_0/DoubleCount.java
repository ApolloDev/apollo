
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoubleCount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DoubleCount">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CountType">
 *       &lt;sequence>
 *         &lt;element name="double" type="{http://types.apollo.pitt.edu/v3_0_0/}NonNegativeDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoubleCount", propOrder = {
    "_double"
})
public class DoubleCount
    extends CountType
{

    @XmlElement(name = "double")
    protected double _double;

    /**
     * Gets the value of the double property.
     * 
     */
    public double getDouble() {
        return _double;
    }

    /**
     * Sets the value of the double property.
     * 
     */
    public void setDouble(double value) {
        this._double = value;
    }

}
