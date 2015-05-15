
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentSystemLogistics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentSystemLogistics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location"/>
 *         &lt;element name="supplySchedulePerDay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplyScheduleUnits" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfMeasureEnum" minOccurs="0"/>
 *         &lt;element name="administrationCapacityPerDay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="administrationCapacityUnits" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfMeasureEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentSystemLogistics", propOrder = {
    "description",
    "location",
    "supplySchedulePerDay",
    "supplyScheduleUnits",
    "administrationCapacityPerDay",
    "administrationCapacityUnits"
})
public class TreatmentSystemLogistics {

    protected String description;
    @XmlElement(required = true)
    protected Location location;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected List<BigInteger> supplySchedulePerDay;
    protected UnitOfMeasureEnum supplyScheduleUnits;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected List<BigInteger> administrationCapacityPerDay;
    protected UnitOfMeasureEnum administrationCapacityUnits;

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
     * Gets the value of the supplySchedulePerDay property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplySchedulePerDay property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplySchedulePerDay().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getSupplySchedulePerDay() {
        if (supplySchedulePerDay == null) {
            supplySchedulePerDay = new ArrayList<BigInteger>();
        }
        return this.supplySchedulePerDay;
    }

    /**
     * Gets the value of the supplyScheduleUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public UnitOfMeasureEnum getSupplyScheduleUnits() {
        return supplyScheduleUnits;
    }

    /**
     * Sets the value of the supplyScheduleUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public void setSupplyScheduleUnits(UnitOfMeasureEnum value) {
        this.supplyScheduleUnits = value;
    }

    /**
     * Gets the value of the administrationCapacityPerDay property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the administrationCapacityPerDay property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdministrationCapacityPerDay().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getAdministrationCapacityPerDay() {
        if (administrationCapacityPerDay == null) {
            administrationCapacityPerDay = new ArrayList<BigInteger>();
        }
        return this.administrationCapacityPerDay;
    }

    /**
     * Gets the value of the administrationCapacityUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public UnitOfMeasureEnum getAdministrationCapacityUnits() {
        return administrationCapacityUnits;
    }

    /**
     * Sets the value of the administrationCapacityUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasureEnum }
     *     
     */
    public void setAdministrationCapacityUnits(UnitOfMeasureEnum value) {
        this.administrationCapacityUnits = value;
    }

}
