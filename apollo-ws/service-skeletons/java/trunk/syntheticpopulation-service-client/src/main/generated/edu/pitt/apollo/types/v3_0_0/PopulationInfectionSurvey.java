
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PopulationInfectionSurvey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationInfectionSurvey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="speciesSampled" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="speciesSampledNcbiTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="numberSampled" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="whereSampled" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="testName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="testLoincId" type="{http://types.apollo.pitt.edu/v3_0_0/}LoincId" minOccurs="0"/>
 *         &lt;element name="numberOfSamplesPositive" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfectionSurvey", propOrder = {
    "speciesSampled",
    "speciesSampledNcbiTaxonId",
    "numberSampled",
    "whereSampled",
    "testName",
    "testLoincId",
    "numberOfSamplesPositive",
    "referenceId"
})
public class PopulationInfectionSurvey {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String speciesSampled;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesSampledNcbiTaxonId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numberSampled;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String whereSampled;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String testName;
    protected String testLoincId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String numberOfSamplesPositive;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the speciesSampled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesSampled() {
        return speciesSampled;
    }

    /**
     * Sets the value of the speciesSampled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesSampled(String value) {
        this.speciesSampled = value;
    }

    /**
     * Gets the value of the speciesSampledNcbiTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesSampledNcbiTaxonId() {
        return speciesSampledNcbiTaxonId;
    }

    /**
     * Sets the value of the speciesSampledNcbiTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesSampledNcbiTaxonId(String value) {
        this.speciesSampledNcbiTaxonId = value;
    }

    /**
     * Gets the value of the numberSampled property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberSampled() {
        return numberSampled;
    }

    /**
     * Sets the value of the numberSampled property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberSampled(BigInteger value) {
        this.numberSampled = value;
    }

    /**
     * Gets the value of the whereSampled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhereSampled() {
        return whereSampled;
    }

    /**
     * Sets the value of the whereSampled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhereSampled(String value) {
        this.whereSampled = value;
    }

    /**
     * Gets the value of the testName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Sets the value of the testName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestName(String value) {
        this.testName = value;
    }

    /**
     * Gets the value of the testLoincId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestLoincId() {
        return testLoincId;
    }

    /**
     * Sets the value of the testLoincId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestLoincId(String value) {
        this.testLoincId = value;
    }

    /**
     * Gets the value of the numberOfSamplesPositive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfSamplesPositive() {
        return numberOfSamplesPositive;
    }

    /**
     * Sets the value of the numberOfSamplesPositive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfSamplesPositive(String value) {
        this.numberOfSamplesPositive = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReferenceId(BigInteger value) {
        this.referenceId = value;
    }

}
