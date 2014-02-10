
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Vaccination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vaccination">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}Treatment">
 *       &lt;sequence>
 *         &lt;element name="vaccine" type="{http://types.apollo.pitt.edu/v2_0/}Vaccine"/>
 *         &lt;element name="vaccinationEfficacies" type="{http://types.apollo.pitt.edu/v2_0/}VaccinationEfficacy" maxOccurs="unbounded" minOccurs="0"/>
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
    "vaccine",
    "vaccinationEfficacies"
})
public class Vaccination
    extends Treatment
{

    @XmlElement(required = true)
    protected Vaccine vaccine;
    protected List<VaccinationEfficacy> vaccinationEfficacies;

    /**
     * Gets the value of the vaccine property.
     * 
     * @return
     *     possible object is
     *     {@link Vaccine }
     *     
     */
    public Vaccine getVaccine() {
        return vaccine;
    }

    /**
     * Sets the value of the vaccine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vaccine }
     *     
     */
    public void setVaccine(Vaccine value) {
        this.vaccine = value;
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
     * {@link VaccinationEfficacy }
     * 
     * 
     */
    public List<VaccinationEfficacy> getVaccinationEfficacies() {
        if (vaccinationEfficacies == null) {
            vaccinationEfficacies = new ArrayList<VaccinationEfficacy>();
        }
        return this.vaccinationEfficacies;
    }

}
