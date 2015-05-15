
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContaminatedThingCensusData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContaminatedThingCensusData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CensusData">
 *       &lt;sequence>
 *         &lt;element name="fractionContaminated" type="{http://types.apollo.pitt.edu/v3_0_0/}Fraction"/>
 *         &lt;element name="exceptionSublocations" type="{http://types.apollo.pitt.edu/v3_0_0/}ContaminatedThingCensusData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContaminatedThingCensusData", propOrder = {
    "fractionContaminated",
    "exceptionSublocations"
})
public class ContaminatedThingCensusData
    extends CensusData
{

    protected double fractionContaminated;
    protected List<ContaminatedThingCensusData> exceptionSublocations;

    /**
     * Gets the value of the fractionContaminated property.
     * 
     */
    public double getFractionContaminated() {
        return fractionContaminated;
    }

    /**
     * Sets the value of the fractionContaminated property.
     * 
     */
    public void setFractionContaminated(double value) {
        this.fractionContaminated = value;
    }

    /**
     * Gets the value of the exceptionSublocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exceptionSublocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExceptionSublocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContaminatedThingCensusData }
     * 
     * 
     */
    public List<ContaminatedThingCensusData> getExceptionSublocations() {
        if (exceptionSublocations == null) {
            exceptionSublocations = new ArrayList<ContaminatedThingCensusData>();
        }
        return this.exceptionSublocations;
    }

}
