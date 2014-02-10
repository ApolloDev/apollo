
package edu.pitt.apollo.types.v2_0;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualTreatmentControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualTreatmentControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v2_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="individualTreatment" type="{http://types.apollo.pitt.edu/v2_0/}Treatment"/>
 *         &lt;element name="supplySchedule" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" maxOccurs="unbounded"/>
 *         &lt;element name="administrationCapacity" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" maxOccurs="unbounded"/>
 *         &lt;element name="targetPopulationsAndPrioritizations" type="{http://types.apollo.pitt.edu/v2_0/}ControlStrategyTargetPopulationsAndPrioritization"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualTreatmentControlStrategy", propOrder = {
    "individualTreatment",
    "supplySchedule",
    "administrationCapacity",
    "targetPopulationsAndPrioritizations"
})
@XmlSeeAlso({
    RingIndividualTreatmentControlStrategy.class
})
public class IndividualTreatmentControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlElement(required = true)
    protected Treatment individualTreatment;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected List<BigInteger> supplySchedule;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected List<BigInteger> administrationCapacity;
    @XmlElement(required = true)
    protected ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritizations;

    /**
     * Gets the value of the individualTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link Treatment }
     *     
     */
    public Treatment getIndividualTreatment() {
        return individualTreatment;
    }

    /**
     * Sets the value of the individualTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Treatment }
     *     
     */
    public void setIndividualTreatment(Treatment value) {
        this.individualTreatment = value;
    }

    /**
     * Gets the value of the supplySchedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplySchedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplySchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getSupplySchedule() {
        if (supplySchedule == null) {
            supplySchedule = new ArrayList<BigInteger>();
        }
        return this.supplySchedule;
    }

    /**
     * Gets the value of the administrationCapacity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the administrationCapacity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdministrationCapacity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getAdministrationCapacity() {
        if (administrationCapacity == null) {
            administrationCapacity = new ArrayList<BigInteger>();
        }
        return this.administrationCapacity;
    }

    /**
     * Gets the value of the targetPopulationsAndPrioritizations property.
     * 
     * @return
     *     possible object is
     *     {@link ControlStrategyTargetPopulationsAndPrioritization }
     *     
     */
    public ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
        return targetPopulationsAndPrioritizations;
    }

    /**
     * Sets the value of the targetPopulationsAndPrioritizations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlStrategyTargetPopulationsAndPrioritization }
     *     
     */
    public void setTargetPopulationsAndPrioritizations(ControlStrategyTargetPopulationsAndPrioritization value) {
        this.targetPopulationsAndPrioritizations = value;
    }

}
