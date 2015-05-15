
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Population complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Population">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="speciesId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="census" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationAndEnvironmentCensus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Population", propOrder = {
    "speciesId",
    "census"
})
public class Population {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesId;
    @XmlElement(required = true)
    protected PopulationAndEnvironmentCensus census;

    /**
     * Gets the value of the speciesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesId() {
        return speciesId;
    }

    /**
     * Sets the value of the speciesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesId(String value) {
        this.speciesId = value;
    }

    /**
     * Gets the value of the census property.
     * 
     * @return
     *     possible object is
     *     {@link PopulationAndEnvironmentCensus }
     *     
     */
    public PopulationAndEnvironmentCensus getCensus() {
        return census;
    }

    /**
     * Sets the value of the census property.
     * 
     * @param value
     *     allowed object is
     *     {@link PopulationAndEnvironmentCensus }
     *     
     */
    public void setCensus(PopulationAndEnvironmentCensus value) {
        this.census = value;
    }

}
