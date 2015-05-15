
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TemporalArrayDimensionsDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TemporalArrayDimensionsDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ArrayDimensionsDefinition">
 *       &lt;sequence>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemporalArrayDimensionsDefinition", propOrder = {
    "time"
})
public class TemporalArrayDimensionsDefinition
    extends ArrayDimensionsDefinition
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected List<BigInteger> time;

    /**
     * Gets the value of the time property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the time property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getTime() {
        if (time == null) {
            time = new ArrayList<BigInteger>();
        }
        return this.time;
    }

}
