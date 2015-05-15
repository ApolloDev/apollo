
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BayesianNetwork complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BayesianNetwork">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="networkStructure" type="{http://types.apollo.pitt.edu/v3_0_0/}BayesianNetworkStructureType"/>
 *         &lt;element name="CPTs" type="{http://types.apollo.pitt.edu/v3_0_0/}ConditionalProbabilityTable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PPFs" type="{http://types.apollo.pitt.edu/v3_0_0/}PrototypicalProbabilityFunction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="GeNIE_XML" type="{http://types.apollo.pitt.edu/v3_0_0/}GeNIE_XMLType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BayesianNetwork", propOrder = {
    "networkStructure",
    "cpTs",
    "ppFs",
    "geNIEXML"
})
public class BayesianNetwork
    extends ProbabilityDistribution
{

    @XmlElement(required = true)
    protected BayesianNetworkStructureType networkStructure;
    @XmlElement(name = "CPTs")
    protected List<ConditionalProbabilityTable> cpTs;
    @XmlElement(name = "PPFs")
    protected List<PrototypicalProbabilityFunction> ppFs;
    @XmlElement(name = "GeNIE_XML")
    protected GeNIEXMLType geNIEXML;

    /**
     * Gets the value of the networkStructure property.
     * 
     * @return
     *     possible object is
     *     {@link BayesianNetworkStructureType }
     *     
     */
    public BayesianNetworkStructureType getNetworkStructure() {
        return networkStructure;
    }

    /**
     * Sets the value of the networkStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link BayesianNetworkStructureType }
     *     
     */
    public void setNetworkStructure(BayesianNetworkStructureType value) {
        this.networkStructure = value;
    }

    /**
     * Gets the value of the cpTs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cpTs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCPTs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConditionalProbabilityTable }
     * 
     * 
     */
    public List<ConditionalProbabilityTable> getCPTs() {
        if (cpTs == null) {
            cpTs = new ArrayList<ConditionalProbabilityTable>();
        }
        return this.cpTs;
    }

    /**
     * Gets the value of the ppFs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ppFs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPPFs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrototypicalProbabilityFunction }
     * 
     * 
     */
    public List<PrototypicalProbabilityFunction> getPPFs() {
        if (ppFs == null) {
            ppFs = new ArrayList<PrototypicalProbabilityFunction>();
        }
        return this.ppFs;
    }

    /**
     * Gets the value of the geNIEXML property.
     * 
     * @return
     *     possible object is
     *     {@link GeNIEXMLType }
     *     
     */
    public GeNIEXMLType getGeNIEXML() {
        return geNIEXML;
    }

    /**
     * Sets the value of the geNIEXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeNIEXMLType }
     *     
     */
    public void setGeNIEXML(GeNIEXMLType value) {
        this.geNIEXML = value;
    }

}
