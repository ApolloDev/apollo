
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RealDateSpanCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RealDateSpanCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="offsetFromUtcInHours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="firstDay" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="lastDay" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RealDateSpanCategoryDefinition", propOrder = {
    "offsetFromUtcInHours",
    "firstDay",
    "lastDay"
})
public class RealDateSpanCategoryDefinition
    extends CategoryDefinition
{

    protected BigInteger offsetFromUtcInHours;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar firstDay;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastDay;

    /**
     * Gets the value of the offsetFromUtcInHours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOffsetFromUtcInHours() {
        return offsetFromUtcInHours;
    }

    /**
     * Sets the value of the offsetFromUtcInHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOffsetFromUtcInHours(BigInteger value) {
        this.offsetFromUtcInHours = value;
    }

    /**
     * Gets the value of the firstDay property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFirstDay() {
        return firstDay;
    }

    /**
     * Sets the value of the firstDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFirstDay(XMLGregorianCalendar value) {
        this.firstDay = value;
    }

    /**
     * Gets the value of the lastDay property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastDay() {
        return lastDay;
    }

    /**
     * Sets the value of the lastDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastDay(XMLGregorianCalendar value) {
        this.lastDay = value;
    }

}
