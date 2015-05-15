
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseCount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseCount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caseDefinitionsIncluded" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseDefinitionEnum" maxOccurs="unbounded"/>
 *         &lt;choice>
 *           &lt;element name="totalCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *           &lt;element name="caseCountArray" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseCountArray"/>
 *         &lt;/choice>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseCount", propOrder = {
    "countTitle",
    "caseDefinitionsIncluded",
    "totalCount",
    "caseCountArray",
    "referenceId"
})
public class CaseCount {

    @XmlElement(required = true)
    protected String countTitle;
    @XmlElement(required = true)
    protected List<CaseDefinitionEnum> caseDefinitionsIncluded;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger totalCount;
    protected CaseCountArray caseCountArray;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the countTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountTitle() {
        return countTitle;
    }

    /**
     * Sets the value of the countTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountTitle(String value) {
        this.countTitle = value;
    }

    /**
     * Gets the value of the caseDefinitionsIncluded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the caseDefinitionsIncluded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCaseDefinitionsIncluded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseDefinitionEnum }
     * 
     * 
     */
    public List<CaseDefinitionEnum> getCaseDefinitionsIncluded() {
        if (caseDefinitionsIncluded == null) {
            caseDefinitionsIncluded = new ArrayList<CaseDefinitionEnum>();
        }
        return this.caseDefinitionsIncluded;
    }

    /**
     * Gets the value of the totalCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the value of the totalCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalCount(BigInteger value) {
        this.totalCount = value;
    }

    /**
     * Gets the value of the caseCountArray property.
     * 
     * @return
     *     possible object is
     *     {@link CaseCountArray }
     *     
     */
    public CaseCountArray getCaseCountArray() {
        return caseCountArray;
    }

    /**
     * Sets the value of the caseCountArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseCountArray }
     *     
     */
    public void setCaseCountArray(CaseCountArray value) {
        this.caseCountArray = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReferenceId(BigInteger value) {
        this.referenceId = value;
    }

}
