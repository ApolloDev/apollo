
package edu.pitt.apollo.types.v3_0_0;

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
 *         &lt;element name="unitOfTimeForSimulatorTimeStep" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfTimeEnum"/>
 *         &lt;element name="numberOfUnitsOfTimeInOneSimulatorTimeStep" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
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
    "unitOfTimeForSimulatorTimeStep",
    "numberOfUnitsOfTimeInOneSimulatorTimeStep",
    "runLength"
})
public class SimulatorTimeSpecification {

    @XmlElement(required = true)
    protected UnitOfTimeEnum unitOfTimeForSimulatorTimeStep;
    protected double numberOfUnitsOfTimeInOneSimulatorTimeStep;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger runLength;

    /**
     * Gets the value of the unitOfTimeForSimulatorTimeStep property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public UnitOfTimeEnum getUnitOfTimeForSimulatorTimeStep() {
        return unitOfTimeForSimulatorTimeStep;
    }

    /**
     * Sets the value of the unitOfTimeForSimulatorTimeStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public void setUnitOfTimeForSimulatorTimeStep(UnitOfTimeEnum value) {
        this.unitOfTimeForSimulatorTimeStep = value;
    }

    /**
     * Gets the value of the numberOfUnitsOfTimeInOneSimulatorTimeStep property.
     * 
     */
    public double getNumberOfUnitsOfTimeInOneSimulatorTimeStep() {
        return numberOfUnitsOfTimeInOneSimulatorTimeStep;
    }

    /**
     * Sets the value of the numberOfUnitsOfTimeInOneSimulatorTimeStep property.
     * 
     */
    public void setNumberOfUnitsOfTimeInOneSimulatorTimeStep(double value) {
        this.numberOfUnitsOfTimeInOneSimulatorTimeStep = value;
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
