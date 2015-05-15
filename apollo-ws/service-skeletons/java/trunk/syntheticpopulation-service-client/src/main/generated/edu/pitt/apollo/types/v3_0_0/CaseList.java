
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CaseList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="speciesOfCases" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="caseRecords" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseRecord" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseList", propOrder = {
    "speciesOfCases",
    "caseRecords"
})
public class CaseList {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesOfCases;
    @XmlElement(required = true)
    protected List<CaseRecord> caseRecords;

    /**
     * Gets the value of the speciesOfCases property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesOfCases() {
        return speciesOfCases;
    }

    /**
     * Sets the value of the speciesOfCases property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesOfCases(String value) {
        this.speciesOfCases = value;
    }

    /**
     * Gets the value of the caseRecords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the caseRecords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCaseRecords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseRecord }
     * 
     * 
     */
    public List<CaseRecord> getCaseRecords() {
        if (caseRecords == null) {
            caseRecords = new ArrayList<CaseRecord>();
        }
        return this.caseRecords;
    }

}
