
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DecisionModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DecisionModel">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="decisionAlternatives" type="{http://types.apollo.pitt.edu/v2_0/}DecisionAlternative" maxOccurs="unbounded"/>
 *         &lt;element name="simulatorConfiguration " type="{http://types.apollo.pitt.edu/v2_0/}RunSimulationMessage" minOccurs="0"/>
 *         &lt;element name="utilityFunction" type="{http://types.apollo.pitt.edu/v2_0/}UtilityFunction"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DecisionModel", propOrder = {
    "decisionAlternatives",
    "simulatorConfiguration0020",
    "utilityFunction"
})
public class DecisionModel
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected List<DecisionAlternative> decisionAlternatives;
    @XmlElement(name = "simulatorConfiguration ")
    protected RunSimulationMessage simulatorConfiguration0020;
    @XmlElement(required = true)
    protected UtilityFunction utilityFunction;

    /**
     * Gets the value of the decisionAlternatives property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the decisionAlternatives property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDecisionAlternatives().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DecisionAlternative }
     * 
     * 
     */
    public List<DecisionAlternative> getDecisionAlternatives() {
        if (decisionAlternatives == null) {
            decisionAlternatives = new ArrayList<DecisionAlternative>();
        }
        return this.decisionAlternatives;
    }

    /**
     * Gets the value of the simulatorConfiguration0020 property.
     * 
     * @return
     *     possible object is
     *     {@link RunSimulationMessage }
     *     
     */
    public RunSimulationMessage getSimulatorConfiguration_0020() {
        return simulatorConfiguration0020;
    }

    /**
     * Sets the value of the simulatorConfiguration0020 property.
     * 
     * @param value
     *     allowed object is
     *     {@link RunSimulationMessage }
     *     
     */
    public void setSimulatorConfiguration_0020(RunSimulationMessage value) {
        this.simulatorConfiguration0020 = value;
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
