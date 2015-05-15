
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ControlStrategyTargetPopulationsAndPrioritization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ControlStrategyTargetPopulationsAndPrioritization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="controlStrategyNamedPrioritizationScheme" type="{http://types.apollo.pitt.edu/v3_0_0/}NamedPrioritizationSchemeEnum"/>
 *           &lt;element name="controlStrategyTargetPopulationsAndPrioritization" type="{http://types.apollo.pitt.edu/v3_0_0/}TargetPriorityPopulation" maxOccurs="unbounded"/>
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
@XmlType(name = "ControlStrategyTargetPopulationsAndPrioritization", propOrder = {
    "controlStrategyNamedPrioritizationScheme",
    "controlStrategyTargetPopulationsAndPrioritization"
})
public class ControlStrategyTargetPopulationsAndPrioritization {

    protected NamedPrioritizationSchemeEnum controlStrategyNamedPrioritizationScheme;
    protected List<TargetPriorityPopulation> controlStrategyTargetPopulationsAndPrioritization;

    /**
     * Gets the value of the controlStrategyNamedPrioritizationScheme property.
     * 
     * @return
     *     possible object is
     *     {@link NamedPrioritizationSchemeEnum }
     *     
     */
    public NamedPrioritizationSchemeEnum getControlStrategyNamedPrioritizationScheme() {
        return controlStrategyNamedPrioritizationScheme;
    }

    /**
     * Sets the value of the controlStrategyNamedPrioritizationScheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedPrioritizationSchemeEnum }
     *     
     */
    public void setControlStrategyNamedPrioritizationScheme(NamedPrioritizationSchemeEnum value) {
        this.controlStrategyNamedPrioritizationScheme = value;
    }

    /**
     * Gets the value of the controlStrategyTargetPopulationsAndPrioritization property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlStrategyTargetPopulationsAndPrioritization property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlStrategyTargetPopulationsAndPrioritization().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TargetPriorityPopulation }
     * 
     * 
     */
    public List<TargetPriorityPopulation> getControlStrategyTargetPopulationsAndPrioritization() {
        if (controlStrategyTargetPopulationsAndPrioritization == null) {
            controlStrategyTargetPopulationsAndPrioritization = new ArrayList<TargetPriorityPopulation>();
        }
        return this.controlStrategyTargetPopulationsAndPrioritization;
    }

}
