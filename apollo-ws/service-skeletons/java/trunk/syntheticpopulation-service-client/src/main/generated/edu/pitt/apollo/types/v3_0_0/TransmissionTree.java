
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransmissionTree complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmissionTree">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="thisCase" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseRecord"/>
 *         &lt;element name="casesThisCaseInfected" type="{http://types.apollo.pitt.edu/v3_0_0/}TransmissionTree" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransmissionTree", propOrder = {
    "thisCase",
    "casesThisCaseInfected"
})
public class TransmissionTree {

    @XmlElement(required = true)
    protected CaseRecord thisCase;
    protected List<TransmissionTree> casesThisCaseInfected;

    /**
     * Gets the value of the thisCase property.
     * 
     * @return
     *     possible object is
     *     {@link CaseRecord }
     *     
     */
    public CaseRecord getThisCase() {
        return thisCase;
    }

    /**
     * Sets the value of the thisCase property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseRecord }
     *     
     */
    public void setThisCase(CaseRecord value) {
        this.thisCase = value;
    }

    /**
     * Gets the value of the casesThisCaseInfected property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the casesThisCaseInfected property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCasesThisCaseInfected().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransmissionTree }
     * 
     * 
     */
    public List<TransmissionTree> getCasesThisCaseInfected() {
        if (casesThisCaseInfected == null) {
            casesThisCaseInfected = new ArrayList<TransmissionTree>();
        }
        return this.casesThisCaseInfected;
    }

}
