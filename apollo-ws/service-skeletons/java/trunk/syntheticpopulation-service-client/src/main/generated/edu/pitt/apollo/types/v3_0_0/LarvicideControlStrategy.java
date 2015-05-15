
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LarvicideControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LarvicideControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}VectorControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="coverRadius" type="{http://types.apollo.pitt.edu/v3_0_0/}Distance"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LarvicideControlStrategy", propOrder = {
    "coverRadius"
})
public class LarvicideControlStrategy
    extends VectorControlStrategy
{

    @XmlElement(required = true)
    protected Distance coverRadius;

    /**
     * Gets the value of the coverRadius property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getCoverRadius() {
        return coverRadius;
    }

    /**
     * Sets the value of the coverRadius property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setCoverRadius(Distance value) {
        this.coverRadius = value;
    }

}
