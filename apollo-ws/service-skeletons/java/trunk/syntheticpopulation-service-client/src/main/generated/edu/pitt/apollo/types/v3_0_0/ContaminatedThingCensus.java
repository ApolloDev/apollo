
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContaminatedThingCensus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContaminatedThingCensus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}Census">
 *       &lt;sequence>
 *         &lt;element name="kindOfThing" type="{http://types.apollo.pitt.edu/v3_0_0/}AbioticEcosystemEnum"/>
 *         &lt;element name="pathogen" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloPathogenCode"/>
 *         &lt;element name="censusData" type="{http://types.apollo.pitt.edu/v3_0_0/}ContaminatedThingCensusData"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContaminatedThingCensus", propOrder = {
    "kindOfThing",
    "pathogen",
    "censusData"
})
public class ContaminatedThingCensus
    extends Census
{

    @XmlElement(required = true)
    protected AbioticEcosystemEnum kindOfThing;
    @XmlElement(required = true)
    protected ApolloPathogenCode pathogen;
    @XmlElement(required = true)
    protected ContaminatedThingCensusData censusData;

    /**
     * Gets the value of the kindOfThing property.
     * 
     * @return
     *     possible object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public AbioticEcosystemEnum getKindOfThing() {
        return kindOfThing;
    }

    /**
     * Sets the value of the kindOfThing property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbioticEcosystemEnum }
     *     
     */
    public void setKindOfThing(AbioticEcosystemEnum value) {
        this.kindOfThing = value;
    }

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
     * Gets the value of the censusData property.
     * 
     * @return
     *     possible object is
     *     {@link ContaminatedThingCensusData }
     *     
     */
    public ContaminatedThingCensusData getCensusData() {
        return censusData;
    }

    /**
     * Sets the value of the censusData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContaminatedThingCensusData }
     *     
     */
    public void setCensusData(ContaminatedThingCensusData value) {
        this.censusData = value;
    }

}
