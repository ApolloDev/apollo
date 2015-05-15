
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationTreatmentCensusData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationTreatmentCensusData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CensusData">
 *       &lt;sequence>
 *         &lt;element name="treatmentCensusDataCells" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationTreatmentCensusDataCell" maxOccurs="unbounded"/>
 *         &lt;element name="exceptionSubpopulations" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationTreatmentCensusData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationTreatmentCensusData", propOrder = {
    "treatmentCensusDataCells",
    "exceptionSubpopulations"
})
public class PopulationTreatmentCensusData
    extends CensusData
{

    @XmlElement(required = true)
    protected List<PopulationTreatmentCensusDataCell> treatmentCensusDataCells;
    protected List<PopulationTreatmentCensusData> exceptionSubpopulations;

    /**
     * Gets the value of the treatmentCensusDataCells property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatmentCensusDataCells property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatmentCensusDataCells().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationTreatmentCensusDataCell }
     * 
     * 
     */
    public List<PopulationTreatmentCensusDataCell> getTreatmentCensusDataCells() {
        if (treatmentCensusDataCells == null) {
            treatmentCensusDataCells = new ArrayList<PopulationTreatmentCensusDataCell>();
        }
        return this.treatmentCensusDataCells;
    }

    /**
     * Gets the value of the exceptionSubpopulations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exceptionSubpopulations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExceptionSubpopulations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationTreatmentCensusData }
     * 
     * 
     */
    public List<PopulationTreatmentCensusData> getExceptionSubpopulations() {
        if (exceptionSubpopulations == null) {
            exceptionSubpopulations = new ArrayList<PopulationTreatmentCensusData>();
        }
        return this.exceptionSubpopulations;
    }

}
