
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DiseaseOutcomeWithLocationDateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiseaseOutcomeWithLocationDateTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diseaseOutcome" type="{http://types.apollo.pitt.edu/v3_0_0/}DiseaseOutcomeEnum"/>
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="place" type="{http://types.apollo.pitt.edu/v3_0_0/}PlaceEnum" minOccurs="0"/>
 *           &lt;element name="namedPolygon" type="{http://types.apollo.pitt.edu/v3_0_0/}NamedMultiGeometry" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseOutcomeWithLocationDateTime", propOrder = {
    "diseaseOutcome",
    "dateTime",
    "place",
    "namedPolygon"
})
public class DiseaseOutcomeWithLocationDateTime {

    @XmlElement(required = true)
    protected DiseaseOutcomeEnum diseaseOutcome;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;
    protected PlaceEnum place;
    protected NamedMultiGeometry namedPolygon;

    /**
     * Gets the value of the diseaseOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public DiseaseOutcomeEnum getDiseaseOutcome() {
        return diseaseOutcome;
    }

    /**
     * Sets the value of the diseaseOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiseaseOutcomeEnum }
     *     
     */
    public void setDiseaseOutcome(DiseaseOutcomeEnum value) {
        this.diseaseOutcome = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the place property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceEnum }
     *     
     */
    public PlaceEnum getPlace() {
        return place;
    }

    /**
     * Sets the value of the place property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceEnum }
     *     
     */
    public void setPlace(PlaceEnum value) {
        this.place = value;
    }

    /**
     * Gets the value of the namedPolygon property.
     * 
     * @return
     *     possible object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public NamedMultiGeometry getNamedPolygon() {
        return namedPolygon;
    }

    /**
     * Sets the value of the namedPolygon property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public void setNamedPolygon(NamedMultiGeometry value) {
        this.namedPolygon = value;
    }

}
