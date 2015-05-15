
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CaseRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caseId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="ageInYears" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="occupations" type="{http://types.apollo.pitt.edu/v3_0_0/}OccupationEnum" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="placesVisitedWhileSusceptibleOrInfectious" type="{http://types.apollo.pitt.edu/v3_0_0/}PlaceVisited" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="infectionAcquiredFromCaseId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="diseaseOutcomesWithLocationDateTime" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeWithLocationDateTime" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="labTestsAndResults" type="{http://types.apollo.pitt.edu/v3_0_0/}LabTestAndResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pathogenGeneSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseRecord", propOrder = {
    "caseId",
    "ageInYears",
    "occupations",
    "placesVisitedWhileSusceptibleOrInfectious",
    "infectionAcquiredFromCaseId",
    "diseaseOutcomesWithLocationDateTime",
    "labTestsAndResults",
    "pathogenGeneSequence"
})
public class CaseRecord {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String caseId;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger ageInYears;
    protected List<OccupationEnum> occupations;
    protected List<PlaceVisited> placesVisitedWhileSusceptibleOrInfectious;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String infectionAcquiredFromCaseId;
    protected List<DiseaseOutcomeWithLocationDateTime> diseaseOutcomesWithLocationDateTime;
    protected List<LabTestAndResult> labTestsAndResults;
    protected String pathogenGeneSequence;

    /**
     * Gets the value of the caseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Sets the value of the caseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseId(String value) {
        this.caseId = value;
    }

    /**
     * Gets the value of the ageInYears property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAgeInYears() {
        return ageInYears;
    }

    /**
     * Sets the value of the ageInYears property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAgeInYears(BigInteger value) {
        this.ageInYears = value;
    }

    /**
     * Gets the value of the occupations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occupations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccupations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OccupationEnum }
     * 
     * 
     */
    public List<OccupationEnum> getOccupations() {
        if (occupations == null) {
            occupations = new ArrayList<OccupationEnum>();
        }
        return this.occupations;
    }

    /**
     * Gets the value of the placesVisitedWhileSusceptibleOrInfectious property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placesVisitedWhileSusceptibleOrInfectious property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacesVisitedWhileSusceptibleOrInfectious().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlaceVisited }
     * 
     * 
     */
    public List<PlaceVisited> getPlacesVisitedWhileSusceptibleOrInfectious() {
        if (placesVisitedWhileSusceptibleOrInfectious == null) {
            placesVisitedWhileSusceptibleOrInfectious = new ArrayList<PlaceVisited>();
        }
        return this.placesVisitedWhileSusceptibleOrInfectious;
    }

    /**
     * Gets the value of the infectionAcquiredFromCaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfectionAcquiredFromCaseId() {
        return infectionAcquiredFromCaseId;
    }

    /**
     * Sets the value of the infectionAcquiredFromCaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfectionAcquiredFromCaseId(String value) {
        this.infectionAcquiredFromCaseId = value;
    }

    /**
     * Gets the value of the diseaseOutcomesWithLocationDateTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseaseOutcomesWithLocationDateTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseaseOutcomesWithLocationDateTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiseaseOutcomeWithLocationDateTime }
     * 
     * 
     */
    public List<DiseaseOutcomeWithLocationDateTime> getDiseaseOutcomesWithLocationDateTime() {
        if (diseaseOutcomesWithLocationDateTime == null) {
            diseaseOutcomesWithLocationDateTime = new ArrayList<DiseaseOutcomeWithLocationDateTime>();
        }
        return this.diseaseOutcomesWithLocationDateTime;
    }

    /**
     * Gets the value of the labTestsAndResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the labTestsAndResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabTestsAndResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LabTestAndResult }
     * 
     * 
     */
    public List<LabTestAndResult> getLabTestsAndResults() {
        if (labTestsAndResults == null) {
            labTestsAndResults = new ArrayList<LabTestAndResult>();
        }
        return this.labTestsAndResults;
    }

    /**
     * Gets the value of the pathogenGeneSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathogenGeneSequence() {
        return pathogenGeneSequence;
    }

    /**
     * Sets the value of the pathogenGeneSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathogenGeneSequence(String value) {
        this.pathogenGeneSequence = value;
    }

}
