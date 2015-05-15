
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InfectiousDisease complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDisease">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="diseaseId" type="{http://types.apollo.pitt.edu/v3_0_0/}SnomedId"/>
 *         &lt;element name="speciesWithDisease" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="causalPathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="incubationPeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration" minOccurs="0"/>
 *         &lt;element name="prodromalPeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration" minOccurs="0"/>
 *         &lt;element name="fulminantPeriod" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration" minOccurs="0"/>
 *         &lt;element name="otherIntervals" type="{http://types.apollo.pitt.edu/v3_0_0/}Interval" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="diseaseOutcomesWithProbabilities" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeWithProbability" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectiousDisease", propOrder = {
    "diseaseId",
    "speciesWithDisease",
    "causalPathogen",
    "incubationPeriod",
    "prodromalPeriod",
    "fulminantPeriod",
    "otherIntervals",
    "diseaseOutcomesWithProbabilities"
})
public class InfectiousDisease
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String diseaseId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesWithDisease;
    @XmlElement(required = true)
    protected ApolloPathogenCode causalPathogen;
    protected Duration incubationPeriod;
    protected Duration prodromalPeriod;
    protected Duration fulminantPeriod;
    protected List<Interval> otherIntervals;
    @XmlElement(required = true)
    protected List<DiseaseOutcomeWithProbability> diseaseOutcomesWithProbabilities;

    /**
     * Gets the value of the diseaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiseaseId() {
        return diseaseId;
    }

    /**
     * Sets the value of the diseaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiseaseId(String value) {
        this.diseaseId = value;
    }

    /**
     * Gets the value of the speciesWithDisease property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesWithDisease() {
        return speciesWithDisease;
    }

    /**
     * Sets the value of the speciesWithDisease property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesWithDisease(String value) {
        this.speciesWithDisease = value;
    }

    /**
     * Gets the value of the causalPathogen property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getCausalPathogen() {
        return causalPathogen;
    }

    /**
     * Sets the value of the causalPathogen property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setCausalPathogen(ApolloPathogenCode value) {
        this.causalPathogen = value;
    }

    /**
     * Gets the value of the incubationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getIncubationPeriod() {
        return incubationPeriod;
    }

    /**
     * Sets the value of the incubationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setIncubationPeriod(Duration value) {
        this.incubationPeriod = value;
    }

    /**
     * Gets the value of the prodromalPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getProdromalPeriod() {
        return prodromalPeriod;
    }

    /**
     * Sets the value of the prodromalPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setProdromalPeriod(Duration value) {
        this.prodromalPeriod = value;
    }

    /**
     * Gets the value of the fulminantPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getFulminantPeriod() {
        return fulminantPeriod;
    }

    /**
     * Sets the value of the fulminantPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setFulminantPeriod(Duration value) {
        this.fulminantPeriod = value;
    }

    /**
     * Gets the value of the otherIntervals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherIntervals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherIntervals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Interval }
     * 
     * 
     */
    public List<Interval> getOtherIntervals() {
        if (otherIntervals == null) {
            otherIntervals = new ArrayList<Interval>();
        }
        return this.otherIntervals;
    }

    /**
     * Gets the value of the diseaseOutcomesWithProbabilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseaseOutcomesWithProbabilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseaseOutcomesWithProbabilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiseaseOutcomeWithProbability }
     * 
     * 
     */
    public List<DiseaseOutcomeWithProbability> getDiseaseOutcomesWithProbabilities() {
        if (diseaseOutcomesWithProbabilities == null) {
            diseaseOutcomesWithProbabilities = new ArrayList<DiseaseOutcomeWithProbability>();
        }
        return this.diseaseOutcomesWithProbabilities;
    }

}
