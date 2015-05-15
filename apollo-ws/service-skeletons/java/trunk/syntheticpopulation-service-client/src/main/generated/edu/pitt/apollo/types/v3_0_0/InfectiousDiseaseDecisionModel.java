
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfectiousDiseaseDecisionModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDiseaseDecisionModel">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="controlStrategies" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy" maxOccurs="unbounded"/>
 *         &lt;element name="simulatorConfiguration " type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseScenario" maxOccurs="unbounded"/>
 *         &lt;element name="utilityFunction" type="{http://types.apollo.pitt.edu/v3_0_0/}UtilityFunction"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectiousDiseaseDecisionModel", propOrder = {
    "controlStrategies",
    "simulatorConfiguration0020",
    "utilityFunction"
})
public class InfectiousDiseaseDecisionModel
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected List<InfectiousDiseaseControlStrategy> controlStrategies;
    @XmlElement(name = "simulatorConfiguration ", required = true)
    protected List<InfectiousDiseaseScenario> simulatorConfiguration0020;
    @XmlElement(required = true)
    protected UtilityFunction utilityFunction;

    /**
     * Gets the value of the controlStrategies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlStrategies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlStrategies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectiousDiseaseControlStrategy }
     * 
     * 
     */
    public List<InfectiousDiseaseControlStrategy> getControlStrategies() {
        if (controlStrategies == null) {
            controlStrategies = new ArrayList<InfectiousDiseaseControlStrategy>();
        }
        return this.controlStrategies;
    }

    /**
     * Gets the value of the simulatorConfiguration0020 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the simulatorConfiguration0020 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSimulatorConfiguration_0020().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectiousDiseaseScenario }
     * 
     * 
     */
    public List<InfectiousDiseaseScenario> getSimulatorConfiguration_0020() {
        if (simulatorConfiguration0020 == null) {
            simulatorConfiguration0020 = new ArrayList<InfectiousDiseaseScenario>();
        }
        return this.simulatorConfiguration0020;
    }

    /**
     * Gets the value of the utilityFunction property.
     * 
     * @return
     *     possible object is
     *     {@link UtilityFunction }
     *     
     */
    public UtilityFunction getUtilityFunction() {
        return utilityFunction;
    }

    /**
     * Sets the value of the utilityFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtilityFunction }
     *     
     */
    public void setUtilityFunction(UtilityFunction value) {
        this.utilityFunction = value;
    }

}
