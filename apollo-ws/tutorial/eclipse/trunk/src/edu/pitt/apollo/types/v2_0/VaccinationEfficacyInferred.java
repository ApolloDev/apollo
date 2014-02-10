
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationEfficacyInferred complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacyInferred">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}VaccinationEfficacy">
 *       &lt;sequence>
 *         &lt;element name="inferredFromTheseStudies" type="{http://types.apollo.pitt.edu/v2_0/}VaccinationEfficacyStudy" maxOccurs="unbounded"/>
 *         &lt;element name="vaccinationEfficacyModel" type="{http://types.apollo.pitt.edu/v2_0/}BayesianNetworkDistribution" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacyInferred", propOrder = {
    "inferredFromTheseStudies",
    "vaccinationEfficacyModel"
})
public class VaccinationEfficacyInferred
    extends VaccinationEfficacy
{

    @XmlElement(required = true)
    protected List<VaccinationEfficacyStudy> inferredFromTheseStudies;
    protected BayesianNetworkDistribution vaccinationEfficacyModel;

    /**
     * Gets the value of the inferredFromTheseStudies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inferredFromTheseStudies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInferredFromTheseStudies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationEfficacyStudy }
     * 
     * 
     */
    public List<VaccinationEfficacyStudy> getInferredFromTheseStudies() {
        if (inferredFromTheseStudies == null) {
            inferredFromTheseStudies = new ArrayList<VaccinationEfficacyStudy>();
        }
        return this.inferredFromTheseStudies;
    }

    /**
     * Gets the value of the vaccinationEfficacyModel property.
     * 
     * @return
     *     possible object is
     *     {@link BayesianNetworkDistribution }
     *     
     */
    public BayesianNetworkDistribution getVaccinationEfficacyModel() {
        return vaccinationEfficacyModel;
    }

    /**
     * Sets the value of the vaccinationEfficacyModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BayesianNetworkDistribution }
     *     
     */
    public void setVaccinationEfficacyModel(BayesianNetworkDistribution value) {
        this.vaccinationEfficacyModel = value;
    }

}
