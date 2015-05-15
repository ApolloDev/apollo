
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
 * <p>Java class for Infection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Infection">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ApolloIndexableItem">
 *       &lt;sequence>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="hostTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="infectiousDiseases" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDisease" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="infectionAcquisitionsFromInfectiousHosts" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectionAcquisitionFromInfectiousHost" maxOccurs="unbounded"/>
 *           &lt;element name="infectionAcquisitionsFromContaminatedThings" type="{http://types.apollo.pitt.edu/v3_0_0/}InfectionAcquisitionFromContaminatedThing" maxOccurs="unbounded"/>
 *         &lt;/choice>
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
    "pathogen",
    "hostTaxonId",
    "infectiousDiseases",
    "infectionAcquisitionsFromInfectiousHosts",
    "infectionAcquisitionsFromContaminatedThings"
})
public class Infection
    extends ApolloIndexableItem
{

    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String hostTaxonId;
    protected List<InfectiousDisease> infectiousDiseases;
    protected List<InfectionAcquisitionFromInfectiousHost> infectionAcquisitionsFromInfectiousHosts;
    protected List<InfectionAcquisitionFromContaminatedThing> infectionAcquisitionsFromContaminatedThings;

    /**
     * Gets the value of the pathogen property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public ApolloPathogenCode getPathogen() {
        return pathogen;
    }

    /**
     * Sets the value of the pathogen property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloPathogenCode }
     *     
     */
    public void setPathogen(ApolloPathogenCode value) {
        this.pathogen = value;
    }

    /**
     * Gets the value of the hostTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostTaxonId() {
        return hostTaxonId;
    }

    /**
     * Sets the value of the hostTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostTaxonId(String value) {
        this.hostTaxonId = value;
    }

    /**
     * Gets the value of the infectiousDiseases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infectiousDiseases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfectiousDiseases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectiousDisease }
     * 
     * 
     */
    public List<InfectiousDisease> getInfectiousDiseases() {
        if (infectiousDiseases == null) {
            infectiousDiseases = new ArrayList<InfectiousDisease>();
        }
        return this.infectiousDiseases;
    }

    /**
     * Gets the value of the infectionAcquisitionsFromInfectiousHosts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infectionAcquisitionsFromInfectiousHosts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfectionAcquisitionsFromInfectiousHosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectionAcquisitionFromInfectiousHost }
     * 
     * 
     */
    public List<InfectionAcquisitionFromInfectiousHost> getInfectionAcquisitionsFromInfectiousHosts() {
        if (infectionAcquisitionsFromInfectiousHosts == null) {
            infectionAcquisitionsFromInfectiousHosts = new ArrayList<InfectionAcquisitionFromInfectiousHost>();
        }
        return this.infectionAcquisitionsFromInfectiousHosts;
    }

    /**
     * Gets the value of the infectionAcquisitionsFromContaminatedThings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infectionAcquisitionsFromContaminatedThings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfectionAcquisitionsFromContaminatedThings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfectionAcquisitionFromContaminatedThing }
     * 
     * 
     */
    public List<InfectionAcquisitionFromContaminatedThing> getInfectionAcquisitionsFromContaminatedThings() {
        if (infectionAcquisitionsFromContaminatedThings == null) {
            infectionAcquisitionsFromContaminatedThings = new ArrayList<InfectionAcquisitionFromContaminatedThing>();
        }
        return this.infectionAcquisitionsFromContaminatedThings;
    }

}
