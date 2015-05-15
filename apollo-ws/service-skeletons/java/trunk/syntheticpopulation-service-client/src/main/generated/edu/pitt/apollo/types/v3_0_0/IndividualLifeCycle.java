
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
 * <p>Java class for IndividualLifeCycle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualLifeCycle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="speciesId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId"/>
 *         &lt;element name="lifeStagesWithDurationsAndMortalities" type="{http://types.apollo.pitt.edu/v3_0_0/}LifeStageWithDurationAndMortality" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualLifeCycle", propOrder = {
    "speciesId",
    "lifeStagesWithDurationsAndMortalities"
})
public class IndividualLifeCycle {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String speciesId;
    @XmlElement(required = true)
    protected List<LifeStageWithDurationAndMortality> lifeStagesWithDurationsAndMortalities;

    /**
     * Gets the value of the speciesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesId() {
        return speciesId;
    }

    /**
     * Sets the value of the speciesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesId(String value) {
        this.speciesId = value;
    }

    /**
     * Gets the value of the lifeStagesWithDurationsAndMortalities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lifeStagesWithDurationsAndMortalities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLifeStagesWithDurationsAndMortalities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LifeStageWithDurationAndMortality }
     * 
     * 
     */
    public List<LifeStageWithDurationAndMortality> getLifeStagesWithDurationsAndMortalities() {
        if (lifeStagesWithDurationsAndMortalities == null) {
            lifeStagesWithDurationsAndMortalities = new ArrayList<LifeStageWithDurationAndMortality>();
        }
        return this.lifeStagesWithDurationsAndMortalities;
    }

}
