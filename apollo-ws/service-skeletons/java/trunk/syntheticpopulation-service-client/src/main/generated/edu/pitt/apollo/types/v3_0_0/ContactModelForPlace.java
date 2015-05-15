
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactModelForPlace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactModelForPlace">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}ContactModelForSetting">
 *       &lt;sequence>
 *         &lt;element name="placeType" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="r_place" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate"/>
 *         &lt;element name="P_Pn_group" type="{http://types.apollo.pitt.edu/v3_0_0/}WithinGroupTransmissionProbability" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="probSymptomaticIndividualAbsent" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="symptomaticAbsenteeMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="probSeverelyInfectedIndividualAbsent" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="severelyInfectedAbsenteeMultiplier" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *         &lt;element name="q_social" type="{http://types.apollo.pitt.edu/v3_0_0/}PositiveDouble"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactModelForPlace", propOrder = {
    "placeType",
    "rPlace",
    "pPnGroup",
    "probSymptomaticIndividualAbsent",
    "symptomaticAbsenteeMultiplier",
    "probSeverelyInfectedIndividualAbsent",
    "severelyInfectedAbsenteeMultiplier",
    "qSocial"
})
public class ContactModelForPlace
    extends ContactModelForSetting
{

    @XmlElement(required = true)
    protected AbioticEcosystemEnum placeType;
    @XmlElement(name = "r_place", required = true)
    protected Rate rPlace;
    @XmlElement(name = "P_Pn_group")
    protected List<WithinGroupTransmissionProbability> pPnGroup;
    protected double probSymptomaticIndividualAbsent;
    protected double symptomaticAbsenteeMultiplier;
    protected double probSeverelyInfectedIndividualAbsent;
    protected double severelyInfectedAbsenteeMultiplier;
    @XmlElement(name = "q_social")
    protected double qSocial;

    /**
     * Gets the value of the placeType property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getPlaceType() {
        return placeType;
    }

    /**
     * Sets the value of the placeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setPlaceType(AbioticEcosystemEnum value) {
        this.placeType = value;
    }

    /**
     * Gets the value of the rPlace property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getRPlace() {
        return rPlace;
    }

    /**
     * Sets the value of the rPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setRPlace(Rate value) {
        this.rPlace = value;
    }

    /**
     * Gets the value of the pPnGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pPnGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPPnGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WithinGroupTransmissionProbability }
     * 
     * 
     */
    public List<WithinGroupTransmissionProbability> getPPnGroup() {
        if (pPnGroup == null) {
            pPnGroup = new ArrayList<WithinGroupTransmissionProbability>();
        }
        return this.pPnGroup;
    }

    /**
     * Gets the value of the probSymptomaticIndividualAbsent property.
     * 
     */
    public double getProbSymptomaticIndividualAbsent() {
        return probSymptomaticIndividualAbsent;
    }

    /**
     * Sets the value of the probSymptomaticIndividualAbsent property.
     * 
     */
    public void setProbSymptomaticIndividualAbsent(double value) {
        this.probSymptomaticIndividualAbsent = value;
    }

    /**
     * Gets the value of the symptomaticAbsenteeMultiplier property.
     * 
     */
    public double getSymptomaticAbsenteeMultiplier() {
        return symptomaticAbsenteeMultiplier;
    }

    /**
     * Sets the value of the symptomaticAbsenteeMultiplier property.
     * 
     */
    public void setSymptomaticAbsenteeMultiplier(double value) {
        this.symptomaticAbsenteeMultiplier = value;
    }

    /**
     * Gets the value of the probSeverelyInfectedIndividualAbsent property.
     * 
     */
    public double getProbSeverelyInfectedIndividualAbsent() {
        return probSeverelyInfectedIndividualAbsent;
    }

    /**
     * Sets the value of the probSeverelyInfectedIndividualAbsent property.
     * 
     */
    public void setProbSeverelyInfectedIndividualAbsent(double value) {
        this.probSeverelyInfectedIndividualAbsent = value;
    }

    /**
     * Gets the value of the severelyInfectedAbsenteeMultiplier property.
     * 
     */
    public double getSeverelyInfectedAbsenteeMultiplier() {
        return severelyInfectedAbsenteeMultiplier;
    }

    /**
     * Sets the value of the severelyInfectedAbsenteeMultiplier property.
     * 
     */
    public void setSeverelyInfectedAbsenteeMultiplier(double value) {
        this.severelyInfectedAbsenteeMultiplier = value;
    }

    /**
     * Gets the value of the qSocial property.
     * 
     */
    public double getQSocial() {
        return qSocial;
    }

    /**
     * Sets the value of the qSocial property.
     * 
     */
    public void setQSocial(double value) {
        this.qSocial = value;
    }

}
