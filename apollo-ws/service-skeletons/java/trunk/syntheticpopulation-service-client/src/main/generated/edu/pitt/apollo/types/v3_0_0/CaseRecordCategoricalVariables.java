
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseRecordCategoricalVariables complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaseRecordCategoricalVariables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="someWayToIdentifyOutbreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categoricalVariablesAndCategories" type="{http://types.apollo.pitt.edu/v3_0_0/}CaseRecordCategoricalVariable" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseRecordCategoricalVariables", propOrder = {
    "someWayToIdentifyOutbreak",
    "categoricalVariablesAndCategories"
})
public class CaseRecordCategoricalVariables {

    @XmlElement(required = true)
    protected String someWayToIdentifyOutbreak;
    @XmlElement(required = true)
    protected List<CaseRecordCategoricalVariable> categoricalVariablesAndCategories;

    /**
     * Gets the value of the someWayToIdentifyOutbreak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSomeWayToIdentifyOutbreak() {
        return someWayToIdentifyOutbreak;
    }

    /**
     * Sets the value of the someWayToIdentifyOutbreak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSomeWayToIdentifyOutbreak(String value) {
        this.someWayToIdentifyOutbreak = value;
    }

    /**
     * Gets the value of the categoricalVariablesAndCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoricalVariablesAndCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoricalVariablesAndCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseRecordCategoricalVariable }
     * 
     * 
     */
    public List<CaseRecordCategoricalVariable> getCategoricalVariablesAndCategories() {
        if (categoricalVariablesAndCategories == null) {
            categoricalVariablesAndCategories = new ArrayList<CaseRecordCategoricalVariable>();
        }
        return this.categoricalVariablesAndCategories;
    }

}
