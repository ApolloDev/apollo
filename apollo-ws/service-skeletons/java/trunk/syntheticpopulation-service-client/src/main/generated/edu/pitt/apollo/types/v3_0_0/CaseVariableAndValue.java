
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseVariableAndValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseVariableAndValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="variable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categoryDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseVariableAndValue", propOrder = {
    "variable",
    "categoryDefinition",
    "value"
})
public class CaseVariableAndValue {

    @XmlElement(required = true)
    protected String variable;
    @XmlElement(required = true)
    protected CategoryDefinition categoryDefinition;
    @XmlElement(required = true)
    protected String value;

    /**
     * Gets the value of the variable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Sets the value of the variable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariable(String value) {
        this.variable = value;
    }

    /**
     * Gets the value of the categoryDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryDefinition }
     *     
     */
    public CategoryDefinition getCategoryDefinition() {
        return categoryDefinition;
    }

    /**
     * Sets the value of the categoryDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryDefinition }
     *     
     */
    public void setCategoryDefinition(CategoryDefinition value) {
        this.categoryDefinition = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
