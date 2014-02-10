
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for VaccinationEfficacy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccinationEfficacy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}TreatmentEfficacy">
 *       &lt;sequence>
 *         &lt;element name="vaccineIdentifier" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="strainIdentifier" type="{http://types.apollo.pitt.edu/v2_0/}ApolloPathogenCode"/>
 *         &lt;element name="hostIdentifier" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId"/>
 *         &lt;element name="forVaccinationPreventableOutcome" type="{http://types.apollo.pitt.edu/v2_0/}VaccinationPreventableOutcome"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationEfficacy", propOrder = {
    "vaccineIdentifier",
    "strainIdentifier",
    "hostIdentifier",
    "forVaccinationPreventableOutcome"
})
@XmlSeeAlso({
    VaccinationEfficacyForSimulatorConfiguration.class,
    VaccinationEfficacyMeasured.class,
    VaccinationEfficacyInferred.class
})
public class VaccinationEfficacy
    extends TreatmentEfficacy
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String vaccineIdentifier;
    @XmlElement(required = true)
    protected ApolloPathogenCode strainIdentifier;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String hostIdentifier;
    @XmlElement(required = true)
    protected VaccinationPreventableOutcome forVaccinationPreventableOutcome;

    /**
     * Gets the value of the vaccineIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccineIdentifier() {
        return vaccineIdentifier;
    }

    /**
     * Sets the value of the vaccineIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccineIdentifier(String value) {
        this.vaccineIdentifier = value;
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
     * Gets the value of the forVaccinationPreventableOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationPreventableOutcome }
     *     
     */
    public VaccinationPreventableOutcome getForVaccinationPreventableOutcome() {
        return forVaccinationPreventableOutcome;
    }

    /**
     * Sets the value of the forVaccinationPreventableOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationPreventableOutcome }
     *     
     */
    public void setForVaccinationPreventableOutcome(VaccinationPreventableOutcome value) {
        this.forVaccinationPreventableOutcome = value;
    }

}
