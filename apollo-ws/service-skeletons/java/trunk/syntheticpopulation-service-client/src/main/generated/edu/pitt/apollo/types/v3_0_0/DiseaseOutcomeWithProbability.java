
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseOutcomeWithProbability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiseaseOutcomeWithProbability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diseaseOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum"/>
 *         &lt;element name="probability" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseOutcomeWithProbability", propOrder = {
    "title",
    "diseaseOutcome",
    "probability"
})
public class DiseaseOutcomeWithProbability {

    protected String title;
    @XmlElement(required = true)
    protected DiseaseOutcomeEnum diseaseOutcome;
    @XmlElement(required = true)
    protected ProbabilisticParameter probability;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the diseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getDiseaseOutcome() {
        return diseaseOutcome;
    }

    /**
     * Sets the value of the diseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setDiseaseOutcome(DiseaseOutcomeEnum value) {
        this.diseaseOutcome = value;
    }

    /**
     * Gets the value of the probability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setProbability(ProbabilisticParameter value) {
        this.probability = value;
    }

}
