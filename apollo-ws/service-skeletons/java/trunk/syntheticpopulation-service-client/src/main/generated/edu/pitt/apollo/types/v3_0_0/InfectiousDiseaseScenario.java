
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfectiousDiseaseScenario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDiseaseScenario">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="scenarioDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location"/>
 *         &lt;element name="infections" type="{http://types.apollo.pitt.edu/v3_0_0/}Infection" maxOccurs="unbounded"/>
 *         &lt;element name="contaminations" type="{http://types.apollo.pitt.edu/v3_0_0/}Contamination" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="populationInfectionAndImmunityCensuses" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensus" maxOccurs="unbounded"/>
 *         &lt;element name="infectiousDiseaseControlStrategies" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="individualLifeCycles" type="{http://types.apollo.pitt.edu/v3_0_0/}IndividualLifeCycle" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="individualBehaviors" type="{http://types.apollo.pitt.edu/v3_0_0/}IndividualBehavior" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="individualReproductions" type="{http://types.apollo.pitt.edu/v3_0_0/}IndividualMosquitoReproduction" minOccurs="0"/>
 *         &lt;element name="abioticEcosystemCensuses" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemCensus" minOccurs="0"/>
 *         &lt;element name="scenarioCartesianOrigin" type="{http://types.apollo.pitt.edu/v3_0_0/}ScenarioCartesianOrigin" minOccurs="0"/>
 *         &lt;element name="nonApolloParameters" type="{http://types.apollo.pitt.edu/v3_0_0/}NonApolloParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectiousDiseaseScenario", propOrder = {
    "scenarioDate",
    "location",
    "infections",
    "contaminations",
    "populationInfectionAndImmunityCensuses",
    "infectiousDiseaseControlStrategies",
    "individualLifeCycles",
    "individualBehaviors",
    "individualReproductions",
    "abioticEcosystemCensuses",
    "scenarioCartesianOrigin",
    "nonApolloParameters"
})
public class InfectiousDiseaseScenario
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scenarioDate;
    @XmlElement(required = true)
    protected Location location;
    @XmlElement(required = true)
    protected List<Infection> infections;
    protected List<Contamination> contaminations;
    @XmlElement(required = true)
    protected List<PopulationInfectionAndImmunityCensus> populationInfectionAndImmunityCensuses;
    protected List<InfectiousDiseaseControlStrategy> infectiousDiseaseControlStrategies;
    protected List<IndividualLifeCycle> individualLifeCycles;
    protected List<IndividualBehavior> individualBehaviors;
    protected IndividualMosquitoReproduction individualReproductions;
    protected AbioticEcosystemCensus abioticEcosystemCensuses;
    protected ScenarioCartesianOrigin scenarioCartesianOrigin;
    protected List<NonApolloParameter> nonApolloParameters;

    /**
     * Gets the value of the scenarioDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScenarioDate() {
        return scenarioDate;
    }

    /**
     * Sets the value of the scenarioDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScenarioDate(XMLGregorianCalendar value) {
        this.scenarioDate = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the infections property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infections property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfections().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Infection }
     * 
     * 
     */
    public List<Infection> getInfections() {
        if (infections == null) {
            infections = new ArrayList<Infection>();
        }
        return this.infections;
    }

    /**
     * Gets the value of the contaminations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contaminations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContaminations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contamination }
     * 
     * 
     */
    public List<Contamination> getContaminations() {
        if (contaminations == null) {
            contaminations = new ArrayList<Contamination>();
        }
        return this.contaminations;
    }

    /**
     * Gets the value of the populationInfectionAndImmunityCensuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the populationInfectionAndImmunityCensuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPopulationInfectionAndImmunityCensuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationInfectionAndImmunityCensus }
     * 
     * 
     */
    public List<PopulationInfectionAndImmunityCensus> getPopulationInfectionAndImmunityCensuses() {
        if (populationInfectionAndImmunityCensuses == null) {
            populationInfectionAndImmunityCensuses = new ArrayList<PopulationInfectionAndImmunityCensus>();
        }
        return this.populationInfectionAndImmunityCensuses;
    }

    /**
     * Gets the value of the infectiousDiseaseControlStrategies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infectiousDiseaseControlStrategies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfectiousDiseaseControlStrategies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectiousDiseaseControlStrategy }
     * 
     * 
     */
    public List<InfectiousDiseaseControlStrategy> getInfectiousDiseaseControlStrategies() {
        if (infectiousDiseaseControlStrategies == null) {
            infectiousDiseaseControlStrategies = new ArrayList<InfectiousDiseaseControlStrategy>();
        }
        return this.infectiousDiseaseControlStrategies;
    }

    /**
     * Gets the value of the individualLifeCycles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the individualLifeCycles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndividualLifeCycles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndividualLifeCycle }
     * 
     * 
     */
    public List<IndividualLifeCycle> getIndividualLifeCycles() {
        if (individualLifeCycles == null) {
            individualLifeCycles = new ArrayList<IndividualLifeCycle>();
        }
        return this.individualLifeCycles;
    }

    /**
     * Gets the value of the individualBehaviors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the individualBehaviors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndividualBehaviors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndividualBehavior }
     * 
     * 
     */
    public List<IndividualBehavior> getIndividualBehaviors() {
        if (individualBehaviors == null) {
            individualBehaviors = new ArrayList<IndividualBehavior>();
        }
        return this.individualBehaviors;
    }

    /**
     * Gets the value of the individualReproductions property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualMosquitoReproduction }
     *     
     */
    public IndividualMosquitoReproduction getIndividualReproductions() {
        return individualReproductions;
    }

    /**
     * Sets the value of the individualReproductions property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualMosquitoReproduction }
     *     
     */
    public void setIndividualReproductions(IndividualMosquitoReproduction value) {
        this.individualReproductions = value;
    }

    /**
     * Gets the value of the abioticEcosystemCensuses property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemCensus }
     *     
     */
    public AbioticEcosystemCensus getAbioticEcosystemCensuses() {
        return abioticEcosystemCensuses;
    }

    /**
     * Sets the value of the abioticEcosystemCensuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemCensus }
     *     
     */
    public void setAbioticEcosystemCensuses(AbioticEcosystemCensus value) {
        this.abioticEcosystemCensuses = value;
    }

    /**
     * Gets the value of the scenarioCartesianOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link ScenarioCartesianOrigin }
     *     
     */
    public ScenarioCartesianOrigin getScenarioCartesianOrigin() {
        return scenarioCartesianOrigin;
    }

    /**
     * Sets the value of the scenarioCartesianOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenarioCartesianOrigin }
     *     
     */
    public void setScenarioCartesianOrigin(ScenarioCartesianOrigin value) {
        this.scenarioCartesianOrigin = value;
    }

    /**
     * Gets the value of the nonApolloParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonApolloParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonApolloParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NonApolloParameter }
     * 
     * 
     */
    public List<NonApolloParameter> getNonApolloParameters() {
        if (nonApolloParameters == null) {
            nonApolloParameters = new ArrayList<NonApolloParameter>();
        }
        return this.nonApolloParameters;
    }

}
