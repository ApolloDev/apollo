
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Category complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Category">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="categoryDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition"/>
 *         &lt;choice>
 *           &lt;element name="unconditionalProbabilityDistribution" type="{http://types.apollo.pitt.edu/v3_0_0/}UnconditionalProbabilityDistribution"/>
 *           &lt;element name="conditioningVariable" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditioningVariable"/>
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
@XmlType(name = "Category", propOrder = {
    "categoryDefinition",
    "unconditionalProbabilityDistribution",
    "conditioningVariable"
})
public class Category {

    @XmlElement(required = true)
    protected CategoryDefinition categoryDefinition;
    protected UnconditionalProbabilityDistribution unconditionalProbabilityDistribution;
    protected ConditioningVariable conditioningVariable;

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
     * Gets the value of the unconditionalProbabilityDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link UnconditionalProbabilityDistribution }
     *     
     */
    public UnconditionalProbabilityDistribution getUnconditionalProbabilityDistribution() {
        return unconditionalProbabilityDistribution;
    }

    /**
     * Sets the value of the unconditionalProbabilityDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnconditionalProbabilityDistribution }
     *     
     */
    public void setUnconditionalProbabilityDistribution(UnconditionalProbabilityDistribution value) {
        this.unconditionalProbabilityDistribution = value;
    }

    /**
     * Gets the value of the conditioningVariable property.
     * 
     * @return
     *     possible object is
     *     {@link ConditioningVariable }
     *     
     */
    public ConditioningVariable getConditioningVariable() {
        return conditioningVariable;
    }

    /**
     * Sets the value of the conditioningVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditioningVariable }
     *     
     */
    public void setConditioningVariable(ConditioningVariable value) {
        this.conditioningVariable = value;
    }

}
