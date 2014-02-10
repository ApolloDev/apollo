
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReactiveStartTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReactiveStartTime">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ControlStrategyStartTime">
 *       &lt;sequence>
 *         &lt;element name="trigger" type="{http://types.apollo.pitt.edu/v2_0/}ReactiveTriggersDefinition"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReactiveStartTime", propOrder = {
    "trigger"
})
public class ReactiveStartTime
    extends ControlStrategyStartTime
{

    @XmlElement(required = true)
    protected ReactiveTriggersDefinition trigger;

    /**
     * Gets the value of the trigger property.
     * 
     * @return
     *     possible object is
     *     {@link ReactiveTriggersDefinition }
     *     
     */
    public ReactiveTriggersDefinition getTrigger() {
        return trigger;
    }

    /**
     * Sets the value of the trigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReactiveTriggersDefinition }
     *     
     */
    public void setTrigger(ReactiveTriggersDefinition value) {
        this.trigger = value;
    }

}
