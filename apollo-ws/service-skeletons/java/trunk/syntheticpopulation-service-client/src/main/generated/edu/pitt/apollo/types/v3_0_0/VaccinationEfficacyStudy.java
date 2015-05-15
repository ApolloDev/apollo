
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationEfficacyStudy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacyStudy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vaccinationPreventableOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentPreventableOutcomeEnum"/>
 *         &lt;element name="vaccinationEfficacyMeasured" type="{http://types.apollo.pitt.edu/v3_0_0/}VaccinationEfficacyMeasured" maxOccurs="unbounded"/>
 *         &lt;element name="dataSets" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="references" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacyStudy", propOrder = {
    "description",
    "vaccinationPreventableOutcome",
    "vaccinationEfficacyMeasured",
    "dataSets",
    "references"
})
public class VaccinationEfficacyStudy {

    protected String description;
    @XmlElement(required = true)
    protected TreatmentPreventableOutcomeEnum vaccinationPreventableOutcome;
    @XmlElement(required = true)
    protected List<VaccinationEfficacyMeasured> vaccinationEfficacyMeasured;
    @XmlElement(required = true)
    protected List<String> dataSets;
    @XmlElement(required = true)
    protected List<String> references;

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
     * Gets the value of the vaccinationPreventableOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentPreventableOutcomeEnum }
     *     
     */
    public TreatmentPreventableOutcomeEnum getVaccinationPreventableOutcome() {
        return vaccinationPreventableOutcome;
    }

    /**
     * Sets the value of the vaccinationPreventableOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentPreventableOutcomeEnum }
     *     
     */
    public void setVaccinationPreventableOutcome(TreatmentPreventableOutcomeEnum value) {
        this.vaccinationPreventableOutcome = value;
    }

    /**
     * Gets the value of the vaccinationEfficacyMeasured property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccinationEfficacyMeasured property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationEfficacyMeasured().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationEfficacyMeasured }
     * 
     * 
     */
    public List<VaccinationEfficacyMeasured> getVaccinationEfficacyMeasured() {
        if (vaccinationEfficacyMeasured == null) {
            vaccinationEfficacyMeasured = new ArrayList<VaccinationEfficacyMeasured>();
        }
        return this.vaccinationEfficacyMeasured;
    }

    /**
     * Gets the value of the dataSets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataSets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataSets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataSets() {
        if (dataSets == null) {
            dataSets = new ArrayList<String>();
        }
        return this.dataSets;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getReferences() {
        if (references == null) {
            references = new ArrayList<String>();
        }
        return this.references;
    }

}
