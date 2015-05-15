
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TemporalTriggerDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TemporalTriggerDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}TriggerDefinition">
 *       &lt;sequence>
 *         &lt;element name="timeScale" type="{http://types.apollo.pitt.edu/v3_0_0/}TimeScaleEnum"/>
 *         &lt;element name="timeSinceTimeScaleZero" type="{http://types.apollo.pitt.edu/v3_0_0/}FixedDuration"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemporalTriggerDefinition", propOrder = {
    "timeScale",
    "timeSinceTimeScaleZero"
})
public class TemporalTriggerDefinition
    extends TriggerDefinition
{

    @XmlElement(required = true)
    protected TimeScaleEnum timeScale;
    @XmlElement(required = true)
    protected FixedDuration timeSinceTimeScaleZero;

    /**
     * Gets the value of the timeScale property.
     * 
     * @return
     *     possible object is
     *     {@link TimeScaleEnum }
     *     
     */
    public TimeScaleEnum getTimeScale() {
        return timeScale;
    }

    /**
     * Sets the value of the timeScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeScaleEnum }
     *     
     */
    public void setTimeScale(TimeScaleEnum value) {
        this.timeScale = value;
    }

    /**
     * Gets the value of the timeSinceTimeScaleZero property.
     * 
     * @return
     *     possible object is
     *     {@link FixedDuration }
     *     
     */
    public FixedDuration getTimeSinceTimeScaleZero() {
        return timeSinceTimeScaleZero;
    }

    /**
     * Sets the value of the timeSinceTimeScaleZero property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixedDuration }
     *     
     */
    public void setTimeSinceTimeScaleZero(FixedDuration value) {
        this.timeSinceTimeScaleZero = value;
    }

}
