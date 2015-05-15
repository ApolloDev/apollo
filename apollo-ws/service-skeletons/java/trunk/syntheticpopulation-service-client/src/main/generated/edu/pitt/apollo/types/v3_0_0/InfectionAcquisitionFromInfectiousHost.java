
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
 * <p>Java class for InfectionAcquisitionFromInfectiousHost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectionAcquisitionFromInfectiousHost">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infectiousHostTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="latentPeriodDuration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="infectiousPeriodDuration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;choice>
 *           &lt;element name="basicReproductionNumbers" type="{http://types.apollo.pitt.edu/v3_0_0/}ReproductionNumber" maxOccurs="unbounded"/>
 *           &lt;element name="beta" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate"/>
 *           &lt;element name="transmissionProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}TransmissionProbability"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectionAcquisitionFromInfectiousHost", propOrder = {
    "infectiousHostTaxonId",
    "latentPeriodDuration",
    "infectiousPeriodDuration",
    "basicReproductionNumbers",
    "beta",
    "transmissionProbability"
})
public class InfectionAcquisitionFromInfectiousHost {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String infectiousHostTaxonId;
    @XmlElement(required = true)
    protected Duration latentPeriodDuration;
    @XmlElement(required = true)
    protected Duration infectiousPeriodDuration;
    protected List<ReproductionNumber> basicReproductionNumbers;
    protected Rate beta;
    protected TransmissionProbability transmissionProbability;

    /**
     * Gets the value of the infectiousHostTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfectiousHostTaxonId() {
        return infectiousHostTaxonId;
    }

    /**
     * Sets the value of the infectiousHostTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfectiousHostTaxonId(String value) {
        this.infectiousHostTaxonId = value;
    }

    /**
     * Gets the value of the latentPeriodDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getLatentPeriodDuration() {
        return latentPeriodDuration;
    }

    /**
     * Sets the value of the latentPeriodDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setLatentPeriodDuration(Duration value) {
        this.latentPeriodDuration = value;
    }

    /**
     * Gets the value of the infectiousPeriodDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getInfectiousPeriodDuration() {
        return infectiousPeriodDuration;
    }

    /**
     * Sets the value of the infectiousPeriodDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setInfectiousPeriodDuration(Duration value) {
        this.infectiousPeriodDuration = value;
    }

    /**
     * Gets the value of the basicReproductionNumbers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the basicReproductionNumbers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBasicReproductionNumbers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReproductionNumber }
     * 
     * 
     */
    public List<ReproductionNumber> getBasicReproductionNumbers() {
        if (basicReproductionNumbers == null) {
            basicReproductionNumbers = new ArrayList<ReproductionNumber>();
        }
        return this.basicReproductionNumbers;
    }

    /**
     * Gets the value of the beta property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setBeta(Rate value) {
        this.beta = value;
    }

    /**
     * Gets the value of the transmissionProbability property.
     * 
     * @return
     *     possible object is
     *     {@link TransmissionProbability }
     *     
     */
    public TransmissionProbability getTransmissionProbability() {
        return transmissionProbability;
    }

    /**
     * Sets the value of the transmissionProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmissionProbability }
     *     
     */
    public void setTransmissionProbability(TransmissionProbability value) {
        this.transmissionProbability = value;
    }

}
