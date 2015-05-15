
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
 * <p>Java class for PreEpidemicEcosystemCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PreEpidemicEcosystemCensus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nameOfAdministrativeUnit" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="numberOfPeople" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numberOfHouseholds" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="numberOfSchools" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="numberOfWorkplaces" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="additionalDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subLocationCensuses" type="{http://types.apollo.pitt.edu/v3_0_0/}PreEpidemicEcosystemCensus" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "PreEpidemicEcosystemCensus", propOrder = {
    "nameOfAdministrativeUnit",
    "numberOfPeople",
    "numberOfHouseholds",
    "numberOfSchools",
    "numberOfWorkplaces",
    "additionalDescription",
    "subLocationCensuses",
    "referenceId"
})
public class PreEpidemicEcosystemCensus {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nameOfAdministrativeUnit;
    @XmlElement(required = true)
    protected BigInteger numberOfPeople;
    protected BigInteger numberOfHouseholds;
    protected BigInteger numberOfSchools;
    protected BigInteger numberOfWorkplaces;
    protected String additionalDescription;
    protected List<PreEpidemicEcosystemCensus> subLocationCensuses;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the nameOfAdministrativeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfAdministrativeUnit() {
        return nameOfAdministrativeUnit;
    }

    /**
     * Sets the value of the nameOfAdministrativeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfAdministrativeUnit(String value) {
        this.nameOfAdministrativeUnit = value;
    }

    /**
     * Gets the value of the numberOfPeople property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Sets the value of the numberOfPeople property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfPeople(BigInteger value) {
        this.numberOfPeople = value;
    }

    /**
     * Gets the value of the numberOfHouseholds property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfHouseholds() {
        return numberOfHouseholds;
    }

    /**
     * Sets the value of the numberOfHouseholds property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfHouseholds(BigInteger value) {
        this.numberOfHouseholds = value;
    }

    /**
     * Gets the value of the numberOfSchools property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfSchools() {
        return numberOfSchools;
    }

    /**
     * Sets the value of the numberOfSchools property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfSchools(BigInteger value) {
        this.numberOfSchools = value;
    }

    /**
     * Gets the value of the numberOfWorkplaces property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfWorkplaces() {
        return numberOfWorkplaces;
    }

    /**
     * Sets the value of the numberOfWorkplaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfWorkplaces(BigInteger value) {
        this.numberOfWorkplaces = value;
    }

    /**
     * Gets the value of the additionalDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalDescription() {
        return additionalDescription;
    }

    /**
     * Sets the value of the additionalDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalDescription(String value) {
        this.additionalDescription = value;
    }

    /**
     * Gets the value of the subLocationCensuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subLocationCensuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubLocationCensuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreEpidemicEcosystemCensus }
     * 
     * 
     */
    public List<PreEpidemicEcosystemCensus> getSubLocationCensuses() {
        if (subLocationCensuses == null) {
            subLocationCensuses = new ArrayList<PreEpidemicEcosystemCensus>();
        }
        return this.subLocationCensuses;
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
