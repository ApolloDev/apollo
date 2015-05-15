
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompartmentalModelPopulationAndEnvironmentCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompartmentalModelPopulationAndEnvironmentCensus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bioticEcosystemParts" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensusData" maxOccurs="unbounded"/>
 *         &lt;element name="abioticEcosystemParts" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemCensus" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompartmentalModelPopulationAndEnvironmentCensus", propOrder = {
    "bioticEcosystemParts",
    "abioticEcosystemParts"
})
public class CompartmentalModelPopulationAndEnvironmentCensus {

    @XmlElement(required = true)
    protected List<PopulationInfectionAndImmunityCensusData> bioticEcosystemParts;
    @XmlElement(required = true)
    protected List<AbioticEcosystemCensus> abioticEcosystemParts;

    /**
     * Gets the value of the bioticEcosystemParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bioticEcosystemParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBioticEcosystemParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationInfectionAndImmunityCensusData }
     * 
     * 
     */
    public List<PopulationInfectionAndImmunityCensusData> getBioticEcosystemParts() {
        if (bioticEcosystemParts == null) {
            bioticEcosystemParts = new ArrayList<PopulationInfectionAndImmunityCensusData>();
        }
        return this.bioticEcosystemParts;
    }

    /**
     * Gets the value of the abioticEcosystemParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abioticEcosystemParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbioticEcosystemParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AbioticEcosystemCensus }
     * 
     * 
     */
    public List<AbioticEcosystemCensus> getAbioticEcosystemParts() {
        if (abioticEcosystemParts == null) {
            abioticEcosystemParts = new ArrayList<AbioticEcosystemCensus>();
        }
        return this.abioticEcosystemParts;
    }

}
