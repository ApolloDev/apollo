
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationStrataDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationStrataDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}ArrayDimensionsDefinition">
 *       &lt;sequence>
 *         &lt;element name="ageRanges" type="{http://types.apollo.pitt.edu/v2_0/}AgeRange" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="genders" type="{http://types.apollo.pitt.edu/v2_0/}Gender" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="diseaseOutcomes" type="{http://types.apollo.pitt.edu/v2_0/}DiseaseOutcome" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationStrataDefinition", propOrder = {
    "ageRanges",
    "genders",
    "diseaseOutcomes"
})
public class PopulationStrataDefinition
    extends ArrayDimensionsDefinition
{

    protected List<AgeRange> ageRanges;
    protected List<Gender> genders;
    protected List<DiseaseOutcome> diseaseOutcomes;

    /**
     * Gets the value of the ageRanges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ageRanges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgeRanges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgeRange }
     * 
     * 
     */
    public List<AgeRange> getAgeRanges() {
        if (ageRanges == null) {
            ageRanges = new ArrayList<AgeRange>();
        }
        return this.ageRanges;
    }

    /**
     * Gets the value of the genders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Gender }
     * 
     * 
     */
    public List<Gender> getGenders() {
        if (genders == null) {
            genders = new ArrayList<Gender>();
        }
        return this.genders;
    }

    /**
     * Gets the value of the diseaseOutcomes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseaseOutcomes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseaseOutcomes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiseaseOutcome }
     * 
     * 
     */
    public List<DiseaseOutcome> getDiseaseOutcomes() {
        if (diseaseOutcomes == null) {
            diseaseOutcomes = new ArrayList<DiseaseOutcome>();
        }
        return this.diseaseOutcomes;
    }

}
