
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgeRangeCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgeRangeCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="unitOfTimeForLowerBound" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfTimeEnum"/>
 *         &lt;element name="lowerBound" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="unitOfTimeForUpperBound" type="{http://types.apollo.pitt.edu/v3_0_0/}UnitOfTimeEnum"/>
 *         &lt;element name="upperBound" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgeRangeCategoryDefinition", propOrder = {
    "unitOfTimeForLowerBound",
    "lowerBound",
    "unitOfTimeForUpperBound",
    "upperBound"
})
public class AgeRangeCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected UnitOfTimeEnum unitOfTimeForLowerBound;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger lowerBound;
    @XmlElement(required = true)
    protected UnitOfTimeEnum unitOfTimeForUpperBound;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger upperBound;

    /**
     * Gets the value of the unitOfTimeForLowerBound property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public UnitOfTimeEnum getUnitOfTimeForLowerBound() {
        return unitOfTimeForLowerBound;
    }

    /**
     * Sets the value of the unitOfTimeForLowerBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public void setUnitOfTimeForLowerBound(UnitOfTimeEnum value) {
        this.unitOfTimeForLowerBound = value;
    }

    /**
     * Gets the value of the lowerBound property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLowerBound() {
        return lowerBound;
    }

    /**
     * Sets the value of the lowerBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLowerBound(BigInteger value) {
        this.lowerBound = value;
    }

    /**
     * Gets the value of the unitOfTimeForUpperBound property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public UnitOfTimeEnum getUnitOfTimeForUpperBound() {
        return unitOfTimeForUpperBound;
    }

    /**
     * Sets the value of the unitOfTimeForUpperBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfTimeEnum }
     *     
     */
    public void setUnitOfTimeForUpperBound(UnitOfTimeEnum value) {
        this.unitOfTimeForUpperBound = value;
    }

    /**
     * Gets the value of the upperBound property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUpperBound() {
        return upperBound;
    }

    /**
     * Sets the value of the upperBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUpperBound(BigInteger value) {
        this.upperBound = value;
    }

}
