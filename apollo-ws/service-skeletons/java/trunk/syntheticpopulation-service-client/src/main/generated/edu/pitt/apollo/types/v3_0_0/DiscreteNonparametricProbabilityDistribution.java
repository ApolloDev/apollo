
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscreteNonparametricProbabilityDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiscreteNonparametricProbabilityDistribution">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}NonparametricProbabilityDistribution">
 *       &lt;sequence>
 *         &lt;element name="probabilityValuePairs" type="{http://types.apollo.pitt.edu/v3_0_0/}ProbabilityValuePair" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscreteNonparametricProbabilityDistribution", propOrder = {
    "probabilityValuePairs"
})
public class DiscreteNonparametricProbabilityDistribution
    extends NonparametricProbabilityDistribution
{

    @XmlElement(required = true)
    protected List<ProbabilityValuePair> probabilityValuePairs;

    /**
     * Gets the value of the probabilityValuePairs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the probabilityValuePairs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProbabilityValuePairs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProbabilityValuePair }
     * 
     * 
     */
    public List<ProbabilityValuePair> getProbabilityValuePairs() {
        if (probabilityValuePairs == null) {
            probabilityValuePairs = new ArrayList<ProbabilityValuePair>();
        }
        return this.probabilityValuePairs;
    }

}
