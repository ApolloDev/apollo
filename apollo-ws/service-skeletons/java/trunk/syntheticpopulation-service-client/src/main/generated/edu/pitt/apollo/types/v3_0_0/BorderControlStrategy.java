
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BorderControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BorderControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="probabilityEntryDenied" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *         &lt;element name="probabilityExitDenied" type="{http://types.apollo.pitt.edu/v3_0_0/}Probability"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BorderControlStrategy", propOrder = {
    "probabilityEntryDenied",
    "probabilityExitDenied"
})
public class BorderControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    protected double probabilityEntryDenied;
    protected double probabilityExitDenied;

    /**
     * Gets the value of the probabilityEntryDenied property.
     * 
     */
    public double getProbabilityEntryDenied() {
        return probabilityEntryDenied;
    }

    /**
     * Sets the value of the probabilityEntryDenied property.
     * 
     */
    public void setProbabilityEntryDenied(double value) {
        this.probabilityEntryDenied = value;
    }

    /**
     * Gets the value of the probabilityExitDenied property.
     * 
     */
    public double getProbabilityExitDenied() {
        return probabilityExitDenied;
    }

    /**
     * Sets the value of the probabilityExitDenied property.
     * 
     */
    public void setProbabilityExitDenied(double value) {
        this.probabilityExitDenied = value;
    }

}
