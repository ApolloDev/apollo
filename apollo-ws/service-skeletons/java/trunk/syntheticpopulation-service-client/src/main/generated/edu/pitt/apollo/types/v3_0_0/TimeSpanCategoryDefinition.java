
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeSpanCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeSpanCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="unitOfTime" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfTimeEnum"/>
 *         &lt;element name="timeZeroReference" type="{http://types.apollo.pitt.edu/v3_0_0/}TimeScaleEnum"/>
 *         &lt;element name="startOfTimeSpan" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="durationInTimeUnits" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeSpanCategoryDefinition", propOrder = {
    "unitOfTime",
    "timeZeroReference",
    "startOfTimeSpan",
    "durationInTimeUnits"
})
public class TimeSpanCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected UnitOfTimeEnum unitOfTime;
    @XmlElement(required = true)
    protected TimeScaleEnum timeZeroReference;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger startOfTimeSpan;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger durationInTimeUnits;

    /**
     * Gets the value of the unitOfTime property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public UnitOfTimeEnum getUnitOfTime() {
        return unitOfTime;
    }

    /**
     * Sets the value of the unitOfTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public void setUnitOfTime(UnitOfTimeEnum value) {
        this.unitOfTime = value;
    }

    /**
     * Gets the value of the timeZeroReference property.
     * 
     * @return
     *     possible object is
     *     {@link TimeScaleEnum }
     *     
     */
    public TimeScaleEnum getTimeZeroReference() {
        return timeZeroReference;
    }

    /**
     * Sets the value of the timeZeroReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeScaleEnum }
     *     
     */
    public void setTimeZeroReference(TimeScaleEnum value) {
        this.timeZeroReference = value;
    }

    /**
     * Gets the value of the startOfTimeSpan property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStartOfTimeSpan() {
        return startOfTimeSpan;
    }

    /**
     * Sets the value of the startOfTimeSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStartOfTimeSpan(BigInteger value) {
        this.startOfTimeSpan = value;
    }

    /**
     * Gets the value of the durationInTimeUnits property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDurationInTimeUnits() {
        return durationInTimeUnits;
    }

    /**
     * Sets the value of the durationInTimeUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDurationInTimeUnits(BigInteger value) {
        this.durationInTimeUnits = value;
    }

}
