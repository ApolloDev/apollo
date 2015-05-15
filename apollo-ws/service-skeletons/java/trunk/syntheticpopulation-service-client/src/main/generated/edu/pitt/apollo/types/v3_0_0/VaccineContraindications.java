
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccineContraindications complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VaccineContraindications">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infants" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="preschoolers" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="schoolAgeChildren" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="youngAdults" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="olderAdults" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="elderly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="pregnantAdultsRestricted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccineContraindications", propOrder = {
    "infants",
    "preschoolers",
    "schoolAgeChildren",
    "youngAdults",
    "olderAdults",
    "elderly",
    "pregnantAdultsRestricted"
})
public class VaccineContraindications {

    protected Boolean infants;
    protected Boolean preschoolers;
    protected Boolean schoolAgeChildren;
    protected Boolean youngAdults;
    protected Boolean olderAdults;
    protected Boolean elderly;
    protected Boolean pregnantAdultsRestricted;

    /**
     * Gets the value of the infants property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInfants() {
        return infants;
    }

    /**
     * Sets the value of the infants property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInfants(Boolean value) {
        this.infants = value;
    }

    /**
     * Gets the value of the preschoolers property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreschoolers() {
        return preschoolers;
    }

    /**
     * Sets the value of the preschoolers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreschoolers(Boolean value) {
        this.preschoolers = value;
    }

    /**
     * Gets the value of the schoolAgeChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSchoolAgeChildren() {
        return schoolAgeChildren;
    }

    /**
     * Sets the value of the schoolAgeChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSchoolAgeChildren(Boolean value) {
        this.schoolAgeChildren = value;
    }

    /**
     * Gets the value of the youngAdults property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isYoungAdults() {
        return youngAdults;
    }

    /**
     * Sets the value of the youngAdults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setYoungAdults(Boolean value) {
        this.youngAdults = value;
    }

    /**
     * Gets the value of the olderAdults property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOlderAdults() {
        return olderAdults;
    }

    /**
     * Sets the value of the olderAdults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOlderAdults(Boolean value) {
        this.olderAdults = value;
    }

    /**
     * Gets the value of the elderly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isElderly() {
        return elderly;
    }

    /**
     * Sets the value of the elderly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setElderly(Boolean value) {
        this.elderly = value;
    }

    /**
     * Gets the value of the pregnantAdultsRestricted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPregnantAdultsRestricted() {
        return pregnantAdultsRestricted;
    }

    /**
     * Sets the value of the pregnantAdultsRestricted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPregnantAdultsRestricted(Boolean value) {
        this.pregnantAdultsRestricted = value;
    }

}
