
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SeasonalityFunctionParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SeasonalityFunctionParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maximumSeasonalLatitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="minimumSeasonalLatitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="seasonalTemporalOffset" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeasonalityFunctionParameters", propOrder = {
    "maximumSeasonalLatitude",
    "minimumSeasonalLatitude",
    "seasonalTemporalOffset"
})
public class SeasonalityFunctionParameters {

    protected double maximumSeasonalLatitude;
    protected double minimumSeasonalLatitude;
    protected double seasonalTemporalOffset;

    /**
     * Gets the value of the maximumSeasonalLatitude property.
     * 
     */
    public double getMaximumSeasonalLatitude() {
        return maximumSeasonalLatitude;
    }

    /**
     * Sets the value of the maximumSeasonalLatitude property.
     * 
     */
    public void setMaximumSeasonalLatitude(double value) {
        this.maximumSeasonalLatitude = value;
    }

    /**
     * Gets the value of the minimumSeasonalLatitude property.
     * 
     */
    public double getMinimumSeasonalLatitude() {
        return minimumSeasonalLatitude;
    }

    /**
     * Sets the value of the minimumSeasonalLatitude property.
     * 
     */
    public void setMinimumSeasonalLatitude(double value) {
        this.minimumSeasonalLatitude = value;
    }

    /**
     * Gets the value of the seasonalTemporalOffset property.
     * 
     */
    public double getSeasonalTemporalOffset() {
        return seasonalTemporalOffset;
    }

    /**
     * Sets the value of the seasonalTemporalOffset property.
     * 
     */
    public void setSeasonalTemporalOffset(double value) {
        this.seasonalTemporalOffset = value;
    }

}
