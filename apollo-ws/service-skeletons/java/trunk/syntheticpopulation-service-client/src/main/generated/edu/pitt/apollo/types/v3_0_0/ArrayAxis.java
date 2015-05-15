
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayAxis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayAxis">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditioningVariableEnum"/>
 *         &lt;element name="categories" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseCountCategory" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayAxis", propOrder = {
    "name",
    "categories"
})
public class ArrayAxis {

    @XmlElement(required = true)
    protected ConditioningVariableEnum name;
    @XmlElement(required = true)
    protected List<CaseCountCategory> categories;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link ConditioningVariableEnum }
     *     
     */
    public ConditioningVariableEnum getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditioningVariableEnum }
     *     
     */
    public void setName(ConditioningVariableEnum value) {
        this.name = value;
    }

    /**
     * Gets the value of the categories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseCountCategory }
     * 
     * 
     */
    public List<CaseCountCategory> getCategories() {
        if (categories == null) {
            categories = new ArrayList<CaseCountCategory>();
        }
        return this.categories;
    }

}
