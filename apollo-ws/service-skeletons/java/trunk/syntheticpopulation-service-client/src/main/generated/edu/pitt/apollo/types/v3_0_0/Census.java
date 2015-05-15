
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Census complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Census">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location"/>
 *         &lt;element name="simulatorTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="referenceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Census", propOrder = {
    "description",
    "location",
    "simulatorTime",
    "referenceDate"
})
@XmlSeeAlso({
    PopulationInfectionAndImmunityCensus.class,
    AbioticEcosystemCensus.class,
    ContaminatedThingCensus.class,
    PopulationTreatmentCensus.class,
    PopulationAndEnvironmentCensus.class
})
public class Census
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected Location location;
    protected int simulatorTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar referenceDate;

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
     * Gets the value of the simulatorTime property.
     * 
     */
    public int getSimulatorTime() {
        return simulatorTime;
    }

    /**
     * Sets the value of the simulatorTime property.
     * 
     */
    public void setSimulatorTime(int value) {
        this.simulatorTime = value;
    }

    /**
     * Gets the value of the referenceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReferenceDate() {
        return referenceDate;
    }

    /**
     * Sets the value of the referenceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReferenceDate(XMLGregorianCalendar value) {
        this.referenceDate = value;
    }

}
