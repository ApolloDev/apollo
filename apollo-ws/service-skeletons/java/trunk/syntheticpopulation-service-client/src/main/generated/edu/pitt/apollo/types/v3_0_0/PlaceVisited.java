
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PlaceVisited complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlaceVisited">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="place" type="{http://types.apollo.pitt.edu/v3_0_0/}PlaceEnum"/>
 *           &lt;element name="namedMultiGeometry" type="{http://types.apollo.pitt.edu/v3_0_0/}NamedMultiGeometry"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="frequencyOfVisits" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate" minOccurs="0"/>
 *           &lt;element name="datesOfVisits" type="{http://www.w3.org/2001/XMLSchema}date" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="realDateSpansOfVisits" type="{http://types.apollo.pitt.edu/v3_0_0/}RealDateSpanCategoryDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="realTimeSpansOfVisits" type="{http://types.apollo.pitt.edu/v3_0_0/}RealTimeSpanCategoryDefinition" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "PlaceVisited", propOrder = {
    "place",
    "namedMultiGeometry",
    "frequencyOfVisits",
    "datesOfVisits",
    "realDateSpansOfVisits",
    "realTimeSpansOfVisits"
})
public class PlaceVisited {

    protected PlaceEnum place;
    protected NamedMultiGeometry namedMultiGeometry;
    protected Rate frequencyOfVisits;
    @XmlSchemaType(name = "date")
    protected List<XMLGregorianCalendar> datesOfVisits;
    protected List<RealDateSpanCategoryDefinition> realDateSpansOfVisits;
    protected List<RealTimeSpanCategoryDefinition> realTimeSpansOfVisits;

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
     * Gets the value of the namedMultiGeometry property.
     * 
     * @return
     *     possible object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public NamedMultiGeometry getNamedMultiGeometry() {
        return namedMultiGeometry;
    }

    /**
     * Sets the value of the namedMultiGeometry property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public void setNamedMultiGeometry(NamedMultiGeometry value) {
        this.namedMultiGeometry = value;
    }

    /**
     * Gets the value of the frequencyOfVisits property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getFrequencyOfVisits() {
        return frequencyOfVisits;
    }

    /**
     * Sets the value of the frequencyOfVisits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setFrequencyOfVisits(Rate value) {
        this.frequencyOfVisits = value;
    }

    /**
     * Gets the value of the datesOfVisits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datesOfVisits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatesOfVisits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getDatesOfVisits() {
        if (datesOfVisits == null) {
            datesOfVisits = new ArrayList<XMLGregorianCalendar>();
        }
        return this.datesOfVisits;
    }

    /**
     * Gets the value of the realDateSpansOfVisits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realDateSpansOfVisits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealDateSpansOfVisits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RealDateSpanCategoryDefinition }
     * 
     * 
     */
    public List<RealDateSpanCategoryDefinition> getRealDateSpansOfVisits() {
        if (realDateSpansOfVisits == null) {
            realDateSpansOfVisits = new ArrayList<RealDateSpanCategoryDefinition>();
        }
        return this.realDateSpansOfVisits;
    }

    /**
     * Gets the value of the realTimeSpansOfVisits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realTimeSpansOfVisits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealTimeSpansOfVisits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RealTimeSpanCategoryDefinition }
     * 
     * 
     */
    public List<RealTimeSpanCategoryDefinition> getRealTimeSpansOfVisits() {
        if (realTimeSpansOfVisits == null) {
            realTimeSpansOfVisits = new ArrayList<RealTimeSpanCategoryDefinition>();
        }
        return this.realTimeSpansOfVisits;
    }

}
