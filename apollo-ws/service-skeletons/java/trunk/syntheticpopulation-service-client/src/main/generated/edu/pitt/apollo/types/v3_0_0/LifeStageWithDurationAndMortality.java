
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LifeStageWithDurationAndMortality complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LifeStageWithDurationAndMortality">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stage" type="{http://types.apollo.pitt.edu/v3_0_0/}DevelopmentalStageEnum"/>
 *         &lt;element name="duration" type="{http://types.apollo.pitt.edu/v3_0_0/}Duration"/>
 *         &lt;choice>
 *           &lt;element name="mortalityRate" type="{http://types.apollo.pitt.edu/v3_0_0/}Rate"/>
 *           &lt;element name="mortalityFunction" type="{http://types.apollo.pitt.edu/v3_0_0/}MortalityFunction"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifeStageWithDurationAndMortality", propOrder = {
    "stage",
    "duration",
    "mortalityRate",
    "mortalityFunction"
})
public class LifeStageWithDurationAndMortality {

    @XmlElement(required = true)
    protected DevelopmentalStageEnum stage;
    @XmlElement(required = true)
    protected Duration duration;
    protected Rate mortalityRate;
    protected MortalityFunction mortalityFunction;

    /**
     * Gets the value of the stage property.
     * 
     * @return
     *     possible object is
     *     {@link DevelopmentalStageEnum }
     *     
     */
    public DevelopmentalStageEnum getStage() {
        return stage;
    }

    /**
     * Sets the value of the stage property.
     * 
     * @param value
     *     allowed object is
     *     {@link DevelopmentalStageEnum }
     *     
     */
    public void setStage(DevelopmentalStageEnum value) {
        this.stage = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the mortalityRate property.
     * 
     * @return
     *     possible object is
     *     {@link Rate }
     *     
     */
    public Rate getMortalityRate() {
        return mortalityRate;
    }

    /**
     * Sets the value of the mortalityRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Rate }
     *     
     */
    public void setMortalityRate(Rate value) {
        this.mortalityRate = value;
    }

    /**
     * Gets the value of the mortalityFunction property.
     * 
     * @return
     *     possible object is
     *     {@link MortalityFunction }
     *     
     */
    public MortalityFunction getMortalityFunction() {
        return mortalityFunction;
    }

    /**
     * Sets the value of the mortalityFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link MortalityFunction }
     *     
     */
    public void setMortalityFunction(MortalityFunction value) {
        this.mortalityFunction = value;
    }

}
