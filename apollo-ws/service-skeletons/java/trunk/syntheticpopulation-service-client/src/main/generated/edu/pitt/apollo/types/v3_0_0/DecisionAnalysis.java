
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DecisionAnalysis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DecisionAnalysis">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="decisionModel" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseDecisionModel"/>
 *         &lt;element name="baseCaseResult " type="{http://types.apollo.pitt.edu/v3_0_0/}BaseCaseResult"/>
 *         &lt;element name="sensitivityAnalyses" type="{http://types.apollo.pitt.edu/v3_0_0/}SensitivityAnalysis" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DecisionAnalysis", propOrder = {
    "decisionModel",
    "baseCaseResult0020",
    "sensitivityAnalyses"
})
public class DecisionAnalysis
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected InfectiousDiseaseDecisionModel decisionModel;
    @XmlElement(name = "baseCaseResult ", required = true)
    protected BaseCaseResult baseCaseResult0020;
    protected List<SensitivityAnalysis> sensitivityAnalyses;

    /**
     * Gets the value of the decisionModel property.
     * 
     * @return
     *     possible object is
     *     {@link InfectiousDiseaseDecisionModel }
     *     
     */
    public InfectiousDiseaseDecisionModel getDecisionModel() {
        return decisionModel;
    }

    /**
     * Sets the value of the decisionModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfectiousDiseaseDecisionModel }
     *     
     */
    public void setDecisionModel(InfectiousDiseaseDecisionModel value) {
        this.decisionModel = value;
    }

    /**
     * Gets the value of the baseCaseResult0020 property.
     * 
     * @return
     *     possible object is
     *     {@link BaseCaseResult }
     *     
     */
    public BaseCaseResult getBaseCaseResult_0020() {
        return baseCaseResult0020;
    }

    /**
     * Sets the value of the baseCaseResult0020 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseCaseResult }
     *     
     */
    public void setBaseCaseResult_0020(BaseCaseResult value) {
        this.baseCaseResult0020 = value;
    }

    /**
     * Gets the value of the sensitivityAnalyses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sensitivityAnalyses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSensitivityAnalyses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SensitivityAnalysis }
     * 
     * 
     */
    public List<SensitivityAnalysis> getSensitivityAnalyses() {
        if (sensitivityAnalyses == null) {
            sensitivityAnalyses = new ArrayList<SensitivityAnalysis>();
        }
        return this.sensitivityAnalyses;
    }

}
