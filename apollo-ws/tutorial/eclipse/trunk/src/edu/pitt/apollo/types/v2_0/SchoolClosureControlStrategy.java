
package edu.pitt.apollo.types.v2_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SchoolClosureControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SchoolClosureControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="schoolClosureDuration" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="schoolClosureTargetFacilities" type="{http://types.apollo.pitt.edu/v2_0/}SchoolClosureTargetFacilities"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SchoolClosureControlStrategy", propOrder = {
    "schoolClosureDuration",
    "schoolClosureTargetFacilities"
})
public class SchoolClosureControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger schoolClosureDuration;
    @XmlElement(required = true)
    protected SchoolClosureTargetFacilities schoolClosureTargetFacilities;

    /**
     * Gets the value of the schoolClosureDuration property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSchoolClosureDuration() {
        return schoolClosureDuration;
    }

    /**
     * Sets the value of the schoolClosureDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSchoolClosureDuration(BigInteger value) {
        this.schoolClosureDuration = value;
    }

    /**
     * Gets the value of the schoolClosureTargetFacilities property.
     * 
     * @return
     *     possible object is
     *     {@link SchoolClosureTargetFacilities }
     *     
     */
    public SchoolClosureTargetFacilities getSchoolClosureTargetFacilities() {
        return schoolClosureTargetFacilities;
    }

    /**
     * Sets the value of the schoolClosureTargetFacilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchoolClosureTargetFacilities }
     *     
     */
    public void setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities value) {
        this.schoolClosureTargetFacilities = value;
    }

}
