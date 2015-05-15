
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Epidemic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Epidemic">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="causalPathogens" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode" maxOccurs="unbounded"/>
 *         &lt;element name="epidemicPeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}EpidemicPeriod"/>
 *         &lt;element name="administrativeLocations" type="{http://types.apollo.pitt.edu/v3_0_0/}Location" maxOccurs="unbounded"/>
 *         &lt;element name="epidemicZones" type="{http://types.apollo.pitt.edu/v3_0_0/}NamedMultiGeometry" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="infections" type="{http://types.apollo.pitt.edu/v3_0_0/}Infection" maxOccurs="unbounded"/>
 *         &lt;element name="preEpidemicCensus" type="{http://types.apollo.pitt.edu/v3_0_0/}PreEpidemicEcosystemCensus" minOccurs="0"/>
 *         &lt;element name="populationSerologySurveys" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationSerologySurvey" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="populationInfectionSurveys" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionSurvey" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="infectiousDiseaseControlStrategies" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="caseDefinitions" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactDefinitions" type="{http://types.apollo.pitt.edu/v3_0_0/}ContactDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="caseLists" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="caseCounts" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseCount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transmissionTrees" type="{http://types.apollo.pitt.edu/v3_0_0/}TransmissionTree" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relativeRiskDataSets" type="{http://types.apollo.pitt.edu/v3_0_0/}RelativeRiskDataSet" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="causalPathogenIsolates" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="references" type="{http://types.apollo.pitt.edu/v3_0_0/}Reference" maxOccurs="unbounded"/>
 *         &lt;element name="curator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editHistory" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reviewedBy" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="acknowledgements" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Epidemic", propOrder = {
    "causalPathogens",
    "epidemicPeriod",
    "administrativeLocations",
    "epidemicZones",
    "infections",
    "preEpidemicCensus",
    "populationSerologySurveys",
    "populationInfectionSurveys",
    "infectiousDiseaseControlStrategies",
    "caseDefinitions",
    "contactDefinitions",
    "caseLists",
    "caseCounts",
    "transmissionTrees",
    "relativeRiskDataSets",
    "causalPathogenIsolates",
    "references",
    "curator",
    "editHistory",
    "reviewedBy",
    "acknowledgements"
})
public class Epidemic
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected List<ApolloPathogenCode> causalPathogens;
    @XmlElement(required = true)
    protected EpidemicPeriod epidemicPeriod;
    @XmlElement(required = true)
    protected List<Location> administrativeLocations;
    protected List<NamedMultiGeometry> epidemicZones;
    @XmlElement(required = true)
    protected List<Infection> infections;
    protected PreEpidemicEcosystemCensus preEpidemicCensus;
    protected List<PopulationSerologySurvey> populationSerologySurveys;
    protected List<PopulationInfectionSurvey> populationInfectionSurveys;
    protected List<InfectiousDiseaseControlStrategy> infectiousDiseaseControlStrategies;
    protected List<CaseDefinition> caseDefinitions;
    protected List<ContactDefinition> contactDefinitions;
    protected List<CaseList> caseLists;
    protected List<CaseCount> caseCounts;
    protected List<TransmissionTree> transmissionTrees;
    protected List<RelativeRiskDataSet> relativeRiskDataSets;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> causalPathogenIsolates;
    @XmlElement(required = true)
    protected List<Reference> references;
    protected String curator;
    protected List<String> editHistory;
    protected List<String> reviewedBy;
    protected List<String> acknowledgements;

    /**
     * Gets the value of the causalPathogens property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the causalPathogens property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCausalPathogens().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApolloPathogenCode }
     * 
     * 
     */
    public List<ApolloPathogenCode> getCausalPathogens() {
        if (causalPathogens == null) {
            causalPathogens = new ArrayList<ApolloPathogenCode>();
        }
        return this.causalPathogens;
    }

    /**
     * Gets the value of the epidemicPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link EpidemicPeriod }
     *     
     */
    public EpidemicPeriod getEpidemicPeriod() {
        return epidemicPeriod;
    }

    /**
     * Sets the value of the epidemicPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link EpidemicPeriod }
     *     
     */
    public void setEpidemicPeriod(EpidemicPeriod value) {
        this.epidemicPeriod = value;
    }

    /**
     * Gets the value of the administrativeLocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the administrativeLocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdministrativeLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Location }
     * 
     * 
     */
    public List<Location> getAdministrativeLocations() {
        if (administrativeLocations == null) {
            administrativeLocations = new ArrayList<Location>();
        }
        return this.administrativeLocations;
    }

    /**
     * Gets the value of the epidemicZones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the epidemicZones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEpidemicZones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedMultiGeometry }
     * 
     * 
     */
    public List<NamedMultiGeometry> getEpidemicZones() {
        if (epidemicZones == null) {
            epidemicZones = new ArrayList<NamedMultiGeometry>();
        }
        return this.epidemicZones;
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
     * Gets the value of the preEpidemicCensus property.
     * 
     * @return
     *     possible object is
     *     {@link PreEpidemicEcosystemCensus }
     *     
     */
    public PreEpidemicEcosystemCensus getPreEpidemicCensus() {
        return preEpidemicCensus;
    }

    /**
     * Sets the value of the preEpidemicCensus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreEpidemicEcosystemCensus }
     *     
     */
    public void setPreEpidemicCensus(PreEpidemicEcosystemCensus value) {
        this.preEpidemicCensus = value;
    }

    /**
     * Gets the value of the populationSerologySurveys property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the populationSerologySurveys property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPopulationSerologySurveys().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationSerologySurvey }
     * 
     * 
     */
    public List<PopulationSerologySurvey> getPopulationSerologySurveys() {
        if (populationSerologySurveys == null) {
            populationSerologySurveys = new ArrayList<PopulationSerologySurvey>();
        }
        return this.populationSerologySurveys;
    }

    /**
     * Gets the value of the populationInfectionSurveys property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the populationInfectionSurveys property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPopulationInfectionSurveys().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationInfectionSurvey }
     * 
     * 
     */
    public List<PopulationInfectionSurvey> getPopulationInfectionSurveys() {
        if (populationInfectionSurveys == null) {
            populationInfectionSurveys = new ArrayList<PopulationInfectionSurvey>();
        }
        return this.populationInfectionSurveys;
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
     * Gets the value of the caseDefinitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the caseDefinitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCaseDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseDefinition }
     * 
     * 
     */
    public List<CaseDefinition> getCaseDefinitions() {
        if (caseDefinitions == null) {
            caseDefinitions = new ArrayList<CaseDefinition>();
        }
        return this.caseDefinitions;
    }

    /**
     * Gets the value of the contactDefinitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactDefinitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactDefinition }
     * 
     * 
     */
    public List<ContactDefinition> getContactDefinitions() {
        if (contactDefinitions == null) {
            contactDefinitions = new ArrayList<ContactDefinition>();
        }
        return this.contactDefinitions;
    }

    /**
     * Gets the value of the caseLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the caseLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCaseLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseList }
     * 
     * 
     */
    public List<CaseList> getCaseLists() {
        if (caseLists == null) {
            caseLists = new ArrayList<CaseList>();
        }
        return this.caseLists;
    }

    /**
     * Gets the value of the caseCounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the caseCounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCaseCounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseCount }
     * 
     * 
     */
    public List<CaseCount> getCaseCounts() {
        if (caseCounts == null) {
            caseCounts = new ArrayList<CaseCount>();
        }
        return this.caseCounts;
    }

    /**
     * Gets the value of the transmissionTrees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transmissionTrees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransmissionTrees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransmissionTree }
     * 
     * 
     */
    public List<TransmissionTree> getTransmissionTrees() {
        if (transmissionTrees == null) {
            transmissionTrees = new ArrayList<TransmissionTree>();
        }
        return this.transmissionTrees;
    }

    /**
     * Gets the value of the relativeRiskDataSets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relativeRiskDataSets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelativeRiskDataSets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelativeRiskDataSet }
     * 
     * 
     */
    public List<RelativeRiskDataSet> getRelativeRiskDataSets() {
        if (relativeRiskDataSets == null) {
            relativeRiskDataSets = new ArrayList<RelativeRiskDataSet>();
        }
        return this.relativeRiskDataSets;
    }

    /**
     * Gets the value of the causalPathogenIsolates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the causalPathogenIsolates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCausalPathogenIsolates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCausalPathogenIsolates() {
        if (causalPathogenIsolates == null) {
            causalPathogenIsolates = new ArrayList<String>();
        }
        return this.causalPathogenIsolates;
    }

    /**
     * Gets the value of the references property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the references property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reference }
     * 
     * 
     */
    public List<Reference> getReferences() {
        if (references == null) {
            references = new ArrayList<Reference>();
        }
        return this.references;
    }

    /**
     * Gets the value of the curator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurator() {
        return curator;
    }

    /**
     * Sets the value of the curator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurator(String value) {
        this.curator = value;
    }

    /**
     * Gets the value of the editHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the editHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEditHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEditHistory() {
        if (editHistory == null) {
            editHistory = new ArrayList<String>();
        }
        return this.editHistory;
    }

    /**
     * Gets the value of the reviewedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reviewedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReviewedBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReviewedBy() {
        if (reviewedBy == null) {
            reviewedBy = new ArrayList<String>();
        }
        return this.reviewedBy;
    }

    /**
     * Gets the value of the acknowledgements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acknowledgements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcknowledgements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAcknowledgements() {
        if (acknowledgements == null) {
            acknowledgements = new ArrayList<String>();
        }
        return this.acknowledgements;
    }

}
