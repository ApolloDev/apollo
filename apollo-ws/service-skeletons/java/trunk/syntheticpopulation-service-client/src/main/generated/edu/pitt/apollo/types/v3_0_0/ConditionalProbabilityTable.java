
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditionalProbabilityTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionalProbabilityTable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="axisDefinitions" type="{http://types.apollo.pitt.edu/v3_0_0/}ArrayDimensionsDefinition"/>
 *         &lt;choice>
 *           &lt;element name="pointProbabilities" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability" maxOccurs="unbounded"/>
 *           &lt;element name="parametricDistribution" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability" maxOccurs="unbounded"/>
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
@XmlType(name = "ConditionalProbabilityTable", propOrder = {
    "axisDefinitions",
    "pointProbabilities",
    "parametricDistribution"
})
public class ConditionalProbabilityTable {

    @XmlElement(required = true)
    protected ArrayDimensionsDefinition axisDefinitions;
    @XmlElement(type = Double.class)
    protected List<Double> pointProbabilities;
    @XmlElement(type = Double.class)
    protected List<Double> parametricDistribution;

    /**
     * Gets the value of the axisDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayDimensionsDefinition }
     *     
     */
    public ArrayDimensionsDefinition getAxisDefinitions() {
        return axisDefinitions;
    }

    /**
     * Sets the value of the axisDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayDimensionsDefinition }
     *     
     */
    public void setAxisDefinitions(ArrayDimensionsDefinition value) {
        this.axisDefinitions = value;
    }

    /**
     * Gets the value of the pointProbabilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pointProbabilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPointProbabilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getPointProbabilities() {
        if (pointProbabilities == null) {
            pointProbabilities = new ArrayList<Double>();
        }
        return this.pointProbabilities;
    }

    /**
     * Gets the value of the parametricDistribution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametricDistribution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametricDistribution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getParametricDistribution() {
        if (parametricDistribution == null) {
            parametricDistribution = new ArrayList<Double>();
        }
        return this.parametricDistribution;
    }

}
