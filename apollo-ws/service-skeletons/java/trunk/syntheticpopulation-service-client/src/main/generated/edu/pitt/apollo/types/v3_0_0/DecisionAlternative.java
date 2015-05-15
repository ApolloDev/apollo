
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DecisionAlternative complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DecisionAlternative">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="singleStrategy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="combinationStrategy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SequentialCombinationStrategy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DecisionAlternative", propOrder = {
    "singleStrategy",
    "combinationStrategy",
    "sequentialCombinationStrategy"
})
public class DecisionAlternative {

    @XmlElement(required = true)
    protected String singleStrategy;
    @XmlElement(required = true)
    protected String combinationStrategy;
    @XmlElement(name = "SequentialCombinationStrategy", required = true)
    protected String sequentialCombinationStrategy;

    /**
     * Gets the value of the singleStrategy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSingleStrategy() {
        return singleStrategy;
    }

    /**
     * Sets the value of the singleStrategy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSingleStrategy(String value) {
        this.singleStrategy = value;
    }

    /**
     * Gets the value of the combinationStrategy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombinationStrategy() {
        return combinationStrategy;
    }

    /**
     * Sets the value of the combinationStrategy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombinationStrategy(String value) {
        this.combinationStrategy = value;
    }

    /**
     * Gets the value of the sequentialCombinationStrategy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequentialCombinationStrategy() {
        return sequentialCombinationStrategy;
    }

    /**
     * Sets the value of the sequentialCombinationStrategy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequentialCombinationStrategy(String value) {
        this.sequentialCombinationStrategy = value;
    }

}
