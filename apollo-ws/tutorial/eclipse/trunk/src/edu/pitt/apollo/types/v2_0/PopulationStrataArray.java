
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationStrataArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationStrataArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://types.apollo.pitt.edu/v2_0/}PopulationStrataDefinition"/>
 *         &lt;element name="values" type="{http://types.apollo.pitt.edu/v2_0/}Fraction" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationStrataArray", propOrder = {
    "description",
    "values"
})
public class PopulationStrataArray {

    @XmlElement(required = true)
    protected PopulationStrataDefinition description;
    @XmlElement(type = Double.class)
    protected List<Double> values;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationStrataDefinition }
     *     
     */
    public PopulationStrataDefinition getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationStrataDefinition }
     *     
     */
    public void setDescription(PopulationStrataDefinition value) {
        this.description = value;
    }

    /**
     * Gets the value of the values property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getValues() {
        if (values == null) {
            values = new ArrayList<Double>();
        }
        return this.values;
    }

}
