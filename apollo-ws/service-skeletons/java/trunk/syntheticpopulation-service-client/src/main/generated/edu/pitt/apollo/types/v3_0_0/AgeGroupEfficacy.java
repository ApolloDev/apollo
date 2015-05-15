
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgeGroupEfficacy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgeGroupEfficacy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ageRange" type="{http://types.apollo.pitt.edu/v3_0_0/}AgeRangeCategoryDefinition"/>
 *         &lt;element name="vaccineEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgeGroupEfficacy", propOrder = {
    "ageRange",
    "vaccineEfficacy"
})
public class AgeGroupEfficacy {

    @XmlElement(required = true)
    protected AgeRangeCategoryDefinition ageRange;
    protected double vaccineEfficacy;

    /**
     * Gets the value of the ageRange property.
     * 
     * @return
     *     possible object is
     *     {@link AgeRangeCategoryDefinition }
     *     
     */
    public AgeRangeCategoryDefinition getAgeRange() {
        return ageRange;
    }

    /**
     * Sets the value of the ageRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgeRangeCategoryDefinition }
     *     
     */
    public void setAgeRange(AgeRangeCategoryDefinition value) {
        this.ageRange = value;
    }

    /**
     * Gets the value of the vaccineEfficacy property.
     * 
     */
    public double getVaccineEfficacy() {
        return vaccineEfficacy;
    }

    /**
     * Sets the value of the vaccineEfficacy property.
     * 
     */
    public void setVaccineEfficacy(double value) {
        this.vaccineEfficacy = value;
    }

}
