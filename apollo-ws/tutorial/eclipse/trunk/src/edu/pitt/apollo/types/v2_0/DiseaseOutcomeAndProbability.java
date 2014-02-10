
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseOutcomeAndProbability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiseaseOutcomeAndProbability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diseaseOutcome" type="{http://types.apollo.pitt.edu/v2_0/}DiseaseOutcome"/>
 *         &lt;element name="probability" type="{http://types.apollo.pitt.edu/v2_0/}ProbabilisticParameterValue"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseOutcomeAndProbability", propOrder = {
    "diseaseOutcome",
    "probability"
})
public class DiseaseOutcomeAndProbability {

    @XmlElement(required = true)
    protected DiseaseOutcome diseaseOutcome;
    @XmlElement(required = true)
    protected ProbabilisticParameterValue probability;

    /**
     * Gets the value of the diseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcome }
     *     
     */
    public DiseaseOutcome getDiseaseOutcome() {
        return diseaseOutcome;
    }

    /**
     * Sets the value of the diseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcome }
     *     
     */
    public void setDiseaseOutcome(DiseaseOutcome value) {
        this.diseaseOutcome = value;
    }

    /**
     * Gets the value of the probability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public ProbabilisticParameterValue getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public void setProbability(ProbabilisticParameterValue value) {
        this.probability = value;
    }

}
