
package edu.pitt.apollo.types.v3_0_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfectiousDiseaseControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectiousDiseaseControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://types.apollo.pitt.edu/v3_0_0/}Location" minOccurs="0"/>
 *         &lt;element name="controlStrategyStartTime" type="{http://types.apollo.pitt.edu/v3_0_0/}TriggerDefinition" maxOccurs="unbounded"/>
 *         &lt;element name="controlStrategyStopTime" type="{http://types.apollo.pitt.edu/v3_0_0/}TriggerDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="controlStrategyResponseDelay" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="controlStrategyStandDownDelay" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectiousDiseaseControlStrategy", propOrder = {
    "description",
    "location",
    "controlStrategyStartTime",
    "controlStrategyStopTime",
    "controlStrategyResponseDelay",
    "controlStrategyStandDownDelay",
    "referenceId"
})
@XmlSeeAlso({
    PlaceClosureControlStrategy.class,
    IndividualTreatmentControlStrategy.class,
    BorderControlStrategy.class,
    CaseQuarantineControlStrategy.class,
    TravelRestrictionControlStrategy.class,
    VectorControlStrategy.class
})
public class InfectiousDiseaseControlStrategy
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected String description;
    protected Location location;
    @XmlElement(required = true)
    protected List<TriggerDefinition> controlStrategyStartTime;
    protected List<TriggerDefinition> controlStrategyStopTime;
    @XmlElement(required = true)
    protected Duration controlStrategyResponseDelay;
    @XmlElement(required = true)
    protected Duration controlStrategyStandDownDelay;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger referenceId;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the controlStrategyStartTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlStrategyStartTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlStrategyStartTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TriggerDefinition }
     * 
     * 
     */
    public List<TriggerDefinition> getControlStrategyStartTime() {
        if (controlStrategyStartTime == null) {
            controlStrategyStartTime = new ArrayList<TriggerDefinition>();
        }
        return this.controlStrategyStartTime;
    }

    /**
     * Gets the value of the controlStrategyStopTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlStrategyStopTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlStrategyStopTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TriggerDefinition }
     * 
     * 
     */
    public List<TriggerDefinition> getControlStrategyStopTime() {
        if (controlStrategyStopTime == null) {
            controlStrategyStopTime = new ArrayList<TriggerDefinition>();
        }
        return this.controlStrategyStopTime;
    }

    /**
     * Gets the value of the controlStrategyResponseDelay property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getControlStrategyResponseDelay() {
        return controlStrategyResponseDelay;
    }

    /**
     * Sets the value of the controlStrategyResponseDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setControlStrategyResponseDelay(Duration value) {
        this.controlStrategyResponseDelay = value;
    }

    /**
     * Gets the value of the controlStrategyStandDownDelay property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getControlStrategyStandDownDelay() {
        return controlStrategyStandDownDelay;
    }

    /**
     * Sets the value of the controlStrategyStandDownDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setControlStrategyStandDownDelay(Duration value) {
        this.controlStrategyStandDownDelay = value;
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
