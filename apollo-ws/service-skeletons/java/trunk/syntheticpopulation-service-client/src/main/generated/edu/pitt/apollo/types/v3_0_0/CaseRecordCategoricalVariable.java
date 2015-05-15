
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseRecordCategoricalVariable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseRecordCategoricalVariable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="categoricalVariable" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditioningVariableEnum"/>
 *         &lt;element name="categories" type="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseRecordCategoricalVariable", propOrder = {
    "categoricalVariable",
    "categories"
})
public class CaseRecordCategoricalVariable {

    @XmlElement(required = true)
    protected ConditioningVariableEnum categoricalVariable;
    @XmlElement(required = true)
    protected List<CategoryDefinition> categories;

    /**
     * Gets the value of the categoricalVariable property.
     * 
     * @return
     *     possible object is
     *     {@link ConditioningVariableEnum }
     *     
     */
    public ConditioningVariableEnum getCategoricalVariable() {
        return categoricalVariable;
    }

    /**
     * Sets the value of the categoricalVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditioningVariableEnum }
     *     
     */
    public void setCategoricalVariable(ConditioningVariableEnum value) {
        this.categoricalVariable = value;
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
     * {@link CategoryDefinition }
     * 
     * 
     */
    public List<CategoryDefinition> getCategories() {
        if (categories == null) {
            categories = new ArrayList<CategoryDefinition>();
        }
        return this.categories;
    }

}
