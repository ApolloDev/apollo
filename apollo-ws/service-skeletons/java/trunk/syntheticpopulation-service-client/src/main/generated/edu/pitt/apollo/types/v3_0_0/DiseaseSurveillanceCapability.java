
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for DiseaseSurveillanceCapability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiseaseSurveillanceCapability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location"/>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="speciesOfCase" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="caseDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum"/>
 *         &lt;element name="sensitivityOfCaseDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="specificityOfCaseDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="timeDelayOfCaseDetection" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseSurveillanceCapability", propOrder = {
    "location",
    "pathogen",
    "speciesOfCase",
    "caseDefinition",
    "sensitivityOfCaseDetection",
    "specificityOfCaseDetection",
    "timeDelayOfCaseDetection"
})
public class DiseaseSurveillanceCapability {

    @XmlElement(required = true)
    protected Location location;
    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesOfCase;
    @XmlElement(required = true)
    protected DiseaseOutcomeEnum caseDefinition;
    protected double sensitivityOfCaseDetection;
    protected double specificityOfCaseDetection;
    @XmlElement(required = true)
    protected Duration timeDelayOfCaseDetection;

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
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
     * Gets the value of the speciesOfCase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesOfCase() {
        return speciesOfCase;
    }

    /**
     * Sets the value of the speciesOfCase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesOfCase(String value) {
        this.speciesOfCase = value;
    }

    /**
     * Gets the value of the caseDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getCaseDefinition() {
        return caseDefinition;
    }

    /**
     * Sets the value of the caseDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setCaseDefinition(DiseaseOutcomeEnum value) {
        this.caseDefinition = value;
    }

    /**
     * Gets the value of the sensitivityOfCaseDetection property.
     * 
     */
    public double getSensitivityOfCaseDetection() {
        return sensitivityOfCaseDetection;
    }

    /**
     * Sets the value of the sensitivityOfCaseDetection property.
     * 
     */
    public void setSensitivityOfCaseDetection(double value) {
        this.sensitivityOfCaseDetection = value;
    }

    /**
     * Gets the value of the specificityOfCaseDetection property.
     * 
     */
    public double getSpecificityOfCaseDetection() {
        return specificityOfCaseDetection;
    }

    /**
     * Sets the value of the specificityOfCaseDetection property.
     * 
     */
    public void setSpecificityOfCaseDetection(double value) {
        this.specificityOfCaseDetection = value;
    }

    /**
     * Gets the value of the timeDelayOfCaseDetection property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getTimeDelayOfCaseDetection() {
        return timeDelayOfCaseDetection;
    }

    /**
     * Sets the value of the timeDelayOfCaseDetection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setTimeDelayOfCaseDetection(Duration value) {
        this.timeDelayOfCaseDetection = value;
    }

}
