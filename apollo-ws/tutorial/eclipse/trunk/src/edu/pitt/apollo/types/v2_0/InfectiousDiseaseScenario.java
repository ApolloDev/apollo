
package edu.pitt.apollo.types.v2_0;

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
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="scenarioDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v2_0/}Location"/>
 *         &lt;element name="infections" type="{http://types.apollo.pitt.edu/v2_0/}Infection" maxOccurs="unbounded"/>
 *         &lt;element name="diseases" type="{http://types.apollo.pitt.edu/v2_0/}InfectiousDisease" maxOccurs="unbounded"/>
 *         &lt;element name="contaminations" type="{http://types.apollo.pitt.edu/v2_0/}Contamination" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="treatments" type="{http://types.apollo.pitt.edu/v2_0/}Treatment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="infectiousDiseaseControlStrategies" type="{http://types.apollo.pitt.edu/v2_0/}InfectiousDiseaseControlStrategy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="populationInfectionAndImmunityCensuses" type="{http://types.apollo.pitt.edu/v2_0/}PopulationInfectionAndImmunityCensus" maxOccurs="unbounded"/>
 *         &lt;element name="populationTreatmentCensuses" type="{http://types.apollo.pitt.edu/v2_0/}PopulationTreatmentCensus" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contaminatedThingCensuses" type="{http://types.apollo.pitt.edu/v2_0/}ContaminatedThingCensus" maxOccurs="unbounded" minOccurs="0"/>
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
    "diseases",
    "contaminations",
    "treatments",
    "infectiousDiseaseControlStrategies",
    "populationInfectionAndImmunityCensuses",
    "populationTreatmentCensuses",
    "contaminatedThingCensuses"
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
    @XmlElement(required = true)
    protected List<InfectiousDisease> diseases;
    protected List<Contamination> contaminations;
    protected List<Treatment> treatments;
    protected List<InfectiousDiseaseControlStrategy> infectiousDiseaseControlStrategies;
    @XmlElement(required = true)
    protected List<PopulationInfectionAndImmunityCensus> populationInfectionAndImmunityCensuses;
    protected List<PopulationTreatmentCensus> populationTreatmentCensuses;
    protected List<ContaminatedThingCensus> contaminatedThingCensuses;

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
     * Gets the value of the diseases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectiousDisease }
     * 
     * 
     */
    public List<InfectiousDisease> getDiseases() {
        if (diseases == null) {
            diseases = new ArrayList<InfectiousDisease>();
        }
        return this.diseases;
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
     * Gets the value of the treatments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Treatment }
     * 
     * 
     */
    public List<Treatment> getTreatments() {
        if (treatments == null) {
            treatments = new ArrayList<Treatment>();
        }
        return this.treatments;
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
     * Gets the value of the populationTreatmentCensuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the populationTreatmentCensuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPopulationTreatmentCensuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationTreatmentCensus }
     * 
     * 
     */
    public List<PopulationTreatmentCensus> getPopulationTreatmentCensuses() {
        if (populationTreatmentCensuses == null) {
            populationTreatmentCensuses = new ArrayList<PopulationTreatmentCensus>();
        }
        return this.populationTreatmentCensuses;
    }

    /**
     * Gets the value of the contaminatedThingCensuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contaminatedThingCensuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContaminatedThingCensuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContaminatedThingCensus }
     * 
     * 
     */
    public List<ContaminatedThingCensus> getContaminatedThingCensuses() {
        if (contaminatedThingCensuses == null) {
            contaminatedThingCensuses = new ArrayList<ContaminatedThingCensus>();
        }
        return this.contaminatedThingCensuses;
    }

}
