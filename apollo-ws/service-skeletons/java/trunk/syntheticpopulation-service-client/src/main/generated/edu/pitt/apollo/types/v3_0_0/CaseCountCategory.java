
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseCountCategory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseCountCategory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="categoryDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition"/>
 *         &lt;choice>
 *           &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *           &lt;element name="arrayAxis" type="{http://types.apollo.pitt.edu/v3_0_0/}ArrayAxis"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseCountCategory", propOrder = {
    "categoryDefinition",
    "count",
    "arrayAxis"
})
public class CaseCountCategory {

    @XmlElement(required = true)
    protected CategoryDefinition categoryDefinition;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger count;
    protected ArrayAxis arrayAxis;

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
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCount(BigInteger value) {
        this.count = value;
    }

    /**
     * Gets the value of the arrayAxis property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayAxis }
     *     
     */
    public ArrayAxis getArrayAxis() {
        return arrayAxis;
    }

    /**
     * Sets the value of the arrayAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayAxis }
     *     
     */
    public void setArrayAxis(ArrayAxis value) {
        this.arrayAxis = value;
    }

}
