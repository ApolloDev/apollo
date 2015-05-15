
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GesParametersForContactAndTransmission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GesParametersForContactAndTransmission">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="seasonalityFunctionParameters" type="{http://types.apollo.pitt.edu/v3_0_0/}SeasonalityFunctionParameters"/>
 *         &lt;element name="infectiousnessParameterSet" type="{http://types.apollo.pitt.edu/v3_0_0/}GesInfectiousnessParameterSet"/>
 *         &lt;element name="contactModelsForSettings" type="{http://types.apollo.pitt.edu/v3_0_0/}ContactModelForSetting" maxOccurs="6" minOccurs="6"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GesParametersForContactAndTransmission", propOrder = {
    "seasonalityFunctionParameters",
    "infectiousnessParameterSet",
    "contactModelsForSettings"
})
public class GesParametersForContactAndTransmission {

    @XmlElement(required = true)
    protected SeasonalityFunctionParameters seasonalityFunctionParameters;
    @XmlElement(required = true)
    protected GesInfectiousnessParameterSet infectiousnessParameterSet;
    @XmlElement(required = true)
    protected List<ContactModelForSetting> contactModelsForSettings;

    /**
     * Gets the value of the seasonalityFunctionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link SeasonalityFunctionParameters }
     *     
     */
    public SeasonalityFunctionParameters getSeasonalityFunctionParameters() {
        return seasonalityFunctionParameters;
    }

    /**
     * Sets the value of the seasonalityFunctionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeasonalityFunctionParameters }
     *     
     */
    public void setSeasonalityFunctionParameters(SeasonalityFunctionParameters value) {
        this.seasonalityFunctionParameters = value;
    }

    /**
     * Gets the value of the infectiousnessParameterSet property.
     * 
     * @return
     *     possible object is
     *     {@link GesInfectiousnessParameterSet }
     *     
     */
    public GesInfectiousnessParameterSet getInfectiousnessParameterSet() {
        return infectiousnessParameterSet;
    }

    /**
     * Sets the value of the infectiousnessParameterSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link GesInfectiousnessParameterSet }
     *     
     */
    public void setInfectiousnessParameterSet(GesInfectiousnessParameterSet value) {
        this.infectiousnessParameterSet = value;
    }

    /**
     * Gets the value of the contactModelsForSettings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactModelsForSettings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactModelsForSettings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactModelForSetting }
     * 
     * 
     */
    public List<ContactModelForSetting> getContactModelsForSettings() {
        if (contactModelsForSettings == null) {
            contactModelsForSettings = new ArrayList<ContactModelForSetting>();
        }
        return this.contactModelsForSettings;
    }

}
