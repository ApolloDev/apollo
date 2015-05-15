
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ContaminationAcquisition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContaminationAcquisition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abioticEcosystemElement" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="pathogenTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;choice>
 *           &lt;element name="infectiousHostSource" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *           &lt;element name="contaminatedSource" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;/choice>
 *         &lt;element name="contaminationProbability" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilisticParameter"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContaminationAcquisition", propOrder = {
    "abioticEcosystemElement",
    "pathogenTaxonId",
    "infectiousHostSource",
    "contaminatedSource",
    "contaminationProbability"
})
public class ContaminationAcquisition {

    @XmlElement(required = true)
    protected AbioticEcosystemEnum abioticEcosystemElement;
    @XmlElement(required = true)
    protected ApolloPathogenCode pathogenTaxonId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String infectiousHostSource;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String contaminatedSource;
    @XmlElement(required = true)
    protected ProbabilisticParameter contaminationProbability;

    /**
     * Gets the value of the abioticEcosystemElement property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getAbioticEcosystemElement() {
        return abioticEcosystemElement;
    }

    /**
     * Sets the value of the abioticEcosystemElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setAbioticEcosystemElement(AbioticEcosystemEnum value) {
        this.abioticEcosystemElement = value;
    }

    /**
     * Gets the value of the pathogenTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getPathogenTaxonId() {
        return pathogenTaxonId;
    }

    /**
     * Sets the value of the pathogenTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setPathogenTaxonId(ApolloPathogenCode value) {
        this.pathogenTaxonId = value;
    }

    /**
     * Gets the value of the infectiousHostSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfectiousHostSource() {
        return infectiousHostSource;
    }

    /**
     * Sets the value of the infectiousHostSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfectiousHostSource(String value) {
        this.infectiousHostSource = value;
    }

    /**
     * Gets the value of the contaminatedSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContaminatedSource() {
        return contaminatedSource;
    }

    /**
     * Sets the value of the contaminatedSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContaminatedSource(String value) {
        this.contaminatedSource = value;
    }

    /**
     * Gets the value of the contaminationProbability property.
     * 
     * @return
     *     possible object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public ProbabilisticParameter getContaminationProbability() {
        return contaminationProbability;
    }

    /**
     * Sets the value of the contaminationProbability property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProbabilisticParameter }
     *     
     */
    public void setContaminationProbability(ProbabilisticParameter value) {
        this.contaminationProbability = value;
    }

}
