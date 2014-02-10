
package edu.pitt.apollo.types.v2_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimulatorTimeSpecification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimulatorTimeSpecification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timeStepUnit" type="{http://types.apollo.pitt.edu/v2_0/}TimeStepUnit"/>
 *         &lt;element name="timeStepValue" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *         &lt;element name="runLength" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimulatorTimeSpecification", propOrder = {
    "timeStepUnit",
    "timeStepValue",
    "runLength"
})
public class SimulatorTimeSpecification {

    @XmlElement(required = true)
    protected TimeStepUnit timeStepUnit;
    protected double timeStepValue;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger runLength;

    /**
     * Gets the value of the timeStepUnit property.
     * 
     * @return
     *     possible object is
     *     {@link TimeStepUnit }
     *     
     */
    public TimeStepUnit getTimeStepUnit() {
        return timeStepUnit;
    }

    /**
     * Sets the value of the timeStepUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeStepUnit }
     *     
     */
    public void setTimeStepUnit(TimeStepUnit value) {
        this.timeStepUnit = value;
    }

    /**
     * Gets the value of the timeStepValue property.
     * 
     */
    public double getTimeStepValue() {
        return timeStepValue;
    }

    /**
     * Sets the value of the timeStepValue property.
     * 
     */
    public void setTimeStepValue(double value) {
        this.timeStepValue = value;
    }

    /**
     * Gets the value of the runLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRunLength() {
        return runLength;
    }

    /**
     * Sets the value of the runLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRunLength(BigInteger value) {
        this.runLength = value;
    }

}
