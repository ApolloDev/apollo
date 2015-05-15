
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UtilityFunction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtilityFunction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lambdaFunction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="textDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "UtilityFunction", propOrder = {
    "lambdaFunction",
    "textDescription"
})
public class UtilityFunction {

    @XmlElement(required = true)
    protected String lambdaFunction;
    protected String textDescription;

    /**
     * Gets the value of the lambdaFunction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLambdaFunction() {
        return lambdaFunction;
    }

    /**
     * Sets the value of the lambdaFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLambdaFunction(String value) {
        this.lambdaFunction = value;
    }

    /**
     * Gets the value of the textDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextDescription() {
        return textDescription;
    }

    /**
     * Sets the value of the textDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextDescription(String value) {
        this.textDescription = value;
    }

}
