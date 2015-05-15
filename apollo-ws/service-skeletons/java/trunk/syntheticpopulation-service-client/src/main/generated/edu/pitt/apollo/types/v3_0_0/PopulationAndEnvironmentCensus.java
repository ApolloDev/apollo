
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
 * <p>Java class for PopulationAndEnvironmentCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationAndEnvironmentCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Census">
 *       &lt;sequence>
 *         &lt;element name="nameOfAdministativeUnit" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="numberOfPeople" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numberOfSchools" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numberOfWorkplaces" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="subLocationCensuses" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationAndEnvironmentCensus" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationAndEnvironmentCensus", propOrder = {
    "nameOfAdministativeUnit",
    "numberOfPeople",
    "numberOfSchools",
    "numberOfWorkplaces",
    "subLocationCensuses"
})
public class PopulationAndEnvironmentCensus
    extends Census
{

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nameOfAdministativeUnit;
    @XmlElement(required = true)
    protected BigInteger numberOfPeople;
    @XmlElement(required = true)
    protected BigInteger numberOfSchools;
    @XmlElement(required = true)
    protected BigInteger numberOfWorkplaces;
    protected List<PopulationAndEnvironmentCensus> subLocationCensuses;

    /**
     * Gets the value of the nameOfAdministativeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfAdministativeUnit() {
        return nameOfAdministativeUnit;
    }

    /**
     * Sets the value of the nameOfAdministativeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfAdministativeUnit(String value) {
        this.nameOfAdministativeUnit = value;
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
     * {@link PopulationAndEnvironmentCensus }
     * 
     * 
     */
    public List<PopulationAndEnvironmentCensus> getSubLocationCensuses() {
        if (subLocationCensuses == null) {
            subLocationCensuses = new ArrayList<PopulationAndEnvironmentCensus>();
        }
        return this.subLocationCensuses;
    }

}
