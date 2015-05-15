
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EpidemicPeriod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EpidemicPeriod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="startDateDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}EpidemicPeriodBoundaryDefinitionEnum"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endDateDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}EpidemicPeriodBoundaryDefinitionEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EpidemicPeriod", propOrder = {
    "startDate",
    "startDateDefinition",
    "endDate",
    "endDateDefinition"
})
public class EpidemicPeriod {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true)
    protected EpidemicPeriodBoundaryDefinitionEnum startDateDefinition;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected EpidemicPeriodBoundaryDefinitionEnum endDateDefinition;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the startDateDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link EpidemicPeriodBoundaryDefinitionEnum }
     *     
     */
    public EpidemicPeriodBoundaryDefinitionEnum getStartDateDefinition() {
        return startDateDefinition;
    }

    /**
     * Sets the value of the startDateDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link EpidemicPeriodBoundaryDefinitionEnum }
     *     
     */
    public void setStartDateDefinition(EpidemicPeriodBoundaryDefinitionEnum value) {
        this.startDateDefinition = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the endDateDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link EpidemicPeriodBoundaryDefinitionEnum }
     *     
     */
    public EpidemicPeriodBoundaryDefinitionEnum getEndDateDefinition() {
        return endDateDefinition;
    }

    /**
     * Sets the value of the endDateDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link EpidemicPeriodBoundaryDefinitionEnum }
     *     
     */
    public void setEndDateDefinition(EpidemicPeriodBoundaryDefinitionEnum value) {
        this.endDateDefinition = value;
    }

}
