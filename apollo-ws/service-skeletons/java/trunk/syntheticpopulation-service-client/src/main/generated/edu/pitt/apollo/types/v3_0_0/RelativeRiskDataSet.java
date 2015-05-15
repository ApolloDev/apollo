
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for RelativeRiskDataSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelativeRiskDataSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="textualDescription" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="caseCountArray" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseCountArray"/>
 *         &lt;element name="controlCountArray" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseCountArray"/>
 *         &lt;element name="referenceId" type="{http://types.apollo.pitt.edu/v3_0_0/}Reference" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelativeRiskDataSet", propOrder = {
    "textualDescription",
    "caseCountArray",
    "controlCountArray",
    "referenceId"
})
public class RelativeRiskDataSet {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String textualDescription;
    @XmlElement(required = true)
    protected CaseCountArray caseCountArray;
    @XmlElement(required = true)
    protected CaseCountArray controlCountArray;
    protected Reference referenceId;

    /**
     * Gets the value of the textualDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextualDescription() {
        return textualDescription;
    }

    /**
     * Sets the value of the textualDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextualDescription(String value) {
        this.textualDescription = value;
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
     * Gets the value of the controlCountArray property.
     * 
     * @return
     *     possible object is
     *     {@link CaseCountArray }
     *     
     */
    public CaseCountArray getControlCountArray() {
        return controlCountArray;
    }

    /**
     * Sets the value of the controlCountArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseCountArray }
     *     
     */
    public void setControlCountArray(CaseCountArray value) {
        this.controlCountArray = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setReferenceId(Reference value) {
        this.referenceId = value;
    }

}
