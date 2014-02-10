
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Infection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Infection">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="pathogenTaxonID" type="{http://types.apollo.pitt.edu/v2_0/}ApolloPathogenCode"/>
 *         &lt;element name="hostTaxonID" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId"/>
 *         &lt;element name="infectiousPeriodDuration" type="{http://types.apollo.pitt.edu/v2_0/}NumericParameterValue"/>
 *         &lt;element name="infectiousness" type="{http://types.apollo.pitt.edu/v2_0/}NumericParameterValue" minOccurs="0"/>
 *         &lt;element name="latentPeriodDuration" type="{http://types.apollo.pitt.edu/v2_0/}NumericParameterValue"/>
 *         &lt;element name="infectionAcquisition" type="{http://types.apollo.pitt.edu/v2_0/}InfectionAcquisition" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Infection", propOrder = {
    "pathogenTaxonID",
    "hostTaxonID",
    "infectiousPeriodDuration",
    "infectiousness",
    "latentPeriodDuration",
    "infectionAcquisition"
})
public class Infection
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected ApolloPathogenCode pathogenTaxonID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String hostTaxonID;
    @XmlElement(required = true)
    protected NumericParameterValue infectiousPeriodDuration;
    protected NumericParameterValue infectiousness;
    @XmlElement(required = true)
    protected NumericParameterValue latentPeriodDuration;
    @XmlElement(required = true)
    protected List<InfectionAcquisition> infectionAcquisition;

    /**
     * Gets the value of the pathogenTaxonID property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getPathogenTaxonID() {
        return pathogenTaxonID;
    }

    /**
     * Sets the value of the pathogenTaxonID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setPathogenTaxonID(ApolloPathogenCode value) {
        this.pathogenTaxonID = value;
    }

    /**
     * Gets the value of the hostTaxonID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostTaxonID() {
        return hostTaxonID;
    }

    /**
     * Sets the value of the hostTaxonID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostTaxonID(String value) {
        this.hostTaxonID = value;
    }

    /**
     * Gets the value of the infectiousPeriodDuration property.
     * 
     * @return
     *     possible object is
     *     {@link NumericParameterValue }
     *     
     */
    public NumericParameterValue getInfectiousPeriodDuration() {
        return infectiousPeriodDuration;
    }

    /**
     * Sets the value of the infectiousPeriodDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericParameterValue }
     *     
     */
    public void setInfectiousPeriodDuration(NumericParameterValue value) {
        this.infectiousPeriodDuration = value;
    }

    /**
     * Gets the value of the infectiousness property.
     * 
     * @return
     *     possible object is
     *     {@link NumericParameterValue }
     *     
     */
    public NumericParameterValue getInfectiousness() {
        return infectiousness;
    }

    /**
     * Sets the value of the infectiousness property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericParameterValue }
     *     
     */
    public void setInfectiousness(NumericParameterValue value) {
        this.infectiousness = value;
    }

    /**
     * Gets the value of the latentPeriodDuration property.
     * 
     * @return
     *     possible object is
     *     {@link NumericParameterValue }
     *     
     */
    public NumericParameterValue getLatentPeriodDuration() {
        return latentPeriodDuration;
    }

    /**
     * Sets the value of the latentPeriodDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericParameterValue }
     *     
     */
    public void setLatentPeriodDuration(NumericParameterValue value) {
        this.latentPeriodDuration = value;
    }

    /**
     * Gets the value of the infectionAcquisition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infectionAcquisition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfectionAcquisition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectionAcquisition }
     * 
     * 
     */
    public List<InfectionAcquisition> getInfectionAcquisition() {
        if (infectionAcquisition == null) {
            infectionAcquisition = new ArrayList<InfectionAcquisition>();
        }
        return this.infectionAcquisition;
    }

}
