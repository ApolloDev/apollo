
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationEfficacyForSimulatorConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacyForSimulatorConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}VaccinationEfficacy">
 *       &lt;sequence>
 *         &lt;element name="averageVaccinationEfficacy" type="{http://types.apollo.pitt.edu/v2_0/}Probability"/>
 *         &lt;element name="vaccinationEfficacybyAgeRange" type="{http://types.apollo.pitt.edu/v2_0/}ProbabilisticParameterValue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vaccinationEfficaciesByTimeSinceMostRecentDose" type="{http://types.apollo.pitt.edu/v2_0/}VaccinationEfficacyByTimeSinceDose" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacyForSimulatorConfiguration", propOrder = {
    "averageVaccinationEfficacy",
    "vaccinationEfficacybyAgeRange",
    "vaccinationEfficaciesByTimeSinceMostRecentDose"
})
public class VaccinationEfficacyForSimulatorConfiguration
    extends VaccinationEfficacy
{

    protected double averageVaccinationEfficacy;
    protected List<ProbabilisticParameterValue> vaccinationEfficacybyAgeRange;
    protected List<VaccinationEfficacyByTimeSinceDose> vaccinationEfficaciesByTimeSinceMostRecentDose;

    /**
     * Gets the value of the averageVaccinationEfficacy property.
     * 
     */
    public double getAverageVaccinationEfficacy() {
        return averageVaccinationEfficacy;
    }

    /**
     * Sets the value of the averageVaccinationEfficacy property.
     * 
     */
    public void setAverageVaccinationEfficacy(double value) {
        this.averageVaccinationEfficacy = value;
    }

    /**
     * Gets the value of the vaccinationEfficacybyAgeRange property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccinationEfficacybyAgeRange property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationEfficacybyAgeRange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProbabilisticParameterValue }
     * 
     * 
     */
    public List<ProbabilisticParameterValue> getVaccinationEfficacybyAgeRange() {
        if (vaccinationEfficacybyAgeRange == null) {
            vaccinationEfficacybyAgeRange = new ArrayList<ProbabilisticParameterValue>();
        }
        return this.vaccinationEfficacybyAgeRange;
    }

    /**
     * Gets the value of the vaccinationEfficaciesByTimeSinceMostRecentDose property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccinationEfficaciesByTimeSinceMostRecentDose property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationEfficaciesByTimeSinceMostRecentDose().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationEfficacyByTimeSinceDose }
     * 
     * 
     */
    public List<VaccinationEfficacyByTimeSinceDose> getVaccinationEfficaciesByTimeSinceMostRecentDose() {
        if (vaccinationEfficaciesByTimeSinceMostRecentDose == null) {
            vaccinationEfficaciesByTimeSinceMostRecentDose = new ArrayList<VaccinationEfficacyByTimeSinceDose>();
        }
        return this.vaccinationEfficaciesByTimeSinceMostRecentDose;
    }

}
