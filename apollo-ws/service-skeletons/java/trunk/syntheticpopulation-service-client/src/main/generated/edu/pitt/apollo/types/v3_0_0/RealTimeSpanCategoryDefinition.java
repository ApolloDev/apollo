
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RealTimeSpanCategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RealTimeSpanCategoryDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CategoryDefinition">
 *       &lt;sequence>
 *         &lt;element name="offsetFromUtcInHours" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="beginningTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="endingTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RealTimeSpanCategoryDefinition", propOrder = {
    "offsetFromUtcInHours",
    "beginningTime",
    "endingTime"
})
public class RealTimeSpanCategoryDefinition
    extends CategoryDefinition
{

    @XmlElement(required = true)
    protected BigInteger offsetFromUtcInHours;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginningTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endingTime;

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
     * Gets the value of the beginningTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginningTime() {
        return beginningTime;
    }

    /**
     * Sets the value of the beginningTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginningTime(XMLGregorianCalendar value) {
        this.beginningTime = value;
    }

    /**
     * Gets the value of the endingTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndingTime() {
        return endingTime;
    }

    /**
     * Sets the value of the endingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndingTime(XMLGregorianCalendar value) {
        this.endingTime = value;
    }

}
