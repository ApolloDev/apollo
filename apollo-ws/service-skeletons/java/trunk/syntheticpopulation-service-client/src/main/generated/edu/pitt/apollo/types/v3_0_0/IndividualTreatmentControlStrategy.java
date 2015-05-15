
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualTreatmentControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualTreatmentControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="individualTreatment" type="{http://types.apollo.pitt.edu/v3_0_0/}Treatment"/>
 *         &lt;element name="populationTreatmentCensus" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationTreatmentCensus" minOccurs="0"/>
 *         &lt;element name="compliance" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *         &lt;element name="delayFromSymptomsToTreatment" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration" minOccurs="0"/>
 *         &lt;element name="treatmentSystemLogistics" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentSystemLogistics" maxOccurs="unbounded"/>
 *         &lt;element name="targetPopulationsAndPrioritizations" type="{http://types.apollo.pitt.edu/v3_0_0/}ControlStrategyTargetPopulationsAndPrioritization"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualTreatmentControlStrategy", propOrder = {
    "pathogen",
    "individualTreatment",
    "populationTreatmentCensus",
    "compliance",
    "delayFromSymptomsToTreatment",
    "treatmentSystemLogistics",
    "targetPopulationsAndPrioritizations"
})
@XmlSeeAlso({
    RingIndividualTreatmentControlStrategy.class
})
public class IndividualTreatmentControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    protected Treatment individualTreatment;
    protected PopulationTreatmentCensus populationTreatmentCensus;
    @XmlElement(required = true)
    protected ProbabilisticParameter compliance;
    protected Duration delayFromSymptomsToTreatment;
    @XmlElement(required = true)
    protected List<TreatmentSystemLogistics> treatmentSystemLogistics;
    @XmlElement(required = true)
    protected ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritizations;

    /**
     * Gets the value of the pathogen property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getPathogen() {
        return pathogen;
    }

    /**
     * Sets the value of the pathogen property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setPathogen(ApolloPathogenCode value) {
        this.pathogen = value;
    }

    /**
     * Gets the value of the individualTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link Treatment }
     *     
     */
    public Treatment getIndividualTreatment() {
        return individualTreatment;
    }

    /**
     * Sets the value of the individualTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Treatment }
     *     
     */
    public void setIndividualTreatment(Treatment value) {
        this.individualTreatment = value;
    }

    /**
     * Gets the value of the populationTreatmentCensus property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationTreatmentCensus }
     *     
     */
    public PopulationTreatmentCensus getPopulationTreatmentCensus() {
        return populationTreatmentCensus;
    }

    /**
     * Sets the value of the populationTreatmentCensus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationTreatmentCensus }
     *     
     */
    public void setPopulationTreatmentCensus(PopulationTreatmentCensus value) {
        this.populationTreatmentCensus = value;
    }

    /**
     * Gets the value of the compliance property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getCompliance() {
        return compliance;
    }

    /**
     * Sets the value of the compliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setCompliance(ProbabilisticParameter value) {
        this.compliance = value;
    }

    /**
     * Gets the value of the delayFromSymptomsToTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDelayFromSymptomsToTreatment() {
        return delayFromSymptomsToTreatment;
    }

    /**
     * Sets the value of the delayFromSymptomsToTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDelayFromSymptomsToTreatment(Duration value) {
        this.delayFromSymptomsToTreatment = value;
    }

    /**
     * Gets the value of the treatmentSystemLogistics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatmentSystemLogistics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatmentSystemLogistics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreatmentSystemLogistics }
     * 
     * 
     */
    public List<TreatmentSystemLogistics> getTreatmentSystemLogistics() {
        if (treatmentSystemLogistics == null) {
            treatmentSystemLogistics = new ArrayList<TreatmentSystemLogistics>();
        }
        return this.treatmentSystemLogistics;
    }

    /**
     * Gets the value of the targetPopulationsAndPrioritizations property.
     * 
     * @return
     *     possible object is
     *     {@link ControlStrategyTargetPopulationsAndPrioritization }
     *     
     */
    public ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
        return targetPopulationsAndPrioritizations;
    }

    /**
     * Sets the value of the targetPopulationsAndPrioritizations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlStrategyTargetPopulationsAndPrioritization }
     *     
     */
    public void setTargetPopulationsAndPrioritizations(ControlStrategyTargetPopulationsAndPrioritization value) {
        this.targetPopulationsAndPrioritizations = value;
    }

}
