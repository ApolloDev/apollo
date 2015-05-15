
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BioticEcosystem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BioticEcosystem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="communities" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensus" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BioticEcosystem", propOrder = {
    "communities"
})
public class BioticEcosystem {

    @XmlElement(required = true)
    protected List<PopulationInfectionAndImmunityCensus> communities;

    /**
     * Gets the value of the communities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationInfectionAndImmunityCensus }
     * 
     * 
     */
    public List<PopulationInfectionAndImmunityCensus> getCommunities() {
        if (communities == null) {
            communities = new ArrayList<PopulationInfectionAndImmunityCensus>();
        }
        return this.communities;
    }

}
