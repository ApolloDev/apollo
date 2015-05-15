
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TreatmentEfficacy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentEfficacy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hostIdentifier" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="strainIdentifier" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="forTreatmentPreventableOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentPreventableOutcomeEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentEfficacy", propOrder = {
    "hostIdentifier",
    "strainIdentifier",
    "forTreatmentPreventableOutcome"
})
@XmlSeeAlso({
    VaccinationEfficacyInferred.class,
    VaccinationEfficacyForSimulatorConfiguration.class,
    AntiviralTreatmentEfficacy.class,
    VaccinationEfficacyMeasured.class,
    DrugTreatmentEfficacyForSimulatorConfiguration.class
})
public class TreatmentEfficacy {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String hostIdentifier;
    @XmlElement(required = true)
    protected ApolloPathogenCode strainIdentifier;
    @XmlElement(required = true)
    protected TreatmentPreventableOutcomeEnum forTreatmentPreventableOutcome;

    /**
     * Gets the value of the hostIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostIdentifier() {
        return hostIdentifier;
    }

    /**
     * Sets the value of the hostIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostIdentifier(String value) {
        this.hostIdentifier = value;
    }

    /**
     * Gets the value of the strainIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getStrainIdentifier() {
        return strainIdentifier;
    }

    /**
     * Sets the value of the strainIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setStrainIdentifier(ApolloPathogenCode value) {
        this.strainIdentifier = value;
    }

    /**
     * Gets the value of the forTreatmentPreventableOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentPreventableOutcomeEnum }
     *     
     */
    public TreatmentPreventableOutcomeEnum getForTreatmentPreventableOutcome() {
        return forTreatmentPreventableOutcome;
    }

    /**
     * Sets the value of the forTreatmentPreventableOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentPreventableOutcomeEnum }
     *     
     */
    public void setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum value) {
        this.forTreatmentPreventableOutcome = value;
    }

}
