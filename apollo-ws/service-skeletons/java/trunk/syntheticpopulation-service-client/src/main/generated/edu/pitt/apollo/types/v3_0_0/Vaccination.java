
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
 * <p>Java class for Vaccination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vaccination">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Treatment">
 *       &lt;sequence>
 *         &lt;element name="vaccineId" type="{http://types.apollo.pitt.edu/v3_0_0/}vaccineOntologyId"/>
 *         &lt;element name="vaccinationEfficacies" type="{http://types.apollo.pitt.edu/v3_0_0/}VaccinationEfficacyForSimulatorConfiguration" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vaccination", propOrder = {
    "vaccineId",
    "vaccinationEfficacies"
})
public class Vaccination
    extends Treatment
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String vaccineId;
    @XmlElement(required = true)
    protected List<VaccinationEfficacyForSimulatorConfiguration> vaccinationEfficacies;

    /**
     * Gets the value of the vaccineId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccineId() {
        return vaccineId;
    }

    /**
     * Sets the value of the vaccineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccineId(String value) {
        this.vaccineId = value;
    }

    /**
     * Gets the value of the vaccinationEfficacies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccinationEfficacies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationEfficacies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationEfficacyForSimulatorConfiguration }
     * 
     * 
     */
    public List<VaccinationEfficacyForSimulatorConfiguration> getVaccinationEfficacies() {
        if (vaccinationEfficacies == null) {
            vaccinationEfficacies = new ArrayList<VaccinationEfficacyForSimulatorConfiguration>();
        }
        return this.vaccinationEfficacies;
    }

}
