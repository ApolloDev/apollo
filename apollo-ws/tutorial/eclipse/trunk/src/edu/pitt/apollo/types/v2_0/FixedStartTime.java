
package edu.pitt.apollo.types.v2_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FixedStartTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FixedStartTime">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ControlStrategyStartTime">
 *       &lt;sequence>
 *         &lt;element name="startTimeRelativeToScenarioDate" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="stopTimeRelativeToScenarioDate" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FixedStartTime", propOrder = {
    "startTimeRelativeToScenarioDate",
    "stopTimeRelativeToScenarioDate"
})
public class FixedStartTime
    extends ControlStrategyStartTime
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger startTimeRelativeToScenarioDate;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger stopTimeRelativeToScenarioDate;

    /**
     * Gets the value of the startTimeRelativeToScenarioDate property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStartTimeRelativeToScenarioDate() {
        return startTimeRelativeToScenarioDate;
    }

    /**
     * Sets the value of the startTimeRelativeToScenarioDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStartTimeRelativeToScenarioDate(BigInteger value) {
        this.startTimeRelativeToScenarioDate = value;
    }

    /**
     * Gets the value of the stopTimeRelativeToScenarioDate property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStopTimeRelativeToScenarioDate() {
        return stopTimeRelativeToScenarioDate;
    }

    /**
     * Sets the value of the stopTimeRelativeToScenarioDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStopTimeRelativeToScenarioDate(BigInteger value) {
        this.stopTimeRelativeToScenarioDate = value;
    }

}
