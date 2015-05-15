
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
 * <p>Java class for DrugTreatment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DrugTreatment">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Treatment">
 *       &lt;sequence>
 *         &lt;element name="drugId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="drugTreatmentEfficacy" type="{http://types.apollo.pitt.edu/v3_0_0/}DrugTreatmentEfficacyForSimulatorConfiguration" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugTreatment", propOrder = {
    "drugId",
    "drugTreatmentEfficacy"
})
public class DrugTreatment
    extends Treatment
{

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String drugId;
    @XmlElement(required = true)
    protected List<DrugTreatmentEfficacyForSimulatorConfiguration> drugTreatmentEfficacy;

    /**
     * Gets the value of the drugId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugId() {
        return drugId;
    }

    /**
     * Sets the value of the drugId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugId(String value) {
        this.drugId = value;
    }

    /**
     * Gets the value of the drugTreatmentEfficacy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drugTreatmentEfficacy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrugTreatmentEfficacy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrugTreatmentEfficacyForSimulatorConfiguration }
     * 
     * 
     */
    public List<DrugTreatmentEfficacyForSimulatorConfiguration> getDrugTreatmentEfficacy() {
        if (drugTreatmentEfficacy == null) {
            drugTreatmentEfficacy = new ArrayList<DrugTreatmentEfficacyForSimulatorConfiguration>();
        }
        return this.drugTreatmentEfficacy;
    }

}
