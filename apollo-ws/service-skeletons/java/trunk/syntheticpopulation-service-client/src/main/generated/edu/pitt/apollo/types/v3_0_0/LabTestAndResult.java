
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for LabTestAndResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabTestAndResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="textualName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="loincCode" type="{http://types.apollo.pitt.edu/v3_0_0/}LoincId"/>
 *         &lt;/choice>
 *         &lt;element name="sampleDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabTestAndResult", propOrder = {
    "textualName",
    "loincCode",
    "sampleDate",
    "result"
})
public class LabTestAndResult {

    protected String textualName;
    protected String loincCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sampleDate;
    protected String result;

    /**
     * Gets the value of the textualName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextualName() {
        return textualName;
    }

    /**
     * Sets the value of the textualName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextualName(String value) {
        this.textualName = value;
    }

    /**
     * Gets the value of the loincCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoincCode() {
        return loincCode;
    }

    /**
     * Sets the value of the loincCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoincCode(String value) {
        this.loincCode = value;
    }

    /**
     * Gets the value of the sampleDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSampleDate() {
        return sampleDate;
    }

    /**
     * Sets the value of the sampleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSampleDate(XMLGregorianCalendar value) {
        this.sampleDate = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

}
