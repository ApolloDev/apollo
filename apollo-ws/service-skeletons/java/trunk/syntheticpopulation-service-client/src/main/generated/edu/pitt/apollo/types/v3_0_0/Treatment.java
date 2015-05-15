
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Treatment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Treatment">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="speciesOfTreatedOrganism" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="numDosesInTreatmentCourse" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="durationOfTreatmentCourse" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="treatmentContraindications" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentContraindication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Treatment", propOrder = {
    "description",
    "speciesOfTreatedOrganism",
    "pathogen",
    "numDosesInTreatmentCourse",
    "durationOfTreatmentCourse",
    "treatmentContraindications"
})
@XmlSeeAlso({
    AntiviralTreatment.class,
    Vaccination.class,
    DrugTreatment.class
})
public class Treatment
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesOfTreatedOrganism;
    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numDosesInTreatmentCourse;
    @XmlElement(required = true)
    protected Duration durationOfTreatmentCourse;
    protected List<TreatmentContraindication> treatmentContraindications;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the speciesOfTreatedOrganism property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesOfTreatedOrganism() {
        return speciesOfTreatedOrganism;
    }

    /**
     * Sets the value of the speciesOfTreatedOrganism property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesOfTreatedOrganism(String value) {
        this.speciesOfTreatedOrganism = value;
    }

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
     * Gets the value of the numDosesInTreatmentCourse property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumDosesInTreatmentCourse() {
        return numDosesInTreatmentCourse;
    }

    /**
     * Sets the value of the numDosesInTreatmentCourse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumDosesInTreatmentCourse(BigInteger value) {
        this.numDosesInTreatmentCourse = value;
    }

    /**
     * Gets the value of the durationOfTreatmentCourse property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDurationOfTreatmentCourse() {
        return durationOfTreatmentCourse;
    }

    /**
     * Sets the value of the durationOfTreatmentCourse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDurationOfTreatmentCourse(Duration value) {
        this.durationOfTreatmentCourse = value;
    }

    /**
     * Gets the value of the treatmentContraindications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatmentContraindications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatmentContraindications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreatmentContraindication }
     * 
     * 
     */
    public List<TreatmentContraindication> getTreatmentContraindications() {
        if (treatmentContraindications == null) {
            treatmentContraindications = new ArrayList<TreatmentContraindication>();
        }
        return this.treatmentContraindications;
    }

}
