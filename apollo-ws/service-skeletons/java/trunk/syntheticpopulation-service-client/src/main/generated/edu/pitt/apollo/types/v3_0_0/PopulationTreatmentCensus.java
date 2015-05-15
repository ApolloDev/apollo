
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PopulationTreatmentCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationTreatmentCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Census">
 *       &lt;sequence>
 *         &lt;element name="populationSpecies" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="treatmentCensusData" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationTreatmentCensusData"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationTreatmentCensus", propOrder = {
    "populationSpecies",
    "treatmentCensusData"
})
public class PopulationTreatmentCensus
    extends Census
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String populationSpecies;
    @XmlElement(required = true)
    protected PopulationTreatmentCensusData treatmentCensusData;

    /**
     * Gets the value of the populationSpecies property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopulationSpecies() {
        return populationSpecies;
    }

    /**
     * Sets the value of the populationSpecies property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopulationSpecies(String value) {
        this.populationSpecies = value;
    }

    /**
     * Gets the value of the treatmentCensusData property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationTreatmentCensusData }
     *     
     */
    public PopulationTreatmentCensusData getTreatmentCensusData() {
        return treatmentCensusData;
    }

    /**
     * Sets the value of the treatmentCensusData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationTreatmentCensusData }
     *     
     */
    public void setTreatmentCensusData(PopulationTreatmentCensusData value) {
        this.treatmentCensusData = value;
    }

}
