
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Duration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Duration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ParameterValue">
 *       &lt;sequence>
 *         &lt;element name="unitOfTime" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfTimeEnum"/>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Duration", propOrder = {
    "unitOfTime",
    "referenceId"
})
@XmlSeeAlso({
    FixedDuration.class,
    UncertainDuration.class
})
public class Duration
    extends ParameterValue
{

    @XmlElement(required = true)
    protected UnitOfTimeEnum unitOfTime;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the unitOfTime property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public UnitOfTimeEnum getUnitOfTime() {
        return unitOfTime;
    }

    /**
     * Sets the value of the unitOfTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public void setUnitOfTime(UnitOfTimeEnum value) {
        this.unitOfTime = value;
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
