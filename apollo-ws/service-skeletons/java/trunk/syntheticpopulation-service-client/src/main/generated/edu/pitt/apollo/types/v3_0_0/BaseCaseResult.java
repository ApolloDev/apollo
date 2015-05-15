
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseCaseResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseCaseResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="decisionAlternatives" type="{http://types.apollo.pitt.edu/v3_0_0/}DecisionAlternative" maxOccurs="unbounded"/>
 *         &lt;element name="expectedUtilities" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseCaseResult", propOrder = {
    "decisionAlternatives",
    "expectedUtilities"
})
public class BaseCaseResult {

    @XmlElement(required = true)
    protected List<DecisionAlternative> decisionAlternatives;
    @XmlElement(type = Double.class)
    protected List<Double> expectedUtilities;

    /**
     * Gets the value of the decisionAlternatives property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the decisionAlternatives property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDecisionAlternatives().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DecisionAlternative }
     * 
     * 
     */
    public List<DecisionAlternative> getDecisionAlternatives() {
        if (decisionAlternatives == null) {
            decisionAlternatives = new ArrayList<DecisionAlternative>();
        }
        return this.decisionAlternatives;
    }

    /**
     * Gets the value of the expectedUtilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expectedUtilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpectedUtilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getExpectedUtilities() {
        if (expectedUtilities == null) {
            expectedUtilities = new ArrayList<Double>();
        }
        return this.expectedUtilities;
    }

}
