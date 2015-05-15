
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PopulationInfectionAndImmunityCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationInfectionAndImmunityCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Census">
 *       &lt;sequence>
 *         &lt;element name="populationSpecies" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="censusData" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensusData"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfectionAndImmunityCensus", propOrder = {
    "populationSpecies",
    "pathogen",
    "censusData"
})
public class PopulationInfectionAndImmunityCensus
    extends Census
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String populationSpecies;
    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    protected PopulationInfectionAndImmunityCensusData censusData;

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
     * Gets the value of the censusData property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationInfectionAndImmunityCensusData }
     *     
     */
    public PopulationInfectionAndImmunityCensusData getCensusData() {
        return censusData;
    }

    /**
     * Sets the value of the censusData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationInfectionAndImmunityCensusData }
     *     
     */
    public void setCensusData(PopulationInfectionAndImmunityCensusData value) {
        this.censusData = value;
    }

}
