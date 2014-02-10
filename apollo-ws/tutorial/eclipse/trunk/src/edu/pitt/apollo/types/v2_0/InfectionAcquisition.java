
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InfectionAcquisition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfectionAcquisition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="pathogenTaxonID" type="{http://types.apollo.pitt.edu/v2_0/}ApolloPathogenCode"/>
 *         &lt;element name="susceptibleHostTaxonID" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId"/>
 *         &lt;choice>
 *           &lt;element name="infectiousHostTaxonID" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId" minOccurs="0"/>
 *           &lt;element name="contaminatedMaterialID" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="basicReproductionNumber" type="{http://types.apollo.pitt.edu/v2_0/}PositiveDouble"/>
 *           &lt;element name="imperialReproductionNumber" type="{http://types.apollo.pitt.edu/v2_0/}ImperialReproductionNumber"/>
 *         &lt;/choice>
 *         &lt;element name="transmissionProbability" type="{http://types.apollo.pitt.edu/v2_0/}ProbabilisticParameterValue" minOccurs="0"/>
 *         &lt;element name="transmissionCoefficient" type="{http://types.apollo.pitt.edu/v2_0/}NumericParameterValue" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfectionAcquisition", propOrder = {
    "pathogenTaxonID",
    "susceptibleHostTaxonID",
    "infectiousHostTaxonID",
    "contaminatedMaterialID",
    "basicReproductionNumber",
    "imperialReproductionNumber",
    "transmissionProbability",
    "transmissionCoefficient"
})
public class InfectionAcquisition
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected ApolloPathogenCode pathogenTaxonID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String susceptibleHostTaxonID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String infectiousHostTaxonID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String contaminatedMaterialID;
    protected Double basicReproductionNumber;
    protected ImperialReproductionNumber imperialReproductionNumber;
    protected ProbabilisticParameterValue transmissionProbability;
    protected NumericParameterValue transmissionCoefficient;

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
     * Gets the value of the susceptibleHostTaxonID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSusceptibleHostTaxonID() {
        return susceptibleHostTaxonID;
    }

    /**
     * Sets the value of the susceptibleHostTaxonID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSusceptibleHostTaxonID(String value) {
        this.susceptibleHostTaxonID = value;
    }

    /**
     * Gets the value of the infectiousHostTaxonID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfectiousHostTaxonID() {
        return infectiousHostTaxonID;
    }

    /**
     * Sets the value of the infectiousHostTaxonID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfectiousHostTaxonID(String value) {
        this.infectiousHostTaxonID = value;
    }

    /**
     * Gets the value of the contaminatedMaterialID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContaminatedMaterialID() {
        return contaminatedMaterialID;
    }

    /**
     * Sets the value of the contaminatedMaterialID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContaminatedMaterialID(String value) {
        this.contaminatedMaterialID = value;
    }

    /**
     * Gets the value of the basicReproductionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBasicReproductionNumber() {
        return basicReproductionNumber;
    }

    /**
     * Sets the value of the basicReproductionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBasicReproductionNumber(Double value) {
        this.basicReproductionNumber = value;
    }

    /**
     * Gets the value of the imperialReproductionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ImperialReproductionNumber }
     *     
     */
    public ImperialReproductionNumber getImperialReproductionNumber() {
        return imperialReproductionNumber;
    }

    /**
     * Sets the value of the imperialReproductionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImperialReproductionNumber }
     *     
     */
    public void setImperialReproductionNumber(ImperialReproductionNumber value) {
        this.imperialReproductionNumber = value;
    }

    /**
     * Gets the value of the transmissionProbability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public ProbabilisticParameterValue getTransmissionProbability() {
        return transmissionProbability;
    }

    /**
     * Sets the value of the transmissionProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameterValue }
     *     
     */
    public void setTransmissionProbability(ProbabilisticParameterValue value) {
        this.transmissionProbability = value;
    }

    /**
     * Gets the value of the transmissionCoefficient property.
     * 
     * @return
     *     possible object is
     *     {@link NumericParameterValue }
     *     
     */
    public NumericParameterValue getTransmissionCoefficient() {
        return transmissionCoefficient;
    }

    /**
     * Sets the value of the transmissionCoefficient property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumericParameterValue }
     *     
     */
    public void setTransmissionCoefficient(NumericParameterValue value) {
        this.transmissionCoefficient = value;
    }

}
