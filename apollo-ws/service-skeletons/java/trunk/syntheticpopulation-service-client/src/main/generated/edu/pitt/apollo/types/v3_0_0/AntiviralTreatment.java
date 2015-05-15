
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for AntiviralTreatment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AntiviralTreatment">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Treatment">
 *       &lt;sequence>
 *         &lt;element name="antiviralId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="antiviralTreatmentEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}AntiviralTreatmentEfficacy" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AntiviralTreatment", propOrder = {
    "antiviralId",
    "antiviralTreatmentEfficacy"
})
public class AntiviralTreatment
    extends Treatment
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String antiviralId;
    @XmlElement(required = true)
    protected List<AntiviralTreatmentEfficacy> antiviralTreatmentEfficacy;

    /**
     * Gets the value of the antiviralId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntiviralId() {
        return antiviralId;
    }

    /**
     * Sets the value of the antiviralId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntiviralId(String value) {
        this.antiviralId = value;
    }

    /**
     * Gets the value of the antiviralTreatmentEfficacy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the antiviralTreatmentEfficacy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAntiviralTreatmentEfficacy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AntiviralTreatmentEfficacy }
     * 
     * 
     */
    public List<AntiviralTreatmentEfficacy> getAntiviralTreatmentEfficacy() {
        if (antiviralTreatmentEfficacy == null) {
            antiviralTreatmentEfficacy = new ArrayList<AntiviralTreatmentEfficacy>();
        }
        return this.antiviralTreatmentEfficacy;
    }

}
