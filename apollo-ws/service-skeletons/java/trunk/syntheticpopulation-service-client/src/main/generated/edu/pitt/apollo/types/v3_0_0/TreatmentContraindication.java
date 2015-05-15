
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentContraindication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentContraindication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="simulatorReferencablePopulation" type="{http://types.apollo.pitt.edu/v3_0_0/}TargetPopulationDefinition" minOccurs="0"/>
 *         &lt;element name="fractionOfSimulatorReferencablePopulation" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentContraindication", propOrder = {
    "description",
    "simulatorReferencablePopulation",
    "fractionOfSimulatorReferencablePopulation"
})
public class TreatmentContraindication {

    protected String description;
    protected TargetPopulationDefinition simulatorReferencablePopulation;
    protected Double fractionOfSimulatorReferencablePopulation;

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
     * Gets the value of the simulatorReferencablePopulation property.
     * 
     * @return
     *     possible object is
     *     {@link TargetPopulationDefinition }
     *     
     */
    public TargetPopulationDefinition getSimulatorReferencablePopulation() {
        return simulatorReferencablePopulation;
    }

    /**
     * Sets the value of the simulatorReferencablePopulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetPopulationDefinition }
     *     
     */
    public void setSimulatorReferencablePopulation(TargetPopulationDefinition value) {
        this.simulatorReferencablePopulation = value;
    }

    /**
     * Gets the value of the fractionOfSimulatorReferencablePopulation property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFractionOfSimulatorReferencablePopulation() {
        return fractionOfSimulatorReferencablePopulation;
    }

    /**
     * Sets the value of the fractionOfSimulatorReferencablePopulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFractionOfSimulatorReferencablePopulation(Double value) {
        this.fractionOfSimulatorReferencablePopulation = value;
    }

}
