
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contamination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contamination">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="pathogenTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="abioticEcosystemElement" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="contaminationDuration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;element name="contaminationAcquisition" type="{http://types.apollo.pitt.edu/v3_0_0/}ContaminationAcquisition" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contamination", propOrder = {
    "pathogenTaxonId",
    "abioticEcosystemElement",
    "contaminationDuration",
    "contaminationAcquisition"
})
public class Contamination
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected ApolloPathogenCode pathogenTaxonId;
    @XmlElement(required = true)
    protected AbioticEcosystemEnum abioticEcosystemElement;
    @XmlElement(required = true)
    protected Duration contaminationDuration;
    @XmlElement(required = true)
    protected List<ContaminationAcquisition> contaminationAcquisition;

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
     * Gets the value of the contaminationDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getContaminationDuration() {
        return contaminationDuration;
    }

    /**
     * Sets the value of the contaminationDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setContaminationDuration(Duration value) {
        this.contaminationDuration = value;
    }

    /**
     * Gets the value of the contaminationAcquisition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contaminationAcquisition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContaminationAcquisition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContaminationAcquisition }
     * 
     * 
     */
    public List<ContaminationAcquisition> getContaminationAcquisition() {
        if (contaminationAcquisition == null) {
            contaminationAcquisition = new ArrayList<ContaminationAcquisition>();
        }
        return this.contaminationAcquisition;
    }

}
