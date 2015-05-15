
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationInfectionAndImmunityCensusData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PopulationInfectionAndImmunityCensusData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}CensusData">
 *       &lt;sequence>
 *         &lt;element name="censusDataCells" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensusDataCell" maxOccurs="unbounded"/>
 *         &lt;element name="exceptionSubpopulations" type="{http://types.apollo.pitt.edu/v3_0_0/}PopulationInfectionAndImmunityCensusData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfectionAndImmunityCensusData", propOrder = {
    "censusDataCells",
    "exceptionSubpopulations"
})
public class PopulationInfectionAndImmunityCensusData
    extends CensusData
{

    @XmlElement(required = true)
    protected List<PopulationInfectionAndImmunityCensusDataCell> censusDataCells;
    protected List<PopulationInfectionAndImmunityCensusData> exceptionSubpopulations;

    /**
     * Gets the value of the censusDataCells property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the censusDataCells property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCensusDataCells().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PopulationInfectionAndImmunityCensusDataCell }
     * 
     * 
     */
    public List<PopulationInfectionAndImmunityCensusDataCell> getCensusDataCells() {
        if (censusDataCells == null) {
            censusDataCells = new ArrayList<PopulationInfectionAndImmunityCensusDataCell>();
        }
        return this.censusDataCells;
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
     * {@link PopulationInfectionAndImmunityCensusData }
     * 
     * 
     */
    public List<PopulationInfectionAndImmunityCensusData> getExceptionSubpopulations() {
        if (exceptionSubpopulations == null) {
            exceptionSubpopulations = new ArrayList<PopulationInfectionAndImmunityCensusData>();
        }
        return this.exceptionSubpopulations;
    }

}
