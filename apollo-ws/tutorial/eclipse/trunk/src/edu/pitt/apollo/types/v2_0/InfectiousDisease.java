
package edu.pitt.apollo.types.v2_0;

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
 * <p>Java class for InfectiousDisease complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDisease">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="diseaseID" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="speciesWithDisease" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId"/>
 *         &lt;element name="causalPathogen" type="{http://types.apollo.pitt.edu/v2_0/}ApolloPathogenCode"/>
 *         &lt;element name="diseaseOutcomes" type="{http://types.apollo.pitt.edu/v2_0/}DiseaseOutcomeAndProbability" maxOccurs="unbounded" minOccurs="0"/>
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
    "diseaseID",
    "speciesWithDisease",
    "causalPathogen",
    "diseaseOutcomes"
})
public class InfectiousDisease
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String diseaseID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesWithDisease;
    @XmlElement(required = true)
    protected ApolloPathogenCode causalPathogen;
    protected List<DiseaseOutcomeAndProbability> diseaseOutcomes;

    /**
     * Gets the value of the diseaseID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiseaseID() {
        return diseaseID;
    }

    /**
     * Sets the value of the diseaseID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiseaseID(String value) {
        this.diseaseID = value;
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
     * Gets the value of the diseaseOutcomes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseaseOutcomes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseaseOutcomes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiseaseOutcomeAndProbability }
     * 
     * 
     */
    public List<DiseaseOutcomeAndProbability> getDiseaseOutcomes() {
        if (diseaseOutcomes == null) {
            diseaseOutcomes = new ArrayList<DiseaseOutcomeAndProbability>();
        }
        return this.diseaseOutcomes;
    }

}
