
package edu.pitt.apollo.types.v2_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ReactiveTriggersDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReactiveTriggersDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reactiveControlStrategyTest" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="reactiveControlStrategyThreshold" type="{http://types.apollo.pitt.edu/v2_0/}Fraction"/>
 *         &lt;element name="ascertainmentFraction" type="{http://types.apollo.pitt.edu/v2_0/}Fraction"/>
 *         &lt;element name="ascertainmentDelay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReactiveTriggersDefinition", propOrder = {
    "reactiveControlStrategyTest",
    "reactiveControlStrategyThreshold",
    "ascertainmentFraction",
    "ascertainmentDelay"
})
public class ReactiveTriggersDefinition {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reactiveControlStrategyTest;
    protected double reactiveControlStrategyThreshold;
    protected double ascertainmentFraction;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger ascertainmentDelay;

    /**
     * Gets the value of the reactiveControlStrategyTest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReactiveControlStrategyTest() {
        return reactiveControlStrategyTest;
    }

    /**
     * Sets the value of the reactiveControlStrategyTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReactiveControlStrategyTest(String value) {
        this.reactiveControlStrategyTest = value;
    }

    /**
     * Gets the value of the reactiveControlStrategyThreshold property.
     * 
     */
    public double getReactiveControlStrategyThreshold() {
        return reactiveControlStrategyThreshold;
    }

    /**
     * Sets the value of the reactiveControlStrategyThreshold property.
     * 
     */
    public void setReactiveControlStrategyThreshold(double value) {
        this.reactiveControlStrategyThreshold = value;
    }

    /**
     * Gets the value of the ascertainmentFraction property.
     * 
     */
    public double getAscertainmentFraction() {
        return ascertainmentFraction;
    }

    /**
     * Sets the value of the ascertainmentFraction property.
     * 
     */
    public void setAscertainmentFraction(double value) {
        this.ascertainmentFraction = value;
    }

    /**
     * Gets the value of the ascertainmentDelay property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAscertainmentDelay() {
        return ascertainmentDelay;
    }

    /**
     * Sets the value of the ascertainmentDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAscertainmentDelay(BigInteger value) {
        this.ascertainmentDelay = value;
    }

}
